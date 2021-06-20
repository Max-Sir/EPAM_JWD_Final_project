package by.sir.max.library.validatior;

import by.sir.max.library.entity.user.User;
import by.sir.max.library.exception.ValidatorException;
import org.apache.commons.lang3.StringUtils;

public class UserValidator {
    private static final String LOGIN_REGEX = "^[\\w-]{3,25}$";
    private static final String PASSWORD_REGEX = "^[\\w-]{8,16}$";
    private static final String EMAIL_REGEX = "^(([\\w-]+)@([\\w]+)\\.([\\p{Lower}]{2,6}))$";
    private static final String PHONE_REGEX = "^[+]?[\\d]{7,15}$";
    private static final String PASSPORT_SN_REGEX = "^[\\p{Upper}]{2}[\\d]{7}$";
    private static final int MAX_EMAIL_FIELD_LENGTH = 45;
    private static final int MAX_FIELD_LENGTH = 25;

    public void validateNewUser(User user) throws ValidatorException {
        if (user == null) {
            throw new ValidatorException("service.commonError");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validatePhone(user.getPhoneNumber());
        validatePassportSN(user.getPassportSerialNumber());
        validateFieldLength(user.getFirstName(), user.getLastName(), user.getAddress());
    }

    public void validateUpdatedUser(User user) throws ValidatorException {
        if (user == null) {
            throw new ValidatorException("service.commonError");
        }
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validatePhone(user.getPhoneNumber());
        validateFieldLength(user.getAddress());
    }

    public void validateLogin(String login) throws ValidatorException {
        if (StringUtils.isBlank(login) || !login.matches(LOGIN_REGEX)) {
            throw new ValidatorException("validation.user.registration.login");
        }
    }

    private void validatePhone(String phone) throws ValidatorException {
        if (StringUtils.isBlank(phone) || !phone.matches(PHONE_REGEX)) {
            throw new ValidatorException("validation.user.registration.phone");
        }
    }

    public void validatePassword(String password) throws ValidatorException {
        if (StringUtils.isBlank(password) || !password.matches(PASSWORD_REGEX)) {
            throw new ValidatorException("validation.user.registration.password");
        }
    }

    public void validateEmail(String email) throws ValidatorException {
        if (StringUtils.isBlank(email) || email.length() > MAX_EMAIL_FIELD_LENGTH || !email.matches(EMAIL_REGEX)) {
            throw new ValidatorException("validation.user.registration.email");
        }
    }

    private void validatePassportSN(String passport) throws ValidatorException {
        if (StringUtils.isBlank(passport) || !passport.matches(PASSPORT_SN_REGEX)) {
            throw new ValidatorException("validation.user.registration.passportSN");
        }
    }

    private void validateFieldLength(String... fields) throws ValidatorException {
        for (String field : fields) {
            if (StringUtils.isBlank(field) || field.length() > MAX_FIELD_LENGTH) {
                throw new ValidatorException("validation.user.registration.fieldlength");
            }
        }
    }
}
