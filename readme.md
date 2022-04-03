# Episodate Rest API

[![Episodate Rest API Actions Status](https://github.com/trodix/episodate-api/actions/workflows/maven.yml/badge.svg)](https://github.com/trodix/episodate-api/actions)

> This project is a Rest API for the episodate chrome extenion

## Development setup

You can configure development properties in **src/resources/application-dev.properties** file.

### Database

By default, a H2 in memory database is used for development purposes.

### Run

Run the app with maven: `mvn spring-boot:run`

You can access the api at <http://localhost:8000/api>

### Swagger documentation

For specs, go to <http://localhost:8000/v2/api-docs>

For Swagger UI, go to <http://localhost:8000/swagger-ui/>

## Tests

Postman tests are available at [./src/test/resources](./src/test/resources)

## Installation

1. Build the project to generate the **.jar** file

   `mvn clean install`

2. Copy the **target/episodate.jar** file to the serveur

    `scp -p target/episodate.jar root@myserver:/opt/episodate/episodate.jar`

3. Create a **application.properties** file at **/opt/episodate** with production credentials

    ```properties
    spring.profiles.active=prod

    # Driver Postgresql
    spring.datasource.driverClassName=org.postgresql.Driver

    # Allows Hibernate to generate SQL optimized for a particular DBMS
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

    # Connection url for the database (if different to default)
    spring.datasource.url=jdbc:postgresql://localhost:5432/<YOUR_DATABASE_NAME>

    # Username and password
    spring.datasource.username=<YOUR_USERNAME>
    spring.datasource.password=<YOUR_PASSWORD>

    # JWT secret
    app.jwt-secret=<YOUR_SECRET_KEY>
    ```

4. Create a new user for this application

    `useradd -r episodate`

5. Set permissions to the app directory for the user

    `chown -R episodate:episodate /opt/episodate`

6. Create a systemd service in **/etc/systemd/system/episodate.service** file

    ```properties
    [Unit]
    Description=Todo SpringBoot Api
    After=syslog.target

    [Service]
    User=episodate
    ExecStart=/opt/episodate/episodate.jar -Dspring.config.location=/opt/episodate/application.properties
    SuccessExitStatus=143

    [Install]
    WantedBy=multi-user.target
    ```

7. Enable the service for booting the app at server boot

    `service episodate enable`

8. Start the service

    `service episodate start`

## Default User

When the application run for the first time, a default user will be generated.

The default user email is: `admin@example.com`

The default username is: `admin`

The default user roles are: `admin`, corresponding to the **ROLE_ADMIN** ERole enum.

You can override this values in your **application.properties** file:

```properties
# ===============================
# = Default admin credentials
# ===============================
app.default-user.username = admin
app.default-user.email = admin@exemple.com
app.default-user.roles = admin
```

The default password is auto-generated and availlable in the logs of the application:

Example of output:

```log
=============== Default credentials are: admin / 62e3766b-934f-4db8-a22a-245eafb220f4 ===============
```

In production environment, you can get the default credentials log by running this command:

`journalctl -u episodate.service | grep "Default credentials"`
