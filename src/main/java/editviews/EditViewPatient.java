package editviews;

import DAO.PatientDAO;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.navigator.View;
import model.Patient;
import model.People;

import java.sql.SQLException;

//import static org.hospital.MyUI.PATIENTVIEW;

public class EditViewPatient extends VerticalLayout implements View {

    public EditViewPatient() throws SQLException {
        setSizeFull();
        PatientDAO patientDAO = new PatientDAO();
        TextField patient_name = new TextField("Имя Пациента", "");
        TextField patient_s_name = new TextField("Отчество Пациента", "");
        TextField patient_t_name = new TextField("Фамилия Пациента", "");
        TextField patient_phone = new TextField("Номер телефона", "");

        Grid<Patient> grid = new Grid<>("Выберите изменяемую строку");
        grid.addColumn(People::getFirst_name).setCaption("Имя");
        grid.addColumn(People::getSecond_name).setCaption("Отчество");
        grid.addColumn(People::getThird_name).setCaption("Фамилия");
        grid.addColumn(Patient::getPhone_number).setCaption("Номер Телефона");
        try {
            grid.setItems(patientDAO.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Button OK = new Button("Применить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    if (patient_name.isEmpty())
                        patient_name.setValue(grid.asSingleSelect().getValue().getFirst_name());
                    if (patient_s_name.isEmpty())
                        patient_s_name.setValue(grid.asSingleSelect().getValue().getSecond_name());
                    if (patient_t_name.isEmpty())
                        patient_t_name.setValue(grid.asSingleSelect().getValue().getThird_name());
                    if (patient_phone.isEmpty())
                        patient_phone.setValue(grid.asSingleSelect().getValue().getPhone_number());
                    patientDAO.update(
                            patientDAO.getByFIO(
                                    grid.asSingleSelect().getValue().getFirst_name(),
                                    grid.asSingleSelect().getValue().getSecond_name(),
                                    grid.asSingleSelect().getValue().getThird_name()
                            ).getID(),
                            patient_name.getValue(),
                            patient_s_name.getValue(),
                            patient_t_name.getValue(),
                            patient_phone.getValue());
                    ListDataProvider<Patient> dataProvider = (ListDataProvider<Patient>) grid
                            .getDataProvider();
                    dataProvider.getItems().remove(grid.asSingleSelect().getValue());
                    dataProvider.getItems().add(new Patient(
                            patient_name.getValue(),
                            patient_s_name.getValue(),
                            patient_t_name.getValue(),
                            patient_phone.getValue()));
                    dataProvider.refreshAll();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                UI.getCurrent().getPage().reload();
//                getUI().getNavigator().navigateTo(PATIENTVIEW);
            }
        });

        Button cancel = new Button("Отмена", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //UI.getCurrent().getPage().reload();
//                getUI().getNavigator().navigateTo(PATIENTVIEW);
            }
        });
        addComponent(grid);
        setComponentAlignment(grid, Alignment.BOTTOM_CENTER);
        addComponents(OK, cancel);
        addComponent(patient_name);
        addComponent(patient_s_name);
        addComponent(patient_t_name);
        addComponent(patient_phone);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
