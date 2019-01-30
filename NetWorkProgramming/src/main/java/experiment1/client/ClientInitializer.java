package experiment1.client;

import experiment1.client.handler.ClientServiceHandler;
import experiment1.client.handler.RequestMsgEncoder;
import experiment1.client.handler.ResponseMsgDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author WallfacerRZD
 * @date 2018/11/10 12:05
 */
@Component
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClientSpringConfig.class);

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
          .addLast((ResponseMsgDecoder) applicationContext.getBean("responseMsgDecoder"))
          .addLast((RequestMsgEncoder) applicationContext.getBean("requestMsgEncoder"))
          .addLast((ClientServiceHandler) applicationContext.getBean("clientServiceHandler"));
    }
}
