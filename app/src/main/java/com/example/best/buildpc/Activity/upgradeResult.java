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
import android.widget.Toast;

import com.example.best.buildpc.DbHelper;
import com.example.best.buildpc.Factory.Case;
import com.example.best.buildpc.Factory.Motherboard;
import com.example.best.buildpc.Factory.PSU;
import com.example.best.buildpc.Factory.Processor;
import com.example.best.buildpc.Factory.RAM;
import com.example.best.buildpc.Factory.Storage;
import com.example.best.buildpc.Factory.VGA;
import com.example.best.buildpc.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class upgradeResult extends AppCompatActivity {
    DbHelper dbHelper;
    List<Processor> pro = new ArrayList<>();
    List<Motherboard> mb = new ArrayList<>();
    List<RAM> ram = new ArrayList<>();
    List<VGA> vga = new ArrayList<>();
    List<PSU> psu = new ArrayList<>();
    List<Case> cs = new ArrayList<>();
    List<Storage> sto = new ArrayList<>();
    List<Processor> proOld = new ArrayList<>();
    List<Motherboard> mbOld = new ArrayList<>();
    List<RAM> ramOld = new ArrayList<>();
    List<VGA> vgaOld = new ArrayList<>();
    List<PSU> psuOld = new ArrayList<>();
    List<Case> csOld = new ArrayList<>();
    List<Storage> stoOld = new ArrayList<>();
    Double[][] proWNDM, mbWNDM,ramWNDM,vgaWNDM;
    Integer numData, numCol;
    LinearLayout container;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_result);

        dbHelper = new DbHelper(getApplicationContext());
        dbHelper.createDataBase();

        container = findViewById(R.id.container);
        extras = getIntent().getExtras();
        NumberFormat myFormat = new DecimalFormat("#,###");

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

        final String strpro = getIntent().getStringExtra("keyPro");
        final String strmb = getIntent().getStringExtra("keyMb");
        final String strram = getIntent().getStringExtra("keyRam");
        final String strvga = getIntent().getStringExtra("keyVga");
        final String strsto = getIntent().getStringExtra("keySto");
        final String strpsu = getIntent().getStringExtra("keyPsu");
        final String strcs = getIntent().getStringExtra("keyCs");
        final String name = getIntent().getStringExtra("keyName");
        final String id = getIntent().getStringExtra("keyBID");
        final String Watt = getIntent().getStringExtra("watt");

        String price = extras.getString("prc");

        proOld =  dbHelper.getAllPro(true, strpro,false,"",false,""
                ,false,"","",false,false,"");
        Integer proPriceOld =   proOld.get(0).getProPrice();
        Integer proTDPOld =   proOld.get(0).getProTdp();

        mbOld = dbHelper.getAllMB(true, strmb, false,"",false,"", ""
                , false,"",false,"",false,"",false,"","");
        Integer mbPriceOld = mbOld.get(0).getMbPrice();
        String mbSizeOld = mbOld.get(0).getMbSize();
        Integer mbTDPOld = 0;

        if(mbSizeOld.equals("E-ATX")){
            mbTDPOld = 55;
        }else if (mbSizeOld.equals("ATX")){
            mbTDPOld = 40;
        }else if (mbSizeOld.equals("Micro-Atx")){
            mbTDPOld = 30;
        }else if (mbSizeOld.equals("Mini-ITX")){
            mbTDPOld = 15;
        }

        ramOld = dbHelper.getAllRAM(true,strram,false,"0","0",false,""
                ,false,"","",false,"","");
        Integer ramPriceOld = ramOld.get(0).getRamPrice();
        Integer ramTDP=5;

        vgaOld = dbHelper.getAllVGA(true,strvga,false,"",false,"");
        Integer vgaPriceOld = vgaOld.get(0).getVgaPrice();
        Integer vgaTDPOld = vgaOld.get(0).getVgaTdp();

        psuOld = dbHelper.getAllPSU(true,strpsu,"",false,"",false,"");
        Integer psuVolt = psuOld.get(0).getPsuVolt();

        stoOld = dbHelper.getAllSto(true,strsto,false,"",false,"",false,"","",false,"");
        Integer stoTDPOld =0;
        String stoType = stoOld.get(0).getStoName();
        if(stoType.contains("HDD")){
            stoTDPOld = 9;
        }else if (stoType.contains("SSHD")){
            stoTDPOld =6;
        }else if (stoType.contains("SSD")){
            stoTDPOld =3;
        }
        Integer freeTDP = psuVolt - ramTDP - mbTDPOld - proTDPOld - stoTDPOld;

        csOld = dbHelper.getAllCase(true,strcs,false,"",false,"",false,"");

        String part = getIntent().getStringExtra("part");
        String budget = getIntent().getStringExtra("budget");

        if(part.equals("0")) {
            mb = dbHelper.getAllMB(true,strmb,false,"0",false,"","",false,"",false,"",false,"",false,"","");
            String mbProSoc = mb.get(0).getMbProSocket();
            String core = proOld.get(0).getProCore().toString();
            String clockSpd = proOld.get(0).getProClockSpd().toString();
            String cache = proOld.get(0).getProCache().toString();

            freeTDP = freeTDP + proTDPOld;
            // core > , clkSpd >
            pro = dbHelper.gettAllCPU("Select * from Processor where proID not like '"+strpro+"' and proTDP <='"+freeTDP.toString()+"' and proSocket = '"+mbProSoc
                    +"' and proCore>'"+core+"' and proClockSpd >'"+clockSpd+"' and proPrice <="+budget);

            if (pro.size()==0){     //handle null
                //core>, clkSpd>=, cache>
                pro = dbHelper.gettAllCPU("Select * from Processor where proID not like '"+strpro+"' and proTDP <='"+freeTDP.toString()+"' and proSocket = '"+mbProSoc
                        +"' and proCore>'"+core+"' and proClockSpd >='"+clockSpd+"' and proCache>'"+cache+"' and proPrice <="+budget);
                if (pro.size()==0){
                    Intent intent = new Intent(upgradeResult.this, upgradePart.class);
                    Toast.makeText(upgradeResult.this,"Sorry we cant find better part. Try adding more budget.",Toast.LENGTH_LONG).show();
                    sendBack(intent);
                }
            }

            proWNDM = getPro(pro);
            Integer[]rank=getRank(proWNDM,pro.size(),9);
            Integer numShow;
            if(pro.size()>=3){
                numShow = 3;
            }else {
                numShow = pro.size();
            }
            for (Integer x = 0; x<numShow;x++){
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView= inflater.inflate(R.layout.rowset,null);
                Button btn1 = addView.findViewById(R.id.btnData);
                Button btn = addView.findViewById(R.id.btnDa);

                Integer prc = Integer.parseInt(price) - proPriceOld;

                String txt = pro.get(rank[x]).getProName()+"\nRp "+ myFormat.format(pro.get(rank[x]).getProPrice());
                btn1.setText(txt);
                final String strproNew = pro.get(rank[x]).getProID().toString();
                final Integer proPriceNew = prc + pro.get(rank[x]).getProPrice();
                btn1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        send(strproNew,strmb,strram,strvga,strsto,strpsu,strcs,name,id, proPriceNew.toString());
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                       sendDet(strproNew,strmb,strram,strvga,strsto,strpsu,strcs,name,id, proPriceNew.toString(),"0");
                    }
                });
                container.addView(addView);
            }
        }
        else if(part.equals("1")) {
            //filter Motherboard
            pro =  dbHelper.getAllPro(true, strpro,false,"",false,"",false,"","",false,false,"");
            String proSocket = pro.get(0).getProSocket();

            String mbMaxSpd = mbOld.get(0).getMbRamMaxSpd().toString();
            String mbMaxCap = mbOld.get(0).getMbRamMaxCap().toString();
            sto = dbHelper.getAllSto(true,strsto,false,"",false,"",false,"","",false,"");
            String stoSlot = sto.get(0).getStoSize();

            freeTDP = freeTDP + mbTDPOld;

            // case size
            String csSize = csOld.get(0).getCaseSize();
            String csMB ="";
            if(csSize.equals("E-ATX")){
                csMB= "'E-ATX','ATX','Micro-ATX','Mini-ITX'";
            }
            else if(csSize.equals("ATX")){
                csMB= "'ATX','Micro-ATX','Mini-ITX'";
            }
            else if(csSize.equals("Micro-ATX")){
                csMB= "'Micro-ATX','Mini-ITX'";
            }

            mb = dbHelper.getAllMB(false, strmb, false, proSocket, false
                    , csMB, mbMaxSpd, false, stoSlot, false, freeTDP.toString()
                    , false, budget, true,mbMaxCap,"");

            if (mb.size()==0){      //handle null
                mb = dbHelper.getAllMB(false, strmb, false, proSocket, false
                        , csMB, mbMaxSpd, false, stoSlot, false, freeTDP.toString()
                        , false, budget, true,mbMaxCap,"1");
                if (mb.size()==0){
                    Intent intent = new Intent(upgradeResult.this, upgradePart.class);
                    Toast.makeText(upgradeResult.this,"Sorry we cant find better part. Try adding more budget.",Toast.LENGTH_LONG).show();
                    sendBack(intent);
                }
            }

            mbWNDM = getMb(mb);
            Integer[] rank=getRank(mbWNDM,mb.size(),5);
            Integer numShow=0;
            if(mb.size()>=3){
                numShow = 3;
            }else {
                numShow = mb.size();
            }

            Integer data=0;
            for (Integer x = 0; x<numShow;x++){
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView= inflater.inflate(R.layout.rowset,null);
                Button btn1 = addView.findViewById(R.id.btnData);
                Button btn = addView.findViewById(R.id.btnDa);

                Integer prc = Integer.parseInt(price) - mbPriceOld;
                if(mb.get(rank[0]).getMbID()==null){
                    break;
                }
                String txt = mb.get(rank[data]).getMbName()+"\nRp "+ myFormat.format(mb.get(rank[data]).getMbPrice());
                btn1.setText(txt);
                final String strMbNew = mb.get(rank[data]).getMbID().toString();
                final Integer mbPriceNew = prc + mb.get(rank[data]).getMbPrice();
                btn1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        send(strpro,strMbNew,strram,strvga,strsto,strpsu,strcs,name,id, mbPriceNew.toString());
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        sendDet(strpro,strMbNew,strram,strvga,strsto,strpsu,strcs,name,id, mbPriceNew.toString(),"1");
                    }
                });

                String mbSizeNew =  mb.get(rank[data]).getMbSize();
                Integer mbTDPNew =  0;

                if(mbSizeNew.equals("E-ATX")){
                    mbTDPNew = 55;
                }else if (mbSizeNew.equals("ATX")){
                    mbTDPNew = 40;
                }else if (mbSizeNew.equals("Micro-Atx")){
                    mbTDPNew = 30;
                }else if (mbSizeNew.equals("Mini-ITX")){
                    mbTDPNew = 15;
                }

                if(freeTDP<=mbTDPNew){
                    data++;
                }else {
                    container.addView(addView);
                    data++;
                }
            }
        }
        else if(part.equals("2")) {
            mb = dbHelper.getAllMB(true,strmb,false,"0",false,"","",false,"",false,"",false,"",false,"","");
            String mbRamVer = mb.get(0).getMbRamVer();
            String mbRamSpd = mb.get(0).getMbRamMaxSpd().toString();
            String mbMaxCap = mb.get(0).getMbRamMaxCap().toString();

            String ramIdOld = ramOld.get(0).getRamID().toString();
            String ramCapOld= ramOld.get(0).getRamCap().toString();
            String ramSpdOld= ramOld.get(0).getRamSpd().toString();

            ram = dbHelper.getAllRAM(false, ramIdOld, false, mbRamVer, mbRamSpd
                    , false, "1", false, ramSpdOld, ramCapOld, true,budget, mbMaxCap.toString());

            //handle null
            if (ram.size()==0){
                ram = dbHelper.getAllRAM(false, ramIdOld, false, mbRamVer, mbRamSpd
                        , false, "2", false, ramSpdOld, ramCapOld, true,budget, mbMaxCap.toString());
                if (ram.size()==0){
                    Intent intent = new Intent(upgradeResult.this, upgradePart.class);
                    Toast.makeText(upgradeResult.this,"Sorry we cant find better part. Try adding more budget.",Toast.LENGTH_LONG).show();
                    sendBack(intent);
                }
            }

            ramWNDM = getRam(ram);
            Integer[] rank = getRank(ramWNDM, ram.size(), 3);
            Integer numShow;
            if(ram.size()>=3){
                numShow = 3;
            }else {
                numShow = ram.size();
            }
            for (Integer x = 0; x<numShow;x++){
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView= inflater.inflate(R.layout.rowset,null);
                Button btn1 = addView.findViewById(R.id.btnData);
                Button btn = addView.findViewById(R.id.btnDa);

                Integer prc = Integer.parseInt(price) - ramPriceOld;

                String txt = ram.get(rank[x]).getRamName()+"\nRp "+ myFormat.format(ram.get(rank[x]).getRamPrice());
                btn1.setText(txt);
                final String strRamNew = ram.get(rank[x]).getRamID().toString();
                final Integer ramPriceNew = prc + ram.get(rank[x]).getRamPrice();
                btn1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        send(strpro,strmb,strRamNew,strvga,strsto,strpsu,strcs,name,id, ramPriceNew.toString());
                    }
                });
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        sendDet(strpro,strmb,strRamNew,strvga,strsto,strpsu,strcs,name,id, ramPriceNew.toString(),"2");
                    }
                });
                container.addView(addView);
            }
        }
        else if(part.equals("3")) {
            //filter VGA
            String vgaCoreOld = vgaOld.get(0).getVgaCoreUnit().toString();
            String vgaCoreSpdOld = vgaOld.get(0).getVgaCoreSpd().toString();
            String vgaMemOld = vgaOld.get(0).getVgaMemCap().toString();
            String vgaMemSpdOld = vgaOld.get(0).getVgaMemSpd().toString();
            freeTDP = freeTDP + vgaTDPOld;
            vga = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+strvga+"' and vgaTdp <="+freeTDP+" and vgaCoreSpd >'"+vgaCoreSpdOld+"' " +
                    "and vgaCoreUnit >'"+vgaCoreOld+"' and vgaMemCap >'"+vgaMemOld+"' and vgaMemSpd >'"+vgaMemSpdOld+"' and vgaPrice <="+budget);

            if (vga.size()==0){     //handle null
                Intent intent = new Intent(upgradeResult.this, upgradePart.class);
                Toast.makeText(upgradeResult.this,"Sorry we cant find better part. Try adding more budget.",Toast.LENGTH_LONG).show();
                sendBack(intent);
            }

            vgaWNDM = getVga(vga);
            Integer[] rank=getRank(vgaWNDM,vga.size(),8);
            Integer numShow;
            if(vga.size()>=3){
                numShow = 3;
            }else {
                numShow = vga.size();
            }
            for (Integer x = 0; x<numShow;x++){
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView= inflater.inflate(R.layout.rowset,null);
                Button btn1 = addView.findViewById(R.id.btnData);
                Button btn = addView.findViewById(R.id.btnDa);

                Integer prc = Integer.parseInt(price) - vgaPriceOld;

                String txt = vga.get(rank[x]).getVgaName()+"\nRp "+ myFormat.format(vga.get(rank[x]).getVgaPrice());
                btn1.setText(txt);
                final String strVgaNew = vga.get(rank[x]).getVgaID().toString();
                final Integer vgaPriceNew = prc + vga.get(rank[x]).getVgaPrice();
                btn1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        send(strpro,strmb,strram,strVgaNew,strsto,strpsu,strcs,name,id, vgaPriceNew.toString());
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        sendDet(strpro,strmb,strram,strVgaNew,strsto,strpsu,strcs,name,id, vgaPriceNew.toString(),"3");
                    }
                });
                container.addView(addView);
            }
        }
    }

    ///========//============
    public void send(String strpro, String strmb, String strram, String strvga
            , String strsto, String strpsu, String strcs, String name, String id, String price){
        Intent intent = new Intent(upgradeResult.this, preview.class);

        intent.putExtra("info", "0");
        intent.putExtra("keyPro", strpro);
        intent.putExtra("keyMb", strmb);
        intent.putExtra("keyRam", strram);
        intent.putExtra("keyVga",strvga);
        intent.putExtra("keySto", strsto);
        intent.putExtra("keyPsu",strpsu);
        intent.putExtra("keyCs", strcs);

        intent.putExtra("keyName",""+name);
        intent.putExtra("keyBID", ""+id);
        intent.putExtra("prc", ""+price);

        startActivity(intent);

    }

    public void sendDet(String strpro, String strmb, String strram, String strvga
            , String strsto, String strpsu, String strcs, String name, String id, String price,String info){

        Intent intent = new Intent(upgradeResult.this, detail.class);
        intent.putExtra("detail","0");
        intent.putExtra("info",info);
        if (info.equals("0")){
            intent.putExtra("id",""+strpro);
        }else if(info.equals("1")){
            intent.putExtra("id",""+strmb);
        }else if(info.equals("2")){
            intent.putExtra("id",""+strram);
        }else if(info.equals("3")){
            intent.putExtra("id",""+strvga);
        }

        intent.putExtra("keyPro", strpro);
        intent.putExtra("keyMb", strmb);
        intent.putExtra("keyRam", strram);
        intent.putExtra("keyVga",strvga);
        intent.putExtra("keySto", strsto);
        intent.putExtra("keyPsu",strpsu);
        intent.putExtra("keyCs", strcs);

        intent.putExtra("keyName",""+name);
        intent.putExtra("keyBID", ""+id);
        intent.putExtra("prc", ""+price);

        startActivity(intent);

    }

    public void sendBack(Intent intent){
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

        Intent killUpPart = new Intent("setDef");
        sendBroadcast(killUpPart);
        startActivity(intent);

    }

    ///========//============
    public Double[][] getPro(List<Processor> pro){
        Double proClockSpd = 0.0;
        Double proClockMaxSpd = 0.0;
        Double proCache = 0.0;
        Double proCore = 0.0;
        Double proThread = 0.0;
        Double proRamChan = 0.0;
        Double proRamMaxSpd = 0.0;
        Double proTdp = 0.0;
        Double proPrice = 0.0;

        Integer size = pro.size();
        numData=pro.size();

        for (Integer counter = 0; counter < pro.size(); counter++) {
            proClockSpd = proClockSpd + Math.pow(pro.get(counter).getProClockSpd(),2);
            proClockMaxSpd = proClockMaxSpd + Math.pow(pro.get(counter).getProClockMaxSpd(),2);
            proCache = proCache + Math.pow(pro.get(counter).getProCache(),2);
            proCore = proCore + Math.pow(pro.get(counter).getProCore(),2);
            proThread = proThread + Math.pow(pro.get(counter).getProThread(),2);
            proRamChan = proRamChan + Math.pow(pro.get(counter).getProRamChan(),2);
            proRamMaxSpd = proRamMaxSpd + Math.pow(pro.get(counter).getProRamMaxSpd(),2); //
            proTdp = proTdp + Math.pow(pro.get(counter).getProTdp(),2);
            proPrice = proPrice + Math.pow(pro.get(counter).getProPrice(),2);
        }

        proClockSpd = Math.sqrt(proClockSpd);
        proClockMaxSpd = Math.sqrt(proClockMaxSpd);
        proCache = Math.sqrt(proCache);
        proCore = Math.sqrt(proCore);
        proThread = Math.sqrt(proThread);
        proRamChan = Math.sqrt(proRamChan);
        proRamMaxSpd = Math.sqrt(proRamMaxSpd);
        proTdp = Math.sqrt(proTdp);
        proPrice = Math.sqrt(proPrice);

        Double proWeight = 0.98 / 8.0;

        //calculate proWNDM
        proWNDM = new Double[pro.size()][9];
        for (Integer counter = 0; counter < pro.size(); counter++) {
            proWNDM [counter][0] = pro.get(counter).getProClockSpd() / proClockSpd * proWeight;
            proWNDM [counter][1] = pro.get(counter).getProClockMaxSpd() / proClockMaxSpd * proWeight;
            proWNDM [counter][2] = pro.get(counter).getProCache() / proCache * proWeight;
            proWNDM [counter][3] = pro.get(counter).getProCore() / proCore * proWeight;
            proWNDM [counter][4] = pro.get(counter).getProThread() / proThread * proWeight;
            proWNDM [counter][5] = pro.get(counter).getProRamChan() / proRamChan * proWeight;
            proWNDM [counter][6] = pro.get(counter).getProRamMaxSpd() / proRamMaxSpd * proWeight;
            proWNDM [counter][7] = pro.get(counter).getProTdp() / proTdp * proWeight;
            proWNDM [counter][8] = pro.get(counter).getProPrice() / proPrice * 0.02;
        }

        return proWNDM;
    }

    public Double[][] getMb(List<Motherboard> mb){
        Double mbRamSlot = 0.0;
        Double mbRamMaxCap = 0.0;
        Double mbRamMaxSpd = 0.0;
        Double mbSize = 0.0;
        Double mbPrice = 0.0;
        Integer sizeInNum = 0;

        for (Integer counter = 0; counter < mb.size(); counter++) {
            mbRamSlot =  mbRamSlot + Math.pow(mb.get(counter).getMbRamSlot(),2);
            mbRamMaxCap =  mbRamMaxCap + Math.pow(mb.get(counter).getMbRamSlot(),2);
            mbRamMaxSpd =  mbRamMaxSpd + Math.pow(mb.get(counter).getMbRamSlot(),2);
            if (mb.get(counter).getMbSize().equals("Mini-ITX")){
                sizeInNum = 1;
            } else if (mb.get(counter).getMbSize().equals("Micro-ATX")){
                sizeInNum = 2;
            } else if (mb.get(counter).getMbSize().equals("ATX")){
                sizeInNum = 3;
            } else if (mb.get(counter).getMbSize().equals("E-ATX")){
                sizeInNum = 4;
            }
            mbSize =  mbSize + Math.pow(sizeInNum,2);
            mbPrice =  mbPrice + Math.pow(mb.get(counter).getMbRamSlot(),2);
        }

        mbRamSlot = Math.sqrt(mbRamSlot);
        mbRamMaxCap = Math.sqrt(mbRamMaxCap);
        mbRamMaxSpd = Math.sqrt(mbRamMaxSpd);
        mbSize = Math.sqrt(mbSize);
        mbPrice = Math.sqrt(mbPrice);

        Double mbWeight = 0.98 / 4.0;

        //calculate mbWNDM
        mbWNDM = new Double[mb.size()][5];
        for (Integer counter = 0; counter < mb.size(); counter++) {
            mbWNDM [counter][0] = mb.get(counter).getMbRamSlot() / mbRamSlot * 0.15;
            mbWNDM [counter][1] = mb.get(counter).getMbRamMaxCap() / mbRamMaxCap * 0.30;
            mbWNDM [counter][2] = mb.get(counter).getMbRamMaxSpd() / mbRamMaxSpd * 0.40;
            if (mb.get(counter).getMbSize().equals("Mini-ITX")){
                sizeInNum = 1;
            } else if (mb.get(counter).getMbSize().equals("Micro-ATX")){
                sizeInNum = 2;
            } else if (mb.get(counter).getMbSize().equals("ATX")){
                sizeInNum = 3;
            } else if (mb.get(counter).getMbSize().equals("E-ATX")){
                sizeInNum = 4;
            }
            mbWNDM [counter][3] = sizeInNum / mbSize * 0.13;
            mbWNDM [counter][4] = mb.get(counter).getMbPrice() / mbPrice * 0.02;
        }

        return mbWNDM;
    }

    public Double[][] getRam(List<RAM> ram){
        Double ramCap = 0.0;
        Double ramSpd = 0.0;
        Double ramPrice = 0.0;

        for (Integer counter = 0; counter < ram.size(); counter++) {
            String rcap  = "(2)";
            String rcp = ram.get(counter).getRamCap().toString();
            String rmcap;
            if (rcp.toLowerCase().contains(rcap)){
                rmcap = rcp.substring(0,rcp.length()-3);
            }else{
                rmcap = rcp;
            }
            ramCap =  ramCap + Math.pow(Integer.parseInt(rmcap),2);
            ramSpd =  ramSpd + Math.pow(ram.get(counter).getRamSpd(),2);
            ramPrice =  ramPrice + Math.pow(ram.get(counter).getRamPrice(),2);

        }

        ramCap = Math.sqrt(ramCap);
        ramSpd = Math.sqrt(ramSpd);
        ramPrice = Math.sqrt(ramPrice);

        Double ramWeight = 0.80 / 2.0;

        //calculate ramWNDM
        ramWNDM = new Double[ram.size()][3];
        for (Integer counter = 0; counter < ram.size(); counter++) {
            String rcap  = "(2)";
            String rcp = ram.get(counter).getRamCap().toString();
            String rmcap;
            if (rcp.toLowerCase().contains(rcap)){
                rmcap = rcp.substring(0,rcp.length()-3);
            }else{
                rmcap = rcp;
            }
            ramWNDM [counter][0] = Integer.parseInt(rmcap) / ramCap * ramWeight;
            ramWNDM [counter][1] = ram.get(counter).getRamSpd() / ramSpd * ramWeight;
            ramWNDM [counter][2] = ram.get(counter).getRamPrice() / ramPrice * 0.20;

        }
        return ramWNDM;
    }

    public Double[][] getVga(List<VGA> vga){
        Double vgaCoreUnit = 0.0;
        Double vgaCoreSpd = 0.0;
        Double vgaCoreMaxSpd = 0.0;
        Double vgaSpd = 0.0;
        Double vgaCap = 0.0;
        Double vgaType = 0.0;
        Double vgaTdp = 0.0;
        Double vgaPrice = 0.0;

        numData = vga.size();
        numCol= 8;
        Integer typeInNum = 0;

        for (Integer counter = 0; counter < numData; counter++) {
            vgaCoreUnit = vgaCoreUnit + Math.pow(vga.get(counter).getVgaCoreUnit(),2);
            vgaCoreSpd = vgaCoreSpd + Math.pow(vga.get(counter).getVgaCoreSpd(),2);
            vgaCoreMaxSpd = vgaCoreMaxSpd + Math.pow(vga.get(counter).getVgaCoreMaxSpd(),2);
            vgaSpd = vgaSpd + Math.pow(vga.get(counter).getVgaMemSpd(),2);
            vgaCap = vgaCap + Math.pow(vga.get(counter).getVgaMemCap(),2);

            if (vga.get(counter).getVgaMemType().equals("DDR3")){
                typeInNum = 1;
            } else if (vga.get(counter).getVgaMemType().equals("DDR4")){
                typeInNum = 2;
            } else if (vga.get(counter).getVgaMemType().equals("DDR5")){
                typeInNum = 3;
            } else if (vga.get(counter).getVgaMemType().equals("DDR5X") || vga.get(counter).getVgaMemType().equals("HBM2")){
                typeInNum = 4;
            } else if (vga.get(counter).getVgaMemType().equals("DDR6")){
                typeInNum = 5;
            }

            vgaType = vgaType + Math.pow(typeInNum,2);
            vgaTdp = vgaTdp + Math.pow(vga.get(counter).getVgaTdp(),2);
            vgaPrice = vgaPrice + Math.pow(vga.get(counter).getVgaPrice(),2);
        }

        vgaCoreUnit = Math.sqrt(vgaCoreUnit);
        vgaCoreSpd = Math.sqrt(vgaCoreSpd);
        vgaCoreMaxSpd = Math.sqrt(vgaCoreMaxSpd);
        vgaSpd = Math.sqrt(vgaSpd);
        vgaCap = Math.sqrt(vgaCap);
        vgaType = Math.sqrt(vgaType);
        vgaTdp = Math.sqrt(vgaTdp);
        vgaPrice = Math.sqrt(vgaPrice);

        Double vgaWeight = 0.98 / 7.0;

        //calculate vgaWNDM
        vgaWNDM = new Double[numData][numCol];
        for (Integer counter = 0; counter <numData; counter++) {

            vgaWNDM [counter][0] = vga.get(counter).getVgaCoreUnit() / vgaCoreUnit * vgaWeight;
            vgaWNDM [counter][1] = vga.get(counter).getVgaCoreSpd() / vgaCoreSpd * vgaWeight;
            vgaWNDM [counter][2] = vga.get(counter).getVgaCoreMaxSpd() / vgaCoreMaxSpd * vgaWeight;
            vgaWNDM [counter][3] = vga.get(counter).getVgaMemSpd() / vgaSpd * vgaWeight;
            vgaWNDM [counter][4] = vga.get(counter).getVgaMemCap() / vgaCap * vgaWeight;
            if (vga.get(counter).getVgaMemType().equals("DDR3")){
                typeInNum = 1;
            } else if (vga.get(counter).getVgaMemType().equals("DDR4")){
                typeInNum = 2;
            } else if (vga.get(counter).getVgaMemType().equals("DDR5") || vga.get(counter).getVgaMemType().equals("HBM2")){
                typeInNum = 3;
            } else if (vga.get(counter).getVgaMemType().equals("DDR6")){
                typeInNum = 4;
            }
            vgaWNDM [counter][5] = typeInNum / vgaType * vgaWeight;
            vgaWNDM [counter][6] = vga.get(counter).getVgaTdp() / vgaTdp * vgaWeight;
            vgaWNDM [counter][7] = vga.get(counter).getVgaPrice() / vgaPrice * 0.02;
        }

        return  vgaWNDM;
    }
    ///========//============
    public Integer[] getRank(Double arr2[][],Integer numData, Integer numCol){

        Double[][] arr;
        arr = new Double [numData][numCol];
        arr=arr2;

        Double[] vPlus,vMinus;
        vPlus = new Double[numCol];
        vMinus = new Double[numCol];
        Integer upward;
        if (numCol==8 || numCol==9){
            upward=2;
        }else {
            upward=1;
        }

        //set default value
        for(Integer changer=0; changer < numCol-upward; changer++){
            vPlus [changer]= 0.0;
            vMinus [changer]= 1.0;
        }
        for(Integer changer=numCol-upward; changer < numCol; changer++){
            vPlus [changer]= 1.0;
            vMinus [changer]= 0.0;
        }

        // search for V+ and V- from the WNDM
        for (Integer counter = 0; counter < numData; counter++) {
            for(Integer changer=0;changer <  numCol-upward;changer++){
                if (vPlus[changer] < arr [counter][changer]){
                    vPlus[changer] = arr [counter][changer];
                }
                if (vMinus[changer] > arr [counter][changer]){
                    vMinus[changer] = arr [counter][changer];
                }
            }
            for(Integer changer= numCol-upward;changer < numCol;changer++){
                if (vPlus[changer] > arr [counter][changer]){
                    vPlus[changer] = arr [counter][changer];
                }
                if (vMinus[changer] < arr [counter][changer]){
                    vMinus[changer] = arr [counter][changer];
                }
            }
        }

        //Calculate Euclidean Distance for s+ and s-
        Double[] sumS, perfScore, temp;
        Double[] sPlus,sMinus;
        sPlus = new Double[numData];
        sMinus = new Double[numData];
        sumS = new Double[numData];
        perfScore = new Double[numData];
        temp = new Double[numData];
        Integer [] rank;rank=new Integer[numData];

        //set default value
        for (Integer counter = 0; counter < numData; counter++) {
            sPlus [counter] = 0.0;
            sMinus [counter] = 0.0;
            sumS [counter] = 0.0;
            perfScore [counter] = 0.0;
            temp [counter] = 0.0;
            rank [counter] = 0;
        }

        //calculate euclidean distance + and euclidean distance -
        for (Integer counter = 0; counter < numData; counter++) {
            for(Integer changer=0;changer < numCol;changer++){
                sPlus [counter] = sPlus [counter] + Math.pow((arr [counter][changer] - vPlus[changer]),2) ;
                sMinus [counter] =  sMinus [counter] + Math.pow((arr [counter][changer] - vMinus[changer]),2);
            }
        }

        for (Integer counter = 0; counter < numData; counter++) {
            sPlus [counter] = Math.sqrt(sPlus[counter]);
            sMinus [counter] = Math.sqrt(sMinus[counter]);
            sumS [counter] = sPlus [counter] + sMinus [counter];
            perfScore [counter] =  sMinus [counter] / sumS [counter];
        }

        for (Integer counter = 0; counter < numData; counter++) {
            temp[counter]=perfScore[counter];
        }
        //Sort Array
        Arrays.sort(temp, Collections.reverseOrder());
        //get Rank
        for(Integer changer=0;changer < numData;changer++){
            rank[changer] = findIndex(perfScore,temp[changer]) ;
        }
        return rank;
    }

    public static Integer findIndex(Double arr[], Double t) {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        Integer len = arr.length;
        Integer i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }
    ///========//============

}
