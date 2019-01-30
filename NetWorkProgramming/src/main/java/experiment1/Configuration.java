package experiment1;

/**
 * @author WallfacerRZD
 * @date 2018/11/6 9:33
 */
public class Configuration {
    public static final int TOTAL_LENGTH = 4;

    public static final int COMMAND_ID_LENGTH = 4;

    public static final int DESCRIPTION_LENGTH = 64;

    public static final int USER_NAME_LENGTH = 20;

    public static final int PASSWORD_LENGTH = 30;

    public static final int STATUS_LENGTH = 1;

    public static final int REQUEST_TOTAL_LENGTH = PASSWORD_LENGTH +
            USER_NAME_LENGTH +
            TOTAL_LENGTH +
            COMMAND_ID_LENGTH;

    public static final int RESPONSE_TOTAL_LENGTH = STATUS_LENGTH +
            DESCRIPTION_LENGTH +
            TOTAL_LENGTH +
            COMMAND_ID_LENGTH;
}
