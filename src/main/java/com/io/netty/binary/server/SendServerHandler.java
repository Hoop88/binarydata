package com.io.netty.binary.server;

import java.util.Random;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import com.io.netty.binary.vo.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  @author edson.liu
 *  2019-04-02 下午15:25:52
 */

@Sharable
public class SendServerHandler extends SimpleChannelHandler {
    private static final Logger logger=LoggerFactory.getLogger(SendServerHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        logger.info("messageReceived");
        if (e.getMessage() instanceof String) {
            String content=(String)e.getMessage();
            logger.info("content is "+content);
            if ("shutdown".equalsIgnoreCase(content)) {
                //e.getChannel().close();
                SendServer.shutdown();
            }else {
                sendResponse(ctx);
            }
        }else {
            logger.error("message is not a String.");
            e.getChannel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        logger.error(e.getCause().getMessage(),e.getCause());
        e.getCause().printStackTrace();
        e.getChannel().close();
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.info("channelConnected");
        sendResponse(ctx);
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.info("channelClosed");
        //删除通道
        SendServer.allChannels.remove(e.getChannel());
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.info("channelDisconnected");
        super.channelDisconnected(ctx, e);
    }

    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.info("channelOpen");
        //增加通道
        SendServer.allChannels.add(e.getChannel());
    }

    /**
     * 发送响应内容
     * @param ctx
     * @param
     * @return
     */
    private ChannelFuture sendResponse(ChannelHandlerContext ctx){
        Channel channel=ctx.getChannel();
        Random random=new Random();
        SendResponse response=new SendResponse();
        response.setEncode((byte)0);
        response.setResult(1);
        response.setValue("name","edson.liu");
        response.setValue("time", String.valueOf(System.currentTimeMillis()));
        response.setValue("age",String.valueOf(random.nextInt()));
        /**
         * 发送接收信息的时间戳到客户端
         * 注意：Netty中所有的IO操作都是异步的！
         */
        ChannelFuture future=channel.write(response); //发送内容
        return future;
    }
}