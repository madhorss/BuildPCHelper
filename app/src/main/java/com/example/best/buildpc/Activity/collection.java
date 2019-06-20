package com.example.best.buildpc.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.best.buildpc.DbHelper;
import com.example.best.buildpc.R;
import com.example.best.buildpc.Factory.build;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class collection extends AppCompatActivity {

    List<build> bd = new ArrayList<build>();
    DbHelper dbHelper;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        container = findViewById(R.id.container);
        dbHelper = new DbHelper(getApplicationContext());
        dbHelper.createDataBase();

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("killCol")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("killCol"));

        final EditText txtsearch = findViewById(R.id.txtSeacrh);
        Button btnSearch = findViewById(R.id.btnSearch);
        Boolean isSearching = getIntent().getBooleanExtra("src",false);
        String txt2 = getIntent().getStringExtra("txt2");
        txtsearch.setHint(txt2);

        NumberFormat myFormat = new DecimalFormat("#,###");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(collection.this, collection.class);
                if (txtsearch.getText().toString().trim().equals("")){
                    txtsearch.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(saved.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtsearch, InputMethodManager.SHOW_IMPLICIT);
                }else{
                    intent.putExtra("src",true);
                    intent.putExtra("txt2",""+txtsearch.getText());

                    startActivity(intent);
                    finish();
                }
            }
        });


        bd =  dbHelper.getAllBuild(true,isSearching,txt2);
        for(final build bld : bd) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addView= inflater.inflate(R.layout.row_coll,null);
            Button btn1 = addView.findViewById(R.id.btnSavedData);
            String txt = bld.getBuildName()+"\nRp "+myFormat.format(bld.getTotPrice());
            btn1.setText(txt);

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(collection.this, preview.class);
                    intent.putExtra("info","2");

                    intent.putExtra("keyPro", bld.getProID().toString());
                    intent.putExtra("keyMb", bld.getMbID().toString());
                    intent.putExtra("keyRam", bld.getRamID().toString());
                    intent.putExtra("keyVga", bld.getVgaID().toString());
                    intent.putExtra("keySto", bld.getStoID().toString());
                    intent.putExtra("keyPsu", bld.getPsuID().toString());
                    intent.putExtra("keyCs", bld.getCaseID().toString());
                    intent.putExtra("keyName", bld.getBuildName());
                    intent.putExtra("keyBID", bld.getBuildID().toString());
                    intent.putExtra("prc", bld.getTotPrice().toString());

                    startActivity(intent);
                }
            });
            container.addView(addView);
        }
    }
}
