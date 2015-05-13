package shan_shine.informus;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroup extends Fragment {
    View v;
    String loggedInAs;
    EditText creator;
    EditText ed_name;
    EditText ed_descr;
    Context context;

    String creatorString;
    String groupName;
    String groupDescr;
    String formattedDate;
    Date dateCreated;

    public CreateGroup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Inflate the layout for this fragment
        loggedInAs = getArguments().getString("Logged as");
        context = getActivity().getApplicationContext();

        v = inflater.inflate(R.layout.fragment_create_group, container, false);

        creator = (EditText)v.findViewById(R.id.text_creatorName);
        creator.setText(loggedInAs);
        //Group Name
        ed_name = (EditText)v.findViewById((R.id.edit_groupNameAdd));
        //Group Description
        ed_descr = (EditText)v.findViewById(R.id.edit_groupDescrAdd);

        creatorString = loggedInAs;




        Button button1 = (Button) v.findViewById(R.id.button_creategroupcomplete);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Printout.message(context, "Attempting to create Group");

                ThisFunction();


                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c.getTime());  
            }
        });


        return v;



    }


    private void ThisFunction()  {

        groupName = ed_name.getText().toString();
        groupDescr = ed_descr.getText().toString();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());

        Printout.message(context, formattedDate);
        Printout.message(context, "rawr");
       // new task().execute();
try {
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
    nameValuePairs.add(new BasicNameValuePair("creator", creatorString));
    nameValuePairs.add(new BasicNameValuePair("groupName", groupName));
    nameValuePairs.add(new BasicNameValuePair("groupDescr", groupDescr));
    nameValuePairs.add(new BasicNameValuePair("dateCreated", formattedDate));


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







}
