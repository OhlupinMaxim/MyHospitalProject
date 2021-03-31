package views;

import DAO.DoctorDAO;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import model.Doctor;
import model.People;
import java.sql.SQLException;

//import static org.hospital.MyUI.EDITDOCTORVIEW;

public class DoctorView extends VerticalLayout implements View {
    private Grid<Doctor> TableDoctor = null;

    public DoctorView() throws SQLException {

        //кнопка выхода на главную
        Button return_button = new Button("На главную",
                (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(""));

        //таблица врачей
        Grid<Doctor> grid = new Grid<>();
        DoctorDAO doctorDAO = new DoctorDAO() ;
        grid.addColumn(People::getFirst_name).setCaption("Имя");
        grid.addColumn(People::getSecond_name).setCaption("Отчество");
        grid.addColumn(People::getThird_name).setCaption("Фамилия");
        grid.addColumn(Doctor::getSpecialization).setCaption("Специализация");
        try {
            grid.setItems(doctorDAO.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        TableDoctor = grid;

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        horizontalLayout.addComponent(grid);
        horizontalLayout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
        addComponents(horizontalLayout);
        setComponentAlignment(horizontalLayout, Alignment.TOP_LEFT);
        horizontalLayout.addComponent(verticalLayout);
        verticalLayout.addComponent(return_button);
        verticalLayout.setComponentAlignment(return_button, Alignment.TOP_RIGHT);

        //добавить врача
        TextField f_name = new TextField("Имя","");
        TextField s_name = new TextField("Отчество","");
        TextField t_name = new TextField("Фамилия","");
        TextField spec = new TextField("Специализация","");

        Button insert = new Button("Добавить", new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                DoctorDAO doctorDAO = null;
                try {
                    doctorDAO = new DoctorDAO();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    doctorDAO.insert(new Doctor(
                            f_name.getValue(),
                            s_name.getValue(),
                            t_name.getValue(),
                            spec.getValue()));
                    ListDataProvider<Doctor> dataProvider = (ListDataProvider<Doctor>) grid
                            .getDataProvider();
                    dataProvider.getItems().add(new Doctor(
                            f_name.getValue(),
                            s_name.getValue(),
                            t_name.getValue(),
                            spec.getValue()));
                    dataProvider.refreshAll();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                f_name.clear();
                s_name.clear();
                t_name.clear();
                spec.clear();

            }

        });
        horizontalLayout.addComponents(verticalLayout);
        verticalLayout.addComponents(f_name,s_name,t_name,spec,insert);

        //удалить врача
        Button delete = new Button("Удалить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                DoctorDAO doctorDAO = null;
                try {
                    doctorDAO = new DoctorDAO();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    doctorDAO.delete(doctorDAO.getByFIO(
                            grid.asSingleSelect().getValue().getFirst_name(),
                            grid.asSingleSelect().getValue().getSecond_name(),
                            grid.asSingleSelect().getValue().getThird_name())
                            .getID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ListDataProvider<Doctor> dataProvider = (ListDataProvider<Doctor>) grid
                        .getDataProvider();
                dataProvider.getItems().remove(grid.asSingleSelect().getValue());
                dataProvider.refreshAll();
            }
        });
        //изменить запись

        Button update = new Button("Изменить", (Button.ClickListener)
                clickEvent -> {
//                    getUI().getNavigator().navigateTo(EDITDOCTORVIEW);
                    //getUI().getCurrent().getPage().reload();
                });
        addComponents(update,delete);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    public Grid<Doctor> getTableDoctor(){
        return TableDoctor;
    }
}
