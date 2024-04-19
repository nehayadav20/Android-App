package com.example.testapp;

import static com.clevertap.android.sdk.CleverTapAPI.createNotificationChannel;

import android.app.NotificationManager;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;
import com.clevertap.android.sdk.interfaces.NotificationHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



public class MainActivity extends AppCompatActivity implements DisplayUnitListener {
    EditText email,phone,id,name;
    Button login;
    CleverTapAPI clevertapDefaultInstance;
    String textEmail,textPhone,textName,textid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);
        Location location=clevertapDefaultInstance.getLocation();
        clevertapDefaultInstance.setLocation(location);// lat-long
        clevertapDefaultInstance.enableDeviceNetworkInfoReporting(true); //Region


        email=findViewById(R.id.text_email);
        phone=findViewById(R.id.text_phone);
        id=findViewById(R.id.text_id);
        name=findViewById(R.id.text_name);

        login=findViewById(R.id.button_login);
        // skip=findViewById(R.id.button_skip);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textEmail = email.getText().toString();
                textPhone = phone.getText().toString();
                textName = name.getText().toString();
                textid = id.getText().toString();


                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", textName);                  // String
                profileUpdate.put("Identity", textid);                    // String or number
                profileUpdate.put("Email", textEmail);               // Email address of the user
                profileUpdate.put("Phone", textPhone);                 // Phone (with the country code, starting with +)

                profileUpdate.put("Sign Up date", new Date());

                profileUpdate.put("MSG-push", true);
                profileUpdate.put("MSG-Whatsapp", true);
                clevertapDefaultInstance.onUserLogin(profileUpdate);
                Toast.makeText(MainActivity.this, "Login Up successfully", Toast.LENGTH_SHORT).show();


                Intent i1=new Intent(MainActivity.this,MainPageActivity.class);
                startActivity(i1);
            }
        });

      // CleverTapAPI.createNotificationChannel(getApplicationContext(),"123","Game of Thrones","Game of Thrones", NotificationManager.IMPORTANCE_MAX,true);

// Creating a Notification Channel With Sound Support
       CleverTapAPI.createNotificationChannel(getApplicationContext(),"123","Default","Default", NotificationManager.IMPORTANCE_DEFAULT,true,"sound.mp3");
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        for (int i = 0; i <units.size() ; i++) {
            CleverTapDisplayUnit unit = units.get(i);
            prepareDisplayView(unit);
        }
    }

    private void prepareDisplayView(CleverTapDisplayUnit unit) {
        LinearLayout parentLayout = findViewById(R.id.linear);
        for (CleverTapDisplayUnitContent contentItem : unit.getContents()) {
            String media = contentItem.getMedia();
            System.out.println("Media: " + media);
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(media).into(imageView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            imageView.setLayoutParams(layoutParams);
            parentLayout.addView(imageView);
        }
    }
}