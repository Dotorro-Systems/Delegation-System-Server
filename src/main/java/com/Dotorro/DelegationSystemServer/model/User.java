package com.Dotorro.DelegationSystemServer.model;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String hashedPassword;
    private String phone;
    private String email;
    private UserRole role;
    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;

    public User() {

    }

    public User(String firstName, String lastName, String hashedPassword, String phone, String email, UserRole role, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashedPassword;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.department = department;
    }

    public User(Long id, String firstName, String lastName, String hashedPassword, String phone, String email, UserRole role, Department department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashedPassword;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
