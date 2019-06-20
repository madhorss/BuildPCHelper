/// from Scratch

package com.example.best.buildpc.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.best.buildpc.Factory.Case;
import com.example.best.buildpc.DbHelper;
import com.example.best.buildpc.Factory.Motherboard;
import com.example.best.buildpc.Factory.PSU;
import com.example.best.buildpc.Factory.Processor;
import com.example.best.buildpc.R;
import com.example.best.buildpc.Factory.RAM;
import com.example.best.buildpc.Factory.Storage;
import com.example.best.buildpc.Factory.VGA;
import com.example.best.buildpc.Factory.build;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class Scratch extends AppCompatActivity {
    Bundle extras;
    GridLayout mainGrid;
    EditText txtName;
    TextView txtPro,txtMb,txtRam,txtVga,txtSto, txtPsu, txtCs, txtPrice,txtWatt;
    Boolean isProPicked, isMbPicked, isRamPicked, isVgaPicked, isStoPicked, isPsuPicked, isCasePicked;
    String info;
    String valueName,checkName,valueID;
    String valuePro, valueProID, valueProWt,valueProPr,valueProSoc, valueProRamMaxSpd;
    String valueMb, valueMbID, valueMbPr, valueMbRamVer, valueMbRamSpd, valueMbProSoc, valueMbWt, valueMbSize, valueMbSlot, valueMbRamMaxCap;
    String valueRam,valueRamID,valueRamWt,valueRamPr, valueRamVer, valueRamSpd, valueRamCap;
    String valueVga, valueVgaID,valueVgaWt,valueVgaPr, valueVgaSusPow, valueVgaSlot;
    String valueStoID,valueSto,valueStoWt,valueStoPr, valueStoSlot;
    String valuePsuID, valuePsu, valuePsuPr, valuePsuSize, valuePsuWt;
    String valueCs, valueCsID, valueCsPr, valueCsSize;
    String name;

    Integer Price,Watt;

    List<build> bld = new ArrayList<>();
    List<Processor> pro = new ArrayList<>();
    List<Motherboard> mb = new ArrayList<>();
    List<RAM> ram = new ArrayList<>();
    List<VGA> vga = new ArrayList<>();
    List<PSU> psu = new ArrayList<>();
    List<Case> cs = new ArrayList<>();
    List<Storage> sto = new ArrayList<>();
    DbHelper dbHelper;

    ImageButton imgPro, imgMb, imgRam, imgVga, imgSto, imgPsu, imgCs;

    int visPro, visMb, visRam, visVga, visSto, visPsu, visCs;

    private static final String VISPRO = "visp";
    private static final String VISMB = "vism";
    private static final String VISRAM = "visram";
    private static final String VISVGA = "visVGA";
    private static final String VISSTO = "visST";
    private static final String VISPSU = "visPSU";
    private static final String VISCS = "visC";

    private static final String TEXT_NAME ="Build 1";
    private static final String TEXT_ID ="txtidbld";


    private static final String SHARED_PREFS ="sharedPrefs";

    private static final String TEXT_PRO = "textp";
    private static final String TEXT_PROWT = "textpWT";
    private static final String TEXT_PROPR = "textpPR";
    private static final String TEXT_PROID = "textpID";
    private static final String TEXT_PRORAMMAXSPD = "textprorammaxspd";


    private static final String TEXT_PROSOC ="txtProSoc";

    private static final String TEXT_MBPROSOC ="txtMbProSoc";
    private static final String TEXT_MBSIZE ="txtMbSize";
    private static final String TEXT_MBWATT ="txtMbWatt";

    private static final String TEXT_MB = "textm";
    private static final String TEXT_MBPR = "textmPR";
    private static final String TEXT_MBID = "textmID";
    private static final String TEXT_MBRAMVER ="txtMbRamVer";
    private static final String TEXT_MBRAMSPD ="txtMbRamSpd";
    private static final String TEXT_MBSLOT ="txttmbSlot";
    private static final String TEXT_MBRAMMAXCAP ="txtmbMaxRamCap";

    private static final String TEXT_RAMVER ="txtRamVer";
    private static final String TEXT_RAMSPD ="txtRamSpd";
    private static final String TEXT_RAMCAP ="txtRamCap";

    private static final String TEXT_RAM = "textr";
    private static final String TEXT_RAMPR = "textrPR";
    private static final String TEXT_RAMID = "textrID";
    private static final String TEXT_RAMWT = "textrWT";

    private static final String TEXT_VGASUSPRO = "textvgasusPro";
    private static final String TEXT_VGASLOT = "textvgaSlot";

    private static final String TEXT_VGA = "textv";
    private static final String TEXT_VGAPR = "textvPR";
    private static final String TEXT_VGAID = "textvID";
    private static final String TEXT_VGAWT = "textvWT";

    private static final String TEXT_STOSLOT = "textstoSlot";

    private static final String TEXT_STOWT = "textsWT";
    private static final String TEXT_STO = "texts";
    private static final String TEXT_STOPR = "textsPR";
    private static final String TEXT_STOID = "textsID";

    private static final String TEXT_PSUSIZE = "textpsuSize";

    private static final String TEXT_PSU = "textps";
    private static final String TEXT_PSUID = "textpsID";
    private static final String TEXT_PSUPR = "textpsPR";
    private static final String TEXT_PSUWT = "textpsuWt";

    private static final String TEXT_CASESIZE = "textcsSize";

    private static final String TEXT_CASE = "textcs";
    private static final String TEXT_CASEPR = "textcsPR";
    private static final String TEXT_CASEID = "textcsID";


    private static final String BOOL_ISPRO = "falea";
    private static final String BOOL_ISMB = "falsaa";
    private static final String BOOL_ISVGA = "falssd";
    private static final String BOOL_ISSTO = "fasdsdsdlse";
    private static final String BOOL_ISPSU = "fasaaazlse";
    private static final String BOOL_ISCS = "falsscse";
    private static final String BOOL_ISRAM = "faltyasase";

    Integer num;
    String BldID;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);

        dbHelper = new DbHelper(getApplicationContext());
        mainGrid =  findViewById(R.id.mainGrid);
        txtPro = findViewById(R.id.txtPro);
        txtMb = findViewById(R.id.txtMb);
        txtRam = findViewById(R.id.txtRim);
        txtVga = findViewById(R.id.txtVga);
        txtSto = findViewById(R.id.txtSto);
        txtPsu = findViewById(R.id.txtPsu);
        txtCs = findViewById(R.id.txtCs);
        txtPrice =findViewById(R.id.txtPrice);
        txtWatt = findViewById(R.id.txtWatt);
        txtName = findViewById(R.id.txtName);
        extras = getIntent().getExtras();

        imgPro= findViewById(R.id.xPro);
        imgMb= findViewById(R.id.xMb);
        imgRam= findViewById(R.id.xRam);
        imgVga= findViewById(R.id.xVga);
        imgSto= findViewById(R.id.xSto);
        imgPsu= findViewById(R.id.xPsu);
        imgCs= findViewById(R.id.xCs);

        num=1;
        Button btnSave = findViewById(R.id.btnSave);

    //------ Get Broadcast from other activity ---------------//
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                }
                else if (action.equals("setDef")) {
                    imgPro.performClick();
                    imgMb.performClick();
                    imgRam.performClick();
                    imgVga.performClick();
                    imgSto.performClick();
                    imgPsu.performClick();
                    imgCs.performClick();
                    finish();

                }

            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
        registerReceiver(broadcastReceiver, new IntentFilter("setDef"));

    //--//--//----------  Get Data from intent and SaveBuild --------------------------//
        if (extras != null && extras.getString("keyBID") != null) {
            BldID = extras.getString("keyBID");
            valueID=BldID;
            saveBuildID();
        }else{
            bld =  dbHelper.getAllBuild(false,false,"");
            num =  1;
            BldID="1"+num.toString();
            for(int x=0;x<bld.size();x++){
                if (BldID.equals(bld.get(x).getBuildID().toString())){
                    num++;
                    BldID = "1"+num.toString();
                    x=0;
                }
            }
            valueID = BldID;
            saveBuildID();
        }

        if (extras != null && extras.getString("keyName") != null) {
            name = extras.getString("keyName");
            valueName = name;
            saveBuildName();
        }else{
            name = "Build " + num.toString();
            valueName = name;
            saveBuildName();
        }

        txtName.setText(name);
        txtName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});
        txtName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        valueName = txtName.getText().toString();
                        saveBuildName();
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        txtName.clearFocus();
                        return true;
                    }
                return false;
            }
        });

        if (extras != null && extras.getString("keyPro") != null) {
            valueProID = extras.getString("keyPro");
            pro =  dbHelper.getAllPro(true, valueProID,false,"",false,"",false,"","",false,false,"");
            for(final Processor pr : pro) {
                valuePro = pr.getProName();
                valueProWt = pr.getProTdp().toString();
                valueProPr = pr.getProPrice().toString();
                valueProSoc = pr.getProSocket();
                valueProRamMaxSpd = pr.getProRamMaxSpd().toString();
                isProPicked = true;
                visPro = View.VISIBLE;
                saveDataPro();
            }
        }

        if (extras != null && extras.getString("keyMb") != null) {
            valueMbID = extras.getString("keyMb");
            mb = dbHelper.getAllMB(true,valueMbID,false,"0",false,"","",false,"",false,"",false,"",false,"","");
            for(final Motherboard mbo : mb) {
                valueMbPr = mbo.getMbPrice().toString();
                valueMb = mbo.getMbName();
                valueMbProSoc = mbo.getMbProSocket();
                valueMbSize = mbo.getMbSize();
                valueMbRamVer = mbo.getMbRamVer();
                valueMbRamSpd = mbo.getMbRamMaxSpd().toString();
                valueMbRamMaxCap = mbo.getMbRamMaxCap().toString();
                if(valueMbSize.equals("E-ATX")){
                    valueMbWt = "55";
                }else if (valueMbSize.equals("ATX")){
                    valueMbWt ="40";
                }else if (valueMbSize.equals("Micro-Atx")){
                    valueMbWt ="30";
                }else if (valueMbSize.equals("Mini-ITX")){
                    valueMbWt ="15";
                }

                valueMbSlot = mbo.getMbSlot();
                isMbPicked = true;
                visMb= View.VISIBLE;
                saveDataMb();
            }
        }

        if (extras != null && extras.getString("keyRam") != null) {
            valueRamID = extras.getString("keyRam");
            ram = dbHelper.getAllRAM(true, valueRamID,false,"0"
                                ,"0",false,"",false,"","",false,"","");
            for(final RAM rm : ram) {
                valueRamPr = rm.getRamPrice().toString();
                valueRam = rm.getRamName();
                valueRamVer = rm.getRamVer();
                valueRamSpd = rm.getRamSpd().toString();
                valueRamCap = rm.getRamCap().toString();
                valueRamWt ="5";
                isRamPicked = true;
                visRam = View.VISIBLE;
                saveDataRam();
            }
        }

        if (extras != null && extras.getString("keyVga") != null) {
            valueVgaID = extras.getString("keyVga");
            vga = dbHelper.getAllVGA(true,valueVgaID,false,"",false,"");
            for(final VGA vg : vga) {
                valueVgaWt = vg.getVgaTdp().toString();
                valueVgaPr = vg.getVgaPrice().toString();
                valueVga = vg.getVgaName();
                valueVgaSusPow = vg.getVgaSugPow().toString();
                valueVgaSlot = vg.getVgaSlotUsed().toString();
                isVgaPicked = extras.getBoolean("isVga");
                visVga = View.VISIBLE;
                saveDataVga();
            }
        }

        if (extras != null && extras.getString("keySto") != null) {
            valueStoID = extras.getString("keySto");
            sto = dbHelper.getAllSto(true,valueStoID,false,"",false,"",false,"","",false,"");
            for(final Storage st : sto) {
                valueStoPr = st.getStoPrice().toString();
                valueSto = st.getStoName();
                valueStoSlot = st.getStoSize();
                if(valueSto.contains("HDD")){
                    valueStoWt = "9";
                }else if (valueSto.contains("SSHD")){
                    valueStoWt ="6";
                }else if (valueSto.contains("SSD")){
                    valueStoWt ="3";
                }

                isStoPicked = extras.getBoolean("isSto");
                visSto = View.VISIBLE;
                saveDataSto();
            }
        }

        if (extras != null && extras.getString("keyPsu") != null) {
            valuePsuID = extras.getString("keyPsu");
            psu = dbHelper.getAllPSU(true,valuePsuID,"",false,"",false,"");
            for(final PSU ps : psu) {
                valuePsuPr = ps.getPsuPrice().toString();
                valuePsu = ps.getPsuName();
                valuePsuSize = ps.getPsuSize();
                valuePsuWt = ps.getPsuVolt().toString();
                isPsuPicked = extras.getBoolean("isPsu");
                visPsu = View.VISIBLE;
                saveDataPsu();
            }
        }

        if (extras != null && extras.getString("keyCs") != null) {
            valueCsID = extras.getString("keyCs");
            cs = dbHelper.getAllCase(true,valueCsID,false,"",false,"",false,"");
             for(final Case cse : cs) {
                 valueCsPr = cse.getCasePrice().toString();
                 valueCs = cse.getCaseName();
                 valueCsSize = cse.getCaseSize();
                 isCasePicked = extras.getBoolean("isCs");
                 visCs = View.VISIBLE;
                 saveDataCs();
             }
        }

    //--//--//---------- End of Get Data from intent and SaveBuild --------------------------//

        //Set Event
        setSingleEvent(mainGrid);
        loadData();
        updateViews();

        //---- Save Build -------------//
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!txtPro.getText().equals("Processor") && !txtMb.getText().equals("Motherboard") &&
                    !txtRam.getText().equals("RAM") && !txtVga.getText().equals("VGA") &&
                    !txtSto.getText().equals("Storage") && !txtPsu.getText().equals("PSU") &&
                    !txtCs.getText().equals("Case")){

                    txtPro.setText(valuePro);
                    txtMb.setText(valueMb);
                    txtRam.setText(valueRam);
                    txtVga.setText(valueVga);
                    txtSto.setText(valueSto);
                    txtPsu.setText(valuePsu);
                    txtCs.setText(valueCs);

                    Intent intent = new Intent(Scratch.this, preview.class);
                    intent.putExtra("info", "0");
                    intent.putExtra("keyPro", valueProID);
                    intent.putExtra("keyMb", valueMbID);
                    intent.putExtra("keyRam", valueRamID);
                    intent.putExtra("keyVga", valueVgaID);
                    intent.putExtra("keySto", valueStoID);
                    intent.putExtra("keyPsu", valuePsuID);
                    intent.putExtra("keyCs", valueCsID);

                    intent.putExtra("keyName",""+txtName.getText().toString());
                    intent.putExtra("keyBID",""+BldID);
                    intent.putExtra("prc",""+Price.toString());

                    startActivity(intent);

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Scratch.this);
                    builder.setCancelable(true);
                    builder.setTitle("Caution !");
                    builder.setMessage("Please select the remaining part first");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }


            }
        });

    //------ Button function for delete selected part  -------//
            imgPro.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    valueProID = "";
                    valuePro = "Processor";
                    valueProWt = "0";
                    valueProPr = "0";
                    valueProSoc = "";
                    valueProRamMaxSpd="0";
                    isProPicked = false;
                    visPro = View.GONE;
                    saveDataPro();
                    loadData();
                    updateViews();
                }
            });


            imgMb.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    valueMbID ="";
                    valueMbPr = "0";
                    valueMb = "Motherboard";
                    valueMbProSoc = "";
                    valueMbSize = "";
                    valueMbRamVer = "";
                    valueMbRamSpd = "";
                    valueMbRamMaxCap="";
                    valueMbWt="0";
                    valueMbSlot="";
                    isMbPicked = false;
                    visMb = View.GONE;
                    saveDataMb();
                    loadData();
                    updateViews();
                }
            });

            imgRam.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    valueRamID ="";
                    valueRamPr = "0";
                    valueRam = "RAM";
                    valueRamVer = "";
                    valueRamSpd = "";
                    valueRamCap = "";
                    valueRamWt="0";
                    isRamPicked = false;
                    visRam = View.GONE;
                    saveDataRam();
                    loadData();
                    updateViews();
                }
            });

            imgVga.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    valueVgaID ="";
                    valueVgaWt = "0";
                    valueVgaPr = "0";
                    valueVga = "VGA";
                    valueVgaSusPow = "";
                    valueVgaSlot = "";
                    isVgaPicked = false;
                    visVga = View.GONE;
                    saveDataVga();
                    loadData();
                    updateViews();
                }
            });

            imgSto.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    valueStoID ="";
                    valueStoPr = "0";
                    valueSto = "Storage";
                    valueStoSlot = "";
                    valueStoWt="0";
                    isStoPicked = false;
                    visSto = View.GONE;
                    saveDataSto();
                    loadData();
                    updateViews();
                }
            });

            imgPsu.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    valuePsuID ="";
                    valuePsuPr = "0";
                    valuePsu = "PSU";
                    valuePsuSize = "";
                    valuePsuWt ="";
                    isPsuPicked = false;
                    visPsu = View.GONE;
                    saveDataPsu();
                    loadData();
                    updateViews();
                }
            });

            imgCs.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    valueCsID ="";
                    valueCsPr = "0";
                    valueCs = "Case";
                    valueCsSize = "";
                    isCasePicked = false;
                    visCs = View.GONE;
                    saveDataCs();
                    loadData();
                    updateViews();
                }
            });

    ///---------  End of Button function for delete selected part ---------//
///--------- checking PSU, Casing Compability --------------------//
        if(imgPsu.getVisibility()==View.VISIBLE){
            if(Watt < Integer.parseInt(valuePsuWt)){

            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(Scratch.this);
                builder.setCancelable(true);
                builder.setTitle("Caution !");
                builder.setMessage("Your current PSU cannot provide the power needed");

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imgPsu.performClick();
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        }
        //======================
        if(imgCs.getVisibility()==View.VISIBLE && !valueMb.equals("Motherboard")){
            if(valueCsSize.equals(valueMbSize) || valueCsSize.equals("E-ATX") && valueMbSize.equals("ATX")
                    || valueCsSize.equals("E-ATX") && valueMbSize.equals("Micro-ATX") || valueCsSize.equals("E-ATX") && valueMbSize.equals("Mini-ITX")
                    || valueCsSize.equals("ATX") && valueMbSize.equals("Micro-ATX") || valueCsSize.equals("ATX") && valueMbSize.equals("Mini-ITX")
                    || valueCsSize.equals("Micro-ATX") && valueMbSize.equals("Mini-ITX") ){

            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(Scratch.this);
                builder.setCancelable(true);
                builder.setTitle("Caution !");
                builder.setMessage("Your current Casing cannot mount the Motherboard");

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imgCs.performClick();
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        }
    }

///=====================================================================//
    private void setSingleEvent( GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI==0){
                        if(isProPicked ){

                            Intent intent = new Intent(Scratch.this, detail.class);
                            intent.putExtra("id",""+valueProID);
                            info = "0";
                            sendDet(intent, info);

                        }else{
                            send(finalI);
                        }
                    }
                    if (finalI==1){
                        if(isMbPicked){
                            Intent intent = new Intent(Scratch.this, detail.class);
                            intent.putExtra("id",""+valueMbID);
                            info = "1";
                            sendDet(intent, info);

                        }else{
                            send(finalI);
                        }
                    }
                    if (finalI==2){
                        if(isRamPicked){
                            Intent intent = new Intent(Scratch.this, detail.class);
                            intent.putExtra("id",""+valueRamID);
                            info = "2";
                            sendDet(intent, info);
                        }else{
                            send(finalI);
                        }
                    }
                    if (finalI==3){
                        if(isVgaPicked){
                            Intent intent = new Intent(Scratch.this, detail.class);
                            intent.putExtra("id",""+valueVgaID);
                            info = "3";
                            sendDet(intent, info);
                        }else{
                            send(finalI);
                        }
                    }
                    if (finalI==4){
                        if(isStoPicked){
                            Intent intent = new Intent(Scratch.this, detail.class);
                            intent.putExtra("id",""+valueStoID);
                            info = "4";
                            sendDet(intent, info);
                        }
                        else{
                            send(finalI);
                        }
                    }
                    if (finalI==5){
                        if (isPsuPicked){
                            Intent intent = new Intent(Scratch.this, detail.class);
                            intent.putExtra("id",""+valuePsuID);
                            info = "5";
                            sendDet(intent, info);
                        }
                        else{
                            send(finalI);
                        }
                    }
                    if (finalI==6){
                        if(isCasePicked){
                            Intent intent = new Intent(Scratch.this, detail.class);
                            intent.putExtra("id",""+valueCsID);
                            info = "6";
                            sendDet(intent, info);
                        }
                        else{
                            send(finalI);
                        }
                    }


                }
            });
        }
    }
///===============================
    public  void send(Integer finalI){
        Intent intent = new Intent(Scratch.this, choose.class);
        intent.putExtra("info",""+finalI);
        intent.putExtra("isPro",""+isProPicked);
        intent.putExtra("proSoc",""+valueProSoc);
        intent.putExtra("mbRamVer",""+valueMbRamVer);
        intent.putExtra("mbRamSpd",""+valueMbRamSpd);
        intent.putExtra("isMb",""+isMbPicked);
        intent.putExtra("mbProSoc",""+valueMbProSoc);
        intent.putExtra("mbSize",""+valueMbSize);
        intent.putExtra("ramVer",""+valueRamVer);
        intent.putExtra("ramSpd",""+valueRamSpd);
        intent.putExtra("isRam",""+isRamPicked);
        intent.putExtra("vgaSusPow",""+valueVgaSusPow);
        intent.putExtra("vgaSlot",""+valueVgaSlot);
        intent.putExtra("isVga",""+isVgaPicked);
        intent.putExtra("stoSlot",""+valueStoSlot);
        intent.putExtra("isSto",""+isStoPicked);
        intent.putExtra("psuSize",""+valuePsuSize);
        intent.putExtra("isPsu",""+isPsuPicked);
        intent.putExtra("isCs",""+isCasePicked);
        intent.putExtra("csSize",""+valueCsSize);
        intent.putExtra("keyName",""+valueName);
        intent.putExtra("keyBID",""+BldID);
        intent.putExtra("ramCap",""+valueRamCap);
        intent.putExtra("mbRamMaxCap",""+valueMbRamMaxCap);
        intent.putExtra("needPow",""+Watt);

        intent.putExtra("proRamMax",""+valueProRamMaxSpd);
        intent.putExtra("mbSlot",""+valueMbSlot);


        intent.putExtra("mbProSoc",""+valueMbProSoc);
        intent.putExtra("ramVer",""+valueRamVer);

        startActivity(intent);
    }
    public  void sendDet(Intent intent, String finalI ){

        intent.putExtra("info",""+finalI);
        intent.putExtra("isPro",""+isProPicked);
        intent.putExtra("proSoc",""+valueProSoc);
        intent.putExtra("mbRamVer",""+valueMbRamVer);
        intent.putExtra("mbRamSpd",""+valueMbRamSpd);
        intent.putExtra("isMb",""+isMbPicked);
        intent.putExtra("mbProSoc",""+valueMbProSoc);
        intent.putExtra("mbSize",""+valueMbSize);
        intent.putExtra("ramVer",""+valueRamVer);
        intent.putExtra("ramSpd",""+valueRamSpd);
        intent.putExtra("isRam",""+isRamPicked);
        intent.putExtra("vgaSusPow",""+valueVgaSusPow);
        intent.putExtra("vgaSlot",""+valueVgaSlot);
        intent.putExtra("isVga",""+isVgaPicked);
        intent.putExtra("stoSlot",""+valueStoSlot);
        intent.putExtra("isSto",""+isStoPicked);
        intent.putExtra("psuSize",""+valuePsuSize);
        intent.putExtra("isPsu",""+isPsuPicked);
        intent.putExtra("isCs",""+isCasePicked);
        intent.putExtra("csSize",""+valueCsSize);
        intent.putExtra("keyName",""+valueName);
        intent.putExtra("keyBID",""+BldID);
        intent.putExtra("ramCap",""+valueRamCap);
        intent.putExtra("mbRamMaxCap",""+valueMbRamMaxCap);
        intent.putExtra("needPow",""+Watt);

        intent.putExtra("proRamMax",""+valueProRamMaxSpd);
        intent.putExtra("mbSlot",""+valueMbSlot);


        intent.putExtra("mbProSoc",""+valueMbProSoc);
        intent.putExtra("ramVer",""+valueRamVer);

        startActivity(intent);
    }
///===============================
    public void saveDataPro(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_PRO, valuePro);
        editor.putString(TEXT_PRORAMMAXSPD, valueProRamMaxSpd);
        editor.putString(TEXT_PROWT, valueProWt);
        editor.putString(TEXT_PROPR, valueProPr);
        editor.putString(TEXT_PROID, valueProID);
        editor.putString(TEXT_PROSOC, valueProSoc);
        editor.putBoolean(BOOL_ISPRO, isProPicked );
        editor.putInt(VISPRO, visPro);
        editor.apply();
    }
    public void saveDataMb(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_MB, valueMb);
        editor.putString(TEXT_MBID, valueMbID);
        editor.putString(TEXT_MBPR, valueMbPr);
        editor.putString(TEXT_MBWATT, valueMbWt);


        editor.putString(TEXT_MBPROSOC, valueMbProSoc);
        editor.putString(TEXT_MBSIZE, valueMbSize);
        editor.putString(TEXT_MBSLOT, valueMbSlot);
        editor.putString(TEXT_MBRAMMAXCAP, valueMbRamMaxCap);
        editor.putString(TEXT_MBRAMVER, valueMbRamVer);
        editor.putString(TEXT_MBRAMSPD, valueMbRamSpd);
        editor.putBoolean(BOOL_ISMB, isMbPicked );
        editor.putInt(VISMB, visMb);

        editor.apply();
    }
    public void saveDataRam(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_RAM, valueRam);
        editor.putString(TEXT_RAMID, valueRamID);
        editor.putString(TEXT_RAMPR, valueRamPr);
        editor.putString(TEXT_RAMWT, valueRamWt);
        editor.putString(TEXT_RAMCAP, valueRamCap);
        editor.putString(TEXT_RAMVER, valueRamVer);
        editor.putString(TEXT_RAMSPD, valueRamSpd);

        editor.putBoolean(BOOL_ISRAM, isRamPicked );
        editor.putInt(VISRAM, visRam);
        editor.apply();
    }
    public void saveDataVga(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_VGA, valueVga);
        editor.putString(TEXT_VGAID, valueVgaID);
        editor.putString(TEXT_VGAPR, valueVgaPr);
        editor.putString(TEXT_VGAWT, valueVgaWt);

        editor.putString(TEXT_VGASUSPRO, valueVgaSusPow);
        editor.putString(TEXT_VGASLOT, valueVgaSlot);

        editor.putBoolean(BOOL_ISVGA, isVgaPicked );
        editor.putInt(VISVGA, visVga);
        editor.apply();
    }
    public void saveDataSto(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_STO, valueSto);
        editor.putString(TEXT_STOWT, valueStoWt);
        editor.putString(TEXT_STOPR, valueStoPr);
        editor.putString(TEXT_STOID, valueStoID);

        editor.putString(TEXT_STOSLOT, valueStoSlot);
        editor.putBoolean(BOOL_ISSTO, isStoPicked );
        editor.putInt(VISSTO, visSto);
        editor.apply();
    }
    public void saveDataPsu(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_PSU, valuePsu);
        editor.putString(TEXT_PSUID, valuePsuID);
        editor.putString(TEXT_PSUPR, valuePsuPr);
        editor.putString(TEXT_PSUWT, valuePsuWt);


        editor.putString(TEXT_PSUSIZE, valuePsuSize);
        editor.putBoolean(BOOL_ISPSU, isPsuPicked );
        editor.putInt(VISPSU, visPsu);
        editor.apply();
    }
    public void saveDataCs(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_CASE, valueCs);
        editor.putString(TEXT_CASEID, valueCsID);
        editor.putString(TEXT_CASEPR, valueCsPr);

        editor.putString(TEXT_CASESIZE,valueCsSize);
        editor.putBoolean(BOOL_ISCS, isCasePicked );
        editor.putInt(VISCS, visCs);
        editor.apply();
    }
    public void saveBuildName(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_NAME, valueName);

        editor.apply();
    }
    public void saveBuildID(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_ID, valueID);

        editor.apply();
    }
///===============================
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        //Load All Value Picked
        valuePro = sharedPreferences.getString(TEXT_PRO, "Processor");
        valueMb = sharedPreferences.getString(TEXT_MB, "Motherboard");
        valueRam = sharedPreferences.getString(TEXT_RAM, "RAM");
        valueVga = sharedPreferences.getString(TEXT_VGA, "VGA");
        valueSto = sharedPreferences.getString(TEXT_STO, "Storage");
        valuePsu = sharedPreferences.getString(TEXT_PSU, "PSU");
        valueCs = sharedPreferences.getString(TEXT_CASE, "Casing");

        //Load All ID
        valueProID = sharedPreferences.getString(TEXT_PROID, "0");
        valueMbID = sharedPreferences.getString(TEXT_MBID, "0");
        valueRamID = sharedPreferences.getString(TEXT_RAMID, "0");
        valueVgaID = sharedPreferences.getString(TEXT_VGAID, "0");
        valueStoID = sharedPreferences.getString(TEXT_STOID, "0");
        valuePsuID = sharedPreferences.getString(TEXT_PSUID, "0");
        valueCsID = sharedPreferences.getString(TEXT_CASEID, "0");

        //Load All Watt value
        valueProWt = sharedPreferences.getString(TEXT_PROWT, "0");
        valueRamWt = sharedPreferences.getString(TEXT_RAMWT, "0");
        valueVgaWt = sharedPreferences.getString(TEXT_VGAWT, "0");
        valueStoWt = sharedPreferences.getString(TEXT_STOWT, "0");
        valueMbWt = sharedPreferences.getString(TEXT_MBWATT, "0");

        //Load All Price value
        valueProPr = sharedPreferences.getString(TEXT_PROPR, "0");
        valueMbPr = sharedPreferences.getString(TEXT_MBPR, "0");
        valueRamPr = sharedPreferences.getString(TEXT_RAMPR, "0");
        valueVgaPr = sharedPreferences.getString(TEXT_VGAPR, "0");
        valueStoPr = sharedPreferences.getString(TEXT_STOPR, "0");
        valuePsuPr = sharedPreferences.getString(TEXT_PSUPR, "0");
        valueCsPr = sharedPreferences.getString(TEXT_CASEPR, "0");

        //Load Processor value
        isProPicked = sharedPreferences.getBoolean(BOOL_ISPRO,false);
        valueProSoc = sharedPreferences.getString(TEXT_PROSOC,"0");
        valueProRamMaxSpd =  sharedPreferences.getString(TEXT_PRORAMMAXSPD,"0");

        //Load Motherboard value
        valueMbRamVer = sharedPreferences.getString(TEXT_MBRAMVER, "0");
        valueMbRamSpd = sharedPreferences.getString(TEXT_MBRAMSPD, "0");
        valueMbSlot = sharedPreferences.getString(TEXT_MBSLOT, "0");
        isMbPicked = sharedPreferences.getBoolean(BOOL_ISMB,false);


        valueMbProSoc = sharedPreferences.getString(TEXT_MBPROSOC, "0");
        valueMbSize = sharedPreferences.getString(TEXT_MBSIZE, "0");

         valueMbRamMaxCap = sharedPreferences.getString(TEXT_MBRAMMAXCAP, "0");

        //Load value from RAM
        valueRamVer = sharedPreferences.getString(TEXT_RAMVER, "0");
        valueRamSpd = sharedPreferences.getString(TEXT_RAMSPD, "0");
        valueRamCap = sharedPreferences.getString(TEXT_RAMCAP, "0");
        //Load value from VGA
        valueVgaSusPow = sharedPreferences.getString(TEXT_VGASUSPRO, "0");
        valueVgaSlot = sharedPreferences.getString(TEXT_VGASLOT, "0");

        //Load value from STO
        valueStoSlot = sharedPreferences.getString(TEXT_STOSLOT, "0");

        //Load value from PSU
        valuePsuSize = sharedPreferences.getString(TEXT_PSUSIZE, "0");
        valuePsuWt = sharedPreferences.getString(TEXT_PSUWT, "0");

        //Load value from CASE
        valueCsSize = sharedPreferences.getString(TEXT_CASESIZE, "0");

        //
        isRamPicked = sharedPreferences.getBoolean(BOOL_ISRAM,false);
        isVgaPicked = sharedPreferences.getBoolean(BOOL_ISVGA,false);
        isStoPicked = sharedPreferences.getBoolean(BOOL_ISSTO,false);
        isPsuPicked = sharedPreferences.getBoolean(BOOL_ISPSU,false);
        isCasePicked = sharedPreferences.getBoolean(BOOL_ISCS,false);


        visPro = sharedPreferences.getInt(VISPRO,View.GONE);
        visMb = sharedPreferences.getInt(VISMB,View.GONE);
        visRam = sharedPreferences.getInt(VISRAM,View.GONE);
        visVga = sharedPreferences.getInt(VISVGA,View.GONE);
        visSto = sharedPreferences.getInt(VISSTO,View.GONE);
        visPsu = sharedPreferences.getInt(VISPSU,View.GONE);
        visCs = sharedPreferences.getInt(VISCS,View.GONE);

        valueName = sharedPreferences.getString(TEXT_NAME, valueName);
        valueID = sharedPreferences.getString(TEXT_ID, BldID);

    }
    public void updateViews(){
        imgPro.setVisibility(visPro);
        imgMb.setVisibility(visMb);
        imgRam.setVisibility(visRam);
        imgVga.setVisibility(visVga);
        imgSto.setVisibility(visSto);
        imgPsu.setVisibility(visPsu);
        imgCs.setVisibility(visCs);

        txtPro.setText(valuePro);
        txtMb.setText(valueMb);
        txtRam.setText(valueRam);
        txtVga.setText(valueVga);
        txtSto.setText(valueSto);
        txtPsu.setText(valuePsu);
        txtCs.setText(valueCs);


       Price =  Integer.parseInt(valueProPr)+
                        Integer.parseInt(valueMbPr)+
                        Integer.parseInt(valueRamPr)+
                        Integer.parseInt(valueVgaPr)+
                        Integer.parseInt(valueStoPr)+
                        Integer.parseInt(valuePsuPr)+
                        Integer.parseInt(valueCsPr);


        Watt =  Integer.parseInt(valueProWt)+Integer.parseInt(valueMbWt)+
                        Integer.parseInt(valueRamWt)+
                        Integer.parseInt(valueVgaWt)+
                        Integer.parseInt(valueStoWt);


        NumberFormat myFormat = new DecimalFormat("#,###");

        String strPrice ="Price : Rp " + myFormat.format(Price);
        String strWatt ="Watt : "+ Watt.toString()+" W";

        txtPrice.setText(strPrice);
        txtWatt.setText(strWatt);
        txtName.setText(valueName);
    }
///===============================

}
