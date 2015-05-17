package shan_shine.informus;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class JoinGroupFragment extends Fragment {

View v;
    Context context;
    String name;
    String description;
    String creator;
    String dateCreated;
    String loggedInAs;
    String result;
    TextView nameOfGroup;
    TextView createdBy;
    TextView groupDescr;
    String formattedDate;
    Button join;

    Communicato comm;



    public JoinGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        result = getArguments().getString("Result");
        loggedInAs = getArguments().getString("Logged as");
        context = getActivity().getApplicationContext();

        v= inflater.inflate(R.layout.fragment_join_group, container, false);

        groupDescr = (TextView)v.findViewById(R.id.text_descritionJoin);
        nameOfGroup = (TextView)v.findViewById(R.id.text_groupNameJoin);
        createdBy= (TextView)v.findViewById(R.id.text_createdByJoin);
        join= (Button)v.findViewById(R.id.button_joinGroup);



        try {
            JSONArray Jarray = new JSONArray(result);
            for (int i = 0; i < Jarray.length(); i++) {
                JSONObject Jasonobject = null;
                Jasonobject = Jarray.getJSONObject(i);

                name = Jasonobject.getString("name");
                description =  Jasonobject.getString("description");
                creator =  Jasonobject.getString("creator");
                dateCreated = Jasonobject.getString("dateCreated");



            nameOfGroup.setText(name);
            createdBy.setText(creator);
            groupDescr.setText(description);

            }

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error parsing data " + e.toString());
        }



        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });

        return v;
    }



    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        comm= (Communicato) getActivity();

    }


    private void Add()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());


        String[] send = new String [3];

        send[0]= formattedDate;
        send[1]= loggedInAs;
        send[2]= name;

        comm.toJoinGroup(send);

    }
}

