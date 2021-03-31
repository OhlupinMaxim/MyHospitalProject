package model;

public class Patient extends People {
    private String Phone_number;

    public Patient(String first_name, String second_name, String third_name, String phone_number) {
        super(first_name, second_name, third_name);
        this.Phone_number = phone_number;
    }

    public Patient() {
        super(null, null, null);
    }

    public String getPhone_number() {
        return this.Phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.Phone_number = phone_number;
    }

    public void setAllInfo(long ID, String first_name, String second_name,
                           String third_name, String phone_number) {
        this.ID = ID;
        this.First_name = first_name;
        this.Second_name = second_name;
        this.Third_name = third_name;
        this.Phone_number = phone_number;
    }

    public String getAllInfo() {
        return this.ID + " "
                + this.First_name + " "
                + this.Second_name + " "
                + this.Third_name + " "
                + this.Phone_number;
    }

}
