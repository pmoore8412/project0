package project0;

public class Person implements Comparable<Person> {

    private String firstName;
    private String midName;
    private String lastName;
    private String phoneNumber;
    private Address address;

    public Person() {}

    public Person(String phoneNumber, String firstName, String midName, String lastName, Address address) {

        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.address = address;

    }

    public String toString() {

        return this.firstName + " " + isMiddle() + " " + this.address.toString() + " " + displayPhoneNumber();

    }

    private String isMiddle() {

        if (this.midName != null)
            return this.midName + " " + this.lastName;
        else
            return this.lastName;

    }

    private String displayPhoneNumber() {

        String splitIt = this.phoneNumber;
        String temp1 = "";
        String temp2 = "";
        String temp3 = "";

        temp1 = splitIt.substring(0, 3);
        temp2 = splitIt.substring(3, 6);
        temp3 = splitIt.substring(6, 10);

        return "(" + temp1 + ")-" + temp2 + "-" + temp3;

    }

    public int compareTo(Person person) {

        return this.lastName.compareTo(person.lastName);

    }

    public String getFirstName() {

        return this.firstName;

    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;

    }

    public String getMidName() {

        return this.midName;

    }

    public void setMidName(String midName) {

        this.midName = midName;

    }

    public String getLastName() {

        return this.lastName;

    }

    public void setLastName(String lastName) {

        this.lastName = lastName;

    }

    public String getPhoneNumber() {

        return this.phoneNumber;

    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;

    }

    public Address getAddress() {

        return this.address;

    }

    public void setAddress(Address address) {

        this.address = address;

    }
    
} // end class