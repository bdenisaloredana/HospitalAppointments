package com.example.demo1.Controllers;

import com.example.demo1.Entities.Appointment;
import com.example.demo1.Entities.Doctor;
import com.example.demo1.Services.AppointmentsService;
import com.example.demo1.Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class AppointmentsOfDoctorControler implements Observer {
    public TableView<Appointment> tableViewAppointments;
    public TableColumn<Appointment, String> tableViewAppointmentsPacientNameColumn;
    public TableColumn<Appointment, Long> tableViewAppointmentsCnpColumn;
    public TableColumn<Appointment, Date> tableViewAppointmentsDateColumn;
    public TableColumn<Appointment, Time> tableViewAppointmentsHourColumn;
    ObservableList<Appointment> modelAppointments = FXCollections.observableArrayList();
    private AppointmentsService appointmentsService;
    private Doctor doctor;


    /***
     *  Sets up the AppointmentsOfDoctorController.
     * @param appointmentsService the appointments service
     * @param doctor the doctor for whom we show the appointments
     */
    public void setService(AppointmentsService appointmentsService, Doctor doctor){
        this.appointmentsService = appointmentsService;
        this.doctor = doctor;
        appointmentsService.addObserver(this);

        innitAppointments();
    }

    /***
     * Sets the values for each column of the table view.
     */
    public void initialize(){
        tableViewAppointmentsCnpColumn.setCellValueFactory(new PropertyValueFactory<>("pacientCnp"));
        tableViewAppointmentsDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableViewAppointmentsPacientNameColumn.setCellValueFactory(new PropertyValueFactory<>("pacientName"));
        tableViewAppointmentsHourColumn.setCellValueFactory(new PropertyValueFactory<>("hour"));
        tableViewAppointments.setItems(modelAppointments);
    }

    /***
     * Fills the modelAppointments with information.
     */
    public void innitAppointments(){
        List<Appointment> appointments = appointmentsService.getConsultationsByDoctor(doctor.getId());
        modelAppointments.setAll(appointments);
    }

    /***
     * Updates the models after modification occured.
     */
    @Override
    public void update() {
        innitAppointments();
    }
}
