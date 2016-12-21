#TARJETA
Proyecto IISS II

####Planteamiento
La empresa IS_II, con multitud de tiendas asociadas, se dispone a sacar al mercado una nueva
tarjeta de fidelización de clientes. Al realizar compras en cualquiera de sus tiendas asociadas,
los clientes acumularán puntos en su tarjeta que podrán canjear por premios que la empresa irá
ofertando periódicamente.

####Desarolladores:
* [Aitordev](https://github.com/Aitordev/)
* [albertomendi](https://github.com/albertomendi/)
* [Argronnar](https://github.com/Argronnar/)

####Licencia:
[GPL-3.0](../master/LICENSE)

####Dependencias para contribuir en el desarrollo:
* [Vaadin 7.7.6](https://vaadin.com/home)
* [Tomcat 9.0.0.M15](http://tomcat.apache.org/download-90.cgi)
* [MySQL 5.7.17](https://www.mysql.com/)

####Ejecucion de la aplicacion web
No es necesario instalar Vaadin para ejecutar la aplicacion, solo será necesario, Tomcat y MySQL.

1. Importar la base de datos *DB.sql* en MySql
1. Guardar *Tarjeta.war* en el *Directorio de instalacion de Tomcat* -> *webapps*
1. Ejecutar *startup.bat* (Windows) or *startup.sh* (Linux) en *Directorio de instalacion de Tomcat* -> *bin*
1. Abrir la URL http://localhost:8080/TARJETA/
