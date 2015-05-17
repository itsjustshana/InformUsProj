package shan_shine.informus;

/**
 * Created by Shanakay on 3/24/2015.
 */

public class Message

{
    String messageId;
    String messageText;
    String email;
    String dateCreated;
    String groupId;
    String read;

    public Message(String messageId, String messageText, String email, String groupId, String DateCreated) {
        super();
        this.messageId = messageId;
        this.messageText = messageText;
        this.groupId=groupId;
        this.email=email;
        this.dateCreated = DateCreated;
        this.read = "F";

    }

    public String getMessageText()
    {
        return this.messageText;
    }

    public String getMessageId()
    {
        return this.messageId;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getDateCreated()
    {
        return this.dateCreated;
    }

    public String getRead()
    {
        return this.read;
    }

    public String getGroupId()
    {
        return this.groupId;
    }

    public String print()
    {
        return (""+messageText+ " "+ read);
    }

    public void setReadRead(){
        this.read="T";
    }

    public void setRead(String read)
    {
        this.read = read;

    }

}