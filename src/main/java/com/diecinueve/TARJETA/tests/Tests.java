package com.diecinueve.TARJETA.tests;

import java.util.concurrent.TimeUnit;

import com.diecinueve.TARJETA.Classes.Card;
import com.diecinueve.TARJETA.Classes.Informs;
import com.diecinueve.TARJETA.Classes.Prize;
import com.diecinueve.TARJETA.Classes.Shop;
import com.diecinueve.TARJETA.Classes.User;

public class Tests {

	public static void main(String[] args) {
		
		
		try {
//			//Test user
//			System.out.println("TU1");
//			User.altaUsuario("pruebaUser", "password1");
//			TimeUnit.SECONDS.sleep(2);
//			
//			System.out.println("TU2");
// 			User.bajaUsuario("pruebaUser");
//			TimeUnit.SECONDS.sleep(2);
//
//			
//			System.out.println("TU3");
//			User.altaUsuario("pruebaUser", "password1");
//			User.editUsuario("pruebaUser", "pruebaNueva1");
//			User.bajaUsuario("pruebaUser");
//			TimeUnit.SECONDS.sleep(2);
//
//			
//			//Test tienda
//			System.out.println("TU4");
//			Shop.altaTienda("tiendaEjemplo");
//			TimeUnit.SECONDS.sleep(2);
//
//			
//			System.out.println("TU5");
//			Shop.bajaTienda("tiendaEjemplo");
//			TimeUnit.SECONDS.sleep(2);
//
//			
//			System.out.println("TU6");
//			Shop.altaTienda("tiendaEjemplo");
//			Shop.editTienda("tiendaEjemplo", "tiendaCambio");
//			Shop.bajaTienda("tiendaCambio");
//			TimeUnit.SECONDS.sleep(2);
//
//			
//			//Test premio
//			System.out.println("TU7");
//			Prize.createPrize("premioEjemplo",  "1", 100);
//			TimeUnit.SECONDS.sleep(2);
//
//			
//			System.out.println("TU8");
//			Prize.deletePrize("premioEjemplo");
//			TimeUnit.SECONDS.sleep(2);
//
//			
//			System.out.println("TU9");
//			Prize.createPrize("premioEjemplo",  "1", 100);
// 			Prize.editPrize("premioEjemplo", "premioCambio", "100", 1);
//			Prize.deletePrize("premioCambio");
//			TimeUnit.SECONDS.sleep(2);
//
//			
//			//Test tarjeta
//			System.out.println("TU10");
//			User.altaUsuario("pruebaUser", "password1");
//			Card.createCard("pruebaUser", " usuario1 Apellido1", "606606606", "direccionEjemplo", "emailEjemplo");
//			TimeUnit.SECONDS.sleep(2);
//			Card card = Card.getCardData("pruebaUser");
//			System.out.println(card.fullName);
//			System.out.println(card.adress);
//			System.out.println(card.mail);
//			System.out.println(card.phone);
//			System.out.println(card.points);
// 			
//			System.out.println("TU11");
//			Card.deleteCard("pruebaUser");
//			TimeUnit.SECONDS.sleep(2);
//
//			
//			System.out.println("TU12");
//			Card.createCard("pruebaUser", " usuario1 Apellido1", "606606606", "direccionEjemplo", "emailEjemplo");
// 			Card.editCard("pruebaUser",  "usuaroNuevoApellidoNuevo", "606909909", "direccionEjemploNuevo", "emailEjemploNuevo");
//			User.bajaUsuario("pruebaUser");
//			TimeUnit.SECONDS.sleep(2);
//
// 			
// 			System.out.println("TU13");
//			User.altaUsuario("pruebaUser", "password1");
//			Card.createCard("pruebaUser", " usuario1 Apellido1", "606606606", "direccionEjemplo", "emailEjemplo");
//			Shop.altaTienda("tiendaEjemplo");
//			Card.buy("pruebaUser", "tiendaEjemplo", 10);
//			TimeUnit.SECONDS.sleep(2);
//			
//			
//			System.out.println("TU14");
//			Prize.createPrize("premioEjemplo",  "1", 100);
//			Card.exchange("pruebaUser", "premioEjemplo");
//			Card.deleteCard("pruebaUser");
//			User.bajaUsuario("pruebaUser");
//			TimeUnit.SECONDS.sleep(2);
//
//
//			
//			System.out.println("TU15");
//			User.altaUsuario("pruebaUser", "password1");
//			boolean login = User.login("pruebaUser", "password1");
//			if(!login) throw new Exception();
//			User.bajaUsuario("pruebaUser");
//			TimeUnit.SECONDS.sleep(2);

//			System.out.println("TU17");
//			System.out.println(Informs.informUsers());
			
//			System.out.println("TU18");
//			System.out.println(Informs.informShop());
			
//			System.out.println("TU19");
//			System.out.println(Informs.informCard());
//			
//			System.out.println("TU20");
//			System.out.println(Informs.informPurchases());
//			
//			System.out.println("TU21");
//			System.out.println(Informs.informExchanges());
			
//			System.out.println("TU23");
//			System.out.println(Informs.informMonthlyUser("pruebaUser"));
			
			System.out.println("TU24");
			System.out.println(Informs.informMonthlyAdmin());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
