package com.app.project.domain.dtos.ChatDto;

public class UserMessageDto {

    private int id;
    private String fullName;
    private String email;
    private String message;

    public UserMessageDto(int id, String fullName, String email, String message) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.message = message;
    }

    public UserMessageDto(){

    }
}
