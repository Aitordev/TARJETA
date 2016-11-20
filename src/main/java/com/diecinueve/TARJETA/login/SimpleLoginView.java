package com.diecinueve.TARJETA.login;

import com.diecinueve.TARJETA.Classes.User;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class SimpleLoginView extends CustomComponent implements View {

	private static final long serialVersionUID = 7802656818550430070L;

	public static final String NAME = "login";
	private final TextField user;
	private final PasswordField password;
	private final Button loginButton;
	private final Button singInButton;
	private static PasswordField passUser;
	
	public SimpleLoginView() {
		setSizeFull();

		// Create the user input field
		user = new TextField("User:");
		user.setWidth("300px");
		user.setRequired(true);
		user.setInputPrompt("Your Nick (ex. admin)");
		user.setInvalidAllowed(false);

		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		// Create login button
		loginButton = new Button("Login");

		// Create login button
		singInButton = new Button("Sing in");
		
		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(user, password, loginButton, singInButton);
		fields.setCaption("Please login to access the application.");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		setCompositionRoot(viewLayout);
		
		loginButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 4486770008049429194L;

			public void buttonClick(ClickEvent event) {

				if (!user.isValid() || !password.isValid()) {
					return;
				}

				String username = user.getValue();
				String pass = password.getValue();
				
				User user = new User();

				try {
					if (user.login(username,pass)) {
						getSession().setAttribute("user", username);
						getUI().getNavigator().navigateTo(SimpleLoginMainView.NAME);//
					} else {
						// contraseña mal
						password.setValue(null);
						password.focus();
					}
				} catch (Exception e) {
					Notification.show("Cannot connect to DB");
				}
			}
		});
		
		singInButton.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = -6196592872230518395L;

			@Override
			public void buttonClick(ClickEvent event) {
				Window register = new Window("Register");
				TextField nameUser = new TextField("Username:");
				nameUser.setWidth("300px");
				nameUser.setRequired(true);
				nameUser.setInputPrompt("Your Username (ex. Mendi)");
				nameUser.setInvalidAllowed(false);
				passUser = new PasswordField("Enter your Password: (8 character with at least 1 number)");
				passUser.setWidth("300px");
				passUser.addValidator(new PasswordValidator());
				passUser.setRequired(true);
				passUser.setValue("");
				passUser.setNullRepresentation("");
				PasswordField rpassUser = new PasswordField("Verify your Password:");
				rpassUser.setWidth("300px");
				rpassUser.addValidator(new EqualPasswordValidator());
				rpassUser.setRequired(true);
				rpassUser.setValue("");
				rpassUser.setNullRepresentation("");	
				
				
				Button accept = new Button("Accept");
				Button cancel = new Button("Cancel");
				HorizontalLayout buttons = new HorizontalLayout(accept,cancel);
				buttons.setSpacing(true);
				// Add both to a panel
				VerticalLayout fields = new VerticalLayout(nameUser, passUser, rpassUser,buttons);
				fields.setCaption("Welcome to the register window, please give your Username and Password.");
				fields.setSpacing(true);
				fields.setMargin(new MarginInfo(true, true, true, true));
				fields.setSizeUndefined();
				register.setContent(fields);
				register.center();
				UI.getCurrent().addWindow(register);
			}
		});
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// focus username 
		user.focus();
	}

	// Validator for validating the passwords
		private static final class PasswordValidator extends
		AbstractValidator<String> {
			private static final long serialVersionUID = 2774912451923479559L;
			public PasswordValidator() {
				super("The password provided is not valid");
			}
			@Override
			protected boolean isValidValue(String value) {
				// 8 caracteres y al menos un numero
				if (value != null
						&& (value.length() < 8 || !value.matches(".*\\d.*"))) {
					return false;
				}
				return true;
			}
			@Override
			public Class<String> getType() {
				return String.class;
			}
		}
		// Validator for validating the passwords
		private static final class EqualPasswordValidator extends
		AbstractValidator<String> {
			private static final long serialVersionUID = 2774912451923479559L;
			public EqualPasswordValidator() {
				super("The passwords are not equal");
			}
			@Override
			protected boolean isValidValue(String value) {
				
				if (value.equals(passUser.getValue())) {
					return true;
				}
				return false;
			}
			@Override
			public Class<String> getType() {
				return String.class;
			}
		}
}
