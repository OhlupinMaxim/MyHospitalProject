package views;

import DAO.PatientDAO;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import model.Patient;
import model.People;

import java.sql.SQLException;

import static org.hospital.MyUI.EDITPATIENTVIEW;


public class PatientView extends VerticalLayout implements View {
    private Grid<Patient> TablePatient = null;
    public PatientView() throws SQLException {

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        addComponent(horizontalLayout);
        //кнопка выхода на главную
        Button return_button = new Button("На главную",
                (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(""));
        horizontalLayout.addComponent(verticalLayout);
        verticalLayout.addComponent(return_button);
        verticalLayout.setComponentAlignment(return_button, Alignment.TOP_RIGHT);

        //таблица пациентов
        Grid<Patient> grid = new Grid<>();
        PatientDAO patientDAO = new PatientDAO() ;
        grid.addColumn(People::getFirst_name).setCaption("Имя");
        grid.addColumn(People::getSecond_name).setCaption("Отчество");
        grid.addColumn(People::getThird_name).setCaption("Фамилия");
        grid.addColumn(Patient::getPhone_number).setCaption("Номер Телефона");
        try {
            grid.setItems(patientDAO.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        TablePatient = grid;
        horizontalLayout.addComponent(grid);
        horizontalLayout.setComponentAlignment(grid, Alignment.BOTTOM_LEFT);

        //добавить пациента
        TextField f_name = new TextField("Имя","");
        TextField s_name = new TextField("Отчество","");
        TextField t_name = new TextField("Фамилия","");
        TextField num = new TextField("Номер телефона","");

        Button insert = new Button("Добавить", new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                PatientDAO patientDAO = null;
                try {
                    patientDAO = new PatientDAO();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    patientDAO.insert(new Patient(
                            f_name.getValue(),
                            s_name.getValue(),
                            t_name.getValue(),
                            num.getValue()));
                    ListDataProvider<Patient> dataProvider = (ListDataProvider<Patient>) grid
                            .getDataProvider();
                    dataProvider.getItems().add(new Patient(
                            f_name.getValue(),
                            s_name.getValue(),
                            t_name.getValue(),
                            num.getValue()));
                    dataProvider.refreshAll();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                f_name.clear();
                s_name.clear();
                t_name.clear();
                num.clear();
            }
        });
        horizontalLayout.addComponents(verticalLayout);
        verticalLayout.addComponents(f_name,s_name,t_name,num,insert);

        //удалить пациента
        Button delete = new Button("Удалить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                PatientDAO patientDAO = null;
                try {
                    patientDAO = new PatientDAO();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    patientDAO.delete(patientDAO.getByFIO(grid.asSingleSelect().getValue().getFirst_name(),
                            grid.asSingleSelect().getValue().getSecond_name(),
                            grid.asSingleSelect().getValue().getThird_name()).getID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ListDataProvider<Patient> dataProvider = (ListDataProvider<Patient>) grid
                        .getDataProvider();
                dataProvider.getItems().remove(grid.asSingleSelect().getValue());
                dataProvider.refreshAll();
            }
        });

        //изменить запись

        Button update = new Button("Изменить", (Button.ClickListener)
                clickEvent -> {
                    getUI().getNavigator().navigateTo(EDITPATIENTVIEW);
                    //UI.getCurrent().getPage().reload();
                });
        addComponents(update,delete);

        addComponents(delete,update);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    public Grid<Patient> getTablePatient(){
        return TablePatient;
    }
}
