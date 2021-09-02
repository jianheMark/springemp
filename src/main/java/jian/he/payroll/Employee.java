package jian.he.payroll;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Employee {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
//    private String name;
    private String firstName;
    private String lastName;

    public Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    private String role;




    public Employee() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getRole() {
        return this.role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        String[] part = name.split(" ");
        this.firstName = part[0];
        this.lastName = part[1];
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Employee))
            return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.firstName, employee.firstName)
                && Objects.equals(this.lastName, employee.lastName)
                && Objects.equals(this.role, employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName,this.lastName, this.role);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id
                + ", firstName='" + this.firstName + '\''
                + ", lastName='" + this.lastName
                +'\'' +  ", role='" + this.role + '\'' + '}';
    }
}

