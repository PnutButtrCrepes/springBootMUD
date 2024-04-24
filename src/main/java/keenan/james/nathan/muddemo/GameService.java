package keenan.james.nathan.muddemo;

import java.util.HashMap;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import keenan.james.nathan.muddemo.game.CommandParserExecutor;
import keenan.james.nathan.muddemo.game.GameCharacter;
import keenan.james.nathan.muddemo.game.GameMap;
import keenan.james.nathan.muddemo.game.GameCharacter.CharacterType;

@Component @Controller @Service
public class GameService {
	
	public record Message(String message) {};
	
	private final SimpMessageSendingOperations messagingTemplate;
	
	public GameMap gameMap = new GameMap("overworld");
	public HashMap<String, GameCharacter> gameCharacters = new HashMap<String, GameCharacter>();
	
	public GameService(SimpMessageSendingOperations messagingTemplate)
	{
		this.messagingTemplate = messagingTemplate;
	}
	
	@GetMapping("")
	public String viewMudPage(Model model)
	{
		model.addAttribute("gameText", "Welcome to Spring Boot MUD!");
		return "mud";
	}
	
	@Scheduled(fixedRate = 1000)
	public void gameUpdate()
	{
		messagingTemplate.convertAndSend("/connections/updates", gameCharacters.values().toArray()); // this is a message to all users subscribed
	}
	
	@MessageMapping("/player.sendCommand")
	public void sendCommand(SimpMessageHeaderAccessor headerAccessor, @Payload Message message)
	{
		String sessionId = headerAccessor.getSessionId();
		GameCharacter gameCharacter = gameCharacters.get(sessionId);
		
		String returnText = CommandParserExecutor.parseAndExecuteCommand(message.message, gameMap, gameCharacter);
		// this sends to a particular user with sessionID, if user is subscribed to /user/... (user/connections/updates)!
		messagingTemplate.convertAndSendToUser(sessionId, "/connections/messages", new Message(returnText), headerAccessor.getMessageHeaders());
	}
	
	@EventListener
	public void connectListener(SessionConnectEvent event)
	{
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = headerAccessor.getSessionId();
		
		if (!gameCharacters.containsKey(sessionId))
		{
			GameCharacter connectingGameCharacter = new GameCharacter(CharacterType.PC, sessionId);
			connectingGameCharacter.setPosition(gameMap.getSpawnX(), gameMap.getSpawnY());
			gameCharacters.put(sessionId, connectingGameCharacter);
		}
	}
	
	// can create a list of users here
	@EventListener()
	public void disconnectListener(SessionDisconnectEvent event)
	{
		System.out.println("Someone disconnected!!!");
		/*
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
        */
	}
	
	@SuppressWarnings("unused")
	private MessageHeaders createHeadersForTargetSessionId(String sessionId)
	{
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
