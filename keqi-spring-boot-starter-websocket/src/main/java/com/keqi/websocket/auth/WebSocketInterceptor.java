package com.keqi.websocket.auth;

import com.keqi.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * WebSocketInterceptor
 *
 * @author keqi
 */
@Component
public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Logger log = LoggerFactory.getLogger(WebSocketInterceptor.class);

    @Autowired
    private WebSocketAuth webSocketAuth;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        // authentication
        WebSocketAuthDto webSocketAuthDto = webSocketAuth.auth(httpServletRequest);
        if (webSocketAuthDto.getUserIdentifier() == null) {
            log.info("websocket authentication failed，request params : {}", JsonUtil.writeValueAsString(httpServletRequest));
            return false;
        }

        attributes.put("userIdentifier", webSocketAuthDto.getUserIdentifier());
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

}