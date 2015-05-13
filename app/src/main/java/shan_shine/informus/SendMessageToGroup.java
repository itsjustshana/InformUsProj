package shan_shine.informus;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendMessageToGroup extends Fragment {

    View v;
    EditText message;
    TextView groupName;
    String groupNameString;
    String loggedInAs;
    String messageToSend;
    Context context;
    Button clicked;
    String formattedDate;
    Communicato comm;

    public SendMessageToGroup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity().getApplicationContext();

        loggedInAs = getArguments().getString("Logged as");
        groupNameString = getArguments().getString("Group name");


        v = inflater.inflate(R.layout.fragment_send_message_to_group, container, false);
        groupName = (TextView) v.findViewById(R.id.label_GroupName);
        groupName.setText("" + groupNameString);
        message = (EditText) v.findViewById(R.id.lbl_messageToSend);

        clicked = (Button) v.findViewById(R.id.button_sendMessage);
        clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageButtonCLicked();
            }
        });


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        comm= (Communicato) getActivity();

    }

    public void sendMessageButtonCLicked() {

        messageToSend = message.getText().toString();
        Printout.message(context, "messageToSend " + messageToSend);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());

        Printout.message(context, "Button Clicked");
        String [] strings = new String[4];

        strings[0] = formattedDate;
        strings[1] = groupNameString;
        strings[2] = messageToSend;

        comm.responsetoCreateMessage(strings);


    }
}