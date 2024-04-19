package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;

import java.util.ArrayList;
import java.util.HashMap;

public class MainPageActivity extends AppCompatActivity implements CTInboxListener {
    Button searchedButton,productViewButton,chargedButton,inboxButton,productTaggedButton,orderButton,webButton,pushUnsubButton;
    CleverTapAPI clevertapDefaultInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        clevertapDefaultInstance.setCTNotificationInboxListener(this);
        //Initialize the inbox and wait for callbacks on overridden methods
        clevertapDefaultInstance.initializeInbox();

        searchedButton = findViewById(R.id.button_search);
        productViewButton = findViewById(R.id.button2_product_viewed);
        chargedButton = findViewById(R.id.button3_Charged);
        inboxButton= findViewById(R.id.button_inbox);
        productTaggedButton=findViewById(R.id.button_tagged);
        orderButton=findViewById(R.id.button_order);
        webButton=findViewById(R.id.button_next);
        pushUnsubButton = findViewById(R.id.push_btn);

        searchedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> searched = new HashMap<>();
                searched.put("Product", "Watch");
                clevertapDefaultInstance.pushEvent("Searched", searched);
            }
        });
        productViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> productViewed= new HashMap<>();
                productViewed.put("Product","Watch");
                clevertapDefaultInstance.pushEvent("Product viewed",productViewed);

            }
        });

        productTaggedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clevertapDefaultInstance.pushEvent("Product tagged");
            }
        });


        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Order Recieved");

            }
        });

        chargedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
                chargeDetails.put("Amount", 300);
                chargeDetails.put("Payment Mode", "Credit card");
                chargeDetails.put("Charged ID", 24052013);

                HashMap<String, Object> item1 = new HashMap<String, Object>();
                item1.put("Product category", "books");
                item1.put("Book name", "The Millionaire next door");
                item1.put("Quantity", 1);

                HashMap<String, Object> item2 = new HashMap<String, Object>();
                item2.put("Product category", "books");
                item2.put("Book name", "Achieving inner zen");
                item2.put("Quantity", 1);

                HashMap<String, Object> item3 = new HashMap<String, Object>();
                item3.put("Product category", "books");
                item3.put("Book name", "Chuck it, let's do it");
                item3.put("Quantity", 5);

                ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                items.add(item1);
                items.add(item2);
                items.add(item3);

                clevertapDefaultInstance.pushChargedEvent(chargeDetails, items);


            }
        });
         webButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i2=new Intent(MainPageActivity.this,MainActivity2.class);
                 startActivity(i2);

             }
         });

         pushUnsubButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                 profileUpdate.put("MSG-sms", true);
                 
                 clevertapDefaultInstance.pushProfile(profileUpdate);
             }
         });


    }

    @Override
    public void inboxDidInitialize() {
        inboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> tabs = new ArrayList<>();
                tabs.add("Promotions");
                tabs.add("Offers");//We support upto 2 tabs only. Additional tabs will be ignored

                CTInboxStyleConfig styleConfig = new CTInboxStyleConfig();
                styleConfig.setFirstTabTitle("First Tab");
                styleConfig.setTabs(tabs);//Do not use this if you don't want to use tabs
                styleConfig.setTabBackgroundColor("#FF0000");
                styleConfig.setSelectedTabIndicatorColor("#0000FF");
                styleConfig.setSelectedTabColor("#0000FF");
                styleConfig.setUnselectedTabColor("#FFFFFF");
                styleConfig.setBackButtonColor("#FF0000");
                styleConfig.setNavBarTitleColor("#FF0000");
                styleConfig.setNavBarTitle("MY INBOX");
                styleConfig.setNavBarColor("#FFFFFF");
                styleConfig.setInboxBackgroundColor("#ADD8E6");
                if (clevertapDefaultInstance != null) {
                    clevertapDefaultInstance.showAppInbox(styleConfig); //With Tabs
                }
            }
        });

    }

    @Override
    public void inboxMessagesDidUpdate() {

    }
}