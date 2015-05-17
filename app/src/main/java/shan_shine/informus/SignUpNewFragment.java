package shan_shine.informus;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpNewFragment extends Fragment {


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
    View v;
    Context context;



    public SignUpNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for context fragment


        v=  inflater.inflate(R.layout.fragment_sign_up_new, container, false);
        return v;
    }

    public void buttonClick(View v)
    {
        //Printout.message(context, "Continue button clicked");

        firstName = (EditText)v.findViewById(R.id.txtFirstName);
        lastName = (EditText)v.findViewById(R.id.txtLastName);
        email = (EditText)v.findViewById(R.id.txtEmail);
        confirmEmail = (EditText)v.findViewById(R.id.txtConfirmEmail);
        password = (EditText)v.findViewById(R.id.txtPassword);
        confirmPassword = (EditText)v.findViewById(R.id.txtConfPassword);
        phoneNum = (EditText)v.findViewById(R.id.txtMobile);

        //RadioButton Group
        g=(RadioGroup)v.findViewById(R.id.radiogroup_gender);
        int selected = g.getCheckedRadioButtonId();

        /* selected button illustrating if it is a male of female */
        selectedButton = (RadioButton) v.findViewById(selected);

        //Transfering Values
        fnameString = firstName.getText().toString();
        lnameString = lastName.getText().toString();
        gender = selectedButton.getText().toString();
        emailString = email.getText().toString();
        confirmEmailString= confirmEmail.getText().toString();
        passwordString = password.getText().toString();
        confirmPasswordString = confirmPassword.getText().toString();
        phoneNumString = phoneNum.getText().toString();


        Printout.message(context, ""+passwordString);
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


                Printout.message(context, "Signup Successful");



            }
            catch (Exception e)
            {
                Printout.message(context, "Signup error");
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
        else {
            Printout.message(context, "Passwords dont match");
        }
        return false;
    }

    private Boolean checkIfEmailsMatch(String email1, String email2 )
    {
        if (email1.equals(email2) ==true)
        {
            return true;
        }

        else {

            Printout.message(context, "Emails dont match");
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
        Printout.message(context, "clicked else");
    }


}
