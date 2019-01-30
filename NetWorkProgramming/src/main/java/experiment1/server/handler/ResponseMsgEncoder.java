package experiment1.server.handler;

import experiment1.Configuration;
import experiment1.server.entity.ResponseMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author WallfacerRZD
 * @date 2018/11/11 11:24
 */
@Component
@Scope("prototype")
public class ResponseMsgEncoder extends MessageToByteEncoder<ResponseMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseMsg msg, ByteBuf out) throws Exception {
        final int totalLength = msg.getTotalLength();
        final int commandID = msg.getCommand().getValue();
        final int status = msg.getStatus().getValue();

        final String description = msg.getDescription().trim();
        final byte[] descriptionBytes = description.getBytes(CharsetUtil.UTF_8);
        final byte[] spaceBytes = new byte[Configuration.DESCRIPTION_LENGTH - descriptionBytes.length];

        out.writeInt(totalLength)
           .writeInt(commandID)
           .writeByte(status)
           .writeBytes(descriptionBytes)
           .writeBytes(spaceBytes);
        assert (out.readableBytes() == Configuration.RESPONSE_TOTAL_LENGTH);
    }
}
