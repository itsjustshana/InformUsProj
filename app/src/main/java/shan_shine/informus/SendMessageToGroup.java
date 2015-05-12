package shan_shine.informus;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendMessageToGroup extends Fragment{

View v;
    EditText message;
    TextView groupName;
    String groupNameString;
    String loggedInAs;
    String messageToSend;
    Context context;
    Button clicked;

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
        groupName.setText(""+groupNameString);
        message = (EditText) v.findViewById(R.id.lbl_messageToSend);

        clicked = (Button)v.findViewById(R.id.button_sendMessage);
        clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageButtonCLicked(v);
            }
        });


        return v;
    }

    public void sendMessageButtonCLicked (View v)
    {
        messageToSend = message.getText().toString();
        Printout.message(context, "messageToSend "+ messageToSend);


    }



/*
    private void ThisFunction()  {


        // new task().execute();
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("creator", creatorString));
            nameValuePairs.add(new BasicNameValuePair("message", ))
            

            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost("http://shanalecia.com/addNewGroup.php");

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();


            Printout.message(context, "Group Created");

            HomePageFrag frag1 = new HomePageFrag();


        }
        catch(Exception e)
        {
            Printout.message(context, "NOPE");
        }

    }
*/
}
