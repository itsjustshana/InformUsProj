package shan_shine.informus;

import java.util.Date;

/**
 * Created by Shanakay on 3/24/2015.
 */
public class SendMessage {
    Message message;
    Date sendDate;
    Person sender;
    
    SendMessage(Message message, Date sendDate, Person sender)
    {
        super();
        this.message = message;
        this.sendDate = sendDate;
        this.sender = sender;
    }
}
