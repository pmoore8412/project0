/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package project0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Phonebook {

    public static ArrayList<Person> phonebook = new ArrayList<>();
    public static Scanner input = new Scanner(System.in);
    public static Scanner stringInput = new Scanner(System.in);
    public static Scanner yahNah = new Scanner(System.in);
    public static Connection conn;

    public static void main(String[] args) {

        boolean killswitch = false;
        int choice;
        Person contact;

        try {

            conn = DriverManager.getConnection("jdbc:postgresql://localhost:8080/rolodex", "rolodex", "D3@dp00!");
            Statement stm = conn.createStatement();
            String selectSQL = "select * from contacts";
            ResultSet rst = stm.executeQuery(selectSQL);
    
            while(rst.next()) {
                Address contactAddress = new Address(rst.getString("street"), rst.getString("city"), rst.getString("state_name"), rst.getString("zip"));
                if (rst.getString("middle_name").equals("null"))
                    contact = new Person(rst.getString("phone"), rst.getString("first_name"), null, rst.getString("last_name"), contactAddress);
                else
                    contact = new Person(rst.getString("phone"), rst.getString("first_name"), rst.getString("middle_name"), rst.getString("last_name"), contactAddress);
    
                phonebook.add(contact);
           } // end while

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to load contacts.");
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Well this was embarrassing");
            }
        }

        welcomeMessage();

        while (killswitch == false) {
            listChoices();
            choice = input.nextInt();
            actionOptions(choice, input);

            System.out.println("");

            if (choice == 0)
                killswitch = true;
        }

        input.close();
        stringInput.close();
        yahNah.close();


        System.out.println("Program incompleate");

    } // end main

    public static void welcomeMessage() {

        System.out.println("Welcome to your personal digital Roladex Phonebook");
        System.out.println("");
        System.out.println("");

    } // end welcome

    public static void listChoices() {

        System.out.println("Here is the list of options you can chose from: ");
        System.out.println("");
        System.out.println("1: add a new entry into the roladex");
        System.out.println("2: remove an entry");
        System.out.println("3: update an entry");
        System.out.println("4: search by first name");
        System.out.println("5: search by last name");
        System.out.println("6: display all contacts");
        System.out.println("0: to end");

    } // end choice list

    public static void actionOptions(int choice, Scanner input) {

        switch (choice) {
            case 1: // add entry
                addEntry();
                break;
            case 2: // remove entry
                removeEntry();
                break;
            case 3: // update entry
                updateEntry();
                break;
            case 4: // search by first name
                searchByFirst();
                break;
            case 5: // search by last name
                searchByLast();
                break;
            case 6: // list all
                listEntries();
                break;
            case 0:
                System.out.println("Program terminated");
                break;
            default:
                System.out.println("invalid input, please try again");
        } // end switch

    } // end options

    public static void addEntry() {

        String entry;
        String splitName;
        //Scanner stringInput = new Scanner(System.in);

        System.out.println("Please enter the the record you would like to add to the phone book in the follwoing format: ");
		System.out.println("First Name Last Name, Street address, City, State, Zip code, Phone Number");
        entry = stringInput.nextLine();

        String[] temp = entry.split(", ");

        splitName = temp[0];

        String[] temp2 = splitName.split(" ");

        String midName = null;

        if (temp.length > 2)
            for (int i = 1; i < temp2.length - 1; i++)
                if (midName == null)
                    midName = temp2[i];
                else
                    midName += " " + temp2[i];

        System.out.println(midName);

        Address tempAddress = new Address(temp[1], temp[2], temp[3], temp[4]);
        Person tempPerson = new Person(temp[5], temp2[0], midName, temp2[temp2.length - 1], tempAddress);

        phonebook.add(tempPerson);

        String insertSQL = "insert into contacts (phone, first_name, middle_name, last_name, street, city, state_name, zip) values ('"+ temp[5] + "', '" + temp2[0] + 
        "', '" + midName + "', '" + temp2[temp2.length - 1] + "', '" + temp[1] + "', '" + temp[2] + "', '" + temp[3] + "', '" + temp[4] + "'); commit;";
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:8080/rolodex", "rolodex", "D3@dp00!");
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(insertSQL);
            ps.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add contact to database.");
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Well this was embarrassing");
            }
        }

        System.out.println("");
        System.out.println("The entry: ");
        System.out.println(phonebook.get(phonebook.size() - 1));
        System.out.println("Has been successfully added to the phone book");

    } // end add

    public static void removeEntry() {

        String lookUp;
        int index = 0;
        //Scanner toRemove = new Scanner(System.in);

        if (phonebook.isEmpty()) {
            System.out.println("No entries to remove. You have removed them all or none are added.");
            System.out.println("Please and an entry before trying to remove one.");
        }
        else {
            System.out.println("Inter the phone number of the entry you wish to remove in the following format:");
            System.out.println("0123456789");

            lookUp = stringInput.nextLine();

            for(Person p : phonebook)
                if (p.getPhoneNumber().equals(lookUp)) {
                    index = phonebook.indexOf(p);
                    System.out.println(phonebook.get(index) + " Has been removed"); 
                }
            
            phonebook.remove(index);
            
            String removeSQL = "delete from contacts where phone = '" + lookUp +"'; commit;";
            try {
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:8080/rolodex", "rolodex", "D3@dp00!");
                conn.setAutoCommit(false);
                PreparedStatement ps = conn.prepareStatement(removeSQL);
                ps.execute();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to remove contact from database.");
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Well this was embarrassing");
                }
            }
            

            
        }


    } // end remove

    public static void updateEntry() {

        String lookUp;
        String yesNo;
        PreparedStatement ps;
        Person updatePerson = new Person();
        Address updateAddress = new Address();
        String[] tempP = new String[4];
        String[] tempA = new String[4];
        int index = 0;

        if (phonebook.isEmpty()) {
            System.out.println("No entries to update.");
            System.out.println("Please and an entry before trying to remove one.");
        }
        else {
            System.out.println("Enter the phone number of the entry you wish to update in the following format:");
            System.out.println("0123456789");

            lookUp = stringInput.nextLine();

            for(Person p : phonebook)
                if(p.getPhoneNumber().equals(lookUp))
                    index = phonebook.indexOf(p);

            System.out.println(phonebook.get(index));

            updatePerson = phonebook.get(index);
            updateAddress = updatePerson.getAddress();

            tempP[0] = updatePerson.getFirstName();
            tempP[1] = updatePerson.getMidName();
            tempP[2] = updatePerson.getLastName();
            tempP[3] = updatePerson.getPhoneNumber();

            tempA[0] = updateAddress.getStreet();
            tempA[1] = updateAddress.getCity();
            tempA[2] = updateAddress.getState();
            tempA[3] = updateAddress.getZip();

            System.out.println("Would you like to change the First Name (y/n)");
            yesNo = yahNah.nextLine();
            if (yesNo.equals("y") || yesNo.equals("Y")) {
                System.out.println("Please enter the new first name");
                tempP[0] = stringInput.nextLine();
                updatePerson.setFirstName(tempP[0]);
            }

            System.out.println("Would you like to change the Middle Name (y/n)");
            yesNo = yahNah.nextLine();
            if (yesNo.equals("y") || yesNo.equals("Y")) {
                System.out.println("Please enter the new middle name");
                tempP[1] = stringInput.nextLine();
                updatePerson.setMidName(tempP[1]);
            }

            System.out.println("Would you like to change the Last Name (y/n)");
            yesNo = yahNah.nextLine();
            if (yesNo.equals("y") || yesNo.equals("Y")) {
                System.out.println("Please enter the new last name");
                tempP[2] = stringInput.nextLine();
                updatePerson.setLastName(tempP[2]);
            }

            System.out.println("Would you like to change the phone number (y/n)");
            yesNo = yahNah.nextLine();
            if (yesNo.equals("y") || yesNo.equals("Y")) {
                System.out.println("Please enter the new phone number");
                tempP[3] = stringInput.nextLine();
                updatePerson.setPhoneNumber(tempP[3]);
            }

            System.out.println("Would you like to change the street address (y/n)");
            yesNo = yahNah.nextLine();
            if (yesNo.equals("y") || yesNo.equals("Y")) {
                System.out.println("Please enter the new street address");
                tempA[0] = stringInput.nextLine();
                updateAddress.setStreet(tempA[0]);
            }

            System.out.println("Would you like to change the city (y/n)");
            yesNo = yahNah.nextLine();
            if (yesNo.equals("y") || yesNo.equals("Y")) {
                System.out.println("Please enter the new city");
                tempA[1] = stringInput.nextLine();
                updateAddress.setCity(tempA[1]);
            }

            System.out.println("Would you like to change the state (y/n)");
            yesNo = yahNah.nextLine();
            if (yesNo.equals("y") || yesNo.equals("Y")) {
                System.out.println("Please enter the new state");
                tempA[2] = stringInput.nextLine();
                updateAddress.setState(tempA[2]);
            }

            System.out.println("Would you like to change the zip code (y/n)");
            yesNo = yahNah.nextLine();
            if (yesNo.equals("y") || yesNo.equals("Y")) {
                System.out.println("Please enter the street address");
                tempA[3] = stringInput.nextLine();
                updateAddress.setZip(tempA[3]);
            }

            updatePerson.setAddress(updateAddress);

            phonebook.set(index, updatePerson);

            String updateSQL = "update contacts set phone = '" + tempP[3] +"', first_name = '" + tempP[0] + "', middle_name = '" + tempP[1] + "', last_name = '" + tempP[2] +
            "', street = '" + tempA[0] + "', city = '" + tempA[1] + "', state_name = '" + tempA[2] + "', zip = '" + tempA[3] + "' where phone = '" + lookUp + "'; commit;";
            try {
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:8080/rolodex", "rolodex", "D3@dp00!");
                conn.setAutoCommit(false);
                ps = conn.prepareStatement(updateSQL);
                ps.execute();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to update contact.");
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Well this was embarrassing");
                }
            }


            System.out.println("the entry at index " + index + " has been successfully updated");
            System.out.println(phonebook.get(index));

        }


    } // end update

    public static void searchByFirst() {

        String first;
        int index = 0;

        if (phonebook.isEmpty())
            System.out.println("No entries found. Please add an entry first.");
        else {

            System.out.println("Please enter in the first name of the contact you are looking for:");
            first = stringInput.nextLine();

            System.out.println("Here is the list of your contacts with the first name of " + first + ":");
            System.out.println("");

            for(Person p : phonebook)
                if(p.getFirstName().equals(first)) {
                    index = phonebook.indexOf(p);
                    System.out.println(phonebook.get(index));
                }

        }

    } // end search by first name

    public static void searchByLast() {

        String last;
        int index = 0;

        if (phonebook.isEmpty())
            System.out.println("No entries found. Please add an entry first.");
        else {

            System.out.println("Please enter in the first name of the contact you are looking for:");
            last = stringInput.nextLine();

            System.out.println("Here is the list of your contacts with the first name of " + last + ":");
            System.out.println("");

            for(Person p : phonebook)
                if(p.getLastName().equals(last)) {
                    index = phonebook.indexOf(p);
                    System.out.println(phonebook.get(index));
                }

        }

    } // end search by last name

    public static void listEntries() {

        if (phonebook.isEmpty())
            System.out.println("No entries found. Please add an entry first.");
        else {
            System.out.println("Here is the list of your contacts:");
            System.out.println("");
            phonebook.forEach(Person -> System.out.println(Person));
        }


    } // end list entries

    public Object getGreeting() {
        return null;
    }
}
