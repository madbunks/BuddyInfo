package addressbook;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by richardcarson3 on 2/2/2017.
 */
@Entity
public class AddressBook {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    String name;

    @OneToMany(cascade=CascadeType.ALL)
    Set<BuddyInfo> buddies;

    protected  AddressBook() {
        buddies = new HashSet<>();
    }

    public AddressBook(String name) {
        this();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BuddyInfo> getBuddies() {
        return buddies;
    }

    public void setBuddies(Set<BuddyInfo> buddies) {
        this.buddies = buddies;
    }

    public void addBuddy(BuddyInfo buddyInfo) {
        buddies.add(buddyInfo);
    }

    public void removeBuddy(BuddyInfo buddyInfo) {
        buddies.remove(buddyInfo);
    }
}
