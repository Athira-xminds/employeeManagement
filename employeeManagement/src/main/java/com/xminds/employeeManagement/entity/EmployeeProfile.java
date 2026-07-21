package com.xminds.employeeManagement.entity;

import jakarta.persistence.*;


@Entity@Table(name = "employee_profile")public class EmployeeProfile {

    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    private String address;

    private String phoneNumber;

    private String dateOfBirth;
    private Long id;

    public EmployeeProfile() {
    }

    public EmployeeProfile(Long profileId,
                           String address,
                           String phoneNumber,
                           String dateOfBirth) {

        this.profileId = profileId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

// Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
}