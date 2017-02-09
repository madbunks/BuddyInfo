package addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

/**
 * Created by richardcarson3 on 2/2/2017.
 */
@RestController
public class AddressBookController {
    @Autowired
    private AddressBookRepository bookRepository;
    @Autowired
    private BuddyInfoRepository buddyRepository;

/*    @Autowired
    public AddressBookController(AddressBookRepository bookRepository, BuddyInfoRepository buddyRepository) {
        this.bookRepository = bookRepository;
    }*/

    @RequestMapping("/new")
    public AddressBook addAddressBook(@RequestParam(value="name", required=true) String name, Model model) {
        AddressBook book = new AddressBook(name);
        bookRepository.save(book);

        return book;
    }

    @RequestMapping("/add")
    public BuddyInfo addBuddyInfo(@RequestParam(value="addressBookID", required=true) long addressBookID, @RequestParam(value="name", required=true) String name, @RequestParam(value="address", required=true) String address, Model model) {
        AddressBook book = bookRepository.findOne(addressBookID);
        BuddyInfo buddy = new BuddyInfo(name, address);
        book.addBuddy(buddy);
        bookRepository.save(book);

        return buddy;
    }

    @RequestMapping("/remove")
    public void removeBuddyInfo(@RequestParam(value="addressBookID", required=true) long addressBookID, @RequestParam(value="buddyInfoID", required=true) long buddyInfoID, Model model) {
        AddressBook book = bookRepository.findOne(addressBookID);
        BuddyInfo buddy = buddyRepository.findOne(buddyInfoID);
        book.removeBuddy(buddy);
        bookRepository.save(book);
    }

    @RequestMapping("/get")
    public AddressBook addAddressBook(@RequestParam(value="addressBookID", required=true) long addressBookID, Model model) {
        AddressBook book = bookRepository.findOne(addressBookID);
        return book;
    }
}
