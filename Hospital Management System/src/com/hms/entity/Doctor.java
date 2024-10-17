package com.hms.entity;

import java.util.Objects;

public class Doctor {

    private int doctorId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String contactNumber;

    public Doctor() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Doctor(String firstName, String lastName, String specialization, String contactNumber) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactNumber, doctorId, firstName, lastName, specialization);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Doctor other = (Doctor) obj;
        return Objects.equals(contactNumber, other.contactNumber) && doctorId == other.doctorId
                && Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
                && Objects.equals(specialization, other.specialization);
    }

    @Override
    public String toString() {
        return "Doctor [doctorId=" + doctorId + ", firstName=" + firstName + ", lastName=" + lastName
                + ", specialization=" + specialization + ", contactNumber=" + contactNumber + "]";
    }

}
