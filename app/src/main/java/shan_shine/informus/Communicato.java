package shan_shine.informus;

/**
 * Created by Shanakay on 5/11/2015.
 */
public interface Communicato {


    public void respondSendToGroup (String data);

    public void responsetoCreateMessage( String[] data);

    public void searchValAddGroup();

    public void joidGroupVal(String data);

    public void toJoinGroup( String[] data);

    public void toViewMessageContent (Message data);

    public void toViewMessageContent2 (Message data);

    public void toLeaveGroup( String data);

    public void DeleteGroup(String data);

    public void joinnGroupVal(String data);

    public void deleteGroup(String delval);

    public void viewMessages(String groupName);

    public void toViewMessageContent3(Message clickedMessage);

    public void toViewSent(String groupName);

    public void goHome();

    public void goToMyGroups();

    public void goToFollowingGroups();


}
