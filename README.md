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
-Las cédulas y correos de los usuarios deben ser unicos. \
-Las credenciales de Administrador (correo: email@some.com, clave: 1234). Este usuario tiene el id = 1.\
-Las credenciales de Propietario (correo: email2@some.com, clave: 1234). Este usuario tiene el id = 2.

+ HU1: Crear un nuevo Propietario (se crea con id desde el 3 en adelante) "/user/createOwner".
+ HU2: Se hizo un endpoint para validar si un usuario es el rol proporcionado "/user/validateRole"
+ HU5: Es necesario usar el endpoint "/auth/login" con las credenciales previamente proporcionadas, o tambien con los usuarios Propietarios previamente creados.

<!-- ROADMAP -->
## Tests

- Right-click the test folder and choose Run tests with coverage
