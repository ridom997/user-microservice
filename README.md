<br />
<div align="center">
<h3 align="center">PRAGMA POWER-UP</h3>
  <p align="center">
    In this challenge you are going to design the backend of a system that centralizes the services and orders of a restaurant chain that has different branches in the city.
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites

* JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MySQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Installation

1. Clone the repository
2. Create a new database in MySQL called powerup
3. Update the database connection settings
   ```yml
   # src/main/resources/application-dev.yml
   spring:
      datasource:
          url: jdbc:mysql://localhost/powerup
          username: root
          password: <your-password>
   ```
5. Run the project (Right-click the class UserMicroserviceApplication and choose Run) to create the tables of the database
6. After the tables are created execute src/main/resources/data.sql content to populate the database


<!-- USAGE -->
## Usage

1. Right-click the class UserMicroserviceApplication and choose Run
2. Open [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html) in your web browser
3. Test the endpoints (view guide)

<!-- GUIDE -->
## GUIDE (SPANISH)
Cada vez que se vaya a revisar un lote de historias de usuario se recomienda borrar la base de datos existente (en caso de tenerla), y generarla otra vez con los pasos 4 y 5 del apartado "Installation"

### Datos importantes: 
1. Desde la hu-5 no pueden existir 2 o más usuarios con el mismo correo. 
2. Desde la hu-6 no pueden existir 2 o más usuarios con par variables iguales (dniNumber, dniType).
3. En caso de llegar un usuario con las caracteristicas antes mencionadas no se podrá agregar a la base de datos, todo esto para seguir el modelo E-R del reto.
4. Las credenciales de Administrador (correo: email@some.com, clave: 1234). Este usuario tiene el id = 1.
5. Las credenciales de Propietario (correo: email2@some.com, clave: 1234). Este usuario tiene el id = 2.

### HU1: 
+ Es necesario ejecutar del data.sql la seccion "-- hu-1"
+ Crear un nuevo Propietario. "/user/createOwner" estando autenticado como administrador (admin)
### HU2: 
+ Se hizo un endpoint para validar si un usuario es el rol proporcionado "/user/validateRole". Se debe estar autenticado como administrador (admin).
### HU5:
+ Es necesario ejecutar del data.sql la seccion "-- hu-5"
+ Es necesario usar el endpoint "/auth/login" con las credenciales previamente proporcionadas, o tambien con los usuarios con rol Propietarios (owner) previamente creados.
### HU6:
+ En caso de que en base de datos en la tabla "user" columna "dni_number" haya una constraint tipo UNIQUE, favor eliminarla (esto aplica solo si se tiene la base de datos de la hu-5 hacia atrás, desde la hu-6 esta acción no es necesaria).
+ Es necesario ejecutar del data.sql la seccion "-- hu-6"
+ Para que la petición se cumpla correctamente hay que tener el microservicio foodcourt-microservice activo.
+ Es necesario usar el endpoint "/user/createEmployee" estando autenticado como un propietario (owner).
+ En la peticion el id del rol empleado (employee) es el 4 (idRole = 4).

<!-- ROADMAP -->
## Tests

- Right-click the test folder and choose Run tests with coverage
