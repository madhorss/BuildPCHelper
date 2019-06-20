package com.example.best.buildpc.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.best.buildpc.NumTextWatcher;
import com.example.best.buildpc.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class upgradePart extends AppCompatActivity {

    RadioButton rdPro,rdMb,rdRam,rdVga;
    RadioGroup rgPart;
    EditText edBudget;
    Button btnUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_part);

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

        rdPro = findViewById(R.id.rdPro);
        rdMb = findViewById(R.id.rdMb);
        rdRam = findViewById(R.id.rdRam);
        rdVga = findViewById(R.id.rdVga);
        rgPart = findViewById(R.id.rgPart);

        edBudget = findViewById(R.id.edBudget);
        edBudget.addTextChangedListener(new NumTextWatcher(edBudget));

        btnUpgrade = findViewById(R.id.btnUpgrade);

        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String budget = edBudget.getText().toString().replace(",",""); //edBudget.getText().toString();
                if (rgPart.getCheckedRadioButtonId() == -1 || budget.equals("")){
                    if (rgPart.getCheckedRadioButtonId() == -1){
                        Toast.makeText(upgradePart.this,"Please select part to upgrade",Toast.LENGTH_LONG).show();
                    }
                    else if (budget.equals("")){
                        Toast.makeText(upgradePart.this,"Please fill your Budget",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent intent = new Intent(upgradePart.this, upgradeResult.class);
                    if(rdPro.isChecked()){
                        intent.putExtra("part", "0");
                    }
                    else if(rdMb.isChecked()){
                        intent.putExtra("part", "1");
                    }
                    else if(rdRam.isChecked()){
                        intent.putExtra("part", "2");
                    }
                    else if(rdVga.isChecked()){
                        intent.putExtra("part", "3");
                    }
                    intent.putExtra("budget",""+budget);
                    send(intent);
                }
            }
        });
    }

    public void send(Intent intent){
        String strpro = getIntent().getStringExtra("keyPro");
        String strmb = getIntent().getStringExtra("keyMb");
        String strram = getIntent().getStringExtra("keyRam");
        String strvga = getIntent().getStringExtra("keyVga");
        String strsto = getIntent().getStringExtra("keySto");
        String strpsu = getIntent().getStringExtra("keyPsu");
        String strcs = getIntent().getStringExtra("keyCs");
        String name = getIntent().getStringExtra("keyName");
        String id = getIntent().getStringExtra("keyBID");
        String price = getIntent().getStringExtra("prc");

        intent.putExtra("keyPro", strpro);
        intent.putExtra("keyMb", strmb);
        intent.putExtra("keyRam", strram);
        intent.putExtra("keyVga",strvga);
        intent.putExtra("keySto", strsto);
        intent.putExtra("keyPsu",strpsu);
        intent.putExtra("keyCs", strcs);

        intent.putExtra("prc", price);
        intent.putExtra("keyName",""+name);
        intent.putExtra("keyBID", ""+id);
        startActivity(intent);
    }
}


