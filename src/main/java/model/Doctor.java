package model;

public class Doctor extends People{
    private String Specialization;

    public Doctor (String first_name, String second_name, String third_name ,String specialization){
        super(first_name,second_name,third_name);
        this.Specialization = specialization;
    }

    public Doctor(){
        super(null,null,null);
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

}

