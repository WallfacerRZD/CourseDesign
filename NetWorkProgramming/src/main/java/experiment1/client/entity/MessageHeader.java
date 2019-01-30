package experiment1.client.entity;

import experiment1.Configuration;

/**
 * @author WallfacerRZD
 * @date 2018/11/10 10:20
 */
public class MessageHeader {
    private int totalLength;

    private Command command;

    public MessageHeader(int totalLength, Command command) {

        this.totalLength = totalLength;
        this.command = command;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public int getLength() {
        return Configuration.TOTAL_LENGTH + Configuration.COMMAND_ID_LENGTH;
    }

    public enum Command {
        RegistrationRequest(1),
        RegistrationResponse(2),
        LoginRequest(3),
        LoginResponse(4);

        private int value;

        Command(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
