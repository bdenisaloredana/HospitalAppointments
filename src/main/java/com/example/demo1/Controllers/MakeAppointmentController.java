package com.example.demo1.Controllers;

import com.example.demo1.Entities.Appointment;
import com.example.demo1.Entities.Doctor;
import com.example.demo1.Entities.Department;
import com.example.demo1.HelloApplication;
import com.example.demo1.Services.AppointmentsService;
import com.example.demo1.Services.DoctorsService;
import com.example.demo1.Utils.MessageAlert;
import com.example.demo1.Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class MakeAppointmentController implements Observer {
    public TableView<Doctor> makeAppointmentTableView;
    public TableColumn<Doctor, String> makeAppointmentTableViewDoctorNameCol;
    public TableColumn<Doctor, String> makeAppointmentTableViewRezidentCol;
    public TableColumn<Doctor, Long> makeAppointmentTableViewSeniorityCol;
    public DatePicker datePicker;
    public TextField nameTextBox;
    public TextField cnpTextBox;
    public Spinner hourSpinner;
    public Spinner minutesSpinner;
    DoctorsService doctorsService;
    AppointmentsService appointmentsService;
    ObservableList<Doctor> modelDoctor = FXCollections.observableArrayList();
    Department department;

    /***
     * Sets up the MakeAppointmentController.
     * @param doctorsService the doctors service
     * @param appointmentsService the appointments service
     * @param department the department to which the doctor belongs
     */
    public void setService(DoctorsService doctorsService, AppointmentsService appointmentsService, Department department){
        this.doctorsService = doctorsService;
        this.appointmentsService = appointmentsService;
        this.department = department;
        doctorsService.addObserver(this);
        appointmentsService.addObserver(this);
        innitDoctors();
    }

    /***
     * Sets the values for each column of the table view.
     */
    public void initialize(){
        makeAppointmentTableViewDoctorNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        makeAppointmentTableViewRezidentCol.setCellValueFactory(new PropertyValueFactory<>("rezident"));
        makeAppointmentTableViewSeniorityCol.setCellValueFactory(new PropertyValueFactory<>("seniority"));
        makeAppointmentTableView.setItems(modelDoctor);

    }

    /***
     * Fills the modelDoctor with information.
     */
    public void innitDoctors(){
        List<Doctor> doctors = doctorsService.getDoctorsFromDepartment(department);
        modelDoctor.setAll(doctors);

    }

    /***
     * Handles the making of an appointment.
     */
    public void makeAppointment() {
        try {
            Date date = java.sql.Date.valueOf(datePicker.getValue());
            String format = hourSpinner.getValue().toString()+ ":" + minutesSpinner.getValue().toString()+":00";
            Time hour = Time.valueOf(format);
            String name = nameTextBox.getText();
            Long cnp = Long.valueOf(cnpTextBox.getText());
            Long idDoctor = makeAppointmentTableView.getSelectionModel().getSelectedItem().getId();
            Appointment appointment = appointmentsService.addAppointment(idDoctor, cnp, name, date, hour, department);
            if(appointment == null)
                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error","Choose a different appointment time! ");

        }catch(NullPointerException | NumberFormatException e){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Try again!", "Data entered incorectly!");
        }

    }

    /***
     * Updates models after a modification.
     */
    @Override
    public void update() {
        innitDoctors();
    }

    /***
     *Opens a new window with the appointments of the selected doctor in order to cancel one/more.
     */
    public void showAppointments(){
        Doctor doctor = makeAppointmentTableView.getSelectionModel().getSelectedItem();
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/cancelAppointment.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            CancelAppointmentForDoctorController cancelAppointmentForDoctorController = fxmlLoader.getController();
            cancelAppointmentForDoctorController.setService(appointmentsService, doctor);
            stage.setScene(scene);
            stage.setTitle("Cancel appointments");
            stage.show();
        }catch (IOException ioException){
            System.out.println(ioException.getMessage());
        }

    }
}
