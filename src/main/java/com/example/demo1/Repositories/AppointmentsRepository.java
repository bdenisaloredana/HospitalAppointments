package com.example.demo1.Repositories;

import com.example.demo1.Entities.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsRepository{
        private String url;
        private String user;
        private String password;

    /***
     * Constructor for the AppointmentsRepository.
     * @param url the url for the database
     * @param user the user of the database
     * @param password the password for the database
     */
        public AppointmentsRepository(String url, String user, String password) {
            this.url = url;
            this.user = user;
            this.password = password;
        }

    /***
     * Finds the appointment with a given id in the database.
     * @param idAppointment -the id of the appointment to be returned
     *           id must not be null
     * @return null- if the appointment with the given id does not exist; the searched appointment-otherwise
     */
        public Appointment findOne(Long idAppointment) {
            String sql = "select * from appointments where id = ?";
            try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setLong(1,idAppointment);
                try(ResultSet resultSet = ps.executeQuery()){
                    while(resultSet.next()){
                        Long id = resultSet.getLong("id");
                        Date date = resultSet.getDate("appointment_date");
                        Time hour = resultSet.getTime("appointment_time");
                        Long idDoctor = resultSet.getLong("id_doctor");
                        Long cnp = resultSet.getLong("pacient_cnp");
                        String name = resultSet.getString("pacient_name");

                        return new Appointment(id, idDoctor, cnp, name, date, hour);
                    }
                }
            }catch(SQLException sq){
                System.out.println(sq.getMessage());
            }
            return null;
        }

    /***
     * Finds all the appointments in the database.
     * @return a list with all the appointments saved in the database
     */
        public List<Appointment> findAll() {
            List<Appointment> appointments = new ArrayList<>();

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement statement = connection.prepareStatement("SELECT * from appointments");
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Date date = resultSet.getDate("appointment_date");
                    Time hour = resultSet.getTime("appointment_time");
                    Long idDoctor = resultSet.getLong("id_doctor");
                    Long cnp = resultSet.getLong("pacient_cnp");
                    String name = resultSet.getString("pacient_name");

                    Appointment Appointment = new Appointment(id, idDoctor, cnp, name, date, hour);
                    appointments.add(Appointment);
                }
                return appointments;
            } catch (SQLException sq) {
                System.out.println(sq.getMessage());
            }
            return appointments;
        }

    /***
     * Saves an appointment in the database.
     * @param appointment the appointment to be saved
     *         appointment must be not null
     * @return the appointment- if it was successfully saved
     */
        public Appointment save(Appointment appointment) {
            String sql = "insert into appointments (appointment_date, appointment_time, id_doctor,pacient_cnp, pacient_name) values (?,?,?,?,?)";

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement ps = connection.prepareStatement(sql);) {

                ps.setDate(1, java.sql.Date.valueOf(appointment.getDate().toString()));
                ps.setTime(2, appointment.getHour());
                ps.setLong(3, appointment.getIdDoctor());
                ps.setLong(4, appointment.getPacientCnp());
                ps.setString(5, appointment.getPacientName());

                ps.executeUpdate();
            } catch (SQLException sq) {
                System.out.println(sq.getMessage());
            }
            return appointment;
        }

    /***
     * Deletes an appointment from the database.
     * @param id the id of the appointment to be deleted
     *      id must be not null
     * @return the appointment-if it was successfully deleted; null-otherwise
     */
        public Appointment delete(Long id) {
            String sql = "delete from appointments where id = ?";
            Appointment appointment = findOne(id);
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();

            } catch (SQLException sq) {
                System.out.println(sq.getMessage());
            }
            return appointment;
        }

    /***
     * Updates an appointment in the database.
     * @param appointment the updated appointment;
     *        appointment must not be null
     * @return the old appointment-if it was successfully updated; null-otherwise
     */
        public Appointment update(Appointment appointment) {
            String sql = "update appointments set appointment_date = ?, appointment_time = ?,  id_doctor = ?,pacient_cnp =?, pacient_name = ? where id = ?";

            Appointment appointment2 = findOne(appointment.getId());

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setDate(1, java.sql.Date.valueOf(appointment.getDate().toString()));
                ps.setTime(2, appointment.getHour());
                ps.setLong(3, appointment.getPacientCnp());
                ps.setString(4, appointment.getPacientName());

                ps.executeUpdate();
            } catch (SQLException sq) {
                System.out.println(sq.getMessage());
            }
            return appointment2;
        }

    /***
     * Getter for the consultations of a given doctor.
     * @param doctorId the id of the doctor for whom we search the consultations
     * @return a list containing the consultations of the given doctor
     */
    public List<Appointment> getConsultationsByIdDoctor(Long doctorId){
            List<Appointment> appointments = new ArrayList<>();
            String sql = "select * from appointments where id_doctor = ?";

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setLong(1, doctorId);

                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        Long id = resultSet.getLong("id");
                        Date date = resultSet.getDate("appointment_date");
                        Time hour = resultSet.getTime("appointment_time");
                        Long idDoctor = resultSet.getLong("id_doctor");
                        Long cnp = resultSet.getLong("pacient_cnp");
                        String name = resultSet.getString("pacient_name");

                        Appointment Appointment = new Appointment(id, idDoctor, cnp, name, date, hour);
                        appointments.add(Appointment);
                    }
                }

                return appointments;

            } catch (SQLException sq) {
                System.out.println(sq.getMessage());
            }
            return appointments;

        }
    }


