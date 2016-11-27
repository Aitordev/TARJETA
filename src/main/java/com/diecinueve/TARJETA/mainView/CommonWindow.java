package com.diecinueve.TARJETA.mainView;

import com.diecinueve.TARJETA.login.SimpleLoginView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class CommonWindow extends CustomComponent implements View {
	private static final long serialVersionUID = -473885274036372376L;
	public static final String NAME = "common";
    private Label text = new Label();
    private Label info = new Label();
    private Button logout;

    public CommonWindow() {
    	
    	logout = new Button("Logout", new Button.ClickListener() {
    		private static final long serialVersionUID = 3365371576893038127L;
    		@Override
            public void buttonClick(ClickEvent event) {
                // "Logout" the user
                getSession().setAttribute("user", null);
                // Refresh this view, should redirect to login view
                getUI().getNavigator().navigateTo(SimpleLoginView.NAME);
            }
        });
    	
        setCompositionRoot(new CssLayout(text,info, logout));
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // Get the user name from the session
        String username = String.valueOf(getSession().getAttribute("user"));
        String inf = String.valueOf(getSession().getAttribute("window"));
        // And show the username
        text.setValue("Hello " + username);
        info.setValue("Ventana:  " + username);
    }
}
