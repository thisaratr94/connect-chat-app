package lk.ijse.dep11.app.wscontroller;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.dep11.app.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatWSController extends TextWebSocketHandler {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    private final List<WebSocketSession> webSocketSessionList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionList.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try{
            MessageDTO messageObj = mapper.readValue(message.getPayload(), MessageDTO.class);

            Set<ConstraintViolation<MessageDTO>> validations = localValidatorFactoryBean.getValidator().validate(messageObj);
            if(validations.isEmpty()) {
                for (WebSocketSession webSocketSession : webSocketSessionList) {
                    if(webSocketSession == session) continue;
                    if(webSocketSession.isOpen()) {
                        webSocketSession.sendMessage(new TextMessage(message.getPayload()));
                    }
                }
            } else {
                session.sendMessage(new TextMessage("Invalid Message Scheme"));
            }

        } catch (JacksonException e) {
            session.sendMessage(new TextMessage("Invalid JSON"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessionList.remove(session);
    }
}
