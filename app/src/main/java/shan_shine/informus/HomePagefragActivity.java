package shan_shine.informus;

import android.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HomePagefragActivity extends FragmentActivity implements Communicato {

    Context context = this;
    String loggedInAs;
    InputStream is = null;
    String result = "";
    String hellHell ;
    String sendToDate;
    String sendToGroup;
    String sendToMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        loggedInAs = getIntent().getExtras().getString("loggedInAs");



        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }


        launchHomePage();

    }


    public void FollowingClick(View v) {
        new task3().execute();

    }

    public void FollowNewGroup(View v)
    {
        FollowerGroupSearch frag2 = new FollowerGroupSearch();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, frag2);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    public void launchMyGroups(View v) {
        new task2().execute();

    }

    public void createNewGroup(View v) {
        CreateGroup fragment = new CreateGroup();
        Bundle bundle = new Bundle();
        bundle.putString("Logged as", loggedInAs);

        fragment.setArguments(bundle);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void launchHomePage() {
        HomePageFrag frag1 = new HomePageFrag();


        Bundle bundle = new Bundle();
        bundle.putString("Logged as", loggedInAs);

        frag1.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, frag1).commit();
    }

    @Override
    public void respondSendToGroup(String data) {
        Printout.message(this, "About to send a message to members of the group " + data+ ", from "+loggedInAs);

        SendMessageToGroup fragment = new SendMessageToGroup();
        Bundle bundle = new Bundle();
        bundle.putString("Logged as", loggedInAs);
        bundle.putString("Group name", data);

        fragment.setArguments(bundle);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }


    @Override
    public void responsetoCreateMessage(String[] data)
    {
        Printout.message(context, "Got something "+data[0]+" "+data[1]+ ""+data[2]);
        sendToGroup = data[1];
        sendToMessage= data[2];
        sendToDate= data[0];
        Printout.message(context, "Before");

        new task4().execute();

        Printout.message(context, "aFTER");
    }



    @Override
    public void searchValAddGroup(String data)
    {

    }
    //my groups
    class task2 extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        InputStream is = null;


        protected void onPreExecute() {
            progressDialog.setMessage("Fetching Groups...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task2.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("username", loggedInAs));


            String url_select = "http://shanalecia.com/LoadMyGroups.php";


            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                //httpPost.setEntity(new UrlEncodedFormEntity(param));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();

                //read content
                is = httpEntity.getContent();

            } catch (Exception e) {

                //Printout.message(context, "Failed at 1");
                Log.e("log_tag", "Error in http connection " + e.toString());

            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                //Printout.message(context, "Here in function"+result);
               // hellHell = result;

            } catch (Exception e) {
                // TODO: handle exception
               // Printout.message(context, "Failed at 2");
                Log.e("log_tag", "Error converting result " + e.toString());
            }

            return null;

        }

        protected void onPostExecute(Void v) {
            //Printout.message(context, "Success :)");
            this.progressDialog.dismiss();
            //Printout.message(context, "The result is" + result);
            hellHell = result;


            Bundle bundle = new Bundle();
            bundle.putString("Logged as", loggedInAs);
            bundle.putString("Groups", result);

            MyGroupsFragment frag2 = new MyGroupsFragment();
            frag2.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, frag2);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }
    }

    //following groups
    class task3 extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        InputStream is = null;


        protected void onPreExecute() {
            progressDialog.setMessage("Fetching Groups...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task3.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {
            String url_select = "http://shanalecia.com/LoadMyFollowedGroups.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                //read content
                is = httpEntity.getContent();

            } catch (Exception e) {

                Printout.message(context, "Failed at 1");
                Log.e("log_tag", "Error in http connection " + e.toString());
                //Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                //Printout.message(context, "Here in function"+result);
                // hellHell = result;

            } catch (Exception e) {
                // TODO: handle exception
                Printout.message(context, "Failed at 2");
                Log.e("log_tag", "Error converting result " + e.toString());
            }

            return null;

        }

        protected void onPostExecute(Void v) {
            //Printout.message(context, "Success :)");
            this.progressDialog.dismiss();
           // Printout.message(context, "The result is" + result);
            hellHell = result;


            Bundle bundle = new Bundle();
            bundle.putString("Logged as", loggedInAs);
            bundle.putString("Groups", result);


            FollowingFragment frag2 = new FollowingFragment();
            frag2.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, frag2);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }
    }


//Sending Message to a Group
    class task4 extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(context);



        protected void onPreExecute() {
            progressDialog.setMessage("Sending Message Groups...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task4.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {

            try
            {
                    String url_select = "http://shanalecia.com/sendNewMessage.php";

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url_select);

                    List<NameValuePair> param = new ArrayList<>(1);
                    param.add(new BasicNameValuePair("dateCreated", sendToDate));
                    param.add(new BasicNameValuePair("messageText", sendToMessage));
                    param.add(new BasicNameValuePair("groupId", sendToGroup));



                        httpPost.setEntity(new UrlEncodedFormEntity(param));
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        HttpEntity httpEntity = httpResponse.getEntity();

                        //Printout.message(context, "Worked?");
                return null;

            }


            catch (Exception e) {

                Printout.message(context, "Failed at 1");

            }
            return null;
        }



        protected void onPostExecute(Void v) {

            this.progressDialog.dismiss();

        }
    }



}

