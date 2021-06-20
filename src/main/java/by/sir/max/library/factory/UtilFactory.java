package by.sir.max.library.factory;

import by.sir.max.library.util.EmailDistributorUtil;
import by.sir.max.library.util.EmailMessageLocalizationDispatcher;
import by.sir.max.library.util.HashGeneratorUtil;

public class UtilFactory {
    private final HashGeneratorUtil hashGeneratorUtil;
    private final EmailDistributorUtil emailDistributorUtil;
    private final EmailMessageLocalizationDispatcher emailMessageLocalizationDispatcher;

    private UtilFactory() {
        hashGeneratorUtil = new HashGeneratorUtil();
        emailDistributorUtil = new EmailDistributorUtil();
        emailMessageLocalizationDispatcher = new EmailMessageLocalizationDispatcher();
    }

    private static class UtilFactorySingletonHolder {
        static final UtilFactory INSTANCE = new UtilFactory();
    }

    public static UtilFactory getInstance() {
        return UtilFactorySingletonHolder.INSTANCE;
    }

    public HashGeneratorUtil getHashGeneratorUtil() {
        return hashGeneratorUtil;
    }

    public EmailDistributorUtil getEmailDistributorUtil() {
        return emailDistributorUtil;
    }

    public EmailMessageLocalizationDispatcher getEmailMessageLocalizationDispatcher() {
        return emailMessageLocalizationDispatcher;
    }
}
