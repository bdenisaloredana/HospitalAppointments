package com.example.demo1.Services;


import com.example.demo1.Entities.Appointment;
import com.example.demo1.Entities.Department;
import com.example.demo1.Repositories.AppointmentsRepository;
import com.example.demo1.Utils.Observable;
import com.example.demo1.Utils.Observer;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentsService implements Observable {
    private AppointmentsRepository appointmentsRepository;
    private final List<Observer> observers=new ArrayList<>();

    /***
     * Constructor for the AppointmentsService.
     * @param appointmentsRepository the appointments repository
     */
    public AppointmentsService(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
    }
    
    /***
     * Getter for the consultations of a given doctor that did not exceed the current date and time.
     * @param idDoctor the id of the doctor for whom we search the appointments
     * @return a list containing the consultations of the given doctor that did not exceed the current date and time
     */
    public List<Appointment> getConsultationsByDoctor(Long idDoctor){
        LocalDate localDate = LocalDate.now();
        List<Appointment> appointments = appointmentsRepository.getConsultationsByIdDoctor(idDoctor);
        appointments = appointments.stream().sorted(Comparator.comparing(Appointment::getDate)).toList();
        appointments = appointments.stream().sorted(Comparator.comparing(Appointment::getHour)).toList();
        appointments = appointments.stream().filter(x->x.getDate().compareTo(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))>=0).collect(Collectors.toList());
        appointments = appointments.stream().filter(x->(x.getDate().compareTo(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))==0 && x.getHour().toLocalTime().compareTo(LocalTime.now())>=0)
                                                        || (x.getDate().compareTo(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))>=0)).collect(Collectors.toList());
        return appointments;
    }

    /***
     * Adds an appointment to the database, if it doesn't exist already.
     * @param idDoctor the doctor's id to whom the appointment was made
     * @param cnp the cnp of the pacient
     * @param name the name of the pacient
     * @param date the date of the appointment
     * @param hour the hour of the appointment
     * @param department the department where the appointment was made
     */
    public Appointment addAppointment(Long idDoctor, Long cnp, String name, Date date, Time hour, Department department){
        List<Appointment> appointments = appointmentsRepository.findAll();
        boolean ok = false;
        for(Appointment appointment : appointments){

            if (appointment.getDate().compareTo( date) == 0 && Objects.equals(appointment.getIdDoctor(), idDoctor)) {
                if (appointment.getHour().before(hour) && appointment.getHour().toLocalTime().plusHours(department.getMaximumDurationPerConsultation()).isAfter(hour.toLocalTime())) {
                    ok = true;
                    break;
                }
                if (hour.toLocalTime().plusHours(department.getMaximumDurationPerConsultation()).isBefore(appointment.getHour().toLocalTime().plusHours(department.getMaximumDurationPerConsultation()))) {
                    ok = true;
                    break;
                }
                if(appointment.getHour().compareTo(hour) == 0){
                    ok = true;
                    break;
                }
            }
        }
        if(!ok) {
            appointmentsRepository.save(new Appointment(idDoctor, cnp, name, date, hour));
            notifyObservers();
            return new Appointment(idDoctor, cnp, name, date, hour);
        }
        return null;
    }

    /***
     * Deletes an appointment from the database.
     * @param idAppointment the id of the appointment to be deleted
     */
    public void deleteAppointment(Long idAppointment){
        this.appointmentsRepository.delete(idAppointment);
        notifyObservers();
    }

    /***
     * Finds the appointment with a given id in the database.
     * @param id the id of the appointment
     * @return an appointment-if the appointment with the given id exists; null-otherwise
     */
    public Appointment findOne(Long id){
        return appointmentsRepository.findOne(id);
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
