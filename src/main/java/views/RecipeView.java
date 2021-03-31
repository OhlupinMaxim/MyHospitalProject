package views;

import DAO.DoctorDAO;
import DAO.PatientDAO;
import DAO.RecipeDAO;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import model.Doctor;
import model.Patient;
import model.People;
import model.Recipe;

import java.sql.SQLException;

import static org.hospital.MyUI.*;

public class RecipeView extends VerticalLayout implements View {
    public RecipeView() throws SQLException, ClassNotFoundException {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        //кнопка выхода на главную
        Button return_button = new Button("На главную",
                (Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(""));

        //таблица врачей
        //Grid<Doctor> grid1 = new DoctorView().getTableDoctor();
        Grid<Doctor> grid1 = new Grid<>("Для добавления, выберете строчку");
        DoctorDAO doctorDAO = new DoctorDAO();
        grid1.addColumn(People::getFirst_name).setCaption("Имя");
        grid1.addColumn(People::getSecond_name).setCaption("Отчество");
        grid1.addColumn(People::getThird_name).setCaption("Фамилия");
        grid1.addColumn(Doctor::getSpecialization).setCaption("Специализация");
        try {
            grid1.setItems(doctorDAO.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //таблица пациентов
        //Grid<Patient> grid2 = new PatientView().getTablePatient();
        Grid<Patient> grid2 = new Grid<>("Для добавления, выберете строчку");
        PatientDAO patientDAO = new PatientDAO();
        grid2.addColumn(People::getFirst_name).setCaption("Имя");
        grid2.addColumn(People::getSecond_name).setCaption("Отчество");
        grid2.addColumn(People::getThird_name).setCaption("Фамилия");
        grid2.addColumn(Patient::getPhone_number).setCaption("Номер Телефона");
        try {
            grid2.setItems(patientDAO.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //таблица рецептов
        Grid<Recipe> grid = new Grid<>();
        RecipeDAO recipeDAO = new RecipeDAO();

        grid.addColumn(Recipe::getDescription).setCaption("Описание");
        grid.addColumn(recipe -> {
            return recipe.getPatient().getFIO();
        }).setCaption("Пациент");
        grid.addColumn(recipe -> {
            return recipe.getDoctor().getFIO();
        }).setCaption("Доктор");
        grid.addColumn(Recipe::getDate).setCaption("Дата назначения");
        grid.addColumn(Recipe::getValidity).setCaption("Дата окончания");
        grid.addColumn(Recipe::getPriority).setCaption("Приоритет");

        try {
            grid.setItems(recipeDAO.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //добавить рецепт
        TextArea descr = new TextArea("Описание", "");
        TextField data = new TextField("Дата назначения YYYY-MM-DD", "");
        TextField validity = new TextField("Дата окончания YYYY-MM-DD", "");
        TextField priority = new TextField("Приоритет (Важный или Нормальный)", "");

        Button insert = new Button("Добавить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                RecipeDAO recipeDAO = null;
                try {
                    recipeDAO = new RecipeDAO();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    recipeDAO.insert(new Recipe(
                            descr.getValue(),
                            grid2.asSingleSelect().getValue(),
                            grid1.asSingleSelect().getValue(),
                            data.getValue(),
                            validity.getValue(),
                            priority.getValue())
                    );

                    ListDataProvider<Recipe> dataProvider = (ListDataProvider<Recipe>) grid
                            .getDataProvider();
                    dataProvider.getItems().add(new Recipe(
                            descr.getValue(),
                            grid2.asSingleSelect().getValue(),
                            grid1.asSingleSelect().getValue(),
                            data.getValue(),
                            validity.getValue(),
                            priority.getValue()));
                    dataProvider.refreshAll();
                    descr.clear();
                    data.clear();
                    validity.clear();
                    priority.clear();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                UI.getCurrent().getPage().reload();
            }
        });

        //удалить рецепт
        Button delete = new Button("Удалить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                RecipeDAO recipeDAO = null;
                try {
                    recipeDAO = new RecipeDAO();
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    recipeDAO.delete(grid.asSingleSelect().getValue().getID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ListDataProvider<Recipe> dataProvider = (ListDataProvider<Recipe>) grid
                        .getDataProvider();
                dataProvider.getItems().remove(grid.asSingleSelect().getValue());
                dataProvider.refreshAll();
            }
        });

        //изменить запись
        Button update = new Button("Изменить", (Button.ClickListener)
                clickEvent -> {
//                    getUI().getNavigator().navigateTo(EDITRECIPEVIEW);
                    UI.getCurrent().getPage().reload();
                });
        addComponents(update, delete);

        Button filter = new Button("Фильтр", (Button.ClickListener)
                clickEvent -> {
                    getUI().getNavigator().navigateTo(FILTERVIEW);
                    //UI.getCurrent().getPage().reload();
                });
        addComponents(update, delete);

        addComponents(return_button, filter);
        setComponentAlignment(return_button, Alignment.TOP_LEFT);
        addComponents(grid, horizontalLayout);
        horizontalLayout.addComponents(descr, insert, delete, update);
        addComponents(data, validity, priority);
        addComponents(grid1, grid2);
        //addComponents(delete, update);


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
