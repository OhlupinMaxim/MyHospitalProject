package DAO;

import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO extends DAO<Patient> {
    private Patient patient = null;

    public PatientDAO() throws SQLException { super(); }

    public List<Patient> getAll() throws SQLException{
        patient = new Patient();
        List<Patient> list = new ArrayList<>();

        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM PATIENT");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            patient.setID(resultSet.getLong("ID"));
            patient.setFIO(resultSet.getString("NAME"),
                    resultSet.getString("SECOND_NAME"),
                    resultSet.getString("THIRD_NAME"));
            patient.setPhone_number(resultSet.getString("PHONE_NUMBER"));
            list.add(patient);
            patient = new Patient();
        }

        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }


    public Patient getById(long id) throws SQLException {
        patient = new Patient();

        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.
                prepareStatement(("SELECT * FROM PATIENT WHERE ID="+id));
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            patient.setID(resultSet.getLong("ID"));
            patient.setFIO(resultSet.getString("NAME"),
                    resultSet.getString("SECOND_NAME"),
                    resultSet.getString("THIRD_NAME"));
            patient.setPhone_number(resultSet.getString("PHONE_NUMBER"));
        }
        resultSet.close();
        statement.close();
        connection.close();
        if (patient != null)
            return patient;
        else
            return null;
    }

    public Patient getByFIO(String f_name, String s_name, String t_name) throws SQLException {
        patient = new Patient();

        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM PATIENT WHERE NAME ="
                        + cav + f_name + cav +
                        " AND SECOND_NAME =" + cav + s_name + cav +
                        " AND THIRD_NAME =" + cav + t_name + cav);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            patient.setID(resultSet.getLong("ID"));
            patient.setFIO(resultSet.getString("NAME"),
                    resultSet.getString("SECOND_NAME"),
                    resultSet.getString("THIRD_NAME"));
            patient.setPhone_number(resultSet.getString("PHONE_NUMBER"));
        }
        resultSet.close();
        statement.close();
        connection.close();
        if (patient != null)
            return patient;
        else
            return null;
    }


    public void update(long id, String First_Name ,String Second_Name
            ,String Third_Name, String Phone_Number) throws SQLException {
        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE PATIENT SET NAME = "
                + cav + First_Name + cav + ", SECOND_NAME ="
                + cav + Second_Name + cav + ", THIRD_NAME ="
                + cav + Third_Name + cav + ", PHONE_NUMBER ="
                + cav + Phone_Number + cav + " WHERE ID =" + id);
        statement.execute();

        statement.close();
        connection.close();
    }


    public void delete(long id) throws SQLException {
        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement
                ("DELETE FROM PATIENT WHERE ID=" + id);
        statement.execute();

        statement.close();
        connection.close();
    }

    public void insert(Patient patient) throws SQLException {
        if (getAll().isEmpty())
            patient.setID(1);
        else{
            patient.setID(getAll().size()+1);
        }
        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO PATIENT VALUES" +
                        "("+ patient.getID() +", "
                        + cav + patient.getFirst_name() + cav + ","
                        + cav + patient.getSecond_name() + cav +","
                        + cav + patient.getThird_name() + cav +","
                        + cav +patient.getPhone_number()+ cav +");");
        statement.execute();

        statement.close();
        connection.close();
    }


}
