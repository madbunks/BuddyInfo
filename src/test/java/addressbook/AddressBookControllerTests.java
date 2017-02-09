package addressbook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookControllerTests {
    @Autowired AddressBookRepository bookRepository;
    @Autowired BuddyInfoRepository buddyRepository;

    private AddressBook addressBook;
    private BuddyInfo buddyInfo;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        addressBook = new AddressBook("Test");
        BuddyInfo buddyInfo = new BuddyInfo("Name", "Address");
        addressBook.addBuddy(buddyInfo);
        bookRepository.save(addressBook);
    }

    @Test
    public void testNew() {
        Assert.assertNotNull(this.restTemplate.getForObject(url("new"), AddressBook.class));
    }

    @Test
    public void testAdd() {
        Assert.assertNotNull(this.restTemplate.getForObject(url(
                "add?addressBookID"+addressBook.getId()+"&name=test&address=test"),
                BuddyInfo.class).getId());
        Assert.assertEquals(2, this.restTemplate.getForObject(url("get"), AddressBook.class).getBuddies().size());
    }

    @Test
    public void testRemove() {
        this.restTemplate.getForObject(url(
                "remove?addressBookID"+addressBook.getId()+"&buddyInfoID="+buddyInfo.getId()), null);
        this.restTemplate.getForObject(url(
                "add?addressBookID"+addressBook.getId()+"&name=test&address=test"), null);
        Assert.assertEquals(0, this.restTemplate.getForObject(url("get"), AddressBook.class).getBuddies().size());
    }

    private String url(String postfix) {
        return String.format("http://localhost:%d/%s", port, postfix);
    }
}