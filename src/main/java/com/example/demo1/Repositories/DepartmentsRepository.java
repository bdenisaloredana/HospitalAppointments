package com.example.demo1.Repositories;

import com.example.demo1.Entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentsRepository{
    private String url;
    private String user;
    private String password;

    /***
     * /***
     *      * Constructor for the DepartmentsRepository.
     *      * @param url the url for the database
     *      * @param user the user of the database
     *      * @param password the password for the database
     *      */
    public DepartmentsRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /***
     * Finds the department with a given id in the database.
     * @param idDepartment -the id of the department to be returned
     *           id must not be null
     * @return null- if the department with the given id does not exist; the searched department-otherwise
     */
    public Department findOne(Long idDepartment) {
        String sql = "select * from departments where id = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,idDepartment);
            try(ResultSet resultSet = ps.executeQuery()){
                while(resultSet.next()){
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("department_name");
                    Long idDepartmentHead = resultSet.getLong("id_department_head");
                    Float price = resultSet.getFloat("price_per_consultation");
                    Long duration = resultSet.getLong("maximum_duration_per_consultation");

                    return new Department(id, name, idDepartmentHead, price, duration);
                }
            }
        }catch(SQLException sq){
            System.out.println(sq.getMessage());
        }
        return null;
    }

    /***
     * Finds all the departments in the database.
     * @return a list with all the departments saved in the database
     */
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from departments");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("department_name");
                Long idDepartmentHead = resultSet.getLong("id_department_head");
                Float price = resultSet.getFloat("price_per_consultation");
                Long duration = resultSet.getLong("maximum_duration_per_consultation");

                Department department = new Department(id, name, idDepartmentHead, price, duration);
                departments.add(department);
            }
            return departments;
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return departments;
    }

    /***
     * Saves a department in the database.
     * @param department the department to be saved
     *         department must be not null
     * @return the department- if it was successfully saved
     */
    public Department save(Department department) {
        String sql = "insert into departments (department_name, id_department_head, price_per_consultation,maximum_duration_per_consultation) values (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setString(1, department.getName());
            ps.setLong(2, department.getIdDepartmentHead());
            ps.setFloat(3, department.getPricePerConsultation());
            ps.setLong(4, department.getMaximumDurationPerConsultation());

            ps.executeUpdate();
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return department;
    }

    /***
     * Deletes a department from the database.
     * @param id the id of the department to be deleted
     *      id must be not null
     * @return the department-if it was successfully deleted; null-otherwise
     */
    public Department delete(Long id) {
        String sql = "delete from departments where id = ?";
        Department department = findOne(id);
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return department;
    }

    /***
     * Updates a department in the database.
     * @param  department the updated department;
     *        department must not be null
     * @return the old department-if it was successfully updated; null-otherwise
     */
    public Department update(Department department) {
        String sql = "update departments set department_name = ?, id_department_head = ?,  price_per_consultation = ?,maximum_duration_per_consultation =? where id = ?";

        Department department2 = findOne(department.getId());

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, department.getName());
            ps.setLong(2, department.getIdDepartmentHead());
            ps.setFloat(3, department.getPricePerConsultation());
            ps.setLong(4, department.getMaximumDurationPerConsultation());
            ps.setLong(5, department.getId());

            ps.executeUpdate();
        } catch (SQLException sq) {
            System.out.println(sq.getMessage());
        }
        return department2;
    }
}
