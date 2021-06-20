package by.sir.max.library.builder;

import by.sir.max.library.entity.user.UserRole;
import by.sir.max.library.entity.user.User;

import java.sql.Timestamp;

public class UserBuilder {
    private int id;
    private UserRole userRole;
    private String login;
    private String password;
    private boolean isBanned;
    private String firstName;
    private String lastName;
    private String passportSerialNumber;
    private String email;
    private String phoneNumber;
    private Timestamp registrationDate;
    private String address;
    private String logInToken;

    public int getId() {
        return id;
    }

    public UserBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public UserBuilder setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean getBanned() {
        return isBanned;
    }

    public UserBuilder setBanned(boolean banned) {
        isBanned = banned;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassportSerialNumber() {
        return passportSerialNumber;
    }

    public UserBuilder setPassportSerialNumber(String passportSerialNumber) {
        this.passportSerialNumber = passportSerialNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public UserBuilder setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getLogInToken() {
        return logInToken;
    }

    public UserBuilder setLogInToken(String logInToken) {
        this.logInToken = logInToken;
        return this;
    }

    public User build(){
        return new User(this);
    }
}
