package com.hms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hms.entity.Appointment;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.exception.PatientNumberNotFoundException;
import com.hms.util.DBUtil;

public class HospitalServiceImpl implements IHospitalService {

    private static Connection connection;

    public boolean insertPatient(Patient patient) {
        try {
            try (Connection connection = DBUtil.createConnection()) {

                String insertQuery = "INSERT INTO patient (firstName, lastName, dateOfBirth, gender, contactNumber, address) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setString(1, patient.getFirstName());
                    preparedStatement.setString(2, patient.getLastName());
                    preparedStatement.setDate(3, java.sql.Date.valueOf(patient.getDateOfBirth()));
                    preparedStatement.setString(4, patient.getGender());
                    preparedStatement.setString(5, patient.getContactNumber());
                    preparedStatement.setString(6, patient.getAddress());

                    int affectedRows = preparedStatement.executeUpdate();

                    return affectedRows > 0;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertDoctor(Doctor doctor) {
        try {
            Connection connection = DBUtil.createConnection();

            String query = "INSERT INTO doctor (firstName, lastName, specialization, contactNumber) "
                    + "VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, doctor.getFirstName());
                preparedStatement.setString(2, doctor.getLastName());
                preparedStatement.setString(3, doctor.getSpecialization());
                preparedStatement.setString(4, doctor.getContactNumber());

                int affectedRows = preparedStatement.executeUpdate();

                return affectedRows > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        try {
            try (Connection connection = DBUtil.createConnection()) {
                String selectQuery = "SELECT * FROM appointment WHERE appointmentId = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

                    preparedStatement.setInt(1, appointmentId);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {

                        if (resultSet.next()) {
                            Appointment appointment = new Appointment();
                            appointment.setAppointmentId(resultSet.getInt("appointmentid"));

                            int patientId = resultSet.getInt("patientid");
                            Patient patient = findPatientById(patientId);
                            appointment.setPatient(patient);

                            int doctorId = resultSet.getInt("doctorid");
                            Doctor doctor = findDoctorById(doctorId);
                            appointment.setDoctor(doctor);

                            appointment.setAppointmentDate(resultSet.getDate("appointmentdate").toLocalDate());
                            appointment.setDescription(resultSet.getString("description"));

                            return appointment;
                        } else {
                            return null;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException | PatientNumberNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) throws PatientNumberNotFoundException {
        try {
            try (Connection connection = DBUtil.createConnection()) {

                String selectQuery = "SELECT * FROM appointment WHERE patientid = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

                    preparedStatement.setInt(1, patientId);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {

                        List<Appointment> appointments = new ArrayList<>();

                        while (resultSet.next()) {
                            Appointment appointment = new Appointment();
                            appointment.setAppointmentId(resultSet.getInt("appointmentid"));

                            int doctorId = resultSet.getInt("doctorid");
                            Doctor doctor = findDoctorById(doctorId);
                            appointment.setDoctor(doctor);

                            appointment.setAppointmentDate(resultSet.getDate("appointmentDate").toLocalDate());
                            appointment.setDescription(resultSet.getString("description"));

                            appointments.add(appointment);
                        }

                        return appointments;
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) throws PatientNumberNotFoundException {
        List<Appointment> appointments = new ArrayList<>();
        try {
            try (Connection connection = DBUtil.createConnection()) {

                String selectQuery = "SELECT * FROM appointment WHERE doctorid = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

                    preparedStatement.setInt(1, doctorId);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            Appointment appointment = new Appointment();
                            appointment.setAppointmentId(resultSet.getInt("appointmentid"));

                            int patientId = resultSet.getInt("patientid");
                            Patient patient = findPatientById(patientId);
                            appointment.setPatient(patient);

                            appointment.setDoctor(findDoctorById(doctorId));

                            appointment.setAppointmentDate(resultSet.getDate("appointmentdate").toLocalDate());
                            appointment.setDescription(resultSet.getString("description"));

                            appointments.add(appointment);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public Patient findPatientById(int patientId) throws PatientNumberNotFoundException {
        try {
            try (Connection connection = DBUtil.createConnection()) {

                String selectQuery = "SELECT * FROM patient WHERE patientid = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

                    preparedStatement.setInt(1, patientId);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {

                        if (resultSet.next()) {
                            Patient patient = new Patient();
                            patient.setPatientId(resultSet.getInt("patientId"));
                            patient.setFirstName(resultSet.getString("firstName"));
                            patient.setLastName(resultSet.getString("lastName"));
                            patient.setDateOfBirth(resultSet.getDate("dateOfBirth").toLocalDate());
                            patient.setGender(resultSet.getString("gender"));
                            patient.setContactNumber(resultSet.getString("contactNumber"));
                            patient.setAddress(resultSet.getString("address"));

                            return patient;
                        } else {
                            throw new PatientNumberNotFoundException("Patient not found with ID: " + patientId);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Doctor findDoctorById(int doctorId) {
        try {
            try (Connection connection = DBUtil.createConnection()) {

                String selectQuery = "SELECT * FROM doctor WHERE doctorid = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

                    preparedStatement.setInt(1, doctorId);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {

                        if (resultSet.next()) {
                            Doctor doctor = new Doctor();
                            doctor.setDoctorId(resultSet.getInt("doctorid"));
                            doctor.setFirstName(resultSet.getString("firstname"));
                            doctor.setLastName(resultSet.getString("lastname"));
                            doctor.setSpecialization(resultSet.getString("specialization"));
                            doctor.setContactNumber(resultSet.getString("contactNumber"));

                            return doctor;
                        } else {
                            return null;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean scheduleAppointment(Appointment appointment) {
        try {
            try (Connection connection = DBUtil.createConnection()) {

                String insertQuery = "INSERT INTO appointment (patientid, doctorid, appointmentdate, description) VALUES (?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setInt(1, appointment.getPatient().getPatientId());
                    preparedStatement.setInt(2, appointment.getDoctor().getDoctorId());
                    preparedStatement.setDate(3, java.sql.Date.valueOf(appointment.getAppointmentDate()));
                    preparedStatement.setString(4, appointment.getDescription());

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                appointment.setAppointmentId(generatedKeys.getInt(1));
                                return true;
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        try {
            try (Connection connection = DBUtil.createConnection()) {

                String updateQuery = "UPDATE appointment SET appointmentdate = ?, description = ? WHERE appointmentid = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                    preparedStatement.setDate(1, java.sql.Date.valueOf(appointment.getAppointmentDate()));
                    preparedStatement.setString(2, appointment.getDescription());
                    preparedStatement.setInt(3, appointment.getAppointmentId());

                    int rowsUpdated = preparedStatement.executeUpdate();

                    return rowsUpdated > 0;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cancelAppointment(int appointmentId) {
        try {
            try (Connection connection = DBUtil.createConnection()) {
                String updateQuery = "DELETE FROM appointment WHERE appointmentid = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, appointmentId);
                    int rowsUpdated = preparedStatement.executeUpdate();

                    return rowsUpdated > 0;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
