package experiment1.client;

/**
 * @author WallfacerRZD
 * @date 2018/11/7 19:16
 */
public class ClientException extends Exception {
    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
