# binarydata

有点小感慨3，4年没有写代码了,写完发现coding能力根本没有下降，只是没有以前那么骚了，没有什么是写不错来的热血了，可能到30了吧，
因为一直在做管理...    在不出来coding下我就老了。   （扫描贴，CPU密集，IO密集简单入门，后面还是上传以前写的框架吧）

使用Netty实现通用二进制协议的高效数据传输

一、协议的定义

无论是请求还是响应，报文都由一个通用报文头和实际数据组成。报文头在前，数据在后。

（1）报文头：由数据解析类型，数据解析方法，编码，扩展字节，包长度组成，共16个字节：
       编码方式（1byte）、加密（1byte）、扩展1（1byte）、扩展2（1byte）、会话ID（4byte）、命令或者结果码（4byte）、数据包长（4byte）
（2）数据：由数据包长指定。请求或回复数据。类型对应为JAVA的Map<String,String>
       数据格式定义：
       字段1键名长度    字段1键名 字段1值长度    字段1值
       字段2键名长度    字段2键名 字段2值长度    字段2值
       字段3键名长度    字段3键名 字段3值长度    字段3值
       …    …    …    …
       长度为整型，占4个字节
package com.io.netty.binary.vo;

import java.util.HashMap;
import java.util.Map;

/**
 *  @author edson.liu
 *  2019-04-02 下午15:25:52
 */

/**
 * 请求数据
 */

/**
 * 通用协议介绍
 *
 * 通用报文格式：无论是请求还是响应，报文都由一个通用报文头和实际数据组成。报文头在前，数据在后
 * （1）报文头:由数据解析类型，数据解析方法，编码，扩展字节，包长度组成,共16个字节：
 * 编码方式（1byte）、加密（1byte）、扩展1（1byte）、扩展2（1byte）、会话ID（4byte）、命令或者结果码（4byte）、包长（4byte）
 * （2）数据:由包长指定。请求或回复数据。类型对应为JAVA的Map<String,String>
 * 数据格式定义：
 * 字段1键名长度    字段1键名 字段1值长度    字段1值
 * 字段2键名长度    字段2键名 字段2值长度    字段2值
 * 字段3键名长度    字段3键名 字段3值长度    字段3值
 * …    …    …    …
 * 长度为整型，占4个字节
 */
public class SendRequest {
    private byte encode;// 数据编码格式。已定义：0：UTF-8，1：GBK，2：GB2312，3：ISO8859-1
    private byte encrypt;// 加密类型。0表示不加密
    private byte extend1;// 用于扩展协议。暂未定义任何值
    private byte extend2;// 用于扩展协议。暂未定义任何值
    private int sessionid;// 会话ID
    private int command;// 命令
    private int length;// 数据包长

    private Map<String,String> params=new HashMap<String, String>(); //参数

    private String ip;

    public byte getEncode() {
        return encode;
    }

    public void setEncode(byte encode) {
        this.encode = encode;
    }

    public byte getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(byte encrypt) {
        this.encrypt = encrypt;
    }

    public byte getExtend1() {
        return extend1;
    }

    public void setExtend1(byte extend1) {
        this.extend1 = extend1;
    }

    public byte getExtend2() {
        return extend2;
    }

    public void setExtend2(byte extend2) {
        this.extend2 = extend2;
    }

    public int getSessionid() {
        return sessionid;
    }

    public void setSessionid(int sessionid) {
        this.sessionid = sessionid;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setValue(String key,String value){
        params.put(key, value);
    }

    public String getValue(String key){
        if (key==null) {
            return null;
        }
        return params.get(key);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "SendRequest [encode=" + encode + ", encrypt=" + encrypt + ", extend1=" + extend1 + ", extend2=" + extend2
                + ", sessionid=" + sessionid + ", command=" + command + ", length=" + length + ", params=" + params + ", ip=" + ip + "]";
    }
}



package com.io.netty.binary.vo;

import java.util.HashMap;
import java.util.Map;

/**
 *  @author edson.liu
 *  2019-04-02 下午15:25:52
 */


/**
 * 响应数据
 */

/**
 * 通用协议介绍
 *
 * 通用报文格式：无论是请求还是响应，报文都由一个通用报文头和实际数据组成。报文头在前，数据在后
 * （1）报文头:由数据解析类型，数据解析方法，编码，扩展字节，包长度组成,共16个字节：
 * 编码方式（1byte）、加密（1byte）、扩展1（1byte）、扩展2（1byte）、会话ID（4byte）、命令或者结果码（4byte）、包长（4byte）
 * （2）数据:由包长指定。请求或回复数据。类型对应为JAVA的Map<String,String>
 * 数据格式定义：
 * 字段1键名长度    字段1键名 字段1值长度    字段1值
 * 字段2键名长度    字段2键名 字段2值长度    字段2值
 * 字段3键名长度    字段3键名 字段3值长度    字段3值
 * …    …    …    …
 * 长度为整型，占4个字节
 */
public class SendResponse {
    private byte encode;// 数据编码格式。已定义：0：UTF-8，1：GBK，2：GB2312，3：ISO8859-1
    private byte encrypt;// 加密类型。0表示不加密
    private byte extend1;// 用于扩展协议。暂未定义任何值
    private byte extend2;// 用于扩展协议。暂未定义任何值
    private int sessionid;// 会话ID
    private int result;// 结果码
    private int length;// 数据包长

    private Map<String,String> values=new HashMap<String, String>();

    private String ip;

    public void setValue(String key,String value){
        values.put(key, value);
    }

    public String getValue(String key){
        if (key==null) {
            return null;
        }
        return values.get(key);
    }

    public byte getEncode() {
        return encode;
    }

    public void setEncode(byte encode) {
        this.encode = encode;
    }

    public byte getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(byte encrypt) {
        this.encrypt = encrypt;
    }

    public byte getExtend1() {
        return extend1;
    }

    public void setExtend1(byte extend1) {
        this.extend1 = extend1;
    }

    public byte getExtend2() {
        return extend2;
    }

    public void setExtend2(byte extend2) {
        this.extend2 = extend2;
    }

    public int getSessionid() {
        return sessionid;
    }

    public void setSessionid(int sessionid) {
        this.sessionid = sessionid;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "SendResponse [encode=" + encode + ", encrypt=" + encrypt + ", extend1=" + extend1 + ", extend2=" + extend2
                + ", sessionid=" + sessionid + ", result=" + result + ", length=" + length + ", values=" + values + ", ip=" + ip + "]";
    }
}


       
二、协议的编码和解码

对于自定义二进制协议，编码解码器往往是Netty开发的重点

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

package com.io.netty.binary.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;


import com.io.netty.binary.util.ProtocolUtil;
import com.io.netty.binary.vo.SendResponse;

/**
 *  @author edson.liu
 *  2019-04-02 下午15:25:52
 */

/**
 * 客户端解码器
 */
public class SendClientDecoder extends FrameDecoder {

    @Override
    protected Object decode(ChannelHandlerContext context, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes()<16) {
            return null;
        }
        buffer.markReaderIndex();
        byte encode=buffer.readByte();
        byte encrypt=buffer.readByte();
        byte extend1=buffer.readByte();
        byte extend2=buffer.readByte();
        int sessionid=buffer.readInt();
        int result=buffer.readInt();
        int length=buffer.readInt(); // 数据包长
        if (buffer.readableBytes()<length) {
            buffer.resetReaderIndex();
            return null;
        }
        ChannelBuffer dataBuffer=ChannelBuffers.buffer(length);
        buffer.readBytes(dataBuffer, length);

        SendResponse response=new SendResponse();
        response.setEncode(encode);
        response.setEncrypt(encrypt);
        response.setExtend1(extend1);
        response.setExtend2(extend2);
        response.setSessionid(sessionid);
        response.setResult(result);
        response.setLength(length);
        response.setValues(ProtocolUtil.decode(encode, dataBuffer));
        response.setIp(ProtocolUtil.getClientIp(channel));
        return response;
    }

}


三、服务器端实现

服务器端提供的功能是：

1、接收客户端的请求（非关闭命令），返回XLResponse类型的数据。

2、如果客户端的请求是关闭命令：shutdown，则服务器端关闭自身进程。

为了展示多协议的运用，这里客户端的请求采用的是基于问本行（\n\r）的协议。

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



四、客户端实现

客户端的功能是连接服务器，发送10次请求，然后发送关闭服务器的命令，最后主动关闭客户端。

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

