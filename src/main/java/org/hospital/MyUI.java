package org.hospital;

import editviews.*;

import views.*;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    Navigator navigator;

    public static final String DOCTORVIEW = "doctorList";
    public static final String PATIENTVIEW = "patientList";
    public static final String RECIPEVIEW = "recipeList";
    public static final String EDITDOCTORVIEW = "editDoctor";
    public static final String EDITPATIENTVIEW = "editPatient";
    public static final String EDITRECIPEVIEW = "editRecipe";
    public static final String FILTERVIEW = "filter";

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("HOSPITAL");

        navigator = new Navigator(this, this);
        navigator.addView("", new StartView());

        try {
            navigator.addView(DOCTORVIEW, new DoctorView());
            navigator.addView(PATIENTVIEW, new PatientView());
            navigator.addView(RECIPEVIEW, new RecipeView());
            navigator.addView(EDITDOCTORVIEW, new EditViewDoctor());
            navigator.addView(EDITPATIENTVIEW, new EditViewPatient());
            navigator.addView(EDITRECIPEVIEW, new EditViewRecipe());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        setNavigator(navigator);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

}