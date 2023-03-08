package com.example.demo1.Repositories;

import com.example.demo1.Entities.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DoctorRepository {
    private String url;
    private String user;
    private String password;

    /***
     * Constructor for the DoctorRepository.
     * @param url the url for the database
     * @param user the user of the database
     * @param password the password for the database
     */
    public DoctorRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /***
     * Finds the doctor with a given id in the database.
     * @param idDoctor -the id of the doctor to be returned
     *           id must not be null
     * @return null- if the doctor with the given id does not exist; the searched doctor-otherwise
     */
    public Doctor findOne(Long idDoctor) {
        String sql = "select * from doctors where id = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,idDoctor);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    Long id = resultSet.getLong("id");
                    Long idDepartment = resultSet.getLong("id_department");
                    String name = resultSet.getString("doctor_name");
                    Long seniority = resultSet.getLong("seniority");
                    boolean rezident = resultSet.getBoolean("rezident");

                    String isRezident;
                    if(rezident)
                        isRezident = "yes";
                    else
                        isRezident = "no";;

                    return new Doctor(id, idDepartment, name, seniority, isRezident);
                }
            }
        }catch(SQLException sq){
            System.out.println(sq.getMessage());
        }
        return null;
    }

    /***
     * Finds all the doctors in the database.
     * @return a list with all the doctors saved in the database
     */
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from doctors");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long idDepartment = resultSet.getLong("id_department");
                String name = resultSet.getString("doctor_name");
                Long seniority = resultSet.getLong("seniority");
                boolean rezident = resultSet.getBoolean("rezident");

                String isRezident;
                if(rezident)
                    isRezident = "yes";
                else
                    isRezident = "no";
                Doctor Doctor = new Doctor(id, idDepartment, name, seniority, isRezident);
                doctors.add(Doctor);
            }
            return doctors;
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return doctors;
    }

    /***
     * Saves a doctor in the database.
     * @param doctor the doctor to be saved
     *         doctor must be not null
     * @return the doctor- if it was successfully saved
     */
    public Doctor save(Doctor doctor) {
        String sql = "insert into doctors (doctor_name, rezident, id_department, seniority) values (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setString(1, doctor.getName());
            if(Objects.equals(doctor.getRezident(), "yes"))
                ps.setBoolean(2, true);
            else
                ps.setBoolean(2, false);
            ps.setLong(3, doctor.getIdDepartment());
            ps.setLong(4, doctor.getSeniority());

            ps.executeUpdate();
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return doctor;
    }

    /***
     * Deletes a doctor from the database.
     * @param id the id of the doctor to be deleted
     *      id must be not null
     * @return the doctor-if it was successfully deleted; null-otherwise
     */
    public Doctor delete(Long id) {
        String sql = "delete from doctors where id = ?";
        Doctor doctor = findOne(id);
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return doctor;
    }

    /***
     * Updates a doctor in the database.
     * @param doctor the updated doctor;
     *        doctor must not be null
     * @return the old doctor-if it was successfully updated; null-otherwise
     */
    public Doctor update(Doctor doctor) {
        String sql = "update doctors set doctor_name = ?, rezident = ?,  id_department = ?,seniority =? where id = ?";

        Doctor doctor2 = findOne(doctor.getId());

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, doctor.getName());
            if(Objects.equals(doctor.getRezident(), "yes"))
                ps.setBoolean(2, true);
            else
                ps.setBoolean(2, false);
            ps.setLong(3, doctor.getIdDepartment());
            ps.setLong(4, doctor.getSeniority());

            ps.executeUpdate();
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return doctor2;
    }
}
