package com.diecinueve.TARJETA.mainView;

import com.diecinueve.TARJETA.login.SimpleLoginView;
import com.diecinueve.TARJETA.login.SimpleLoginView.EqualPasswordValidator;
import com.diecinueve.TARJETA.login.SimpleLoginView.PasswordValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class OptionWindow extends CustomComponent implements View {
	private static final long serialVersionUID = -473885274036372376L;
	public static final String NAME = "common";
    private Label text = new Label();
    private Button logout;
	private ClickListener newWindowListener;

    public OptionWindow() {
    	
		setSizeFull();

    	text = new Label();
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
    	
    	newWindowListener = new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Window w = new Window("Register");
				TextArea text = new TextArea(event.getButton().getCaption());
				Button accept = new Button("Accept");
				Button cancel = new Button("Cancel");
				HorizontalLayout buttons = new HorizontalLayout(accept,cancel);
				buttons.setSpacing(true);
				// Add both to a panel
				VerticalLayout fields = new VerticalLayout(text,buttons);
				fields.setCaption("Welcome to the register window, please give your Username and Password.");
				fields.setSpacing(true);
				fields.setMargin(new MarginInfo(true, true, true, true));
				fields.setSizeUndefined();
				w.setContent(fields);
				w.center();
				UI.getCurrent().addWindow(w);
				
			}
		};
    	
    	Button modificarDatos = new Button("Mis Compras");
    	Button solicitarTarjeta = new Button("Mi Tarjeta");
    	Button modificarTarjeta = new Button("Mis Datos");
    	Button eliminarCuenta = new Button("Mis Puntos");
    	Button atras = new Button("Opciones");
    	misCompras.addClickListener(newWindowListener);
    	miTarjeta.addClickListener(newWindowListener);
    	misDatos.addClickListener(newWindowListener);
    	misPuntos.addClickListener(newWindowListener);
    	atras.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainView.NAME);
				
			}
		});
    	
    	HorizontalLayout tittle = new HorizontalLayout();
    	HorizontalLayout body = new HorizontalLayout();
    	VerticalLayout column1 = new VerticalLayout();
    	VerticalLayout column2 = new VerticalLayout();
    	HorizontalLayout footer = new HorizontalLayout();
    	
    	body.addComponent(column1);
    	body.addComponent(column2);
    	body.setSpacing(true);
    	body.setMargin(new MarginInfo(true, true, true, true));
    	body.setSizeUndefined();
    	tittle.addComponent(text);
    	column1.addComponent(modificarDatos);
    	column1.addComponent(solicitarTarjeta);
    	column1.setSpacing(true);
    	column1.setMargin(new MarginInfo(true, true, true, true));
    	column1.setSizeUndefined();
    	column2.addComponent(modificarTarjeta);
    	column2.addComponent(eliminarCuenta);
    	column2.setSpacing(true);
    	column2.setMargin(new MarginInfo(true, true, true, true));
    	column2.setSizeUndefined();
    	footer.addComponent(atras);
    	footer.addComponent(logout);
    	footer.setMargin(new MarginInfo(true, false, true, true));
    	footer.setSizeUndefined();
    	
    	VerticalLayout fields = new VerticalLayout(tittle, body, footer);
		fields.setCaption("Options:");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();
    	
		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		setCompositionRoot(viewLayout);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // Get the user name from the session
        String username = String.valueOf(getSession().getAttribute("user"));
        // And show the username
        text.setValue("Hello " + username);
    }
}
