package com.geekbrains.cloud.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class StringToByteBufOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        String str = (String) msg;
        log.debug("Send: {}", str);
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes(str.getBytes(StandardCharsets.UTF_8));
        buf.retain();
        ctx.writeAndFlush(buf);
    }
}
