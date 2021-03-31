package model;

public class Recipe {
    private static long ID = 0;
    private String description;
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String validity;
    private String priority;

    public Recipe( String description, Patient patient, Doctor doctor,
                   String date, String validity, String priority) {
        //ID ++;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.validity = validity;
        this.priority = priority;
    }

    public Recipe(){
        //this.ID = 0;
        this.description = null;
        this.patient = null;
        this.doctor = null;
        this.date = null;
        this.validity = null;
        this.priority = null;
    }


    public String getDate() {
        return date;
    }

    public String getValidity() {
        return validity;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

}