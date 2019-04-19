package com.io.netty.binary.client;


/**
 *  @author edson.liu
 *  2019-04-02 下午15:25:52
 */


import com.io.netty.binary.server.SendServer;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;


/**
 * 服务器特征：
 * 1、使用专用解码器解析服务器发过来的数据
 * 2、客户端主动关闭连接
 */
public class SendClient {
    public static final int port = SendServer.port;
    public static final String host = "localhost";
    private static final Logger logger = LoggerFactory.getLogger(SendClient.class);
    private static final NioClientSocketChannelFactory clientSocketChannelFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
    private static final ClientBootstrap clientBootstrap = new ClientBootstrap(clientSocketChannelFactory);

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ChannelFuture future = SendClient.startup();
        logger.info("future state is " + future.isSuccess());
    }

    /**
     * 启动客户端
     *
     * @return
     * @throws Exception
     */
    public static ChannelFuture startup() throws Exception {
        /**
         * 注意：由于XLClientHandler中有状态的成员变量，因此不能采用默认共享ChannelPipeline的方式
         * 例如，下面的代码形式是错误的：
         * ChannelPipeline pipeline=clientBootstrap.getPipeline();
         * pipeline.addLast("handler", new XLClientHandler());
         */
        clientBootstrap.setPipelineFactory(new SendClientPipelineFactory()); //只能这样设置
        /**
         * 请注意，这里不存在使用“child.”前缀的配置项，客户端的SocketChannel实例不存在父级Channel对象
         */
        clientBootstrap.setOption("tcpNoDelay", true);
        clientBootstrap.setOption("keepAlive", true);

        ChannelFuture future = clientBootstrap.connect(new InetSocketAddress(host, port));
        /**
         * 阻塞式的等待，直到ChannelFuture对象返回这个连接操作的成功或失败状态
         */
        future.awaitUninterruptibly();
        /**
         * 如果连接失败，我们将打印连接失败的原因。
         * 如果连接操作没有成功或者被取消，ChannelFuture对象的getCause()方法将返回连接失败的原因。
         */
        if (!future.isSuccess()) {
            future.getCause().printStackTrace();
        } else {
            logger.info("client is connected to server " + host + ":" + port);
        }
        return future;
    }

    /**
     * 关闭客户端
     *
     * @param future
     * @throws Exception
     */
    public static void shutdown(ChannelFuture future) throws Exception {
        try {
            /**
             * 主动关闭客户端连接，会阻塞等待直到通道关闭
             */
            future.getChannel().close().awaitUninterruptibly();
            //future.getChannel().getCloseFuture().awaitUninterruptibly();
            /**
             * 释放ChannelFactory通道工厂使用的资源。
             * 这一步仅需要调用 releaseExternalResources()方法即可。
             * 包括NIO Secector和线程池在内的所有资源将被自动的关闭和终止。
             */
            clientBootstrap.releaseExternalResources();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            System.exit(1);
            logger.info("client is shutdown to server " + host + ":" + port);
        }
    }
}