package shan_shine.informus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.TRANSPARENT;
import static shan_shine.informus.R.id.buttonContin;


public class SignUpActivity  extends Activity
{

    EditText firstName, lastName, email, confirmEmail, password, confirmPassword, phoneNum;
    RadioGroup g;
    RadioButton selectedButton;

    Boolean passwordsMatch;
    Boolean emailsMatch;
    Boolean allFilled;

    String nothing="";
    String fnameString ="";
    String lnameString="";
    String gender;
    String emailString="";
    String confirmEmailString="";
    String passwordString ="";
    String confirmPasswordString="";
    String phoneNumString ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_sign_up);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClick(View v)
    {
        Printout.message(this, "Continue button clicked");


        firstName = (EditText)findViewById(R.id.txtFirstName);
        lastName = (EditText)findViewById(R.id.txtLastName);
        email = (EditText)findViewById(R.id.txtEmail);
        confirmEmail = (EditText)findViewById(R.id.txtConfirmEmail);
        password = (EditText)findViewById(R.id.txtPassword);
        confirmPassword = (EditText)findViewById(R.id.txtConfPassword);
        phoneNum = (EditText)findViewById(R.id.txtMobile);

        //RadioButton Group
        g=(RadioGroup)findViewById(R.id.radiogroup_gender);
        int selected = g.getCheckedRadioButtonId();

        /* selected button illustrating if it is a male of female */
        selectedButton = (RadioButton) findViewById(selected);

        //Transfering Values
        fnameString = firstName.getText().toString();
        lnameString = lastName.getText().toString();
        gender = selectedButton.getText().toString();
        emailString = email.getText().toString();
        confirmEmailString= confirmEmail.getText().toString();
        passwordString = password.getText().toString();
        confirmPasswordString = confirmPassword.getText().toString();
        phoneNumString = phoneNum.getText().toString();


        Printout.message(this, ""+passwordString);
        passwordsMatch = checkIfPasswordsMatch(passwordString, confirmPasswordString);

        emailsMatch = checkIfEmailsMatch(emailString, confirmEmailString);

        allFilled = checkIfAllFilled(fnameString, lnameString,emailString,confirmEmailString,passwordString,confirmPasswordString,phoneNumString);



        if (emailsMatch && passwordsMatch && allFilled)

        {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("username", emailString));
            nameValuePairs.add(new BasicNameValuePair("fname", fnameString));
            nameValuePairs.add(new BasicNameValuePair("lname", lnameString));
            nameValuePairs.add(new BasicNameValuePair("gender", gender));
            nameValuePairs.add(new BasicNameValuePair("phoneNum", phoneNumString));
            nameValuePairs.add(new BasicNameValuePair("password", passwordString));


            try {

                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost("http://shanalecia.com/addNewUser.php");

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();


                Printout.message(this, "Successful");
            }
            catch (Exception e)
            {
                Printout.message(this, "Womp, error");
                e.printStackTrace();
            }
        }

    }
    private Boolean checkIfPasswordsMatch(String password1, String password2)
    {
        if (password1.equals(password2)== true)
        {
            return true;
        }
        return false;
    }

    private Boolean checkIfEmailsMatch(String email1, String email2 )
    {
        if (email1.equals(email2) ==true)
        {
            return true;
        }
        return false;
    }





    private Boolean checkIfAllFilled (String fn, String ln, String email, String emailc, String pass, String passC, String mobile)
    {
        if ((fn.equals(nothing)==false) && (ln.equals(nothing)==false) && (email.equals(nothing)==false) &&(emailc.equals(nothing)==false) &&((pass.equals(nothing)==false))&&(passC.equals(nothing)==false) &&(mobile.equals(nothing)==false))
        {
            return true;
        }
        return false;
    }

    public void EndOnClick(View view)
    {
        Printout.message(this, "clicked else");
    }

}
