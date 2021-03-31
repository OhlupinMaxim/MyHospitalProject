package model;

public class People {
    protected static long ID = 0;
    protected String First_name;
    protected String Second_name;
    protected String Third_name;

    public People(String first_name, String second_name, String third_name) {
        //ID++;
        this.First_name = first_name;
        this.Second_name = second_name;
        this.Third_name = third_name;
    }

    public People() {
        //this.ID = 0;
        this.First_name = null;
        this.Second_name = null;
        this.Third_name = null;
    }

    public String getFIO() {
        return this.First_name + ' '
                + this.Second_name + ' '
                + this.Third_name;
    }

    public void setFIO(String first_name, String second_name, String third_name) {
        this.First_name = first_name;
        this.Second_name = second_name;
        this.Third_name = third_name;
    }


    public String getFirst_name() {
        return First_name;
    }

    public String getSecond_name() {
        return Second_name;
    }

    public String getThird_name() {
        return Third_name;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public void setSecond_name(String second_name) {
        Second_name = second_name;
    }

    public void setThird_name(String third_name) {
        Third_name = third_name;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
