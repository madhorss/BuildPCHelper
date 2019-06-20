package com.example.best.buildpc.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.best.buildpc.DbHelper;
import com.example.best.buildpc.R;
import com.example.best.buildpc.Factory.build;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class upgrade extends AppCompatActivity {
    List<build> bd = new ArrayList<build>();

    DbHelper dbHelper;
    LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("setDef")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("setDef"));

        NumberFormat myFormat = new DecimalFormat("#,###");

        container = findViewById(R.id.container);
        dbHelper = new DbHelper(getApplicationContext());
        dbHelper.createDataBase();

        Button BtnScr = findViewById(R.id.BtnScr);
        BtnScr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent killScratch = new Intent("setDef");
                sendBroadcast(killScratch);
                Intent intent = new Intent(upgrade.this, Scratch.class);

                startActivity(intent);
            }
        });

        bd =  dbHelper.getAllBuild(false,false,"");
        for(final build bld : bd) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addView= inflater.inflate(R.layout.row_coll,null);
            Button btn1 = addView.findViewById(R.id.btnSavedData);

            String txt = bld.getBuildName()+"\nRp "+myFormat.format(bld.getTotPrice());
            btn1.setText(txt);

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(upgrade.this, preview.class);
                    intent.putExtra("info","3");
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
