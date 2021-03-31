package views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;

import java.io.File;
import java.nio.file.Path;

import static org.hospital.MyUI.*;
//import static org.hospital.MyUI.RECIPEVIEW;

public class StartView extends VerticalLayout implements View {
    public StartView(){
        //картинку можно вставить но нужен "правильный путь"
        //Image img = new Image("", new FileResource(new File("C:\Java Project\Hospital Project\src\main\resources\scale_1200.jpg")));
        //addComponent(img);
        //setComponentAlignment(img, Alignment.BOTTOM_CENTER);
//        Button doctor_view_button = new Button("Доктор",
//                (Button.ClickListener) event ->{
//                    //UI.getCurrent().getPage().reload();
//                    getUI().getNavigator().navigateTo(DOCTORVIEW);
//                }
//        );
//        Button pacient_view_button = new Button("Пациент",
//                (Button.ClickListener) clickEvent ->{
//                    //UI.getCurrent().getPage().reload();
//                    getUI().getNavigator().navigateTo(PATIENTVIEW);
//                });
//
//        Button recipe_view_button = new Button("Рецепт",
//                (Button.ClickListener) clickEvent ->{
//                    UI.getCurrent().getPage().reload();
//                    getUI().getNavigator().navigateTo(RECIPEVIEW);
//                });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        addComponent(horizontalLayout);
        setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
//        horizontalLayout.addComponent(doctor_view_button);
//        horizontalLayout.setComponentAlignment(doctor_view_button, Alignment.MIDDLE_LEFT);
//        horizontalLayout.addComponent(pacient_view_button);
//        horizontalLayout.setComponentAlignment(pacient_view_button, Alignment.MIDDLE_CENTER);
//        horizontalLayout.addComponent(recipe_view_button);
//        horizontalLayout.setComponentAlignment(recipe_view_button, Alignment.MIDDLE_RIGHT);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
