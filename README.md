I Want It Data Way API
---
Before running this application, you will need to set up the following environment variables to enable a database connection:
```
DB_HOST
DB_USERNAME
DB_PASSWORD
DB_NAME
JWT_SECRET
TEST_VALID_EMAIL_API
TEST_VALID_USER_PASSWORD_API
```

How to start the DropwizardWebService application
---
1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/IWantItDataWay-API-1.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080/api/hello-world`

Swagger
---
To see your applications Swagger UI `http://localhost:8080/swagger`

Tests
---
1. Run `mvn clean test` to run tests.
2. Run `mvn clean integration-test` to run integration tests.
