package experiment1.server.entity;

import java.util.Objects;

/**
 * @author WallfacerRZD
 * @date 2018/11/5 17:11
 */
public class User {
    private final String account;

    private final String password;

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(getAccount(), user.getAccount()) &&
                Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAccount(), getPassword());
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }
}
