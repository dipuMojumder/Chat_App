package cse.chatApp;

public class Message {
    private String sender;
    private String receiver;
    private String content;

    // Constructor
    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    // Getter for sender
    public String getSender() {
        return sender;
    }

    // Setter for sender
    public void setSender(String sender) {
        this.sender = sender;
    }

    // Getter for receiver
    public String getReceiver() {
        return receiver;
    }

    // Setter for receiver
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    // Getter for content
    public String getContent() {
        return content;
    }

    // Setter for content
    public void setContent(String content) {
        this.content = content;
    }
}

