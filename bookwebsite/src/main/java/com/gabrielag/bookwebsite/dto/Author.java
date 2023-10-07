package com.gabrielag.bookwebsite.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 *
 * @author Gabriela Gutierrez
 */

// Class represents blueprint for creating 'Author' objects
public class Author {
    /**
     * Private variables = Instance variables.
     * Instance Variables.: variable specific to a certain object.
     * Use of encapsulation through these private instance variables
     * to enclose the data within them.
     * Control of Access or modification through public getter and setter methods
     * to protect the integrity of data.
     */
    private int id;
    // @NotBlank is used as validation constraints for the class fields
    @NotBlank(message = "Please input first name!")
    private String firstName;
    @NotBlank(message = "Please input last name!")
    private String lastName;
    @NotBlank(message = "Please input author bio!")
    private String description;


    // Getters & Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Enables comparison of two 'Author' objects for equality based on their attributes.
    // This is essential when working with collection of objects.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (id != author.id) return false;
        if (!Objects.equals(firstName, author.firstName)) return false;
        if (!Objects.equals(lastName, author.lastName)) return false;
        return Objects.equals(description, author.description);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
