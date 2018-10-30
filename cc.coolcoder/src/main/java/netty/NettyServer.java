package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.charset.Charset;
import java.util.Date;

public class NettyServer {

    //开始绑定的端口
    private static int BEGIN_PORT = 1000;


    /**
     * 要启动一个Netty服务端，必须要指定三类属性，
     * 1.分别是线程模型、
     * 2.IO 模型
     * 3.连接读写处理逻辑
     *
     * @param args
     */
    public static void main(String[] args) {

        /**
         * 工作线程 处理每一条连接数据的线程组
         */
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        /**
         * 管理线程 监听端口 accpt 新连接的线程组  bossGroup 接受连接扔给 workGroup 处理
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childHandler(new ChannelInitializer<NioServerSocketChannel>() {

                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        System.out.println("服务端启动中");
                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                });
        bind(bootstrap, BEGIN_PORT);

    }

    private static void bind(ServerBootstrap bootstrap, int port) {
        bootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("bind port success:" + port);
            } else {
                System.err.println("bind port error:" + port);
                bind(bootstrap, port + 1);

            }
        });
    }


    private static class FirstServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        }
    }
}
