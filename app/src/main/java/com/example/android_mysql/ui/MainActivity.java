package com.example.android_mysql.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.android_mysql.utils.HttpsTrustManager;
import com.example.android_mysql.utils.RequestHandler;
import com.example.android_mysql.utils.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etusername,etpassword,etemail;
    private Button bregister;
    private ProgressDialog progressDialog;
    private TextView regtologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(SharedPreferenceManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this,HomeActivity.class));
            return;
        }

        etusername=(EditText)findViewById(R.id.username);
        etpassword=(EditText)findViewById(R.id.password);
        etemail=(EditText)findViewById(R.id.email);

        bregister=(Button)findViewById(R.id.signup);
        progressDialog=new ProgressDialog(this);

        bregister.setOnClickListener(this);

        regtologin=(TextView)findViewById(R.id.regToLogin);
        regtologin.setOnClickListener(this);
    }

    private  void registerUser()
    {
       final String email=etemail.getText().toString().trim();
       final String username=etusername.getText().toString().trim();
       final  String password=etpassword.getText().toString().trim();

        HttpsTrustManager.allowAllSSL();

       progressDialog.setMessage("Registering user");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                        System.out.println(error.getMessage());
                    }
                }){
            @org.jetbrains.annotations.NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                params.put("email",email);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {

        if(v==bregister){registerUser();}
        if(v==regtologin){startActivity(new Intent(this,LoginActivity.class));}
    }
}