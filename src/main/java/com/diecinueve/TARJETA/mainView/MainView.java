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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
	private Button inform;

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
				if (nameButton.equals("Options"))
					getUI().getNavigator().navigateTo(OptionWindow.NAME);
				else{
					Window w = new Window(nameButton);
					TextArea text = new TextArea(event.getButton().getCaption()+ " information:");
					switch (nameButton) {
					case "My Compras":
						text.setValue("Not avaliable");
						break;
					case "My Tarjeta":
						text.setValue("Not avaliable");
						break;
					case "My Data":
						text.setValue("Not avaliable");
						break;
					case "My Points":
						text.setValue("Not avaliable");
						break;
					case "Inform User":
						////////////////////////////////////////////AAAAAAAAAAQQQQQQQQQQQQUIIIIIIIIIIIII!!!!!!!!
						text.setValue("Not avaliable");
						break;
					default:
						break;
					}
					Button accept = new Button("Accept");
					HorizontalLayout buttons = new HorizontalLayout(accept);
					buttons.setSpacing(true);
					// Add both to a panel
					VerticalLayout fields = new VerticalLayout(text,buttons);
					fields.setSpacing(true);
					fields.setMargin(new MarginInfo(true, true, true, true));
					fields.setSizeUndefined();
					w.setContent(fields);
					w.center();
					UI.getCurrent().addWindow(w);

					accept.addClickListener(new ClickListener() {
						private static final long serialVersionUID = -2395838433272275856L;

						@Override
						public void buttonClick(ClickEvent event) {
							w.close();							
						}
					});				}
			}
		};

		misCompras = new Button("My Compras");
		miTarjeta = new Button("My Tarjeta");
		misDatos = new Button("My Data");
		misPuntos = new Button("My Points");
		opciones = new Button("Options");
		inform = new Button("Inform User");
		misCompras.addClickListener(newWindowListener);
		miTarjeta.addClickListener(newWindowListener);
		misDatos.addClickListener(newWindowListener);
		misPuntos.addClickListener(newWindowListener);
		opciones.addClickListener(newWindowListener);
		inform.addClickListener(newWindowListener);

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
