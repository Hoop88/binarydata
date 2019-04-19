package com.io.netty.binary.codec;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelDownstreamHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.io.netty.binary.util.ProtocolUtil;
import com.io.netty.binary.vo.SendResponse;

/**
 *  @author edson.liu
 *  2019-04-02 下午15:25:52
 */

/**
 * 服务器端编码器
 */
public class SendServerEncoder extends SimpleChannelDownstreamHandler {
    Logger logger=LoggerFactory.getLogger(SendServerEncoder.class);

    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        SendResponse response=(SendResponse)e.getMessage();
        ByteBuffer headBuffer=ByteBuffer.allocate(16);
        /**
         * 先组织报文头
         */
        headBuffer.put(response.getEncode());
        headBuffer.put(response.getEncrypt());
        headBuffer.put(response.getExtend1());
        headBuffer.put(response.getExtend2());
        headBuffer.putInt(response.getSessionid());
        headBuffer.putInt(response.getResult());

        /**
         * 组织报文的数据部分
         */
        ChannelBuffer dataBuffer=ProtocolUtil.encode(response.getEncode(),response.getValues());
        int length=dataBuffer.readableBytes();
        headBuffer.putInt(length);
        /**
         * 非常重要
         * ByteBuffer需要手动flip()，ChannelBuffer不需要
         */
        headBuffer.flip();
        ChannelBuffer totalBuffer=ChannelBuffers.dynamicBuffer();
        totalBuffer.writeBytes(headBuffer);
        logger.info("totalBuffer size="+totalBuffer.readableBytes());
        totalBuffer.writeBytes(dataBuffer);
        logger.info("totalBuffer size="+totalBuffer.readableBytes());
        Channels.write(ctx, e.getFuture(), totalBuffer);
    }

}