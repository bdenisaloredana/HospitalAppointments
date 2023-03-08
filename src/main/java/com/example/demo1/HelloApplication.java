package com.example.demo1;

import com.example.demo1.Controllers.AppointmentsOfDoctorControler;
import com.example.demo1.Controllers.DepartmentController;
import com.example.demo1.Entities.Doctor;
import com.example.demo1.Repositories.AppointmentsRepository;
import com.example.demo1.Repositories.DoctorRepository;
import com.example.demo1.Repositories.DepartmentsRepository;
import com.example.demo1.Services.AppointmentsService;
import com.example.demo1.Services.DoctorsService;
import com.example.demo1.Services.DepartmentsService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        AppointmentsRepository appointmentsRepository = new AppointmentsRepository("jdbc:postgresql://localhost:5432/hospital", "postgres", "postgres");
        DoctorRepository doctorRepository = new DoctorRepository("jdbc:postgresql://localhost:5432/hospital", "postgres", "postgres");
        DepartmentsRepository departmentsRepository = new DepartmentsRepository("jdbc:postgresql://localhost:5432/hospital", "postgres", "postgres");
        DepartmentsService departmentsService = new DepartmentsService(departmentsRepository);
        DoctorsService doctorsService = new DoctorsService(doctorRepository);
        AppointmentsService appointmentsService = new AppointmentsService(appointmentsRepository);
        List<Doctor> medici = doctorRepository.findAll();
        for(Doctor doctor : medici){
            Stage stage2 = new Stage();
            FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("views/appointmentsOfDoctor.fxml"));
            Scene scene2 = new Scene(fxmlLoader2.load(), 600, 400);
            AppointmentsOfDoctorControler appointmentsOfDoctorControler = fxmlLoader2.getController();
            appointmentsOfDoctorControler.setService(appointmentsService, doctor);
            stage2.setTitle("Doctor: "+ doctor.getName());
            stage2.setScene(scene2);
            stage2.show();

        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/departments.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        DepartmentController departmentController = fxmlLoader.getController();
        departmentController.setServices(departmentsService, doctorsService, appointmentsService);
        stage.setTitle("Departments");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}