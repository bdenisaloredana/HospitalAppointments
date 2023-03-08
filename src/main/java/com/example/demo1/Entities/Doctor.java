package com.example.demo1.Entities;

import java.util.Objects;

public class Doctor extends Entity<Long>{
    private Long idDepartment;
    private String name;
    private Long seniority;
    private String rezident;

    /***
     * Constructor for a doctor.
     * @param id the id of the doctor
     * @param idDepartment the id of the department
     * @param name the name of the department
     * @param seniority the seniority of the doctor
     * @param rezident yes-if the doctor is a rezident; no-otherwise
     */
    public Doctor(Long id, Long idDepartment, String name, Long seniority, String rezident) {
        setId(id);
        this.idDepartment = idDepartment;
        this.name = name;
        this.seniority = seniority;
        this.rezident = rezident;
    }

    /***
     * Getter for the id of the department.
     * @return the id of the department
     */
    public Long getIdDepartment() {
        return idDepartment;
    }

    /***
     * Setter for the id of the department.
     * @param idDepartment the id of the department
     */
    public void setIdDepartment(Long idDepartment) {
        this.idDepartment = idDepartment;
    }

    /***
     * Getter for the name of the doctor.
     * @return the name of the doctor
     */
    public String getName() {
        return name;
    }

    /***
     * Setter for the name of the doctor.
     * @param name the name of the doctor
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Getter for the seniority of the doctor.
     * @return the seniority of the doctor
     */
    public Long getSeniority() {
        return seniority;
    }

    /***
     * Setter for the seniority of the doctor.
     * @param seniority the seniority of the doctor
     */
    public void setSeniority(Long seniority) {
        this.seniority = seniority;
    }

    /***
     * Getter for the resident property of the doctor.
     * @return yes-if the doctor is a resident; no-otherwise
     */
    public String getRezident() {
        return rezident;
    }

    /***
     * Setter for the is resident property.
     * @param rezident yes-if the doctor is a resident; no-otherwise
     */
    public void setRezident(String rezident) {
        this.rezident = rezident;
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
        Doctor doctor = (Doctor) o;
        return Objects.equals(getRezident(), doctor.getRezident()) && Objects.equals(getId(), doctor.getId()) && Objects.equals(idDepartment, doctor.idDepartment) && Objects.equals(name, doctor.name) && Objects.equals(seniority, doctor.seniority);
    }

    /***
     * Override for the hasCode function.
     * @return the hash value of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(),idDepartment, name, seniority, rezident);
    }
}
