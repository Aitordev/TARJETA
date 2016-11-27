package com.diecinueve.TARJETA.mainView;

import com.diecinueve.TARJETA.login.SimpleLoginView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;
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
    	column1.addComponent(misDatos);
    	column1.addComponent(miTarjeta);
    	column1.setSpacing(true);
    	column1.setMargin(new MarginInfo(true, true, true, true));
    	column1.setSizeUndefined();
    	column2.addComponent(misCompras);
    	column2.addComponent(misPuntos);
    	column2.setSpacing(true);
    	column2.setMargin(new MarginInfo(true, true, true, true));
    	column2.setSizeUndefined();
    	footer.addComponent(opciones);
    	footer.addComponent(logout);
    	footer.setMargin(new MarginInfo(true, false, true, true));
    	footer.setSizeUndefined();
    	
    	VerticalLayout fields = new VerticalLayout(tittle, body, footer);
		fields.setCaption("Please select an option.");
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
