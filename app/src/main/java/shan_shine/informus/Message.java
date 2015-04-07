package shan_shine.informus;

/**
 * Created by Shanakay on 3/24/2015.
 */

public class Message

{
    String messageId;
    String messageText;

    public Message(String messageId, String messageText) {
        super();
        this.messageId = messageId;
        this.messageText = messageText;
    }

    public String getMessageText()
    {
        return this.messageText;
    }

}