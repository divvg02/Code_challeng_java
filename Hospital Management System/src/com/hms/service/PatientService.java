package com.hms.service;

import com.hms.dao.IHospitalService;
import com.hms.entity.Patient;
import com.hms.exception.PatientNumberNotFoundException;

public class PatientService {

    private IHospitalService hospitalService;

    public Patient findPatientById(int patientId) {
        try {
            Patient patient = hospitalService.findPatientById(patientId);

            if (patient != null) {
                return patient;
            } else {
                throw new PatientNumberNotFoundException("Patient not found with ID: " + patientId);
            }
        } catch (PatientNumberNotFoundException e) {
            System.out.println("Patient not found: " + e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}