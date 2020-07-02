### Homework 14
- Data migration program from jpa repositories to mongo repositories
- Scheme and data files are stored in resources
- Used spring boot, spring data jpa, spring data mongo, spring shell
- Used h2 in-memory database, embedded mongo database

#### Shell commands:

`start` - start data migration

`status` - get status data migration

`genres` - show all genres from final repository

`authors` - show all authors from final repository

`books` - show all books from final repository

`comments` - show all comments from final repository

#### Build and launch

Build: `mvn clean package`

Launch: `java -jar target/spring-hw-14-1.0.jar`