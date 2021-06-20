package by.sir.max.library.validator;

import by.sir.max.library.builder.UserBuilder;
import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.ValidatorException;
import by.sir.max.library.factory.ValidatorFactory;
import by.sir.max.library.validatior.UserValidator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserValidatorTest {
    private static final UserValidator userValidator =  ValidatorFactory.getInstance().getUserValidator();

    private User validUser;
    private User invalidUserWithNullFields;

    @Before
    public void init() {
        validUser = new UserBuilder()
                .setLogin("login")
                .setPassword("password")
                .setFirstName("firstName")
                .setLastName("lastName")
                .setPassportSerialNumber("MN1234567")
                .setEmail("testemail@mail.io")
                .setPhoneNumber("+375121231212")
                .setAddress("address")
                .build();

        invalidUserWithNullFields = new User();
    }

    @Test
    public void testValidateNewUserPositive() throws ValidatorException {
        userValidator.validateNewUser(validUser);
        assertNotNull(validUser);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewUserLoginNegative() throws ValidatorException {
        validUser.setLogin("");
        userValidator.validateNewUser(validUser);
        assertNotNull(validUser);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewUserPasswordNegative() throws ValidatorException {
        validUser.setPassword("");
        userValidator.validateNewUser(validUser);
        assertNotNull(validUser);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewUserEmailNegative() throws ValidatorException {
        validUser.setEmail("");
        userValidator.validateNewUser(validUser);
        assertNotNull(validUser);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewUserPhoneNumberNegative() throws ValidatorException {
        validUser.setPhoneNumber("");
        userValidator.validateNewUser(validUser);
        assertNotNull(validUser);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewUserPassportSNNegative() throws ValidatorException {
        validUser.setPassportSerialNumber("");
        userValidator.validateNewUser(validUser);
        assertNotNull(validUser);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewUserFirstnameNegative() throws ValidatorException {
        validUser.setFirstName("");
        userValidator.validateNewUser(validUser);
        assertNotNull(validUser);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewUserLastnameNegative() throws ValidatorException {
        validUser.setLastName("");
        userValidator.validateNewUser(validUser);
        assertNotNull(validUser);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewUserAddressNegative() throws ValidatorException {
        validUser.setAddress("");
        userValidator.validateNewUser(validUser);
        assertNotNull(validUser);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewUserWithNullFieldsNegative() throws ValidatorException {
        userValidator.validateNewUser(invalidUserWithNullFields);
        assertNull(invalidUserWithNullFields);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNewNullUserNegative() throws ValidatorException {
        userValidator.validateNewUser(null);
        assertNull(null);
    }
}
