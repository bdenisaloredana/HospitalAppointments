package com.example.demo1.Entities;

import java.util.Objects;

public class Department extends Entity<Long>{
    private String name;
    private Long idDepartmentHead;
    private Float pricePerConsultation;
    private Long maximumDurationPerConsultation;

    /***
     * Constructor for a department.
     * @param id the id of the department
     * @param name the name of the department
     * @param idDepartmentHead the id of the department head
     * @param pricePerConsultation the price per consultation in the department
     * @param maximumDurationPerConsultation the maximum duration for a consultation in the department
     */
    public Department(Long id, String name, Long idDepartmentHead, Float pricePerConsultation, Long maximumDurationPerConsultation) {
        setId(id);
        this.name = name;
        this.idDepartmentHead = idDepartmentHead;
        this.pricePerConsultation = pricePerConsultation;
        this.maximumDurationPerConsultation = maximumDurationPerConsultation;
    }

    /***
     * Getter for the name of the department.
     * @return the name of the department
     */
    public String getName() {
        return name;
    }

    /***
     * Getter for the id of the department head.
     * @return the id of the department head
     */
    public Long getIdDepartmentHead() {
        return idDepartmentHead;
    }

    /***
     * Getter for the price of a consultation in the department.
     * @return the price of a consultation
     */
    public Float getPricePerConsultation() {
        return pricePerConsultation;
    }

    /***
     * Getter for the maximum duration for a consultation in the department.
     * @return the maximum duration for a consultation
     */
    public Long getMaximumDurationPerConsultation() {
        return maximumDurationPerConsultation;
    }

    /***
     * Setter for the name of the department.
     * @param name the name of the department
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Setter for the id of the department head.
     * @param idDepartmentHead the id of the department head
     */
    public void setIdDepartmentHead(Long idDepartmentHead) {
        this.idDepartmentHead = idDepartmentHead;
    }

    /***
     * Setter for the price for a consultation.
     * @param pricePerConsultation the price for a consultation
     */
    public void setPricePerConsultation(Float pricePerConsultation) {
        this.pricePerConsultation = pricePerConsultation;
    }

    /***
     * Setter for the maximum duration for a consultation.
     * @param maximumDurationPerConsultation the maximum duration for a consultation
     */
    public void setMaximumDurationPerConsultation(Long maximumDurationPerConsultation) {
        this.maximumDurationPerConsultation = maximumDurationPerConsultation;
    }

    /***
     * Overrides the equals function.
     * @param o the object with which we compare
     * @return true if the two objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Department department = (Department) o;
        return Objects.equals(getId(), department.getId()) &&Objects.equals(name, department.name) && Objects.equals(idDepartmentHead, department.idDepartmentHead) && Objects.equals(pricePerConsultation, department.pricePerConsultation) && Objects.equals(maximumDurationPerConsultation, department.maximumDurationPerConsultation);
    }

    /***
     * Override for the hasCode function.
     * @return the hash value of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), name, idDepartmentHead, pricePerConsultation, maximumDurationPerConsultation);
    }
}
