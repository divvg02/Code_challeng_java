package com.hms.main;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.hms.dao.HospitalServiceImpl;
import com.hms.entity.Appointment;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.exception.PatientNumberNotFoundException;
import com.hms.util.DBUtil;

public class MainModule {

    private static HospitalServiceImpl hospitalService = new HospitalServiceImpl();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            DBUtil.createConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean exit = false;

        while (!exit) {
            System.out.println("----------- Hospital Management System -----------");
            System.out.println("1. Insert Patient Details");
            System.out.println("2. Insert Doctor Details");
            System.out.println("3. Get Appointment by ID");
            System.out.println("4. Get Appointments for Patient");
            System.out.println("5. Get Appointments for Doctor");
            System.out.println("6. Schedule Appointment");
            System.out.println("7. Update Appointment");
            System.out.println("8. Cancel Appointment");
            System.out.println("9. Find Patient by ID");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    insertPatient();
                    break;
                case 2:
                    insertDoctor();
                    break;
                case 3:
                    getAppointmentById();
                    break;
                case 4:
                    getAppointmentsForPatient();
                    break;
                case 5:
                    getAppointmentsForDoctor();
                    break;
                case 6:
                    scheduleAppointment();
                    break;
                case 7:
                    updateAppointment();
                    break;
                case 8:
                    cancelAppointment();
                    break;
                case 9:
                    findPatientById();
                    break;
                case 10:
                    exit = true;
                    System.out.println("Exiting the application!!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void insertPatient() {
        try {
            System.out.print("Enter Patient's First Name: ");
            String firstName = scanner.next();
            System.out.print("Enter Patient's Last Name: ");
            String lastName = scanner.next();
            System.out.print("Enter Patient's Date of Birth (YYYY-MM-DD): ");
            String dateString = scanner.next();
            LocalDate dateOfBirth = LocalDate.parse(dateString);
            scanner.nextLine();

            System.out.print("Enter Patient's Gender: ");
            String gender = scanner.next();
            System.out.print("Enter Patient's Contact Number: ");
            String contactNumber = scanner.next();
            scanner.nextLine();
            System.out.print("Enter Patient's Address: ");
            String address = scanner.nextLine();

            Patient patient = new Patient(firstName, lastName, dateOfBirth, gender, contactNumber, address);

            boolean success = hospitalService.insertPatient(patient);

            if (success) {
                System.out.println("Patient inserted successfully.");
            } else {
                System.out.println("Failed to insert the patient.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void insertDoctor() {
        try {
            System.out.print("Enter Doctor's First Name: ");
            String firstName = scanner.next();
            System.out.print("Enter Doctor's Last Name: ");
            String lastName = scanner.next();
            System.out.print("Enter Doctor's Specialization: ");
            String specialization = scanner.next();
            scanner.nextLine();
            System.out.print("Enter Doctor's Contact Number: ");
            String contactNumber = scanner.next();

            Doctor doctor = new Doctor(firstName, lastName, specialization, contactNumber);

            boolean success = hospitalService.insertDoctor(doctor);

            if (success) {
                System.out.println("Doctor inserted successfully.");
            } else {
                System.out.println("Failed to insert the doctor.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private static void getAppointmentById() {
        try {
            System.out.print("Enter Appointment ID: ");
            int appointmentId = scanner.nextInt();

            Appointment appointment = hospitalService.getAppointmentById(appointmentId);

            if (appointment != null) {
                System.out.println("Appointment details: " + appointment);
            } else {
                System.out.println("Appointment not found with ID: " + appointmentId);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getAppointmentsForPatient() {
        try {
            System.out.print("Enter Patient ID: ");
            int patientId = scanner.nextInt();

            List<Appointment> appointments = hospitalService.getAppointmentsForPatient(patientId);

            if (!appointments.isEmpty()) {
                System.out.println("Appointments for the patient: ");
                for (Appointment appointment : appointments) {
                    System.out.println(appointment);
                }
            } else {
                System.out.println("No appointments found for the patient with ID: " + patientId);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getAppointmentsForDoctor() {
        try {
            System.out.print("Enter Doctor ID: ");
            int doctorId = scanner.nextInt();
            List<Appointment> appointments = hospitalService.getAppointmentsForDoctor(doctorId);

            if (appointments.isEmpty()) {
                System.out.println("No appointments found for the doctor with ID: " + doctorId);
            } else {
                System.out.println("Appointments for the doctor: ");
                for (Appointment appointment : appointments) {
                    System.out.println(appointment);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void scheduleAppointment() {
        try {
            System.out.print("Enter Patient ID: ");
            int patientId = scanner.nextInt();

            Patient patient = hospitalService.findPatientById(patientId);
            if (patient == null) {
                System.out.println("Patient not found with ID: " + patientId);
                return;
            }

            System.out.print("Enter Doctor ID: ");
            int doctorId = scanner.nextInt();

            Doctor doctor = hospitalService.findDoctorById(doctorId);
            if (doctor == null) {
                System.out.println("Doctor not found with ID: " + doctorId);
                return;
            }

            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String dateString = scanner.next();
            LocalDate appointmentDate = LocalDate.parse(dateString);

            scanner.nextLine(); // Consume the newline character
            System.out.print("Enter Appointment Description: ");
            String description = scanner.nextLine();

            Appointment appointment = new Appointment(patient, doctor, appointmentDate, description);

            boolean success = hospitalService.scheduleAppointment(appointment);

            if (success) {
                System.out.println("Appointment scheduled successfully.");
            } else {
                System.out.println("Failed to schedule the appointment.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateAppointment() {
        try {
            System.out.print("Enter Appointment ID to update: ");
            int appointmentId = scanner.nextInt();
            scanner.nextLine();

            Appointment existingAppointment = hospitalService.getAppointmentById(appointmentId);

            if (existingAppointment != null) {
                System.out.println("Enter updated details for the appointment:");

                System.out.print("Enter updated appointment date (YYYY-MM-DD): ");
                String updatedDateStr = scanner.next();
                scanner.nextLine();
                LocalDate updatedAppointmentDate = LocalDate.parse(updatedDateStr);
                scanner.nextLine();
                System.out.print("Enter updated description: ");
                String updatedDescription = scanner.nextLine();

                Appointment updatedAppointment = new Appointment();
                updatedAppointment.setAppointmentId(appointmentId);
                updatedAppointment.setPatient(existingAppointment.getPatient());
                updatedAppointment.setDoctor(existingAppointment.getDoctor());
                updatedAppointment.setAppointmentDate(updatedAppointmentDate);
                updatedAppointment.setDescription(updatedDescription);

                boolean success = hospitalService.updateAppointment(updatedAppointment);

                if (success) {
                    System.out.println("Appointment updated successfully.");
                } else {
                    System.out.println("Failed to update the appointment.");
                }
            } else {
                System.out.println("Appointment with ID " + appointmentId + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelAppointment() {
        System.out.print("Enter Appointment ID to cancel: ");
        int appointmentId = scanner.nextInt();
        boolean success = hospitalService.cancelAppointment(appointmentId);
        if (success) {
            System.out.println("Appointment canceled successfully.");
        } else {
            System.out.println("Failed to cancel the appointment.");
        }
    }

    private static void findPatientById() {
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        try {
            Patient patient = hospitalService.findPatientById(patientId);
            System.out.println("Patient details: " + patient);
        } catch (PatientNumberNotFoundException e) {
            System.out.println("Patient not found with ID: " + patientId);
        }
    }
}
