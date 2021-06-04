package com.example.android_mysql.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android_mysql.R;
import com.example.android_mysql.utils.Constants;
import com.example.android_mysql.utils.RequestHandler;
import com.example.android_mysql.utils.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
private TextView logitoregister;
private EditText etusers;
private  EditText etpassword;
private Button login;
private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if(SharedPreferenceManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this,HomeActivity.class));
            return;
        }

        logitoregister=(TextView)findViewById(R.id.loginToReg);
        etusers=(EditText)findViewById(R.id.username);
        etpassword=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.logIn);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");


        logitoregister.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==logitoregister){startActivity(new Intent(this,MainActivity.class));}
        if(v==login){
            userLogin();
        }
    }

    private void  userLogin()
    {
    final String username1=etusers.getText().toString().trim();
    final  String password1=etpassword.getText().toString().trim();

       progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj=new JSONObject(response);
                    if(!obj.getBoolean("error")){
                        SharedPreferenceManager.getInstance(getApplicationContext()).userLogin(
                                obj.getInt("id"),
                                obj.getString("username"),
                                obj.getString("email")
                        );
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        finish();
 }
                    else
                    {
                        Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                   System.out.println("error"); }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }
        ){
            @org.jetbrains.annotations.NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username1);
                params.put("password",password1);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}