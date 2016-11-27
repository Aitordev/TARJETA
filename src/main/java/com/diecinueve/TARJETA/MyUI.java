package com.diecinueve.TARJETA;

import javax.servlet.annotation.WebServlet;

import com.diecinueve.TARJETA.login.SimpleLoginView;
import com.diecinueve.TARJETA.mainView.OptionWindow;
import com.diecinueve.TARJETA.mainView.AdminView;
import com.diecinueve.TARJETA.mainView.MainView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;


@Theme("mytheme")
public class MyUI extends UI {

	private static final long serialVersionUID = 7631387814122037077L;

	@Override
    protected void init(VaadinRequest vaadinRequest) {
        new Navigator(this, this);
        getNavigator().addView(SimpleLoginView.NAME, SimpleLoginView.class);//
        getNavigator().addView(MainView.NAME, MainView.class);
        getNavigator().addView(OptionWindow.NAME, OptionWindow.class);
        getNavigator().addView(AdminView.NAME, AdminView.class);
        getNavigator().setErrorView(SimpleLoginView.class);
        getNavigator().addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = 8868055238974896938L;

			@Override
            public boolean beforeViewChange(ViewChangeEvent event) {

                // Mirar si esta loggeado
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof SimpleLoginView;

                if (!isLoggedIn && !isLoginView) {
                    // Redirecionar a login si no login
                    getNavigator().navigateTo(SimpleLoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // si alguien quiere login y ya login el entonces a la mierda
                    return false;
                }

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {

            }
        });

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

		private static final long serialVersionUID = -1413965626316122781L;
    }
}
