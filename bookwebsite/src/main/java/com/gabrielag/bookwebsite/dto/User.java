package com.gabrielag.bookwebsite.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

/**
 *
 * @author Gabriela Gutierrez
 */

public class User {
    /**
     * Private variables = Instance variables.
     * Instance Variables.: variable specific to a certain object.
     * Use of encapsulation through these private instance variables
     * to enclose the data within them.
     * Control of Access or modification through public getter and setter methods
     * to protect the integrity of data.
     */
    private int id;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Address is required")
    private String address;
    @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number")
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Enables comparison of two 'Order' objects for equality based on their attributes.
    // This is essential when working with collection of objects.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!Objects.equals(firstName, user.firstName)) return false;
        if (!Objects.equals(lastName, user.lastName)) return false;
        if (!Objects.equals(address, user.address)) return false;
        return Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
