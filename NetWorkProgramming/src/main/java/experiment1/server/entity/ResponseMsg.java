package experiment1.server.entity;

import experiment1.Configuration;

import java.util.Objects;

/**
 * @author WallfacerRZD
 * @date 2018/11/10 22:33
 */
public class ResponseMsg {
    private int totalLength;

    private Command command;

    private Status status;

    private String description;

    public ResponseMsg(Command command, Status status, String description) {
        this.command = command;
        this.status = status;
        this.description = description;
        this.totalLength = Configuration.RESPONSE_TOTAL_LENGTH;
    }

    public Command getCommand() {
        return command;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ResponseMsg{" +
                "totalLength=" + totalLength +
                ", status=" + status +
                ", description='" + description + '\'' +
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
        ResponseMsg that = (ResponseMsg) o;
        return getTotalLength() == that.getTotalLength() &&
                getStatus() == that.getStatus() &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTotalLength(), getStatus(), getDescription());
    }

    public int getTotalLength() {
        return totalLength;

    }

    public enum Command {
        REGISTRATION(2),
        LOGIN(4);

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
            throw new IllegalArgumentException("commandID不存在");
        }

        public int getValue() {
            return value;
        }
    }

    public enum Status {
        SUCCEED(1),
        FAILED(0);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public static Status getStatus(int value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            throw new IllegalArgumentException("statues不存在");
        }

        public int getValue() {
            return value;
        }
    }
}
