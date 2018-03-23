#Prequisite: 

download open library dump from : https://openlibrary.org/data/ol_dump_latest.txt.gz . Untar.gz it and set the path name to openlibrary.file in the application properties. It contains ISBN information of 130 million books. 

create a schema ATHENAEUM in mysql and run schema.sql there. Put mysql credentials into application.properties. 

run mongodb and put the credentials into application.properties. 

set an empty folder for lucene indexes as lucene.index.location property of application.properties. 

Once run - it will first create the book records and start indexing afterwards. 

#Run the application

mvn spring-boot:run 

#Swagger API Doc for the Project

http://localhost:8080/swagger-ui.html

#How it works:

get /api/booksearch/title/{searchTitle}
get /api/booksearch/isbn/{isbn}

The above APIs will provide BookIDs of searched books. 

get /api/bookinformation/{bookID} 

Using the bookID one can request for bookinformation. If the information is present in the  it will be shown immediately. Otherwise a request will be received and processed internally. The api will show the information on a later time if the information is received and processed. 

#Other commands
#Generate JOOQ code from schema.sql 

mvn clean generate-sources -Pgenerate


