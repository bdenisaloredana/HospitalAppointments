package com.example.demo1.Controllers;

import com.example.demo1.Entities.Appointment;
import com.example.demo1.Entities.Doctor;
import com.example.demo1.Services.AppointmentsService;
import com.example.demo1.Utils.MessageAlert;
import com.example.demo1.Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class CancelAppointmentForDoctorController implements Observer {

    @FXML
    public TableView<Appointment> tableViewAppointments;
    @FXML
    public TableColumn<Appointment, String> tableViewAppointmentsNameCol;
    @FXML
    public TableColumn<Appointment, String> tableViewAppointmentsCnpCol;
    @FXML
    public TableColumn<Appointment, Date> tableViewAppointmentsDateCol;
    ObservableList<Appointment> modelAppointments = FXCollections.observableArrayList();
    Doctor doctor;
    AppointmentsService appointmentsService;


    /***
     * Sets up the CancelAppointmentForDoctorController.
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
        tableViewAppointmentsCnpCol.setCellValueFactory(new PropertyValueFactory<>("pacientCnp"));
        tableViewAppointmentsDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableViewAppointmentsNameCol.setCellValueFactory(new PropertyValueFactory<>("pacientName"));
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
     * Updates the models after a modification occured.
     */
    @Override
    public void update(){
        innitAppointments();
    }

    /***
     * Handles the cancellation of an appointment.
     */
    public void cancelAppointment() {
        Appointment appointmentToDelete = tableViewAppointments.getSelectionModel().getSelectedItem();
        if(appointmentToDelete != null){
           this.appointmentsService.deleteAppointment(appointmentToDelete.getId());
            if(appointmentsService.findOne(appointmentToDelete.getId()) == null){
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Canceled!", "The appointment was successfully canceled!");
            }
        }


    }
}
