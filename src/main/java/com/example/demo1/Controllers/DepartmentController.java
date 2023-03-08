package com.example.demo1.Controllers;

import com.example.demo1.Entities.Department;
import com.example.demo1.HelloApplication;
import com.example.demo1.Services.AppointmentsService;
import com.example.demo1.Services.DoctorsService;
import com.example.demo1.Services.DepartmentsService;
import com.example.demo1.Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentController implements Observer {
    @FXML
    public TableView<Department> tableDepartments;
    @FXML
    public TableColumn<Department, String> tableDepartmentsNameColumn;
    @FXML
    public TableColumn<Department, Long> tableDepartmentsDurationColumn;
    @FXML
    public TableColumn<Department, Float> tableDepartmentsPriceColumn;
    public Button button;
    ObservableList<Department> modelDepartments = FXCollections.observableArrayList();
    DepartmentsService departmentsService;
    DoctorsService doctorsService;
    AppointmentsService appointmentsService;

    /***
     * Sets up the AppointmentsOfDoctorController.
     * @param departmentsService the departments service
     * @param doctorsService the doctors service
     * @param appointmentsService the appointments service
     */
    public void setServices(DepartmentsService departmentsService, DoctorsService doctorsService, AppointmentsService appointmentsService){
        this.departmentsService = departmentsService;
        this.doctorsService = doctorsService;
        this.appointmentsService = appointmentsService;
        appointmentsService.addObserver(this);
        departmentsService.addObserver(this);
        innitDepartments();

    }

    /***
     * Sets the values for each column of the table view.
     */
    @FXML
    public void initialize(){
        tableDepartmentsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableDepartmentsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerConsultation"));
        tableDepartmentsDurationColumn.setCellValueFactory(new PropertyValueFactory<>("maximumDurationPerConsultation"));
        tableDepartments.setItems(modelDepartments);
    }

    /***
     * Fills the modelDepartments with information.
     */
    public void innitDepartments(){
        List<Department> departments = new ArrayList<>();
        departments = departmentsService.getDepartments();
        modelDepartments.setAll(departments);
    }

    /***
     * Opens a new window containing the doctors from the selected department in order to make an appointment.
     */
    public void openAppointment(){
        Department department = tableDepartments.getSelectionModel().getSelectedItem();
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/appointment.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            MakeAppointmentController makeAppointmentController = fxmlLoader.getController();
            makeAppointmentController.setService(doctorsService, appointmentsService, department);
            stage.setScene(scene);
            stage.setTitle("Make appointment");
            stage.show();
        }catch (IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }

    /***
     * Updates the models after a modification occured.
     */
    @Override
    public void update() {
        innitDepartments();

    }
}
