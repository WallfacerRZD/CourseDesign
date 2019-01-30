package experiment1.server.exception;

/**
 * @author WallfacerRZD
 * @date 2018/11/5 21:45
 */
public class ServerException extends Exception {
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(String message) {
        super(message);
    }
}
