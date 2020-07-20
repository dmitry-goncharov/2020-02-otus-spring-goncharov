### Homework 17

- Simple Library Program
- Used spring boot and data jpa with hibernate
- Used spring security with authentication and authorization
- Used spring web with thymeleaf and actuator
- Used H2 database
- Scheme and data files are stored in resources

##### Authentication and authorization

- Admin
  - login/password: `admin/admin`
  - Permissions:
    - view/add/edit/delete genres/authors/books/comments
    - view users

- Editor
  - login/password: `editor/editor`
  - Permissions:
    - view/add/edit/delete genres/authors/books/comments

- User
  - login/password: `user/user`
  - Permissions:
    - view genres/authors/books/comments
    - add comments

- Without any authentication:
  - Permissions:
    - view genres/authors/books/comments

##### Management

- Endpoints
  - health
  - metrics
  - prometheus
  - logfile
  - info
  - user
  - content

##### Build and launch

Build: `mvn clean package`

Launch: `java -jar target/spring-hw-17-1.0.jar`

##### Build and run in Docker

Build: docker build -t hw17 .

Run: docker run -p 8080:8080 --rm hw17
