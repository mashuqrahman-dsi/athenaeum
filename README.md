#Generate JOOQ code from schema.sql 

mvn clean generate-sources -Pgenerate

#Run the application

mvn spring-boot:run 