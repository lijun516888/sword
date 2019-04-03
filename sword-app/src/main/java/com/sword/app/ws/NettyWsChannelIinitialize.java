package com.sword.app.ws;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

public class NettyWsChannelIinitialize extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(20, 15,0, TimeUnit.SECONDS));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new NettyWsHandshaker());
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
    }

    class NettyWsHandshaker extends SimpleChannelInboundHandler<Object> {

        private WebSocketServerHandshaker handshaker;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("=============channelRead============");
            if(msg instanceof FullHttpRequest) {
                FullHttpRequest request = (FullHttpRequest) msg;
                String uri = request.uri();
                uri = StringUtils.substringBefore(uri, "?");
                if(uri.equalsIgnoreCase("/ws")) {
                    QueryStringDecoder query = new QueryStringDecoder(request.uri());
                    WebSocketServerHandshakerFactory factory =
                            new WebSocketServerHandshakerFactory(request.uri(), null ,false);
                    handshaker = factory.newHandshaker(request);
                    handshaker.handshake(ctx.channel(), request);
                }
            } else if(msg instanceof CloseWebSocketFrame) {
                WebSocketFrame frame = (WebSocketFrame) msg;
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
                return;
            } else if(msg instanceof PingWebSocketFrame) {
                System.out.println("===========PingWebSocketFrame============");
                return;
            } else if(msg instanceof PongWebSocketFrame) {
                System.out.println("===========PongWebSocketFrame============");
            }
            super.channelRead(ctx, msg);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("=============channelRead0============");
            if(msg instanceof TextWebSocketFrame) {
                TextWebSocketFrame frame = new TextWebSocketFrame("123");
                ctx.channel().writeAndFlush(frame);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("=============exceptionCaught============");
            super.exceptionCaught(ctx, cause);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("=============channelActive============");
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("=============channelInactive============");
            super.channelInactive(ctx);
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("=============handlerAdded============");
            super.handlerAdded(ctx);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            System.out.println("=============handlerRemoved============");
            super.handlerRemoved(ctx);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("=============channelReadComplete============");
            super.channelReadComplete(ctx);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("=============channelUnregistered============");
            super.channelUnregistered(ctx);
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            System.out.println("=============channelWritabilityChanged============");
            super.channelWritabilityChanged(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if(evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if(event.state() == IdleState.READER_IDLE) {
                    ctx.close();
                }
                if(event.state() == IdleState.WRITER_IDLE) {
                    PingWebSocketFrame frame = new PingWebSocketFrame();
                    ctx.channel().writeAndFlush(frame);
                }
                if(event.state() == IdleState.ALL_IDLE) {

                }
            }
            super.userEventTriggered(ctx, evt);
        }
    }

}




