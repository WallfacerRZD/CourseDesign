package experiment1.client;

import experiment1.client.entity.RequestMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author WallfacerRZD
 * @date 2018/11/7 20:45
 */
public class ClientBootstrap {

    private final int port;

    private final String host;

    public ClientBootstrap(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public static void main(String[] args) {
        new ClientBootstrap("192.168.43.108", 8888).start();
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                     .channel(NioSocketChannel.class)
                     .remoteAddress(new InetSocketAddress(host, port))
                     .handler(new ClientInitializer());
            while (true) {
                Scanner scanner = new Scanner(System.in);
                final String input = scanner.nextLine();
                //         REGISTRATION(1),
                //        LOGIN(3);
                String[] parameters = input.split(" ");
                Channel channel = bootstrap.connect().sync().channel();
                channel.writeAndFlush(
                        new RequestMsg(
                                RequestMsg.Command.getCommand(Integer.valueOf(parameters[0])),
                                parameters[1],
                                parameters[2]
                        )).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("发送成功");
                        } else {
                            future.cause().printStackTrace();
                        }
                    }
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
