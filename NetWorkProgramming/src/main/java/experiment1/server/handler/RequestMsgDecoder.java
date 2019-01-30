package experiment1.server.handler;

import experiment1.Configuration;
import experiment1.client.entity.RequestMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WallfacerRZD
 * @date 2018/11/10 21:45
 */
@Component
@Scope("prototype")
public class RequestMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= Configuration.REQUEST_TOTAL_LENGTH) {
            final int totalLength = in.readInt();
            final int commandID = in.readInt();
            final String userName = in.readCharSequence(Configuration.USER_NAME_LENGTH,
                                                        CharsetUtil.UTF_8).toString().trim();
            final String password = in.readCharSequence(Configuration.PASSWORD_LENGTH,
                                                        CharsetUtil.UTF_8).toString().trim();
            RequestMsg loginRequest = new RequestMsg(
                    RequestMsg.Command.getCommand(commandID),
                    userName, password);
            out.add(loginRequest);

            assert (commandID == loginRequest.getCommand().getValue());
            assert (totalLength == loginRequest.getTotalLength());
            assert (in.readableBytes() == 0);
        }

    }
}
