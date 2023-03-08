package com.example.demo1.Entities;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Appointment extends Entity<Long> {
    private Long idDoctor;
    private Long pacientCnp;
    private String pacientName;
    private Date date;
    private Time hour;

    /***
     * Constructor for an appointment.
     * @param id the id of the appointment
     * @param idDoctor the doctor's id to whom the appointment was made
     * @param pacientCnp the cnp of the pacient for whom the appointment is made
     * @param pacientName  the name of the pacient for whom the appointment is made
     * @param date the date of the appointment
     * @param hour the hour of the appointment
     */
    public Appointment(Long id, Long idDoctor, Long pacientCnp, String pacientName, Date date, Time hour) {
        setId(id);
        this.idDoctor = idDoctor;
        this.pacientCnp = pacientCnp;
        this.pacientName = pacientName;
        this.date = date;
        this.hour = hour;
    }

    /***
     * Constructor for an appointment.
     * @param idDoctor the doctor's id to whom the appointment was made
     * @param pacientCnp the cnp of the pacient for whom the appointment is made
     * @param pacientName  the name of the pacient for whom the appointment is made
     * @param date the date of the appointment
     * @param hour the hour of the appointment
     */
    public Appointment(Long idDoctor, Long pacientCnp, String pacientName, Date date, Time hour) {

        this.idDoctor = idDoctor;
        this.pacientCnp = pacientCnp;
        this.pacientName = pacientName;
        this.date = date;
        this.hour = hour;
    }

    /***
     * Getter for the id of the doctor.
     * @return the id of the doctor
     */
    public Long getIdDoctor() {
        return idDoctor;
    }

    /***
     * Setter for the id of the doctor.
     * @param idDoctor the id of the doctor
     */
    public void setIdDoctor(Long idDoctor) {
        this.idDoctor = idDoctor;
    }

    /***
     * Getter for the cnp of the pacient.
     * @return the cnp of the pacient
     */
    public Long getPacientCnp() {
        return pacientCnp;
    }

    /***
     * Setter for the cnp of the pacient.
     * @param pacientCnp the cnp of the pacient
     */
    public void setPacientCnp(Long pacientCnp) {
        this.pacientCnp = pacientCnp;
    }

    /***
     * Getter for the name of the pacient.
     * @return the name of the pacient
     */
    public String getPacientName() {
        return pacientName;
    }

    /***
     * Setter for the name of the pacient.
     * @param pacientName the name of the pacient
     */
    public void setPacientName(String pacientName) {
        this.pacientName = pacientName;
    }

    /***
     * Getter fot the date of the appointment.
     * @return the date of the appointment
     */
    public Date getDate() {
        return date;
    }

    /***
     * Setter for the date of the appointment.
     * @param date the date of the appointment
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /***
     * Getter for the hour of the appointment.
     * @return the hour of the appointment.
     */
    public Time getHour() {
        return hour;
    }

    /***
     * Setter for the hour of the appointment.
     * @param hour the hour of the appointment.
     */
    public void setHour(Time hour) {
        this.hour = hour;
    }

    /***
     * Override for the equals function.
     * @param o the object with which we compare
     * @return true if the two objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(idDoctor, that.idDoctor) && Objects.equals(pacientCnp, that.pacientCnp) && Objects.equals(pacientName, that.pacientName) && Objects.equals(date, that.date) && Objects.equals(hour, that.hour);
    }

    /***
     * Override for the hashCode function.
     * @return the hash value of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), idDoctor, pacientCnp, pacientName, date, hour);
    }
}
