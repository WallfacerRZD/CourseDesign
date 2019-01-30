package experiment1.server;

import experiment1.server.handler.RequestMsgDecoder;
import experiment1.server.handler.ResponseMsgEncoder;
import experiment1.server.handler.ServerServiceHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author WallfacerRZD
 * @date 2018/11/10 12:01
 */
@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(SpringConfig.class);

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
          .addLast((RequestMsgDecoder) CONTEXT.getBean("requestMsgDecoder"))
          .addLast((ResponseMsgEncoder) CONTEXT.getBean("responseMsgEncoder"))
          .addLast((ServerServiceHandler) CONTEXT.getBean("serverServiceHandler"));
    }
}
