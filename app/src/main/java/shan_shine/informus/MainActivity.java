package shan_shine.informus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import shan_shine.informus.Databases.LogInLogDatabase;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    Button login;
    EditText password;
    EditText username;
    String name;
    String pass;
    TextView link;
    Context context = this;

    LogInLogDatabase logger;
    Boolean logEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login= (Button) findViewById(R.id.btnLogIn);
        username = (EditText) findViewById(R.id.txtLogin);
        password = (EditText) findViewById(R.id.txtPassword);
        link = (TextView)findViewById(R.id.link_signUp);



        logger= new LogInLogDatabase(this);

        List<LoginLog> leto = new ArrayList<LoginLog>();

        leto= logger.getAllLoginItems();
        if (leto.isEmpty())
        {
            Log.d("IsEmptyValue","True" );
            logEmpty = true;
        }
        else
        {
            Log.d("IsEmptyValue","False" );


            List<LoginLog> lis = new ArrayList<>();
            lis = logger.getAllLoginItems();

            String one;
            String two;

            one = lis.get(0).getUsername();
            two = lis.get(0).getPassword();


            Log.e("Checking values 1: ", one);
            Log.e("Checking values 2: ", two);

            if (lis.get(0).getPassword().equals("true"))
            {
                Intent nextScreen = new Intent(getApplicationContext(), HomePagefragActivity.class);
                nextScreen.putExtra("loggedInAs", one);
                startActivity(nextScreen);


                finish();
            }


            //--> straight in
        }


        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             signupClick();
            }
        });

        login.setOnClickListener(this);
    }

    class task extends AsyncTask<String, String, Void>
    {
        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        InputStream is = null ;
        String result = "";
        protected void onPreExecute() {
            progressDialog.setMessage("Fetching data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task.this.cancel(true);
                }
            });
        }
        @Override
        protected Void doInBackground(String... params) {
            String url_select = "http://shanalecia.com/Login.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                //read content
                is =  httpEntity.getContent();

            } catch (Exception e) {

                Log.e("log_tag", "Error in http connection " + e.toString());
                //Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line=br.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                is.close();
                result=sb.toString();

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error converting result "+e.toString());
            }

            return null;

        }
        protected void onPostExecute(Void v) {

            // ambil data dari Json database
            try {
                JSONArray Jarray = new JSONArray(result);
                for(int i=0;i<Jarray.length();i++)
                {
                    JSONObject Jasonobject = null;
                    Jasonobject = Jarray.getJSONObject(i);

                    name = Jasonobject.getString("username");
                    String db_detail="";

                    if(username.getText().toString().equalsIgnoreCase(name))

                    {  pass = Jasonobject.getString("password");

                            if (password.getText().toString().equals(pass))
                            {
                               // Printout.message(context, "Progress");
                                localDatabase();

                                if( logEmpty = true)
                                {
                                    LoginLog ite = new LoginLog(name, "true");
                                    logger.addLoginItem(ite);
                                    Log.d("Added?", "Yes");
                                }

                                Intent nextScreen = new Intent(getApplicationContext(), HomePagefragActivity.class);
                                nextScreen.putExtra("loggedInAs", name);
                                startActivity(nextScreen);


                                finish();
                                }


                            else
                            {
                                Printout.message(context, "Didnt Work");
                            }



                    }


                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    }








    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
            case R.id.btnLogIn :
                //Printout.message(this, "here we go");
                new task().execute();
                break;
        }

    }

    public void signupClick()
    {
        //Printout.message(this, "SignUp CLicked");


        Intent nextScreen = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(nextScreen);




    }

    public void forgetPasswordClick(View v)
    {
        //Printout.message(this, "Forget Password Clicked");

        Intent nextScreen = new Intent(getApplicationContext(), ForgotPassword.class);
        startActivity(nextScreen);

    }


    public void elseClick(View v)
    {
        Printout.message(this, "else");
    }

    public void localDatabase()
    {
        try {

            LoginDatabase db = new LoginDatabase(this);
            if ( db.getLoginItem(name) ==null)
            {
                //Printout.message(this, "not there");
                db.addLoginItem(new LoginItem(name,pass));
            }
            else {
                //Printout.message(this, "there");
            }
        }
        catch(Exception e)
        {
            //Printout.message(this, "nah");
        }
    }

    protected void loadGroup()
    {
        InputStream is = null ;
        Printout.message(this, "attempting to load again");

        String url_select = "http://shanalecia.com/Login.php";

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url_select);

        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(param));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            //read content
            is =  httpEntity.getContent();

        } catch (Exception e) {

            Log.e("log_tag", "Error in http connection " + e.toString());
            Printout.message(this, "error in loading ");
            //Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
        }
    }

}
