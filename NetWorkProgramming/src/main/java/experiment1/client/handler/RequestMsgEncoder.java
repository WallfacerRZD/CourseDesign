package experiment1.client.handler;

import experiment1.Configuration;
import experiment1.client.entity.RequestMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author WallfacerRZD
 * @date 2018/11/10 21:17
 */
@Component
@Scope("prototype")
public class RequestMsgEncoder extends MessageToByteEncoder<RequestMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMsg msg, ByteBuf out) throws Exception {
        final int totalLength = msg.getTotalLength();
        final int commandID = msg.getCommand().getValue();

        final String userName = msg.getUserName();
        final byte[] userNameBytes = userName.getBytes(CharsetUtil.UTF_8);
        final String password = msg.getPassword();
        final byte[] passwordBytes = password.getBytes(CharsetUtil.UTF_8);

        if (userNameBytes.length > Configuration.USER_NAME_LENGTH ||
                passwordBytes.length > Configuration.PASSWORD_LENGTH) {
            throw new IllegalArgumentException("用户名或密码太长");
        }

        final byte[] userNameSpaceBytes = new byte[Configuration.USER_NAME_LENGTH - userNameBytes.length];
        final byte[] passwordSpaceBytes = new byte[Configuration.PASSWORD_LENGTH - passwordBytes.length];

        out.writeInt(totalLength)
           .writeInt(commandID)
           .writeBytes(userName.getBytes(CharsetUtil.UTF_8))
           .writeBytes(userNameSpaceBytes)
           .writeBytes(password.getBytes(CharsetUtil.UTF_8))
           .writeBytes(passwordSpaceBytes);
        System.out.println(out.toString());
    }
}
