package jun.projavawebapp;

import java.time.Instant;
import java.time.MonthDay;
import java.util.Date;

public class Contact implements Comparable<Contact> {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private MonthDay birthDay;
    private Instant dateCreated;

    public Contact() {

    }

    public Contact(String firstName, String lastName, String phoneNumber,
                   String address, MonthDay birthDay, Instant dateCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDay = birthDay;
        this.dateCreated = dateCreated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MonthDay getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(MonthDay birthDay) {
        this.birthDay = birthDay;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getOldDateCreated() {
        return new Date(this.dateCreated.toEpochMilli());
    }

    @Override
    public int compareTo(Contact o) {
        int result = lastName.compareTo(o.lastName);
        if (result == 0) {
            return firstName.compareTo(o.firstName);
        }
        return result;
    }
}
