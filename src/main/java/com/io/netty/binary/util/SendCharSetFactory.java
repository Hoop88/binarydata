package com.io.netty.binary.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 *  @author edson.liu
 *  2019-04-02 下午15:35:52
 */

public class SendCharSetFactory {
    public static Charset getCharset(int encode) {
        String ecode = "UTF-8";
        switch(encode)
        {
            case '0' :
                ecode = "UTF-8";
                System.out.println("UTF-8");
                break;
            case '1' :
                ecode = "GBK";
                System.out.println("GBK");
                break;
            case '2' :
                ecode = "GB2312";
                System.out.println("GB2312");
                break;
            case '3' :
                ecode = "ISO8859-1";
                System.out.println("ISO8859-1");
                break;
            default :
                ecode = "UTF-8";
                System.out.println("默认编码:"+ecode);
        }

        Charset charset = Charset.forName(ecode);
       /* System.out.println(charset.name()+"--"+charset.canEncode());
        //返回一个包含该字符的别名，字符集的别名是不可变的
        Set<String> set = charset.aliases();
        Iterator<String> it = set.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println("----------编码----------------");
        ByteBuffer buffer = charset.encode("sdf");
        System.out.println(buffer);

        System.out.println("缓冲区剩余的元素数--"+buffer.remaining());
        while(buffer.hasRemaining()) {
            System.out.println((char)buffer.get());
        }
        System.out.println("缓冲区剩余的元素数--"+buffer.remaining());
        System.out.println("----------解码----------------");
        //清空缓冲区，将限制设置恢复，如果定义了标记，则将它们丢弃
        buffer.flip();*/
        return charset;
    }
}
