package com.chat.chatapp.chatroom;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    public Optional<String> getChatRoomId(
            String senderId,
            String receipientId,
            boolean createNewRoomIfNotExists
    ){
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId,receipientId)
                .map(ChatRoom :: getChatId)
                .or(()->{
                    if(createNewRoomIfNotExists){
                        var chatId=createChatId(senderId,receipientId);
                        Optional.of(chatId);
                    }
                    return Optional.empty();
                });

    }

    private String createChatId(String senderId, String receipientId) {
        var chatId=String.format("%s_%s",senderId, receipientId);
        ChatRoom senderRecipient=ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receipientId(receipientId)
                .build();
        ChatRoom recipientSender=ChatRoom.builder()
                .chatId(chatId)
                .senderId(receipientId)
                .receipientId(senderId)
                .build();
        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);
        return chatId;
    }
}
