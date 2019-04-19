package com.io.netty.binary.server;


import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.io.netty.binary.codec.SendServerEncoder;

/**
 *  @author edson.liu
 *  2019-04-02 下午15:25:52
 */

public class SendServer {
    public static final int port =8001;
    public static final Logger logger=LoggerFactory.getLogger(SendServer.class);
    public static final ChannelGroup allChannels=new DefaultChannelGroup("SendServer");
    private static final ServerBootstrap serverBootstrap=new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

    public static void main(String [] args){
        try {
            SendServer.startup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean startup() throws Exception{
        /**
         * 采用默认ChannelPipeline管道
         * 这意味着同一个SendServerHandler实例将被多个Channel通道共享
         * 这种方式对于SendServerHandler中无有状态的成员变量是可以的，并且可以提高性能！
         */
        ChannelPipeline pipeline=serverBootstrap.getPipeline();
        /**
         * 解码器是基于文本行的协议，\r\n或者\n\r
         */
        pipeline.addLast("frameDecoder", new DelimiterBasedFrameDecoder(80, Delimiters.lineDelimiter()));
        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("encoder", new SendServerEncoder());
        pipeline.addLast("handler", new SendServerHandler());

        serverBootstrap.setOption("child.tcpNoDelay", true); //注意child前缀
        serverBootstrap.setOption("child.keepAlive", true); //注意child前缀

        /**
         * ServerBootstrap对象的bind方法返回了一个绑定了本地地址的服务端Channel通道对象
         */
        Channel channel=serverBootstrap.bind(new InetSocketAddress(port));
        allChannels.add(channel);
        logger.info("server is started on port "+port);
        return false;
    }

    public static void shutdown() throws Exception{
        try {
            /**
             * 主动关闭服务器
             */
            ChannelGroupFuture future=allChannels.close();
            future.awaitUninterruptibly();//阻塞，直到服务器关闭
            //serverBootstrap.releaseExternalResources();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        finally{
            logger.info("server is shutdown on port "+port);
            System.exit(1);
        }
    }
}

