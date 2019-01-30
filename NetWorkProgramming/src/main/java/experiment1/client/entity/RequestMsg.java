package experiment1.client.entity;

import experiment1.Configuration;

import java.util.Objects;

/**
 * @author WallfacerRZD
 * @date 2018/11/10 10:34
 */
public class RequestMsg {
    private final int totalLength;

    private Command command;

    private String userName;

    private String password;

    public RequestMsg(Command command, String userName, String password) {
        this.command = command;
        this.userName = userName.trim();
        this.password = password.trim();
        this.totalLength = Configuration.REQUEST_TOTAL_LENGTH;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public Command getCommand() {
        return command;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "RequestMsg{" +
                "totalLength=" + totalLength +
                ", command=" + command +
                ", userName='" + userName + '\'' +
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
        RequestMsg that = (RequestMsg) o;
        return getTotalLength() == that.getTotalLength() &&
                getCommand() == that.getCommand() &&
                Objects.equals(getUserName(), that.getUserName()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTotalLength(), getCommand(), getUserName(), getPassword());
    }

    public enum Command {
        REGISTRATION(1),
        LOGIN(3);

        private int value;

        Command(int value) {
            this.value = value;
        }

        public static Command getCommand(int value) {
            for (Command command : Command.values()) {
                if (command.getValue() == value) {
                    return command;
                }
            }
            throw new IllegalArgumentException("commandID不匹配");
        }

        public int getValue() {
            return value;
        }
    }

}
