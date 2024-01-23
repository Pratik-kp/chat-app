package com.chat.chatapp.user;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {

    @Id
    private  String nickname;
    private String fullname;
    private Status status;
}
