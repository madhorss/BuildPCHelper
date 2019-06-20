package com.example.best.buildpc.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.best.buildpc.DbHelper;
import com.example.best.buildpc.R;
import com.example.best.buildpc.Factory.build;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class saved extends AppCompatActivity {
    List<build> bd = new ArrayList<build>();
    DbHelper dbHelper;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        container = findViewById(R.id.container);
        dbHelper = new DbHelper(getApplicationContext());
        dbHelper.createDataBase();

        NumberFormat myFormat = new DecimalFormat("#,###");

        final EditText txtsearch = findViewById(R.id.txtSeacrh);
        Button btnSearch = findViewById(R.id.btnSearch);
        Boolean isSearching = getIntent().getBooleanExtra("src",false);
        String txt2 = getIntent().getStringExtra("txt2");

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("killSaved")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("killSaved"));

        bd =  dbHelper.getAllBuild(false,isSearching,txt2);
        for(final build bld : bd) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addView= inflater.inflate(R.layout.row_saved,null);
            Button btn1 = addView.findViewById(R.id.btnSavedData);
            String txt = bld.getBuildName()+"\n"+bld.getDate()+"\nRp "+myFormat.format(bld.getTotPrice());
            btn1.setText(txt);
            Button btnDel = addView.findViewById(R.id.btnDel);

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(saved.this, preview.class);
                    intent.putExtra("info","1");

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

            txtsearch.setHint(txt2);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(saved.this, saved.class);
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


            btnDel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(saved.this);
                    builder.setCancelable(true);
                    builder.setTitle("Caution !");
                    builder.setMessage("Are you sure DELETING this build ?");

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(saved.this, saved.class);

                            Boolean isDeleted = dbHelper.deleteBuild(bld.getBuildID().toString());
                            if(isDeleted)
                                Toast.makeText(saved.this,"DATA DELETED",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(saved.this,"DATA -not- DELETED",Toast.LENGTH_SHORT).show();

                            startActivity(intent);
                            finish();
                        }
                    });
                builder.show();
                }
            });
            container.addView(addView);
        }
    }
}
