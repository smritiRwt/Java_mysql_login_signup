package com.example.android_mysql.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_mysql.R;
import com.example.android_mysql.utils.SharedPreferenceManager;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView txtusername;
    private TextView txtemail;
    private ImageView logoutbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(!SharedPreferenceManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        txtusername=(TextView)findViewById(R.id.usernamee);
        txtemail=(TextView)findViewById(R.id.emaill);
        logoutbtn=(ImageView)findViewById(R.id.logout);

        txtusername.setText(SharedPreferenceManager.getInstance(this).getUsername());
        txtemail.setText(SharedPreferenceManager.getInstance(this).getUserEmail());
        logoutbtn.setOnClickListener(this);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id=item.getItemId();
       switch (item.getItemId()){
           case R.id.menu:
               SharedPreferenceManager.getInstance(this).logOut();
               finish();
               startActivity(new Intent(this,LoginActivity.class));
               break;
       }
        return true;
    }
*/

    @Override
    public void onClick(View v) {
        if(v==logoutbtn){
            SharedPreferenceManager.getInstance(this).logOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}