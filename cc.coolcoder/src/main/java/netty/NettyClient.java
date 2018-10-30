package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static int MAX_TRY = 5;

    public static void main(String[] args) {
        new NettyClient().client();

    }


    private void client() {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });

        connect(bootstrap, "127.0.0.1", 1024, MAX_TRY);
        bootstrap.attr(AttributeKey.newInstance("my first netty"), "nettyClient");
        //表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);

    }


    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                System.err.println("连接失败，开始重连");

                int order = (MAX_TRY - retry) + 1;

                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");

                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1),
                        delay, TimeUnit.SECONDS);


            }
        });
    }


    private class FirstClientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

            System.out.println(new Date() + "客户端开始发送消息");
            //1.读取byteBuf
            ByteBuf byteBuf = getByteBuf(ctx);
            ctx.channel().writeAndFlush(byteBuf);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        // 1. 获取二进制抽象 ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();

        // 2. 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = "你好小兵".getBytes(Charset.forName("utf-8"));
        // 3. 填充数据到 ByteBuf
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}


