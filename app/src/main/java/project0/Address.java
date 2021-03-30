package project0;

public class Address {

    private String street;
    private String city;
    private String state;
    private String zip;

    public Address() {}

    public Address(String street, String city, String state, String zip) {

        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;

    }

    public String toString() {

        return this.street + ", " + this.city + ", " + this.state + ", " + this.zip;

    }

    public String getStreet() {

        return this.street;

    }

    public void setStreet(String street) {

        this.street = street;

    }

    public String getCity() {

        return this.city;

    }

    public void setCity(String city) {

        this.city = city;

    }

    public String getState() {

        return this.state;

    }

    public void setState(String state) {

        this.state = state;

    }

    public String getZip() {

        return this.zip;

    }

    public void setZip(String zip) {

        this.zip = zip;

    }
    
} // end class