package experiment1.client.handler;

import experiment1.Configuration;
import experiment1.server.entity.ResponseMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WallfacerRZD
 * @date 2018/11/11 11:34
 */
@Component
@Scope("prototype")
public class ResponseMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= Configuration.RESPONSE_TOTAL_LENGTH) {
            final int totalLength = in.readInt();
            final int commandID = in.readInt();
            final int status = in.readByte();
            final String description = in.readCharSequence(
                    Configuration.DESCRIPTION_LENGTH,
                    CharsetUtil.UTF_8
            ).toString().trim();
            ResponseMsg responseMsg = new ResponseMsg(
                    ResponseMsg.Command.getCommand(commandID),
                    ResponseMsg.Status.getStatus(status),
                    description
            );
            assert (totalLength == responseMsg.getTotalLength());
            out.add(responseMsg);
        } else {
            System.out.println("长度不够");
        }
    }
}
