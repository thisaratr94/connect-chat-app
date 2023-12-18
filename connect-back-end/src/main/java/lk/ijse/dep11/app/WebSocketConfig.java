package lk.ijse.dep11.app;

import lk.ijse.dep11.app.wscontroller.ChatWSController;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public ChatWSController chatWSController() {
        return new ChatWSController();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWSController(), "api/v3/message").setAllowedOriginPatterns("*");
    }
}
