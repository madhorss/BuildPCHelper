package com.example.best.buildpc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.best.buildpc.Factory.Case;
import com.example.best.buildpc.DbHelper;
import com.example.best.buildpc.Factory.Motherboard;
import com.example.best.buildpc.Factory.PSU;
import com.example.best.buildpc.Factory.Processor;
import com.example.best.buildpc.Factory.build;
import com.example.best.buildpc.R;
import com.example.best.buildpc.Factory.RAM;
import com.example.best.buildpc.Factory.Storage;
import com.example.best.buildpc.Factory.VGA;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class preview extends AppCompatActivity {
    Bundle extras;
    DbHelper dbHelper;

    Boolean isInserted;

    List<Processor> pro = new ArrayList<>();
    List<Motherboard> mb = new ArrayList<>();
    List<RAM> ram = new ArrayList<>();
    List<VGA> vga = new ArrayList<>();
    List<PSU> psu = new ArrayList<>();
    List<Case> cs = new ArrayList<>();
    List<Storage> sto = new ArrayList<>();
    List<build> bld = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        final Boolean cek = true;

        dbHelper = new DbHelper(getApplicationContext());

        TextView txtPro = findViewById(R.id.txtPro);
        TextView txtMb = findViewById(R.id.txtMb);
        TextView txtRAM = findViewById(R.id.txtRim);
        TextView txtVga = findViewById(R.id.txtVga);
        TextView txtSto = findViewById(R.id.txtSto);
        TextView txtPsu = findViewById(R.id.txtPsu);
        TextView txtCs = findViewById(R.id.txtCs);
        TextView txtName = findViewById(R.id.txtName);

        TextView txtTotal = findViewById(R.id.txtTotal);

        extras = getIntent().getExtras();

        final String name = extras.getString("keyName");
        txtName.setText("Build Name : "+name);
        final String id = extras.getString("keyBID");

        Date tDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final String strDate = formatter.format(tDate);

        final String Price =  extras.getString("prc");
        NumberFormat numberFormat = new DecimalFormat("#,###");
        txtTotal.setText("Total Price : RP "+ numberFormat.format(Integer.parseInt(Price)));


        final String strpro = extras.getString("keyPro");
        pro = dbHelper.getAllPro(cek,strpro,false,"",false,"",false,"","",false,false,"");
        for(final Processor pr : pro) {
            String str=pr.getProName();
            txtPro.setText("Processor : " +str );
        }

        final String strmb = extras.getString("keyMb");
        mb = dbHelper.getAllMB(cek,strmb,false,"",false,"","",false,"",false,"",false,"",false,"","");
        for(final Motherboard mbo : mb) {
            String str=mbo.getMbName();
            txtMb.setText("Motherboard : " + str);
        }

        final String strram = extras.getString("keyRam");
        ram = dbHelper.getAllRAM(cek, strram,false,"0","0",false,
                                "",false,"","",false,"","");
        for(final RAM rm : ram){
            String str=rm.getRamName();
            txtRAM.setText("RAM : " + str );
        }

        final String strvga = extras.getString("keyVga");
        vga = dbHelper.getAllVGA(cek,strvga,false,"",false,"");
        for(final VGA vg : vga) {
            String str=vg.getVgaName();
            txtVga.setText("VGA : " + str);
        }

        final String strsto = extras.getString("keySto");
        sto = dbHelper.getAllSto(cek,strsto,false,"",false,"",false,"","",false,"");
        for(final Storage st : sto) {
            String str=st.getStoName();
            txtSto.setText("Storage : " +str );
        }

        final String strpsu = extras.getString("keyPsu");
        psu = dbHelper.getAllPSU(cek,strpsu,"",false,"",false,"");
        for(final PSU ps : psu) {
            String str=ps.getPsuName();
            txtPsu.setText("PSU : " +str );
        }

        final String strcs = extras.getString("keyCs");
        cs = dbHelper.getAllCase(cek,strcs,false,"",false,"",false,"");
        for(final Case cse : cs) {
            String str=cse.getCaseName();
            txtCs.setText("Casing : " + str);
        }

        final String info = extras.getString("info");
        Button btnSave = findViewById(R.id.btnSaveBuild);
        if(info.equals("0")) {
            btnSave.setText("Save");
        }
        else if(info.equals("1")) {
            btnSave.setText("Edit");
        }
        else if(info.equals("2")) {
            btnSave.setText("Edit");
        }
        else if(info.equals("3")){
            btnSave.setText("Upgrade");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //if from choose part then save
                if(info.equals("0")) {
                    Intent intent = new Intent(preview.this, saved.class);

                    isInserted = dbHelper.insertBuild(id,name,strmb,strpro,strram,strvga,strsto,strpsu,strcs,strDate,Price);

                    if(isInserted) {
                        Toast.makeText(preview.this, "Build INSERTED", Toast.LENGTH_LONG).show();
                    }else{
                        isInserted = dbHelper.updateBuild(id,name,strmb,strpro,strram,strvga,strsto,strpsu,strcs,strDate,Price);
                        Toast.makeText(preview.this,"Build Updated",Toast.LENGTH_LONG).show();
                    }
                    Intent killScratch = new Intent("setDef");
                    sendBroadcast(killScratch);
                    startActivity(intent);
                    finish();
                }
                //if from saved or collection then edit
                else if(info.equals("1")) {
                    Intent intent = new Intent(preview.this, Scratch.class);

                    intent.putExtra("keyPro", strpro);
                    intent.putExtra("keyMb", strmb);
                    intent.putExtra("keyRam", strram);
                    intent.putExtra("keyVga",strvga);
                    intent.putExtra("keySto", strsto);
                    intent.putExtra("keyPsu",strpsu);
                    intent.putExtra("keyCs", strcs);
                    intent.putExtra("keyName",name);
                    intent.putExtra("keyBID", id);

                    intent.putExtra("isPro", true);
                    intent.putExtra("isMb", true);
                    intent.putExtra("isRam", true);
                    intent.putExtra("isVga",true);
                    intent.putExtra("isSto", true);
                    intent.putExtra("isPsu", true);
                    intent.putExtra("isCs",true);

                    Intent killSaved = new Intent("killSaved");
                    sendBroadcast(killSaved);

                    startActivity(intent);
                    finish();
                }
                else if(info.equals("2")) {
                    Intent intent = new Intent(preview.this, Scratch.class);

                    intent.putExtra("keyPro", strpro);
                    intent.putExtra("keyMb", strmb);
                    intent.putExtra("keyRam", strram);
                    intent.putExtra("keyVga",strvga);
                    intent.putExtra("keySto", strsto);
                    intent.putExtra("keyPsu",strpsu);
                    intent.putExtra("keyCs", strcs);
                    intent.putExtra("keyName","my"+name);
                    //intent.putExtra("keyBID", "1"+id);


                    intent.putExtra("isPro", true);
                    intent.putExtra("isMb", true);
                    intent.putExtra("isRam", true);
                    intent.putExtra("isVga",true);
                    intent.putExtra("isSto", true);
                    intent.putExtra("isPsu", true);
                    intent.putExtra("isCs",true);

                    Intent killCol = new Intent("killCol");
                    sendBroadcast(killCol);

                    startActivity(intent);
                    finish();
                }
                //if from upgrade the upgrade
                else{
                    Intent intent = new Intent(preview.this, upgradePart.class);

                    intent.putExtra("keyPro", strpro);
                    intent.putExtra("keyMb", strmb);
                    intent.putExtra("keyRam", strram);
                    intent.putExtra("keyVga",strvga);
                    intent.putExtra("keySto", strsto);
                    intent.putExtra("keyPsu",strpsu);
                    intent.putExtra("keyCs", strcs);
                    intent.putExtra("prc", Price);

                    intent.putExtra("keyName",""+name);
                    intent.putExtra("keyBID", ""+id);

                    startActivity(intent);
                   /* Intent killUp = new Intent("killUp");
                    sendBroadcast(killUp);*/
                    finish();
                }
            }
        });
    }
}
