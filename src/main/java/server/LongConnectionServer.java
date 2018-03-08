package server;

import config.Configurator;
import config.Log4JConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Created by  qiao
 * @date 18-3-8 下午7:14
 */

public class LongConnectionServer {
    private int port;
    private String host;

    public LongConnectionServer() {
    }

    private void init() throws Exception {
        Configurator.init();
        Log4JConfig.load();
        Thread listener = new Thread(new ConsoleListener());
        listener.start();
    }

    public static LongConnectionServer newInstance() throws Exception {
        LongConnectionServer lcServer = new LongConnectionServer();
        lcServer.init();
        return lcServer;
    }

    public LongConnectionServer bind(String host,int port){
        this.host = host;
        this.port = port;
        return this;
    }

    public void run(){
        EventLoopGroup bossgroup = new NioEventLoopGroup();
        EventLoopGroup workgroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();

            server.group(bossgroup, workgroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 102480)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializerImp());

            ChannelFuture future = server.bind(host,port).sync();

            future.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            bossgroup.shutdownGracefully();
            workgroup.shutdownGracefully();
        }
    }

    public void close(){

    }
}
