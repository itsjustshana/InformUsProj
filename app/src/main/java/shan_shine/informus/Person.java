package shan_shine.informus;

import java.text.NumberFormat;

/**
 * Created by Shanakay on 3/24/2015.
 */
public class Person {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private char sex;


    public Person(String firstName, String lastName, String email, String phone, char sex )
    {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.sex = sex;

    }

    public String getEmail()
    {
        return this.email;
    }

}
