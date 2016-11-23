package com.diecinueve.TARJETA.mainView;

import com.diecinueve.TARJETA.login.SimpleLoginView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MainView extends CustomComponent implements View {
	private static final long serialVersionUID = -473885274036372376L;
	public static final String NAME = "main";
    private Label text;
    private Button misDatos;
    private Button miTarjeta;
    private Button misCompras;
    private Button misPuntos;
    private Button logout;
    private Button opciones;
    private ClickListener newWindowListener;

    public MainView() {
    	
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
			
			private static final long serialVersionUID = -3186265215968011758L;

			@Override
			public void buttonClick(ClickEvent event) {
				String nameButton = event.getButton().getCaption();
				getSession().setAttribute("window", nameButton);
				getUI().getNavigator().navigateTo(CommonWindow.NAME);
			}
		};
    	
    	misCompras = new Button("Mis Compras");
    	miTarjeta = new Button("Mi Tarjeta");
    	misDatos = new Button("Mis Datos");
    	misPuntos = new Button("Mis Puntos");
    	opciones = new Button("Opciones");
    	misCompras.addClickListener(newWindowListener);
    	miTarjeta.addClickListener(newWindowListener);
    	misDatos.addClickListener(newWindowListener);
    	misPuntos.addClickListener(newWindowListener);
    	opciones.addClickListener(newWindowListener);
    	
    	VerticalLayout main = new VerticalLayout();
    	HorizontalLayout tittle = new HorizontalLayout();
    	HorizontalLayout body = new HorizontalLayout();
    	VerticalLayout column1 = new VerticalLayout();
    	VerticalLayout column2 = new VerticalLayout();
    	HorizontalLayout footer = new HorizontalLayout();
    	main.addComponent(tittle);
    	main.addComponent(body);
    	main.addComponent(footer);
    	body.addComponent(column1);
    	body.addComponent(column2);
    	tittle.addComponent(text);
    	column1.addComponent(misDatos);
    	column1.addComponent(miTarjeta);
    	column2.addComponent(misCompras);
    	column2.addComponent(misPuntos);
    	footer.addComponent(opciones);
    	footer.addComponent(logout);
    	setCompositionRoot(main);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // Get the user name from the session
        String username = String.valueOf(getSession().getAttribute("user"));

        // And show the username
        text.setValue("Hello " + username);
    }
}
