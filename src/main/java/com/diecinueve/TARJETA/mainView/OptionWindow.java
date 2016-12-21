package com.diecinueve.TARJETA.mainView;

import com.diecinueve.TARJETA.Classes.User;
import com.diecinueve.TARJETA.login.SimpleLoginView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class OptionWindow extends CustomComponent implements View {
	private static final long serialVersionUID = -473885274036372376L;
	public static final String NAME = "common";
	private Label text = new Label();
	private Button logout;

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

		Button solicitarTarjeta = new Button("Request Tarjeta");
		Button eliminarCuenta = new Button("Delete Account");
		Button atras = new Button("Return back");
		
		solicitarTarjeta.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -6250117129810876328L;

			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("Not avaliable");

			}
		});
		
		eliminarCuenta.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 8008170470519832303L;

			@Override
			public void buttonClick(ClickEvent event) {
				String nameButton = event.getButton().getCaption();
				Window w = new Window(nameButton);
				Label l = new Label("Really?");
				Button accept = new Button("Accept");
				Button cancel = new Button("Cancel");
				HorizontalLayout j = new HorizontalLayout(l);
				HorizontalLayout h = new HorizontalLayout(accept,cancel);
				h.setSpacing(true);
				VerticalLayout v = new VerticalLayout(j,h);
				v.setMargin(new MarginInfo(true, true, true, true));
				accept.addClickListener(new ClickListener() {
					private static final long serialVersionUID = -2984165706585825647L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							User.bajaUsuario(text.getValue());
						} catch (Exception e) {
							e.printStackTrace();
							Notification.show("Invalid Operation");
						}
						w.close();
					}

				});
				cancel.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 5135703379856412801L;

					@Override
					public void buttonClick(ClickEvent event) {
						w.close();						
					}
				});
				w.setContent(v);
				w.center();
				UI.getCurrent().addWindow(w);
			}
		});
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
		column1.setSpacing(true);
		column1.setMargin(new MarginInfo(true, true, true, true));
		column1.setSizeUndefined();
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
