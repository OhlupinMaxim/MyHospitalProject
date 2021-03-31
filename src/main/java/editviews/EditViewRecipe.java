package editviews;

import DAO.RecipeDAO;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import model.Recipe;

import java.sql.SQLException;

//import static org.hospital.MyUI.RECIPEVIEW;

public class EditViewRecipe extends VerticalLayout implements View {
    public EditViewRecipe() throws SQLException, ClassNotFoundException {
        HorizontalLayout hcontetn = new HorizontalLayout();

        Grid<Recipe> grid = new Grid<>("Выберите изменяемую строку");
        RecipeDAO recipeDAO = new RecipeDAO() ;

        grid.addColumn(Recipe::getDescription).setCaption("Описание");
        grid.addColumn(recipe -> recipe.getPatient().getFIO()).setCaption("Пациент");
        grid.addColumn(recipe -> recipe.getDoctor().getFIO()).setCaption("Доктор");
        grid.addColumn(Recipe::getDate).setCaption("Дата назначения");
        grid.addColumn(Recipe::getValidity).setCaption("Дата окончания");
        grid.addColumn(Recipe::getPriority).setCaption("Приоритет");

        try {
            grid.setItems(recipeDAO.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        TextArea descr = new TextArea("Описание");
        TextField data = new TextField("Дата выдачи");
        TextField validity = new TextField("Дата окончания");
        TextField priority = new TextField("Приоритет");

        Button OK = new Button("OK", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    if(descr.isEmpty())
                        descr.setValue(grid.asSingleSelect().getValue().getDescription());
                    if(data.isEmpty())
                        data.setValue(grid.asSingleSelect().getValue().getDate());
                    if(validity.isEmpty())
                        validity.setValue(grid.asSingleSelect().getValue().getValidity());
                    if(validity.isEmpty())
                        priority.setValue(grid.asSingleSelect().getValue().getPriority());
                    recipeDAO.update( grid.asSingleSelect().getValue().getID(),
                            descr.getValue(),
                            grid.asSingleSelect().getValue().getPatient(),
                            grid.asSingleSelect().getValue().getDoctor(),
                            data.getValue(),
                            validity.getValue(),
                            priority.getValue());
                    ListDataProvider<Recipe> dataProvider = (ListDataProvider<Recipe>) grid
                            .getDataProvider();
                    dataProvider.getItems().remove(grid.asSingleSelect().getValue());
                    dataProvider.getItems().add(new Recipe(
                            descr.getValue(),
                            grid.asSingleSelect().getValue().getPatient(),
                            grid.asSingleSelect().getValue().getDoctor(),
                            data.getValue(),
                            validity.getValue(),
                            priority.getValue()));
                    dataProvider.refreshAll();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                JavaScript.eval("close()");
                getUI().close();
                UI.getCurrent().getPage().reload();
            }
        });

        Button cancel = new Button("Отмена", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //UI.getCurrent().getPage().reload();
//                getUI().getNavigator().navigateTo(RECIPEVIEW);
            }
        });
        hcontetn.addComponent(grid);
        addComponents(OK, cancel);
        addComponents(descr, data, validity, priority);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
