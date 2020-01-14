package com.app.project.domain.entities;

import javax.persistence.*;

@Entity
@Table(name="chat")
public class Chat extends BaseEntity{

    private String message;
    private String response;
    private User user;


    public Chat() {
    }

    @Column(name="message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name="response")
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
