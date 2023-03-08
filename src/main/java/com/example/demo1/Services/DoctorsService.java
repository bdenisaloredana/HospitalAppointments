package com.example.demo1.Services;

import com.example.demo1.Entities.Doctor;
import com.example.demo1.Entities.Department;
import com.example.demo1.Repositories.DoctorRepository;
import com.example.demo1.Utils.Observable;
import com.example.demo1.Utils.Observer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DoctorsService implements Observable {
    private DoctorRepository doctorRepository;
    private final List<Observer> observers=new ArrayList<>();

    /***
     * Constructor for the DoctorsService.
     * @param doctorRepository the doctors repository
     */
    public DoctorsService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /***
     * Getter for the doctors in the database.
     * @return a list containing the doctors
     */
    public List<Doctor> getDoctors(){
        return doctorRepository.findAll();
    }

    /***
     * Getter for the doctors from a given department.
     * @param department the given department
     * @return a list containing the doctors that belong to the given department
     */
    public List<Doctor> getDoctorsFromDepartment(Department department){
        List<Doctor> doctors = doctorRepository.findAll();
        int i = 0;
        while(i<doctors.size()){
            if(!Objects.equals(doctors.get(i).getIdDepartment(), department.getId()))
                doctors.remove(i);
            else i++;
        }
        return doctors;
    }

    /***
     * Adds an observer to the list of observers.
     * @param observer the observer to be added to the list
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }


    /***
     * Removes an observer from the list of observers.
     * @param observer the observer to be removed from the list
     */
    @Override
    public void removeObserver(Observer observer) {
    observers.remove(observers);
    }

    /***
     * Notifies the observers when a change occurred.
     */
    @Override
    public void notifyObservers() {
        observers.stream().forEach(x-> {
            try {
                x.update();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
