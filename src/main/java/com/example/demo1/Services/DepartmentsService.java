package com.example.demo1.Services;


import com.example.demo1.Entities.Department;
import com.example.demo1.Repositories.DepartmentsRepository;
import com.example.demo1.Utils.Observable;
import com.example.demo1.Utils.Observer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentsService implements Observable {
    private DepartmentsRepository departmentsRepository;
    private final List<Observer> observers=new ArrayList<>();

    /***
     * Constructor for the DepartmentsService.
     * @param departmentsRepository the departments repository
     */
    public DepartmentsService(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    /***
     * Getter for the departments that exist in the database.
     * @return a list of departments
     */
    public List<Department> getDepartments(){
        return departmentsRepository.findAll();
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
        observers.remove(observer);
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
