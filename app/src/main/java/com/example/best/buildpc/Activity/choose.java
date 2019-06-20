package com.example.best.buildpc.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.best.buildpc.Factory.Case;
import com.example.best.buildpc.DbHelper;
import com.example.best.buildpc.Factory.Motherboard;
import com.example.best.buildpc.Factory.PSU;
import com.example.best.buildpc.Factory.Processor;
import com.example.best.buildpc.R;
import com.example.best.buildpc.Factory.RAM;
import com.example.best.buildpc.Factory.Storage;
import com.example.best.buildpc.Factory.VGA;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class choose extends AppCompatActivity {

    List<Processor> pro = new ArrayList<>();
    List<Motherboard> mb = new ArrayList<>();
    List<RAM> ram = new ArrayList<>();
    List<VGA> vga = new ArrayList<>();
    List<PSU> psu = new ArrayList<>();
    List<Case> cs = new ArrayList<>();
    List<Storage> sto = new ArrayList<>();
    DbHelper dbHelper;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        container = findViewById(R.id.container);

        NumberFormat myFormat = new DecimalFormat("#,###");

        dbHelper = new DbHelper(getApplicationContext());
        final String BldID =  getIntent().getStringExtra("keyBID");
        final String name =  getIntent().getStringExtra("keyName");

        ////
        Boolean isProPicked = Boolean.parseBoolean(getIntent().getStringExtra("isPro"));
        String proSoc = getIntent().getStringExtra("proSoc");
        String proRamMax = getIntent().getStringExtra("proRamMax");

        Boolean isMbPicked = Boolean.parseBoolean(getIntent().getStringExtra("isMb"));
        String mbRamVer = getIntent().getStringExtra("mbRamVer");
        String mbRamSpd = getIntent().getStringExtra("mbRamSpd");
        String mbSlot  = getIntent().getStringExtra("mbSlot");

        String mbProSoc = getIntent().getStringExtra("mbProSoc");
        String mbSize = getIntent().getStringExtra("mbSize");

        Boolean isRamPicked = Boolean.parseBoolean(getIntent().getStringExtra("isRam"));
        String ramVer = getIntent().getStringExtra("ramVer");
        String ramSpd = getIntent().getStringExtra("ramSpd");
        String ramCap = getIntent().getStringExtra("ramCap");

        String needPow = getIntent().getStringExtra("needPow");

        Boolean isStoPicked = Boolean.parseBoolean(getIntent().getStringExtra("isSto"));
        String stoSlot = getIntent().getStringExtra("stoSlot");

        Boolean isPsuPicked = Boolean.parseBoolean(getIntent().getStringExtra("isPsu"));
        String psuSize = getIntent().getStringExtra("psuSize");


        final EditText txtsearch = findViewById(R.id.txtSeacrh);
        Button btnSearch = findViewById(R.id.btnSearch);
        Boolean isSearching = getIntent().getBooleanExtra("src",false);
        String txtSrc = getIntent().getStringExtra("txtSrc");
        txtsearch.setHint(txtSrc);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (txtsearch.getText().toString().trim().equals("")){
                    txtsearch.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(saved.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtsearch, InputMethodManager.SHOW_IMPLICIT);
                }else{
                    Intent intent = new Intent(choose.this, choose.class);
                    intent.putExtra("src",true);
                    intent.putExtra("txtSrc",""+txtsearch.getText());
                    send(intent);
                }
            }
        });

        if (getIntent()!=null){
           String info = getIntent().getStringExtra("info");
            if(info.equals("0")){

                pro = dbHelper.getAllPro(false,"",isMbPicked,mbProSoc,isSearching, txtSrc,false,"","",false,isRamPicked,ramVer);
                //qry = "select * from Processor where proSocket = \""+ MbProSoc +"\"";
                for(final Processor pr : pro){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView= inflater.inflate(R.layout.rowset,null);
                    Button btn1 = addView.findViewById(R.id.btnData);
                    Button btn = addView.findViewById(R.id.btnDa);

                    String txt = pr.getProName()+"\nRp "+ myFormat.format(pr.getProPrice());
                    btn1.setText(txt);

                    btn1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent killScratch = new Intent("finish_activity");
                            sendBroadcast(killScratch);
                            Intent intent = new Intent(choose.this, Scratch.class);
                            intent.putExtra("keyPro", pr.getProID().toString() );
                            intent.putExtra("isPro",true);
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("keyBID",""+BldID);
                            startActivity(intent);
                            finish();

                        }
                    });

                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(choose.this, detail.class);
                            intent.putExtra("detail","1");
                            intent.putExtra("info","0");
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("id",""+pr.getProID());

                            startActivity(intent);
                        }
                    });
                    container.addView(addView);
                }
            }

            else if(info.equals("1")){
                mb = dbHelper.getAllMB(false,"0",isProPicked,proSoc,isRamPicked,ramVer,ramSpd,isStoPicked,stoSlot, isSearching, txtSrc,false,"",false,ramCap,"");
                //qry = "select * from Motherboard where mbProSocket = \""+pro+"\"";
                for(final Motherboard mbo : mb){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView= inflater.inflate(R.layout.rowset,null);
                    Button btn1 = addView.findViewById(R.id.btnData);
                    String txt = mbo.getMbName()+"\nRp "+myFormat.format(mbo.getMbPrice());

                    btn1.setText(txt);

                    btn1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent killScratch = new Intent("finish_activity");
                            sendBroadcast(killScratch);
                            Intent intent = new Intent(choose.this, Scratch.class);
                            intent.putExtra("keyMb",  mbo.getMbID().toString());
                            intent.putExtra("isMb",  true);
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("keyBID",""+BldID);
                            startActivity(intent);
                            finish();
                        }
                    });

                    Button btn =  addView.findViewById(R.id.btnDa);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(choose.this, detail.class);
                            intent.putExtra("detail","1");
                            intent.putExtra("info","1");
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("id",""+mbo.getMbID());

                            startActivity(intent);
                        }
                    });
                    container.addView(addView);
                }
            }

            else if(info.equals("2")){
                String ver="";
                if (isProPicked && !isMbPicked){
                    mb = dbHelper.getAllMB(false,"",isProPicked,proSoc,false,"","",false,"",false,"",false,"",false,"","");
                    for(final Motherboard mbo : mb) {
                        ver = mbo.getMbRamVer();
                    }
                }
                ram = dbHelper.getAllRAM(false,"0",isMbPicked,mbRamVer,mbRamSpd, isSearching, txtSrc,isProPicked,proRamMax,ver,false,"","16");
                //qry = "select * from RAM where ramVer = \""+ ramVer +"\" and ramCap <="+ mbMaxCap +" AND ramSpd <= "+ramSpeed; //+"\"";
                for(final RAM rm : ram){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView= inflater.inflate(R.layout.rowset,null);
                    Button btn1 = addView.findViewById(R.id.btnData);
                    String txt = rm.getRamName()+"\nRp "+myFormat.format(rm.getRamPrice());

                    btn1.setText(txt);
                    btn1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent killScratch = new Intent("finish_activity");
                            sendBroadcast(killScratch);
                            Intent intent = new Intent(choose.this, Scratch.class);
                            intent.putExtra("keyRam", rm.getRamID().toString());
                            intent.putExtra("isRam",  true);
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("keyBID",""+BldID);
                            startActivity(intent);
                            finish();
                        }
                    });

                    Button btn =  addView.findViewById(R.id.btnDa);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(choose.this, detail.class);
                            intent.putExtra("detail","1");
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("info","2");
                            intent.putExtra("id",""+rm.getRamID());

                            startActivity(intent);
                        }
                    });
                    container.addView(addView);
                }
            }
            else if(info.equals("3")){
                vga = dbHelper.getAllVGA(false,"0", isSearching, txtSrc,false,"");
                for(final VGA vg : vga){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView= inflater.inflate(R.layout.rowset,null);
                    Button btn1 = addView.findViewById(R.id.btnData);
                    String txt = vg.getVgaName()+"\nRp "+myFormat.format(vg.getVgaPrice());
                    btn1.setText(txt);
                  //  btn1.setTextColor(0xffffffff);

                    btn1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent killScratch = new Intent("finish_activity");
                            sendBroadcast(killScratch);
                            Intent intent = new Intent(choose.this, Scratch.class);
                            intent.putExtra("keyVga", vg.getVgaID().toString());
                            intent.putExtra("isVga",  true);
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("keyBID",""+BldID);
                            startActivity(intent);
                            finish();
                        }
                    });

                    Button btn =  addView.findViewById(R.id.btnDa);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(choose.this, detail.class);
                            intent.putExtra("info","3");
                            intent.putExtra("detail","1");
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("id",""+vg.getVgaID());

                            startActivity(intent);
                        }
                    });
                    container.addView(addView);
                }
            }
            else if(info.equals("4")){
                sto = dbHelper.getAllSto(false,"0", isSearching, txtSrc,isMbPicked,mbSlot,false,"","",false,"");
                //qry = "select * from Storage where stoName not like '%M.2%'";
                for(final Storage st : sto){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView= inflater.inflate(R.layout.rowset,null);
                    Button btn1 = addView.findViewById(R.id.btnData);
                    String txt = st.getStoName()+"\nRp "+myFormat.format(st.getStoPrice());
                    btn1.setText(txt);
                  //  btn1.setTextColor(0xffffffff);

                    btn1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent killScratch = new Intent("finish_activity");
                            sendBroadcast(killScratch);
                            Intent intent = new Intent(choose.this, Scratch.class);
                            intent.putExtra("keySto", st.getStoID().toString());
                            intent.putExtra("isSto",  true);
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("keyBID",""+BldID);
                            startActivity(intent);
                            finish();
                        }
                    });


                    Button btn =  addView.findViewById(R.id.btnDa);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(choose.this, detail.class);
                            intent.putExtra("info","4");
                            intent.putExtra("detail","1");
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("id",""+st.getStoID());

                            startActivity(intent);
                        }
                    });
                    container.addView(addView);
                }
            }
            else if(info.equals("5")){
                psu = dbHelper.getAllPSU(false,"0",needPow, isSearching, txtSrc,false,"");
                //qry = "select * from PSU where psuVolt >= "+needPow;

                for(final PSU ps : psu){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView= inflater.inflate(R.layout.rowset,null);
                    Button btn1 = addView.findViewById(R.id.btnData);
                    String txt = ps.getPsuName()+"\nRp "+myFormat.format(ps.getPsuPrice());
                    btn1.setText(txt);
                  //  btn1.setTextColor(0xffffffff);

                    btn1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent killScratch = new Intent("finish_activity");
                            sendBroadcast(killScratch);
                            Intent intent = new Intent(choose.this, Scratch.class);
                            intent.putExtra("keyPsu", ps.getPsuID().toString());
                            intent.putExtra("isPsu",  true);

                            intent.putExtra("keyName",""+name);
                            intent.putExtra("keyBID",""+BldID);

                            startActivity(intent);
                            finish();
                        }
                    });

                    Button btn =  addView.findViewById(R.id.btnDa);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(choose.this, detail.class);
                            intent.putExtra("info","5");
                            intent.putExtra("id",""+ps.getPsuID());
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("detail","1");

                            startActivity(intent);
                        }
                    });
                    container.addView(addView);
                }
            }
            else if(info.equals("6")){
                cs = dbHelper.getAllCase(false,"0",isMbPicked,mbSize, isSearching, txtSrc,false,"");
                //qry = "select * from Casing where caseSize = \""+ MbSize +"\"";
                for(final Case cse : cs){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView= inflater.inflate(R.layout.rowset,null);
                    Button btn1 = addView.findViewById(R.id.btnData);
                    String txt = cse.getCaseName()+"\nRp "+myFormat.format(cse.getCasePrice());
                    btn1.setText(txt);
                 //   btn1.setTextColor(0xffffffff);

                    btn1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent killScratch = new Intent("finish_activity");
                            sendBroadcast(killScratch);
                            Intent intent = new Intent(choose.this, Scratch.class);
                            intent.putExtra("keyCs", cse.getCaseID().toString());
                            intent.putExtra("isCs",  true);
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("keyBID",""+BldID);
                            startActivity(intent);
                            finish();
                        }
                    });

                    Button btn =  addView.findViewById(R.id.btnDa);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(choose.this, detail.class);
                            intent.putExtra("info","6");
                            intent.putExtra("detail","1");
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("id",""+cse.getCaseID());

                            startActivity(intent);
                        }
                    });
                    container.addView(addView);
                }
            }
        }
    }
    private void send(Intent intent){
        ////
        final  Boolean isProPicked = Boolean.parseBoolean(getIntent().getStringExtra("isPro"));
        final  String proSoc = getIntent().getStringExtra("proSoc");

        final  Boolean isMbPicked = Boolean.parseBoolean(getIntent().getStringExtra("isMb"));
        final  String mbRamVer = getIntent().getStringExtra("mbRamVer");
        final  String mbRamSpd = getIntent().getStringExtra("mbRamSpd");

        final  String mbProSoc = getIntent().getStringExtra("mbProSoc");
        final String mbSize = getIntent().getStringExtra("mbSize");

        final  Boolean isRamPicked = Boolean.parseBoolean(getIntent().getStringExtra("isRam"));
        final  String ramVer = getIntent().getStringExtra("ramVer");
        final   String ramSpd = getIntent().getStringExtra("ramSpd");

        final  Boolean isVgaPicked = Boolean.parseBoolean(getIntent().getStringExtra("isVga"));
        final String needPow = getIntent().getStringExtra("needPow");
        final String vgaSlot = getIntent().getStringExtra("vgaSlot");

        final Boolean isStoPicked = Boolean.parseBoolean(getIntent().getStringExtra("isSto"));
        final String stoSlot = getIntent().getStringExtra("stoSlot");

        final  Boolean isPsuPicked = Boolean.parseBoolean(getIntent().getStringExtra("isPsu"));
        final String psuSize = getIntent().getStringExtra("psuSize");

        final Boolean isCasePicked = Boolean.parseBoolean(getIntent().getStringExtra("isCs"));
        final String csSize = getIntent().getStringExtra("csSize");

        final String info = getIntent().getStringExtra("info");

        final String BldID =  getIntent().getStringExtra("keyBID");
        final String name =  getIntent().getStringExtra("keyName");

        String mbSlot  = getIntent().getStringExtra("mbSlot");
        String proRamMax = getIntent().getStringExtra("proRamMax");
        ////
        intent.putExtra("proRamMax",""+proRamMax);
        intent.putExtra("mbSlot",""+mbSlot);

        intent.putExtra("info",""+info);
        intent.putExtra("isPro",""+isProPicked);
        intent.putExtra("proSoc",""+proSoc);
        intent.putExtra("mbRamVer",""+mbRamVer);
        intent.putExtra("mbRamSpd",""+mbRamSpd);
        intent.putExtra("isMb",""+isMbPicked);
        intent.putExtra("mbProSoc",""+mbProSoc);
        intent.putExtra("mbSize",""+mbSize);
        intent.putExtra("ramVer",""+ramVer);
        intent.putExtra("ramSpd",""+ramSpd);
        intent.putExtra("isRam",""+isRamPicked);
        intent.putExtra("vgaSlot",""+vgaSlot);
        intent.putExtra("isVga",""+isVgaPicked);
        intent.putExtra("stoSlot",""+stoSlot);
        intent.putExtra("isSto",""+isStoPicked);
        intent.putExtra("psuSize",""+psuSize);
        intent.putExtra("isPsu",""+isPsuPicked);
        intent.putExtra("isCs",""+isCasePicked);
        intent.putExtra("csSize",""+csSize);
        intent.putExtra("keyName",""+name);
        intent.putExtra("keyBID",""+BldID);
        intent.putExtra("needPow",""+needPow);

        startActivity(intent);
        finish();

    }
}
