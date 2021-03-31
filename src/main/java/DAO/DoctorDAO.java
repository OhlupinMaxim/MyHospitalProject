package DAO;
import model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO extends DAO<Doctor>{
    private Doctor doctor = null;

    public DoctorDAO() throws SQLException {
        super();
    }

    public List<Doctor> getAll() throws SQLException{
        doctor = new Doctor();
        List<Doctor> list = new ArrayList<>();

        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM DOCTOR");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            doctor.setID(resultSet.getLong("ID"));
            doctor.setFIO(resultSet.getString("NAME"),
                    resultSet.getString("SECOND_NAME"),
                    resultSet.getString("THIRD_NAME"));
            doctor.setSpecialization(resultSet.getString("SPECIALIZATION"));
            list.add(doctor);
            doctor = new Doctor();
        }

        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }

    public Doctor getById(long id) throws SQLException{

        doctor = new Doctor();
        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM DOCTOR WHERE ID="+id);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            doctor.setID(resultSet.getLong("ID"));
            doctor.setFIO(resultSet.getString("NAME"),
                    resultSet.getString("SECOND_NAME"),
                    resultSet.getString("THIRD_NAME"));
            doctor.setSpecialization(resultSet.getString("SPECIALIZATION"));
        }

        resultSet.close();
        statement.close();
        connection.close();

        if (doctor != null)
            return doctor;
        else
            return null;
    }

    public Doctor getByFIO(String f_name, String s_name, String t_name) throws SQLException{

        doctor = new Doctor();

        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM DOCTOR WHERE " +
                "NAME =" + cav + f_name + cav +
                " AND SECOND_NAME =" + cav + s_name + cav +
                " AND THIRD_NAME =" + cav + t_name + cav);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            doctor.setID(resultSet.getLong("ID"));
            doctor.setFIO(resultSet.getString("NAME"),
                    resultSet.getString("SECOND_NAME"),
                    resultSet.getString("THIRD_NAME"));
            doctor.setSpecialization(resultSet.getString("SPECIALIZATION"));
        }

        resultSet.close();
        statement.close();
        connection.close();

        if (doctor != null)
            return doctor;
        else
            return null;
    }

    public void update(long id, String First_Name ,String Second_Name
            ,String Third_Name, String Specialization) throws SQLException {
        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE DOCTOR SET NAME = "
                + cav + First_Name + cav + ", SECOND_NAME ="
                + cav + Second_Name + cav + ", THIRD_NAME ="
                + cav + Third_Name + cav + ", SPECIALIZATION="
                + cav + Specialization + cav + " WHERE ID =" + id);
        statement.execute();

        statement.close();
        connection.close();
    }

    public void delete(long id) throws SQLException {
        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.
                prepareStatement("DELETE FROM DOCTOR WHERE ID=" + id);
        statement.execute();

        statement.close();
        connection.close();
    }

    public void insert(Doctor doctor) throws SQLException {
        if (getAll().isEmpty())
            doctor.setID(1);
        else{
            doctor.setID(getAll().size()+1);
        }

        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.
                prepareStatement(
                        "INSERT INTO DOCTOR VALUES" +
                                "("+ doctor.getID() +", "
                                + cav + doctor.getFirst_name() + cav + ","
                                + cav + doctor.getSecond_name() + cav +","
                                + cav + doctor.getThird_name() + cav +","
                                + cav +doctor.getSpecialization()+ cav +");");
        statement.execute();

        statement.close();
        connection.close();
    }
}
