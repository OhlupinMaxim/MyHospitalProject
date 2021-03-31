package DAO;

import model.Doctor;
import model.Patient;
import model.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO extends DAO<Recipe> {
    private Recipe recipe = null;

    public RecipeDAO() throws SQLException, ClassNotFoundException {
        super();
    }

    public List<Recipe> getAll() throws SQLException {
        recipe = new Recipe();
        List<Recipe> list = new ArrayList<>();

        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM RECIPE");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            recipe.setID(resultSet.getLong("ID"));
            recipe.setDescription(resultSet.getString("DESCRIPTION"));
            recipe.setPatient(new PatientDAO().
                    getByFIO(resultSet.getString("PACIENT").split("\\s+")[0],
                            resultSet.getString("PACIENT").split("\\s+")[1],
                            resultSet.getString("PACIENT").split("\\s+")[2]));
            recipe.setDoctor(new DoctorDAO().getByFIO(resultSet.getString("DOCTOR").split("\\s+")[0],
                    resultSet.getString("DOCTOR").split("\\s+")[1],
                    resultSet.getString("DOCTOR").split("\\s+")[2]));
            recipe.setDate(resultSet.getString("DATA"));
            recipe.setValidity(resultSet.getString("VALIDITY"));
            recipe.setPriority(resultSet.getString("PRIORITY"));
            list.add(recipe);
            recipe = new Recipe();
        }

        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }


    public Recipe getById(long id) throws SQLException {
        recipe = new Recipe();

        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM RECIPE WHERE ID=" + id);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            recipe.setID(resultSet.getLong("ID"));
            recipe.setDescription(resultSet.getString("DESCRIPTION"));
            recipe.setPatient(new PatientDAO().
                    getByFIO(resultSet.getString("PACIENT").split("\\s+")[0],
                            resultSet.getString("PACIENT").split("\\s+")[1],
                            resultSet.getString("PACIENT").split("\\s+")[2]));
            recipe.setDoctor(new DoctorDAO().getByFIO(resultSet.getString("DOCTOR").split("\\s+")[0],
                    resultSet.getString("DOCTOR").split("\\s+")[1],
                    resultSet.getString("DOCTOR").split("\\s+")[2]));
            recipe.setDate(resultSet.getString("DATA"));
            recipe.setValidity(resultSet.getString("VALIDITY"));
            recipe.setPriority(resultSet.getString("PRIORITY"));
        }

        resultSet.close();
        statement.close();
        connection.close();

        if (recipe != null)
            return recipe;
        else
            return null;
    }


    public void insert(Recipe recipe) throws SQLException {
        if (getAll().isEmpty())
            recipe.setID((long) 1);
        else {
            recipe.setID((long) getAll().size() + 1);
        }

        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO RECIPE VALUES" +
                        "(" + recipe.getID() + ", "
                        + cav + recipe.getDescription() + cav + ","
                        + cav + recipe.getPatient().getFIO() + cav + ","
                        + cav + recipe.getDoctor().getFIO() + cav + ","
                        + cav + recipe.getDate() + cav + ","
                        + cav + recipe.getValidity() + cav + ","
                        + cav + recipe.getPriority() + cav + ")");
        statement.execute();

        statement = connection.prepareStatement("INSERT INTO PATIENT_DOCTOR VALUES" + "(" +
                recipe.getID() + "," + recipe.getPatient().getID() + "," + recipe.getDoctor().getID() + ")");
        statement.execute();

        statement = connection.prepareStatement("INSERT INTO PATIENT_DOCTOR_RECIPE VALUES" + "(" +
                recipe.getID() + "," + recipe.getID() + ")");
        statement.execute();

        statement.close();
        connection.close();
    }

    public void delete(long id) throws SQLException {
        getById(id).getDoctor();

        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement
                ("DELETE FROM PATIENT_DOCTOR_RECIPE WHERE ID_RECIPE =" + id);
        statement.execute();

        statement = connection.prepareStatement("DELETE FROM PATIENT_DOCTOR WHERE ID_PATIENT = " +
                getById(id).getPatient().getID() + " AND ID_DOCTOR = "
                + getById(id).getDoctor().getID());
        statement.execute();

        statement = connection.prepareStatement("DELETE FROM RECIPE WHERE ID=" + id);
        statement.execute();

        statement.close();
        connection.close();
    }

    public void update(long id, String description, Patient patient
            , Doctor doctor, String data, String validation, String priority) throws SQLException {
        Connection connection = getPoolConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE RECIPE SET DESCRIPTION = "
                + cav + description + cav + ", PACIENT ="
                + cav + patient.getFIO() + cav + ", DOCTOR ="
                + cav + doctor.getFIO() + cav + ", DATA ="
                + cav + data + cav + ", VALIDITY = "
                + cav + validation + cav + ", PRIORITY = "
                + cav + priority + cav + " WHERE ID =" + id);
        statement.execute();

        statement.close();
        connection.close();

    }

}
