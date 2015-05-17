package shan_shine.informus;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFrag extends Fragment {

    String loggedInAs;
    TextView logged;
    View v;
    String result;
    Context context;
    TextView tester;
    MessageDatabase inbox;
    List<Message> groupList;
    TextView unreadC;
    TextView readC;

    //For the message Objects
    String messID;
    String messText;
    String email;
    String dateCr;
    String groupID;
    String read;

    ListView listViewHandleUnread;
    ListView listViewHandleRead;

    Integer unreadCount;
    Integer readCount;

    List<Message>ReadResults ;
    List<Message>UnreadResults;

    Communicato comm;

    public HomePageFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loggedInAs = getArguments().getString("Logged as");
        result = getArguments().getString("Result");
        context = getActivity().getApplicationContext();

        readCount=0;
        unreadCount=0;


        v = inflater.inflate(R.layout.fragment_home_page, container, false);

        logged = (TextView) v.findViewById(R.id.text_loggedInAs);
        unreadC =(TextView)v.findViewById(R.id.textView_unreadMessageCount);
        readC =(TextView)v.findViewById(R.id.textView_readMessageCount);

        logged.setText("Logged in as " + loggedInAs);

        inbox = new MessageDatabase(context);

        groupList =new ArrayList<>();


        if (result != null) {

            try {
                JSONArray Jarray = new JSONArray(result);
                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject Jasonobject = null;
                    Jasonobject = Jarray.getJSONObject(i);

                    messID = Jasonobject.getString("messageID");
                    messText = Jasonobject.getString("messageText");
                    email = Jasonobject.getString("email");
                    dateCr = Jasonobject.getString("dateCreated");
                    groupID = Jasonobject.getString("groupId");

                    Message mess = new Message(messID, messText, groupID, email, dateCr);



                if (isInDatabase(messID) == false)
                    {
                      //  groupList.add(mess);
                        inbox.addMessageItem(mess);
                    }
                }


            } catch (Exception e) {
                // TODO: handle exception
                Printout.message(context, "This is an error message");
                Log.e("log_tag", "Error parsing data " + e.toString());
            }





            ReadResults = getReadMessages();

            List<String>ReadString = new ArrayList<>();
            ReadString= getReadStrings(ReadResults);
            readC.setText("("+readCount+")");

            //List<Message>UnreadResults= new ArrayList<>();
            UnreadResults = getUnreadMessages();


            List<String>UnreadString = new ArrayList<>();
            UnreadString= getUnreadStrings(UnreadResults);
            unreadC.setText("(" + unreadCount + ")");

            listViewHandleUnread = (ListView) v.findViewById(R.id.listView_unreadMessages);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, UnreadString);
            listViewHandleUnread.setAdapter(adapter);

            listViewHandleRead = (ListView) v.findViewById(R.id.listView_ReadMessages);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, ReadString);
            listViewHandleRead.setAdapter(adapter2);

            listViewHandleRead.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Printout.message(context, " This is the total for project: " + position);
                    Message clickedMessage = ReadResults.get(position);


                    String val = clickedMessage.getMessageText();

                    Printout.message(context, ""+val);
                    Printout.message(context, "here");

                }
            });


            listViewHandleUnread.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Printout.message(context, " This is the total for project: "+ position);

                    Message clickedMessage = UnreadResults.get(position);
                    comm.toViewMessageContent(clickedMessage);


                }
            });


        }
        else

        {
            Printout.message(context, "Null received");
        }
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        comm= (Communicato) getActivity();

    }

    private Boolean isInDatabase(String messID)
    {
        List<Message> listt= new ArrayList<Message>();
        listt = inbox.getAllMessageItems();
        Boolean inDatabase = false;

        for (Message ti : listt) {
            String result = "Message ID: " + ti.getMessageId() + "Message Text: " + ti.getMessageText()+ "Message ReadStatus: " + ti.getRead();

            if ((ti.getMessageId().equals(messID))== true)
            {
                inDatabase = true;
            }

        }

        return inDatabase;

    }

    private List<Message> getReadMessages()
    {
        List<Message> readMessages= new ArrayList<>();

            List<Message> listt= new ArrayList<Message>();
            listt = inbox.getAllMessageItems();


            for (Message ti : listt) {

                if (ti.getRead().equals("T")==true)
                {
                    readMessages.add(ti);
                }
            }

        return readMessages;

    }

    private List<Message> getUnreadMessages()
    {
        List<Message> readMessages= new ArrayList<>();

        List<Message> listt= new ArrayList<Message>();
        listt = inbox.getAllMessageItems();


        for (Message ti : listt) {

            if (ti.getRead().equals("F")==true)
            {
                readMessages.add(ti);
            }
        }

        return readMessages;

    }

    private List<String> getReadStrings(List<Message> mess)
    {
        List<String> returnn = new ArrayList<>();

        for (Message ti : mess) {

            if (ti.getRead().equals("T")==true)
            {
                returnn.add(""+ti.getGroupId()+"   "+ti.getMessageText());
                readCount++;
            }
        }
        return returnn;
    }

    private List<String> getUnreadStrings(List<Message> mess)
    {
        List<String> returnn = new ArrayList<>();

        for (Message ti : mess) {

            if (ti.getRead().equals("F")==true)
            {
                returnn.add(""+ti.getGroupId()+"   "+ti.getMessageText());
                unreadCount++;
                //Printout.message(context, ""+unreadCount);
            }
        }
        return returnn;
    }


}
