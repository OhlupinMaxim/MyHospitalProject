package editviews;

import DAO.DoctorDAO;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import model.Doctor;
import model.People;

import java.sql.SQLException;

//import static org.hospital.MyUI.DOCTORVIEW;

public class EditViewDoctor extends VerticalLayout implements View {
    public EditViewDoctor() throws SQLException {
        setSizeFull();
        DoctorDAO doctorDAO = new DoctorDAO();
        TextField doctor_name = new TextField("Имя Врача","");
        TextField doctor_s_name = new TextField("Отчество Врача","");
        TextField doctor_t_name = new TextField("Фамилия Врача","");
        TextField doctor_spec = new TextField("Специализация","");

        Grid<Doctor> grid = new Grid<>("Выберете изменяемую строку");
        grid.addColumn(People::getFirst_name).setCaption("Имя");
        grid.addColumn(People::getSecond_name).setCaption("Отчество");
        grid.addColumn(People::getThird_name).setCaption("Фамилия");
        grid.addColumn(Doctor::getSpecialization).setCaption("Специализация");
        try {
            grid.setItems(doctorDAO.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Button OK = new Button("ОК", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    if (doctor_name.isEmpty())
                        doctor_name.setValue(grid.asSingleSelect().getValue().getFirst_name());
                    if (doctor_s_name.isEmpty())
                        doctor_s_name.setValue(grid.asSingleSelect().getValue().getSecond_name());
                    if (doctor_t_name.isEmpty())
                        doctor_t_name.setValue(grid.asSingleSelect().getValue().getThird_name());
                    if (doctor_spec.isEmpty())
                        doctor_spec.setValue(grid.asSingleSelect().getValue().getSpecialization());
                    doctorDAO.update(
                            doctorDAO.getByFIO(
                                    grid.asSingleSelect().getValue().getFirst_name(),
                                    grid.asSingleSelect().getValue().getSecond_name(),
                                    grid.asSingleSelect().getValue().getThird_name()
                            ).getID(),
                            doctor_name.getValue(),
                            doctor_s_name.getValue(),
                            doctor_t_name.getValue(),
                            doctor_spec.getValue());
                    ListDataProvider<Doctor> dataProvider = (ListDataProvider<Doctor>) grid
                            .getDataProvider();
                    dataProvider.getItems().remove(grid.asSingleSelect().getValue());
                    dataProvider.getItems().add(new Doctor(
                            doctor_name.getValue(),
                            doctor_s_name.getValue(),
                            doctor_t_name.getValue(),
                            doctor_spec.getValue()));
                    dataProvider.refreshAll();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

//                getUI().getNavigator().navigateTo(DOCTORVIEW);
                UI.getCurrent().getPage().reload();
            }
        });

        Button cancel = new Button("Отмена", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //UI.getCurrent().getPage().reload();
//                getUI().getNavigator().navigateTo(DOCTORVIEW);
            }
        });
        addComponent(grid);
        setComponentAlignment(grid, Alignment.BOTTOM_CENTER);
        addComponents(OK, cancel);
        addComponent(doctor_name);
        addComponent(doctor_s_name);
        addComponent(doctor_t_name);
        addComponent(doctor_spec);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
