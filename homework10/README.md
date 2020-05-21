### Homework 10
- Simple Library REST API
- Used spring boot
- Used spring data jpa with hibernate
- Used H2 database
- Scheme and data files are stored in resources
- Used react for spa

#### Build and launch:

##### rest:

`rest> mvn clean package`

`rest> java -jar target/spring-hw-10-1.0.jar` 

##### spa:

`spa> npm install`

`spa> npm start`

##### all in one:

`rest> mvn clean package -P all-in-one`

`rest> java -jar target/spring-hw-10-1.0.jar`
