### Homework 13
- Simple Library Program
- Used spring boot
- Used spring data jpa with hibernate
- Used spring security with authentication and authorization
- Used spring web with thymeleaf
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

##### Build and launch

Build: `mvn clean package`

Launch: `java -jar target/spring-hw-13-1.0.jar`