package by.sir.max.library.util;

import by.sir.max.library.exception.UtilException;
import by.sir.max.library.factory.UtilFactory;

import org.junit.Test;

import static org.junit.Assert.*;

public class HashGeneratorUtilTest {
    private static final String PASSWORD = "passWord1234";
    private static final HashGeneratorUtil hashGenerator = UtilFactory.getInstance().getHashGeneratorUtil();

    @Test
    public void testGenerateHashPasswordNotNullPositive() throws UtilException {
        String hashPass = hashGenerator.generateHash(PASSWORD);
        assertNotNull(hashPass);
    }

    @Test
    public void testGeneratedHashPasswordIsEqualStoredPositive() throws UtilException {
        String hashPass = hashGenerator.generateHash(PASSWORD);
        boolean result = hashGenerator.validatePassword(PASSWORD, hashPass);
        assertTrue(result);
    }

    @Test
    public void testGeneratedHashPasswordIsNotEqualStoredPositive() throws UtilException {
        String hashPass = hashGenerator.generateHash(PASSWORD);
        String differentPassword = "diffPassWord";
        boolean result = hashGenerator.validatePassword(differentPassword, hashPass);
        assertFalse(result);
    }

    @Test(expected = UtilException.class)
    public void testGenerateHashPasswordNotNullNegative() throws UtilException {
        String hashPass = hashGenerator.generateHash(null);
        assertNotNull(hashPass);
    }

    @Test(expected = UtilException.class)
    public void testGeneratedHashPasswordIsNotEqualStoredNegative() throws UtilException {
        String hashPass = hashGenerator.generateHash(PASSWORD);
        boolean result = hashGenerator.validatePassword(null, hashPass);
        assertTrue(result);
    }
}
