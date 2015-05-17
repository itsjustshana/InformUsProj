package shan_shine.informus;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MessageContent extends Fragment {

    String loggedInAs;
    Message message;
    View v;
    Context context;
    List<Message> messageList;

    String messageText;
    String messageId;
    String date;
    String group;
    TextView messageTextHere;
    TextView dateHere;
    TextView groupHere;
    TextView logged;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();
        loggedInAs = getArguments().getString("Logged as");
        messageText = getArguments().getString("MessageText");
        date = getArguments().getString("Date");
        group =  getArguments().getString("GroupId");





        v= inflater.inflate(R.layout.fragment_message_content, container, false);
        messageTextHere = (TextView) v.findViewById(R.id.text_messageHere);
        dateHere = (TextView) v.findViewById(R.id.text_dateRec);
        groupHere = (TextView) v.findViewById(R.id.text_groupIdHere);
        logged = (TextView) v.findViewById(R.id.text_logged);


        messageTextHere.setText(messageText);
        dateHere.setText(date);
        groupHere.setText("From: "+group);
        logged.setText("Logged in as " + loggedInAs);




        return v;
    }



}
