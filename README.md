Proyecto realizado por: Patrik Paul Sirbu, Jacob Altenburger Villar y Pedro Alonso Tapia Lobo
-------------------
Fecha de entrega: 29/09/2024
-------------------
Link al repositorio: 
# CasoPráctico

Esta aplicación fue creada con Bootify.io - consejos sobre cómo trabajar con el código [pueden encontrarse aquí](https://bootify.io/next-steps/).

## Desarrollo

Al iniciar la aplicación, se ejecuta `docker compose up` y la aplicación se conectará a los servicios incluidos. [Docker](https://www.docker.com/get-started/) debe estar disponible en el sistema actual.

Durante el desarrollo, se recomienda usar el perfil `local`. En IntelliJ, puedes agregar `-Dspring.profiles.active=local` en las opciones de VM de la configuración de ejecución después de habilitar esta propiedad en "Modificar opciones". Crea tu propio archivo `application-local.yml` para sobrescribir configuraciones durante el desarrollo.

El IDE debe soportar Lombok. Para IntelliJ, instala el plugin de Lombok y habilita el procesamiento de anotaciones - [aprende más aquí](https://bootify.io/next-steps/spring-boot-with-lombok.html).

Además de la aplicación de Spring Boot, también debe iniciarse el DevServer - para esto se requiere la versión 20 de [Node.js](https://nodejs.org/). Al primer uso y después de actualizaciones, es necesario instalar las dependencias:

```
npm install
```

El DevServer puede iniciarse de la siguiente manera:

```
npm run devserver
```

Usando un proxy, toda la aplicación es accesible ahora en `localhost:8081`. Todos los cambios en las plantillas y archivos JS/CSS son inmediatamente visibles en el navegador.

## Compilación

La aplicación puede construirse usando el siguiente comando:

```
./mvnw clean package
```

Node.js se descarga automáticamente utilizando el `frontend-maven-plugin` y los archivos finales JS/CSS se integran en el archivo JAR.

Inicia tu aplicación con el siguiente comando, aquí con el perfil `production`:

```
java -Dspring.profiles.active=production -jar ./target/caso-practico-0.0.1-SNAPSHOT.jar
```

Si es necesario, se puede crear una imagen de Docker con el plugin de Spring Boot. Agrega `SPRING_PROFILES_ACTIVE=production` como una variable de entorno al ejecutar el contenedor.

```
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=grupo1/caso-practico
```

## Lecturas adicionales

Enlaces útiles para el proyecto:

- [Maven docs](https://maven.apache.org/guides/index.html)
- [Referencia de Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Referencia de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa.html)
- [Documentación de Thymeleaf](https://www.thymeleaf.org/documentation.html)
- [Conceptos de Webpack](https://webpack.js.org/concepts/)
- [Documentación de npm](https://docs.npmjs.com/)
- [Documentación de Bootstrap](https://getbootstrap.com/docs/5.3/getting-started/introduction/)
- [Aprender Spring Boot con Thymeleaf](https://www.wimdeblauwe.com/books/taming-thymeleaf/)

---

Este README incluye las instrucciones generales sobre cómo configurar, desarrollar, y desplegar el proyecto. Si necesitas agregar más información específica del proyecto, házmelo saber.
