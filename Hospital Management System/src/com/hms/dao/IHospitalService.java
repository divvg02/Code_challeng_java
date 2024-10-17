package com.hms.dao;

import java.util.List;

import com.hms.entity.Appointment;
import com.hms.entity.Patient;
import com.hms.exception.PatientNumberNotFoundException;

public interface IHospitalService {
    Appointment getAppointmentById(int appointmentId);

    List<Appointment> getAppointmentsForPatient(int patientId) throws PatientNumberNotFoundException;

    List<Appointment> getAppointmentsForDoctor(int doctorId) throws PatientNumberNotFoundException;

    boolean scheduleAppointment(Appointment appointment);

    boolean updateAppointment(Appointment appointment);

    boolean cancelAppointment(int appointmentId);

    Patient findPatientById(int patientId) throws PatientNumberNotFoundException;

}


