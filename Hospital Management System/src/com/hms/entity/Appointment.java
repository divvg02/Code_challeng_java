package com.hms.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Appointment {
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDate appointmentDate;
    private String description;

    public Appointment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Appointment(Patient patient, Doctor doctor, LocalDate appointmentDate, String description) {
        super();
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.description = description;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentDate, appointmentId, description, doctor, patient);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Appointment other = (Appointment) obj;
        return Objects.equals(appointmentDate, other.appointmentDate) && appointmentId == other.appointmentId
                && Objects.equals(description, other.description) && Objects.equals(doctor, other.doctor)
                && Objects.equals(patient, other.patient);
    }

    @Override
    public String toString() {
        return "Appointment [appointmentId=" + appointmentId + ", patient=" + patient + ", doctor=" + doctor
                + ", appointmentDate=" + appointmentDate + ", description=" + description + "]";
    }

}
