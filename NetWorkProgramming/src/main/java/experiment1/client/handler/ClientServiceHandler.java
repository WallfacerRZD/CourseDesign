package experiment1.client.handler;

import experiment1.server.entity.ResponseMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author WallfacerRZD
 * @date 2018/11/11 23:40
 */
@Component
@Scope("prototype")
public class ClientServiceHandler extends SimpleChannelInboundHandler<ResponseMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMsg msg) throws Exception {
        System.out.println(msg);
        ctx.close().sync();
    }
}
