package com.diecinueve.TARJETA.mainView;

import com.diecinueve.TARJETA.Classes.Card;
import com.diecinueve.TARJETA.Classes.Informs;
import com.diecinueve.TARJETA.Classes.Prize;
import com.diecinueve.TARJETA.Classes.Shop;
import com.diecinueve.TARJETA.Classes.User;
import com.diecinueve.TARJETA.DatabaseOp.DataBaseOperations;
import com.diecinueve.TARJETA.login.SimpleLoginView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AdminView extends CustomComponent implements View {
	private static final long serialVersionUID = -473885274036372376L;
	public static final String NAME = "admin";
	private Label text;
	private Button lisUser;
	private Button listiendas;
	private Button lisPremios;
	private Button listarjeta;
	private Button borrarUser;
	private Button crearTienda;
	private Button borrarTienda;
	private Button modificarPremios;
	private Button borrarTarjeta;
	private Button borrarPremio;
	private Button modificarTienda;
	private Button modificarTarjeta;
	private Button newPremio;
	private Button logout;
	private Button listapurchases;
	private Button listaexchanges;
	private TextArea textArea;
	private ClickListener newWindowListener;
	private Button monthInfo;

	public AdminView() {
		setSizeFull();
		textArea =new TextArea();
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
					Window w = new Window(nameButton);
					TextArea text = new TextArea(event.getButton().getCaption()+ " information:");
					switch (nameButton) {
					case "Users List":
						text.setValue(Informs.informUsers());
						break;
					case "Tiendas list":
						text.setValue(Informs.informShop());
						break;
					case "Premios list":
						text.setValue(Informs.informPrizes());
						break;
					case "Tarjeta list":
						text.setValue(Informs.informCard());
						break;
					case "Purchases List":
						text.setValue(Informs.informPurchases());
						break;
					case "Exchanges List":
						text.setValue(Informs.informExchanges());
						break;
					case "Informe mesual tarjetas":
						text.setValue(Informs.informMonthlyAdmin());
						break;
					default:
						text.setValue("Not avaliable");
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
					});				
					}
		};

		 lisUser= new Button("Users List");
		 listiendas= new Button("Tiendas list");
		 lisPremios= new Button("Premios list");
		 listarjeta= new Button("Tarjeta list");
		 borrarUser= new Button("Delete user");
		 crearTienda=new Button("New Tienda");
		 borrarTienda= new Button("Delete tienda");
		 modificarPremios= new Button("Modificar premio");
		 borrarPremio= new Button("Delete premio");
		 borrarTarjeta= new Button("Delete Tarjeta");
		 modificarTarjeta= new Button("Modify Tarjeta");
		 modificarTienda= new Button("Modificar Tienda");
		 newPremio= new Button("New Premio");
		 listapurchases = new Button("Purchases List");
		 listaexchanges = new Button("Exchanges List");
		 monthInfo = new Button("Informe mesual tarjetas");
		 monthInfo.addClickListener(newWindowListener);
		 lisUser.addClickListener(newWindowListener);
		 listiendas.addClickListener(newWindowListener);
		 lisPremios.addClickListener(newWindowListener);
		 listarjeta.addClickListener(newWindowListener);
		 listapurchases.addClickListener(newWindowListener);
		 listaexchanges.addClickListener(newWindowListener);
		 borrarUser.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 8008170470519832303L;

				@Override
				public void buttonClick(ClickEvent event) {
					String nameButton = event.getButton().getCaption();
					Window w = new Window(nameButton);
					TextField us =new TextField("User:");
					Button accept = new Button("Accept");
					Button cancel = new Button("Cancel");
					HorizontalLayout j = new HorizontalLayout(us);
					HorizontalLayout h = new HorizontalLayout(accept,cancel);
					h.setSpacing(true);
					h.setMargin(new MarginInfo(true, true, true, true));
					VerticalLayout v = new VerticalLayout(j,h);
					v.setMargin(new MarginInfo(true, true, true, true));
					accept.addClickListener(new ClickListener() {
						private static final long serialVersionUID = -2984165706585825647L;

						@Override
						public void buttonClick(ClickEvent event) {
							try {
								User.bajaUsuario(us.getValue());
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
		 
		 borrarTienda.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 8008170470519832303L;

				@Override
				public void buttonClick(ClickEvent event) {
					String nameButton = event.getButton().getCaption();
					Window w = new Window(nameButton);
					TextField us =new TextField("Tienda:");
					Button accept = new Button("Accept");
					Button cancel = new Button("Cancel");
					HorizontalLayout j = new HorizontalLayout(us);
					HorizontalLayout h = new HorizontalLayout(accept,cancel);
					h.setSpacing(true);
					h.setMargin(new MarginInfo(true, true, true, true));
					VerticalLayout v = new VerticalLayout(j,h);
					v.setMargin(new MarginInfo(true, true, true, true));
					accept.addClickListener(new ClickListener() {
						private static final long serialVersionUID = -2984165706585825647L;

						@Override
						public void buttonClick(ClickEvent event) {
							try {
								Shop.bajaTienda(us.getValue());
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
		 
		 crearTienda.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 8008170470519832303L;

				@Override
				public void buttonClick(ClickEvent event) {
					String nameButton = event.getButton().getCaption();
					Window w = new Window(nameButton);
					TextField us =new TextField("Tienda:");
					Button accept = new Button("Accept");
					Button cancel = new Button("Cancel");
					HorizontalLayout j = new HorizontalLayout(us);
					HorizontalLayout h = new HorizontalLayout(accept,cancel);
					h.setSpacing(true);
					h.setMargin(new MarginInfo(true, true, true, true));
					VerticalLayout v = new VerticalLayout(j,h);
					v.setMargin(new MarginInfo(true, true, true, true));
					accept.addClickListener(new ClickListener() {
						private static final long serialVersionUID = -2984165706585825647L;

						@Override
						public void buttonClick(ClickEvent event) {
							try {
								Shop.altaTienda(us.getValue());
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
		 modificarPremios.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				HorizontalLayout buttons = new HorizontalLayout();
				Window w = new Window();
				buttons.setSpacing(true);
				Button accept = new Button("Modify");
				Button cancel = new Button("Cancel");
				buttons.addComponent(accept);
				buttons.addComponent(cancel);
				TextField nick = new TextField();//TODO
				nick.setCaption("Concept: ");
				nick.setIcon(FontAwesome.EMPIRE);
				TextField nombre = new TextField();//TODO
				nombre.setCaption("New Concept: ");
				nombre.setIcon(FontAwesome.CREDIT_CARD);
				TextField telefono = new TextField();//TODO
				telefono.setIcon(FontAwesome.PHONE);
				telefono.setCaption("Quantity: ");
				TextField dir = new TextField();//TODO
				dir.setIcon(FontAwesome.TRIPADVISOR);
				dir.setCaption("Points: ");
				FormLayout cosas2 = new FormLayout();
				cosas2.addComponent(nick);
				cosas2.addComponent(nombre);
				cosas2.addComponent(telefono);
				cosas2.addComponent(dir);
				VerticalLayout fields = new VerticalLayout(cosas2,buttons);
				fields.setSpacing(true);
				fields.setMargin(new MarginInfo(true, true, true, true));
				fields.setSizeUndefined();
				w.setContent(fields);
				w.center();
				UI.getCurrent().addWindow(w);
				accept.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 8254770808228815879L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							Prize.editPrize(nick.getValue(), nombre.getValue(), telefono.getValue(), Integer.getInteger(dir.getValue()));
						} catch (Exception e) {
							e.printStackTrace();
						};
						w.close();
					}
				});
				cancel.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						w.close();										
					}
				});
				
			}
		});
		 borrarTarjeta.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				String nameButton = event.getButton().getCaption();
				Window w = new Window(nameButton);
				TextField us =new TextField("User Nick:");
				Button accept = new Button("Accept");
				Button cancel = new Button("Cancel");
				VerticalLayout j = new VerticalLayout(us);
				HorizontalLayout h = new HorizontalLayout(accept,cancel);
				h.setSpacing(true);
				h.setMargin(new MarginInfo(true, true, true, true));
				VerticalLayout v = new VerticalLayout(j,h);
				v.setMargin(new MarginInfo(true, true, true, true));
				accept.addClickListener(new ClickListener() {
					private static final long serialVersionUID = -2984165706585825647L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							Card.deleteCard(us.getValue());
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
		 modificarTienda.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				String nameButton = event.getButton().getCaption();
				Window w = new Window(nameButton);
				TextField us =new TextField("Tienda:");
				TextField us2 =new TextField("Tienda Nuevo nombre:");
				Button accept = new Button("Accept");
				Button cancel = new Button("Cancel");
				VerticalLayout j = new VerticalLayout(us,us2);
				HorizontalLayout h = new HorizontalLayout(accept,cancel);
				h.setSpacing(true);
				h.setMargin(new MarginInfo(true, true, true, true));
				VerticalLayout v = new VerticalLayout(j,h);
				v.setMargin(new MarginInfo(true, true, true, true));
				accept.addClickListener(new ClickListener() {
					private static final long serialVersionUID = -2984165706585825647L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							Shop.editTienda(us.getValue(),us2.getValue());
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

		borrarPremio.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				String nameButton = event.getButton().getCaption();
				Window w = new Window(nameButton);
				TextField us =new TextField("Prize concept:");
				Button accept = new Button("Accept");
				Button cancel = new Button("Cancel");
				VerticalLayout j = new VerticalLayout(us);
				HorizontalLayout h = new HorizontalLayout(accept,cancel);
				h.setSpacing(true);
				h.setMargin(new MarginInfo(true, true, true, true));
				VerticalLayout v = new VerticalLayout(j,h);
				v.setMargin(new MarginInfo(true, true, true, true));
				accept.addClickListener(new ClickListener() {
					private static final long serialVersionUID = -2984165706585825647L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							Prize.deletePrize(us.getValue());
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
		
		modificarTarjeta.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				HorizontalLayout buttons = new HorizontalLayout();
				Window w = new Window();
				buttons.setSpacing(true);
				Button accept = new Button("Modify");
				Button cancel = new Button("Cancel");
				buttons.addComponent(accept);
				buttons.addComponent(cancel);
				TextField nick = new TextField();//TODO
				nick.setCaption("Nick: ");
				nick.setIcon(FontAwesome.EMPIRE);
				TextField nombre = new TextField();//TODO
				nombre.setCaption("Nombre: ");
				nombre.setIcon(FontAwesome.CREDIT_CARD);
				TextField telefono = new TextField();//TODO
				telefono.setIcon(FontAwesome.PHONE);
				telefono.setCaption("Telefono: ");
				TextField dir = new TextField();//TODO
				dir.setIcon(FontAwesome.TRIPADVISOR);
				dir.setCaption("Direccion: ");
				TextField email = new TextField();//TODO
				email.setIcon(FontAwesome.MAIL_FORWARD);
				email.setCaption("Email: ");
				FormLayout cosas2 = new FormLayout();
				cosas2.addComponent(nick);
				cosas2.addComponent(nombre);
				cosas2.addComponent(telefono);
				cosas2.addComponent(dir);
				cosas2.addComponent(email);
				VerticalLayout fields = new VerticalLayout(cosas2,buttons);
				fields.setSpacing(true);
				fields.setMargin(new MarginInfo(true, true, true, true));
				fields.setSizeUndefined();
				w.setContent(fields);
				w.center();
				UI.getCurrent().addWindow(w);
				accept.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 8254770808228815879L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							Card.editCard(nick.getValue(), nombre.getValue(), telefono.getValue(), dir.getValue(), email.getValue());
						} catch (Exception e) {
							e.printStackTrace();
						};
						w.close();
					}
				});
				cancel.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						w.close();										
					}
				});
			}				
		});
		
		newPremio.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				HorizontalLayout buttons = new HorizontalLayout();
				Window w = new Window();
				buttons.setSpacing(true);
				Button accept = new Button("Modify");
				Button cancel = new Button("Cancel");
				buttons.addComponent(accept);
				buttons.addComponent(cancel);
				TextField nick = new TextField();//TODO
				nick.setCaption("Concept: ");
				nick.setIcon(FontAwesome.EMPIRE);
				TextField telefono = new TextField();//TODO
				telefono.setIcon(FontAwesome.PHONE);
				telefono.setCaption("Quantity: ");
				TextField dir = new TextField();//TODO
				dir.setIcon(FontAwesome.TRIPADVISOR);
				dir.setCaption("Points: ");
				FormLayout cosas2 = new FormLayout();
				cosas2.addComponent(nick);
				cosas2.addComponent(telefono);
				cosas2.addComponent(dir);
				VerticalLayout fields = new VerticalLayout(cosas2,buttons);
				fields.setSpacing(true);
				fields.setMargin(new MarginInfo(true, true, true, true));
				fields.setSizeUndefined();
				w.setContent(fields);
				w.center();
				UI.getCurrent().addWindow(w);
				accept.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 8254770808228815879L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							Prize.createPrize(nick.getValue(), telefono.getValue(), Integer.getInteger(dir.getValue()));
						} catch (Exception e) {
							e.printStackTrace();
						};
						w.close();
					}
				});
				cancel.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						w.close();										
					}
				});
				
			}
		});
		HorizontalLayout tittle = new HorizontalLayout();
		HorizontalLayout body = new HorizontalLayout();
		VerticalLayout column1 = new VerticalLayout();
		VerticalLayout column2 = new VerticalLayout();
		VerticalLayout column3 = new VerticalLayout();
		HorizontalLayout footer = new HorizontalLayout();

		body.addComponent(column1);
		body.addComponent(column2);
		body.setSpacing(true);
		body.setMargin(new MarginInfo(true, true, true, true));
		body.setSizeUndefined();
		tittle.addComponent(lisUser);
		column1.addComponent(listiendas);
		column1.addComponent(lisPremios);
		column1.addComponent(listarjeta);
		column1.addComponent(modificarTienda);
		column1.addComponent(listapurchases);
		column1.addComponent(listaexchanges);
		column1.addComponent(crearTienda);
		column1.setSpacing(true);
		column1.setMargin(new MarginInfo(true, true, true, true));
		column1.setSizeUndefined();
		column2.addComponent(modificarTarjeta);
		column2.addComponent(borrarTarjeta);
		column2.addComponent(borrarTienda);
		column2.addComponent(borrarUser);
		column2.addComponent(modificarPremios);
		column2.addComponent(borrarPremio);
		column2.addComponent(newPremio);
		column2.addComponent(monthInfo);
		column2.setSpacing(true);
		column2.setMargin(new MarginInfo(true, true, true, true));
		column2.setSizeUndefined();
		column3.addComponent(textArea);
		column3.setSpacing(true);
		column3.setMargin(new MarginInfo(true, true, true, true));
		column3.setSizeUndefined();
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
		textArea.setCaption("Information");
		textArea.setValue("Not Avaliable");
	}
}
