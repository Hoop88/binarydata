package com.io.netty.binary.client;


import com.io.netty.binary.vo.SendResponse;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


/**
 *  @author edson.liu
 *  2019-04-02 下午15:25:52
 */


/**
 * 服务器特征：
 * 1、使用专用的编码解码器，解决数据分段的问题
 * 2、使用POJO替代ChannelBuffer传输
 */
public class SendClientHandler extends SimpleChannelHandler {
    private static final Logger logger= LoggerFactory.getLogger(SendClientHandler.class);
    private final AtomicInteger count=new AtomicInteger(0); //计数器

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        processMethod1(ctx, e); //处理方式一
    }

    /**
     * @param ctx
     * @param e
     * @throws Exception
     */
    public void processMethod1(ChannelHandlerContext ctx, MessageEvent e) throws Exception{
        logger.info("processMethod1……,count="+count.addAndGet(1));
        SendResponse serverTime=(SendResponse)e.getMessage();
        logger.info("messageReceived,content:"+serverTime.toString());
        Thread.sleep(1000);

        if (count.get()<10) {
            //从新发送请求获取最新的服务器时间
            ctx.getChannel().write(ChannelBuffers.wrappedBuffer("again\r\n".getBytes()));
        }else{
            //从新发送请求关闭服务器
            ctx.getChannel().write(ChannelBuffers.wrappedBuffer("shutdown\r\n".getBytes()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        logger.info("exceptionCaught");
        e.getCause().printStackTrace();
        ctx.getChannel().close();
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.info("channelClosed");
        super.channelClosed(ctx, e);
    }


}
