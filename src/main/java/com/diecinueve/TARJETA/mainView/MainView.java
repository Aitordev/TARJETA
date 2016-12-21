package com.diecinueve.TARJETA.mainView;

import com.diecinueve.TARJETA.Classes.Card;
import com.diecinueve.TARJETA.Classes.Informs;
import com.diecinueve.TARJETA.Classes.User;
import com.diecinueve.TARJETA.login.SimpleLoginView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class MainView extends CustomComponent implements View {
	private static final long serialVersionUID = -473885274036372376L;
	public static final String NAME = "main";
	private Label text;
	private Button misDatos;
	private Button logout;
	private Button miTarjeta;
	private Button misCompras;
	private Button misPuntos;
	private Button misPremios;
	private Button opciones;
	private Button monthInfo;
	private ClickListener newWindowListener;
	private Layout cosas;
	private Layout cosas2;
	private String username;
	private TextField nick;
	private Window w2;


	public MainView() {
		setSizeFull();
		nick = new TextField(); 
		cosas = new FormLayout();
		cosas2 = new FormLayout();
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
				HorizontalLayout buttons = new HorizontalLayout();
				Button accept = new Button("Accept");
				Button accept2= new Button("Accept");
				Button modify = new Button("Modify");
				Button cancel = new Button("Cancel");
				cosas.removeAllComponents();
				if (nameButton.equals("Options"))
					getUI().getNavigator().navigateTo(OptionWindow.NAME);
				else{
					Window w = new Window(nameButton);
					Label text = new Label(event.getButton().getCaption()+ " information:");
					cosas.addComponent(text);

					switch (nameButton) {
					case "My Purchases":
						TextArea pur = new TextArea();
						pur.setValue(Informs.informPurchases(nick.getValue()));//TODO
						cosas.addComponent(pur);
						buttons.addComponent(accept);
						break;
					case "My Tarjeta":
						Button informacion = new Button("Informacion");
						informacion.addClickListener(new ClickListener() {

							private static final long serialVersionUID = 1L;

							@Override
							public void buttonClick(ClickEvent event) {
								cosas2.removeAllComponents();
								HorizontalLayout buttons = new HorizontalLayout();
								w2 = new Window();
								Card tar = null;
								try {
									tar = Card.getCardData(nick.getValue());
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								buttons.setSpacing(true);
								buttons.addComponent(modify);
								buttons.addComponent(cancel);
								TextField nombre = new TextField();//TODO
								if (tar != null) nombre.setValue(tar.fullName);
								nombre.setCaption("Nombre: ");
								nombre.setIcon(FontAwesome.CREDIT_CARD);
								TextField telefono = new TextField();//TODO
								if (tar != null) telefono.setValue(tar.phone);

								telefono.setIcon(FontAwesome.PHONE);
								telefono.setCaption("Telefono: ");
								TextField dir = new TextField();//TODO
								if (tar != null) dir.setValue(tar.adress);

								dir.setIcon(FontAwesome.TRIPADVISOR);
								dir.setCaption("Direccion: ");
								TextField email = new TextField();//TODO
								if (tar != null) email.setValue(tar.mail);
								email.setIcon(FontAwesome.MAIL_FORWARD);
								email.setCaption("Email: ");
								cosas2.addComponent(nombre);
								cosas2.addComponent(telefono);
								cosas2.addComponent(dir);
								cosas2.addComponent(email);
								VerticalLayout fields = new VerticalLayout(cosas2,buttons);
								fields.setSpacing(true);
								fields.setMargin(new MarginInfo(true, true, true, true));
								fields.setSizeUndefined();
								w2.setContent(fields);
								w2.center();
								UI.getCurrent().addWindow(w2);
								modify.addClickListener(new ClickListener() {
									private static final long serialVersionUID = 8254770808228815879L;

									@Override
									public void buttonClick(ClickEvent event) {
										try {
											Card.editCard(nick.getValue(), nombre.getValue(), telefono.getValue(), dir.getValue(), email.getValue());
										} catch (Exception e) {
											e.printStackTrace();
										};
										w2.close();
									}
								});
								cancel.addClickListener(new ClickListener() {

									private static final long serialVersionUID = 1L;

									@Override
									public void buttonClick(ClickEvent event) {
										w2.close();										
									}
								});
							}

						});
						Button crear = new Button("Crear");
						crear.addClickListener(new ClickListener() {
							private static final long serialVersionUID = 1L;

							@Override
							public void buttonClick(ClickEvent event) {
								cosas2.removeAllComponents();
								HorizontalLayout buttons = new HorizontalLayout();
								w2 = new Window();
								buttons.setSpacing(true);
								buttons.addComponent(accept2);
								buttons.addComponent(cancel);
								TextField nombre = new TextField();
								nombre.setCaption("Nombre: ");
								nombre.setIcon(FontAwesome.CREDIT_CARD);
								TextField telefono = new TextField();
								telefono.setIcon(FontAwesome.PHONE);
								telefono.setCaption("Telefono: ");
								TextField dir = new TextField();
								dir.setIcon(FontAwesome.TRIPADVISOR);
								dir.setCaption("Direccion: ");
								TextField email = new TextField();
								email.setIcon(FontAwesome.MAIL_FORWARD);
								email.setCaption("Email: ");
								cosas2.addComponent(nombre);
								cosas2.addComponent(telefono);
								cosas2.addComponent(dir);
								cosas2.addComponent(email);
								VerticalLayout fields = new VerticalLayout(cosas2,buttons);
								fields.setSpacing(true);
								fields.setMargin(new MarginInfo(true, true, true, true));
								fields.setSizeUndefined();
								w2.setContent(fields);
								w2.center();
								UI.getCurrent().addWindow(w2);
								accept2.addClickListener(new ClickListener() {
									private static final long serialVersionUID = 8254770808228815879L;

									@Override
									public void buttonClick(ClickEvent event) {
										try {
											Card.createCard(nick.getValue(), nombre.getValue(), telefono.getValue(), dir.getValue(), email.getValue());
										} catch (Exception e) {
											e.printStackTrace();
										};
										w2.close();
									}
								});
								cancel.addClickListener(new ClickListener() {

									private static final long serialVersionUID = 1L;

									@Override
									public void buttonClick(ClickEvent event) {
										w2.close();										
									}
								});
							}

						});
						Button borrar = new Button("Borrar tarjeta");
						borrar.addClickListener(new ClickListener() {

							private static final long serialVersionUID = 1L;

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
											Card.deleteCard(nick.getValue());
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
										if (w2!=null) w2.close();
									}
								});
								w.setContent(v);
								w.center();
								UI.getCurrent().addWindow(w);

							}
						});
						Button add = new Button("Añadir compra");
						VerticalLayout a = new VerticalLayout(informacion,crear);
						VerticalLayout b = new VerticalLayout(borrar,add);						
						a.setSpacing(true);
						b.setSpacing(true);
						buttons.addComponent(a);
						buttons.addComponent(b);
						break;
					case "My Data":
						nick.setCaption("Nick: ");
						nick.setIcon(FontAwesome.USER);
						nick.setReadOnly(true);
						TextField tarjeta = new TextField();
						try {
							tarjeta.setValue(""+User.getCardId(nick.getValue()));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						tarjeta.setCaption("Nª Tarjeta: ");
						tarjeta.setIcon(FontAwesome.CREDIT_CARD);
						tarjeta.setReadOnly(true);
						TextField newpass = new TextField();
						newpass.setIcon(FontAwesome.BEER);
						newpass.setCaption("New Pass: ");
						cosas.addComponent(nick);
						cosas.addComponent(tarjeta);
						cosas.addComponent(newpass);
						modify.addClickListener(new ClickListener() {
							private static final long serialVersionUID = 1L;

							@Override
							public void buttonClick(ClickEvent event) {
								try {
									User.editUsuario(nick.getValue(), newpass.getValue());
									w.close();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						buttons.addComponent(modify);
						buttons.addComponent(cancel);
						break;
					case "My Points":
						TextField poi = new TextField();
						try {
							poi.setValue(Card.getMyPoints(nick.getValue()));
						} catch (Exception e) {
							e.printStackTrace();
						}
						poi.setIcon(FontAwesome.UPLOAD);
						poi.setCaption("Points: ");
						poi.setReadOnly(true);
						cosas.addComponent(poi);
						buttons.addComponent(accept);
						break;
					case "Premios":
						Image pa = new Image(null, new ThemeResource("pa.jpg"));
						Image pb = new Image(null, new ThemeResource("pb.jpg"));
						cosas.addComponent(pa);
						cosas.addComponent(pb);
						buttons.addComponent(accept);
						break;
					case "Informe mensual":
						TextArea in = new TextArea();
						in.setValue(Informs.informMonthlyUser(nick.getValue()));//TODO
						cosas.addComponent(in);
						buttons.addComponent(accept);
						break;
					default:
						break;
					}
					buttons.setSpacing(true);
					// Add both to a panel
					VerticalLayout fields = new VerticalLayout(cosas,buttons);
					fields.setSpacing(true);
					fields.setMargin(new MarginInfo(true, true, true, true));
					fields.setSizeUndefined();
					w.setContent(fields);
					w.center();
					UI.getCurrent().addWindow(w);
					ClickListener close = new ClickListener() {
						private static final long serialVersionUID = -2395838433272275856L;

						@Override
						public void buttonClick(ClickEvent event) {
							w.close();		
						}
					};
					accept.addClickListener(close);
					accept.setClickShortcut(KeyCode.ENTER);
					accept.addStyleName(Reindeer.BUTTON_DEFAULT);
					cancel.addClickListener(close);
				}
			}
		};

		misCompras = new Button("My Purchases");
		miTarjeta = new Button("My Tarjeta");
		misDatos = new Button("My Data");
		misPuntos = new Button("My Points");
		misPremios = new Button("Premios");
		opciones = new Button("Options");
		monthInfo = new Button("Informe mensual");
		misCompras.addClickListener(newWindowListener);
		miTarjeta.addClickListener(newWindowListener);
		misDatos.addClickListener(newWindowListener);
		misPuntos.addClickListener(newWindowListener);
		misPremios.addClickListener(newWindowListener);
		opciones.addClickListener(newWindowListener);
		monthInfo.addClickListener(newWindowListener);

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
		column2.addComponent(misPremios);
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
		username = String.valueOf(getSession().getAttribute("user"));

		// And show the username
		text.setValue("Hello " + username);
		nick.setValue(username);
	}
}
