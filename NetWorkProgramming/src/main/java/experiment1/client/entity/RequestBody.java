package experiment1.client.entity;

import experiment1.Configuration;

/**
 * @author WallfacerRZD
 * @date 2018/11/10 10:31
 */
public class RequestBody {
    private String userName;

    private String password;

    public RequestBody(String userName, String password) {

        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLength() {
        return Configuration.PASSWORD_LENGTH + Configuration.PASSWORD_LENGTH;
    }
}
