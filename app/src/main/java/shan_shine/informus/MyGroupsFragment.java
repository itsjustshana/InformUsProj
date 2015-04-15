package shan_shine.informus;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroupsFragment extends Fragment {

    String loggedInAs;
    View v;
    TextView logged;
    Context context;
    InputStream is = null ;
    String result;
    String creator;

    public MyGroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        loggedInAs = getArguments().getString("Logged as");
        result = getArguments().getString("Groups");

        context = getActivity().getApplicationContext();

        //Printout.message(context, "The result is!!!!!!!!!" + result);

        v = inflater.inflate(R.layout.fragment_my_groups, container, false);

        // new task().execute();

        logged = (TextView) v.findViewById(R.id.text_loggedAsMyGroupsPage);
        logged.setText("Logged in as " + loggedInAs);


        try {
            JSONArray Jarray = new JSONArray(result);
            for(int i=0;i<Jarray.length();i++) {
                JSONObject Jasonobject = null;
                Jasonobject = Jarray.getJSONObject(i);

                creator = Jasonobject.getString("creator");
                Printout.message(context, "Creator"+creator);
            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error parsing data "+e.toString());
        }


        return v;
    }





}


