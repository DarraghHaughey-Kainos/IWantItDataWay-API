FROM maven:latest
WORKDIR /code
COPY . /code
RUN mvn clean install -DskipTests=true
EXPOSE 8080
ARG DB_HOST
ENV DB_HOST ${DB_HOST}
ARG DB_USERNAME
ENV DB_USERNAME ${DB_USERNAME}
ARG DB_PASSWORD
ENV DB_PASSWORD ${DB_PASSWORD}
ARG DB_NAME
ENV DB_NAME ${DB_NAME}
CMD ["java","-jar", "/code/target/IWantItDataWay-API-1.0-SNAPSHOT.jar", "server", "/code/config.yml"]