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
        addressBook = this.restTemplate.getForObject(url("new?name=Test"), AddressBook.class);
        buddyInfo = this.restTemplate.getForObject(url("add?addressBookID="+addressBook.getId()+"&name=test&address=test"), BuddyInfo.class);
    }

    @Test
    public void testNew() {
        Assert.assertNotNull(this.restTemplate.getForObject(url("new?name=Test"), AddressBook.class));
    }

    @Test
    public void testAdd() {
        Assert.assertNotNull(this.restTemplate.getForObject(url(
                "add?addressBookID="+addressBook.getId()+"&name=test&address=test"),
                BuddyInfo.class));
        Assert.assertEquals(2, this.restTemplate.getForObject(url("get?addressBookID="+addressBook.getId()), AddressBook.class).getBuddies().size());
    }

    @Test
    public void testRemove() {
        this.restTemplate.getForObject(url(
                "remove?addressBookID="+addressBook.getId()+"&buddyInfoID="+buddyInfo.getId()), String.class);
        Assert.assertEquals(0, this.restTemplate.getForObject(url("get"), AddressBook.class).getBuddies().size());
    }

    private String url(String postfix) {
        String url = String.format("http://localhost:%d/%s", port, postfix);
        System.out.println("Using "+url);

        return url;
    }
}