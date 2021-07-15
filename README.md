# Library final task for EPAM JWD
###### Website visitors are provided with the following services:
***
### *Guest:*
- registration (with sending email, which contains single-use auth-token for first log in)
- view book catalog list
- search catalog books 
- switch language (with saving chosen lang into the cookies) 
***
### *User:*
- switch language (with saving chosen lang into the cookies) 
- view book catalog list
- search catalog books
- order book for reading hole or account and return it after usage (with sending email, which contains order status)
- view all own orders list
- cancel own orders if it has pending status (with sending email, which contains order status)
- log in by the login\password or login by _remember me_ command next time if checkbox had being activated (cookie token)
- log in by forget password command (with sending email, which contains single-use auth-token) 
- log out (its flush _remember me_ cookie token if its exist)
- profile data edit
***
### *Admin:*
- __all user's allowed services__
- add the book and book components
- approve or reject user's book orders (with sending email, which contains order status)
- ban or unban users (with sending email, which contains user's ban status)
- view online users list
- view all users list 

***
### Components used for the project:
- Java 8
- Maven
- Git
- JavaEE: Servlet, JSP, JSTL, JavaMail
- Server / Servlet container: Tomcat 10
- HTTPS by SSL certificate
- Database: MySQL
- JDBC
- Logger: Log4J2
- Tests: JUnit 4


***
### CREATED BY MAXIM SYROMOLOTOV 2021 
