package experiment1.server.handler;

import experiment1.client.entity.RequestMsg;
import experiment1.server.entity.ResponseMsg;
import experiment1.server.entity.User;
import experiment1.server.service.ServerService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WallfacerRZD
 * @date 2018/11/11 14:08
 */
@Controller
@Scope("prototype")
public class ServerServiceHandler extends SimpleChannelInboundHandler<RequestMsg> {
    private final Map<RequestMsg.Command, ServerService> serverServiceMap;

    @Autowired
    public ServerServiceHandler(
            @Qualifier("serverLoginServiceImpl")
                    ServerService loginService,
            @Qualifier("serverRegistrationServiceImpl")
                    ServerService registrationService) {
        serverServiceMap = new HashMap<>();
        serverServiceMap.put(RequestMsg.Command.LOGIN, loginService);
        serverServiceMap.put(RequestMsg.Command.REGISTRATION, registrationService);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close().sync();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMsg msg) {
        User user = new User(msg.getUserName(), msg.getPassword());
        ResponseMsg responseMsg = serverServiceMap.get(msg.getCommand()).serve(user);
        System.out.println(msg);
        ctx.writeAndFlush(responseMsg).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("成功写回");
            } else {
                future.cause().printStackTrace();
            }
        });
    }
}
