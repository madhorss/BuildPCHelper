package com.example.best.buildpc.Activity;

import android.content.Intent;
import android.net.Uri;
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

public class detail extends AppCompatActivity {

    List<Processor> pro = new ArrayList<Processor>();
    List<Motherboard> mb = new ArrayList<Motherboard>();
    List<RAM> ram = new ArrayList<RAM>();
    List<VGA> vga = new ArrayList<VGA>();
    List<PSU> psu = new ArrayList<PSU>();
    List<Case> cs = new ArrayList<Case>();
    List<Storage> sto = new ArrayList<Storage>();
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new DbHelper(getApplicationContext());

        Button btnSave = findViewById(R.id.btnSave);
        Button btn = findViewById(R.id.btnMoreInfo);
        Button btnEdit = findViewById(R.id.btnEditPart);

        TextView txt1 = findViewById(R.id.txt1);
        TextView txt2 = findViewById(R.id.txt2);
        TextView txt3 = findViewById(R.id.txt3);
        TextView txt4 = findViewById(R.id.txt4);
        TextView txt5 = findViewById(R.id.txt5);
        TextView txt6 = findViewById(R.id.txt6);
        TextView txt7 = findViewById(R.id.txt7);
        TextView txt8 = findViewById(R.id.txt8);
        TextView txt9 = findViewById(R.id.txt9);
        TextView txt10 = findViewById(R.id.txt10);
        TextView txt11 = findViewById(R.id.txt11);
        Boolean cek = true;

        NumberFormat myFormat = new DecimalFormat("#,###");

        final String BldID =  getIntent().getStringExtra("keyBID");
        final String name =  getIntent().getStringExtra("keyName");

        final String detail =  getIntent().getStringExtra("detail");
        if(detail!=null){
            btnEdit.setVisibility(View.INVISIBLE);
        }else{
            btnEdit.setVisibility(View.VISIBLE);
        }

        final String strpro = getIntent().getStringExtra("keyPro");
        final String strmb = getIntent().getStringExtra("keyMb");
        final String strram = getIntent().getStringExtra("keyRam");
        final String strvga = getIntent().getStringExtra("keyVga");
        final String strsto = getIntent().getStringExtra("keySto");
        final String strpsu = getIntent().getStringExtra("keyPsu");
        final String strcs = getIntent().getStringExtra("keyCs");
        final String namePrev = getIntent().getStringExtra("keyName");
        final String id = getIntent().getStringExtra("keyBID");
        final String price = getIntent().getStringExtra("prc");

        Date tDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final String strDate = formatter.format(tDate);

        final String info = getIntent().getStringExtra("info");
        if(info.equals("0")) {
            final String s = getIntent().getStringExtra("id");
            pro =  dbHelper.getAllPro(cek, s,false,"",false,"",false
                                    ,"","",false,false,"");
            for(final Processor pr : pro) {

                txt1.setText("Name : "+pr.getProName());
                txt2.setText("Socket : "+pr.getProSocket());
                txt3.setText("Clock Speed/Max: "+pr.getProClockSpd().toString()+"/"+pr.getProClockMaxSpd().toString()+" Ghz");
                txt4.setText("Cache : "+pr.getProCache()+" MB");
                txt5.setText("Core/Thread : "+pr.getProCore().toString()+"/"+pr.getProThread().toString());
                txt6.setText("RAM Channel/Max Speed : "+pr.getProRamChan().toString()+"/"+pr.getProRamMaxSpd().toString()+" Mhz");
                txt7.setText("Integrated GPU : "+pr.getProIntGpu());
                txt8.setText("TDP : "+pr.getProTdp().toString()+" W");
                txt9.setText("Price : Rp "+ myFormat.format(pr.getProPrice()));
                txt10.setText("");
                txt11.setText("");

                btnSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(detail!=null && detail.equals("0")){
                            Intent intent = new Intent(detail.this, saved.class);
                            Boolean isInserted = dbHelper.insertBuild(id,name,strmb,strpro,strram,strvga,strsto,strpsu,strcs,strDate,price);
                            if(isInserted) {
                                Toast.makeText(detail.this, "Build INSERTED", Toast.LENGTH_LONG).show();
                            }else{
                                isInserted = dbHelper.updateBuild(id,name,strmb,strpro,strram,strvga,strsto,strpsu,strcs,strDate,price);
                                Toast.makeText(detail.this,"Build Updated",Toast.LENGTH_LONG).show();
                            }
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(detail.this, Scratch.class);
                            intent.putExtra("keyPro", pr.getProID().toString() );
                            intent.putExtra("keyName",""+name);
                            intent.putExtra("keyBID",""+BldID);

                            startActivity(intent);
                            finish();
                        }

                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent search = new Intent(android.content.Intent.ACTION_VIEW);
                        String strUrl = "http://www.google.com/search?q=" + pr.getProName();
                        search.setData(Uri.parse(strUrl));
                        startActivity(search);
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                            send();
                    }
                });
            }
        }
        else if(info.equals("1")) {

            String s = getIntent().getStringExtra("id");
            mb = dbHelper.getAllMB(cek,s,false,"0",false,"","",false,"",false,"",false,"",false,"","");
            for(final Motherboard mbo : mb) {
                txt1.setText("Name : "+mbo.getMbName());
                txt2.setText("Processor Socket : "+mbo.getMbProSocket());
                txt3.setText("Chipset : "+mbo.getMbChipset());
                txt4.setText("Ram Version : "+mbo.getMbRamVer());
                txt5.setText("Ram Slot : "+mbo.getMbRamSlot().toString());
                txt6.setText("Max RAM Speed : "+mbo.getMbRamMaxSpd().toString()+ " Mhz");
                txt7.setText("Max RAM Capacity : "+mbo.getMbRamMaxCap().toString()+" GB");
                txt8.setText("Slot : "+mbo.getMbSlot());
                txt9.setText("Size : "+mbo.getMbSize());
                txt10.setText("Price : Rp "+ myFormat.format(mbo.getMbPrice()));
                txt11.setText("");

                btnSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(detail!=null && detail.equals("0")){
                            Intent intent = new Intent(detail.this, saved.class);
                            Boolean isInserted = dbHelper.insertBuild(id,name,strmb,strpro,strram,strvga,strsto,strpsu,strcs,strDate,price);
                            if(isInserted) {
                                Toast.makeText(detail.this, "Build INSERTED", Toast.LENGTH_LONG).show();
                            }else{
                                isInserted = dbHelper.updateBuild(id,name,strmb,strpro,strram,strvga,strsto,strpsu,strcs,strDate,price);
                                Toast.makeText(detail.this,"Build Updated",Toast.LENGTH_LONG).show();
                            }
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(detail.this, Scratch.class);
                            intent.putExtra("keyMb", mbo.getMbID().toString());
                            intent.putExtra("keyName", "" + name);
                            intent.putExtra("keyBID", "" + BldID);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent search = new Intent(android.content.Intent.ACTION_VIEW);
                        String strUrl = "http://www.google.com/search?q=" +mbo.getMbName();

                        search.setData(Uri.parse(strUrl));
                        startActivity(search);
                    }
                });

                 btnEdit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                            send();
                    }
                });
            }
        }
        else if(info.equals("2")) {

            String s = getIntent().getStringExtra("id");
            ram = dbHelper.getAllRAM(cek,s,false,"0","0",false,""
                                    ,false,"","",false,"","");
            for(final RAM rm : ram) {
                txt1.setText("Name : "+rm.getRamName());
                txt2.setText("Capacity : "+rm.getRamCap().toString()+" GB");
                txt3.setText("Version : "+rm.getRamVer());
                txt4.setText("Speed : "+rm.getRamSpd().toString()+" Mhz");
                txt5.setText("Price : Rp "+ myFormat.format(rm.getRamPrice()));
                txt6.setText("");
                txt7.setText("");
                txt8.setText("");
                txt9.setText("");
                txt10.setText("");
                txt11.setText("");

                btnSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(detail!=null && detail.equals("0")){
                            Intent intent = new Intent(detail.this, saved.class);
                            Boolean isInserted = dbHelper.insertBuild(id,name,strmb,strpro,strram,strvga,strsto,strpsu,strcs,strDate,price);
                            if(isInserted) {
                                Toast.makeText(detail.this, "Build INSERTED", Toast.LENGTH_LONG).show();
                            }else{
                                isInserted = dbHelper.updateBuild(id,name,strmb,strpro,strram,strvga,strsto,strpsu,strcs,strDate,price);
                                Toast.makeText(detail.this,"Build Updated",Toast.LENGTH_LONG).show();
                            }
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(detail.this, Scratch.class);
                            intent.putExtra("keyRam", rm.getRamID().toString());
                            intent.putExtra("keyName", "" + name);
                            intent.putExtra("keyBID", "" + BldID);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent search = new Intent(android.content.Intent.ACTION_VIEW);
                        String strUrl = "http://www.google.com/search?q=" + rm.getRamName();

                        search.setData(Uri.parse(strUrl));
                        startActivity(search);
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                            send();
                    }
                });
            }
        }
        else if(info.equals("3")) {
            String s = getIntent().getStringExtra("id");
            vga = dbHelper.getAllVGA(cek,s,false,"",false,"");
            for(final VGA vg : vga) {
                txt1.setText("Name : "+vg.getVgaName());
                txt2.setText("Core : "+vg.getVgaCoreUnit().toString()+" unit, "+vg.getVgaCoreSpd().toString()+"-"+vg.getVgaCoreMaxSpd().toString()+" Mhz");
                txt3.setText("Memory Type : "+vg.getVgaMemType());
                txt4.setText("Memory Capacity : "+vg.getVgaMemCap().toString()+" GB");
                txt5.setText("Memory Speed : "+vg.getVgaMemSpd().toString()+" Mhz");
                txt6.setText("Interface : "+vg.getVgaInterface());
                txt7.setText("Power Pin : "+vg.getVgaPin());
                txt8.setText("TDP : "+vg.getVgaTdp().toString()+" W");
                txt9.setText("Suggested Power : "+vg.getVgaSugPow().toString()+" W");
                txt10.setText("Slot Used : "+vg.getVgaSlotUsed().toString());
                txt11.setText("Price : Rp "+ myFormat.format(vg.getVgaPrice()));

                btnSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(detail!=null && detail.equals("0")){
                            Intent intent = new Intent(detail.this, saved.class);
                            Boolean isInserted = dbHelper.insertBuild(id,name,strmb,strpro,strram,strvga,strsto,strpsu,strcs,strDate,price);
                            if(isInserted) {
                                Toast.makeText(detail.this, "Build INSERTED", Toast.LENGTH_LONG).show();
                            }else{
                                isInserted = dbHelper.updateBuild(id,name,strmb,strpro,strram,strvga,strsto,strpsu,strcs,strDate,price);
                                Toast.makeText(detail.this,"Build Updated",Toast.LENGTH_LONG).show();
                            }
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(detail.this, Scratch.class);
                            intent.putExtra("keyVga", vg.getVgaID().toString());
                            intent.putExtra("isVga", true);
                            intent.putExtra("keyName", "" + name);
                            intent.putExtra("keyBID", "" + BldID);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent search = new Intent(android.content.Intent.ACTION_VIEW);
                        String strUrl = "http://www.google.com/search?q=" +vg.getVgaName();

                        search.setData(Uri.parse(strUrl));
                        startActivity(search);
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                            send();
                    }
                });
            }
        }
        else if(info.equals("4")) {
            String s = getIntent().getStringExtra("id");
            sto = dbHelper.getAllSto(cek,s,false,"",false,"",false,"","",false,"");
            for(final Storage st : sto) {
                txt1.setText("Storage Name : "+st.getStoName());
                txt2.setText("Capacity : "+st.getStoCap().toString() +" GB");
                txt3.setText("Rotation Speed : "+st.getStoRotSpd().toString());
                txt4.setText("Read : "+st.getStoRead().toString()+" MB/s");
                txt5.setText("Write : "+st.getStoWrite().toString()+" MB/s");
                txt6.setText("Interface : "+st.getStoInterface());
                txt7.setText("Size : "+st.getStoSize());
                txt8.setText("Price : Rp "+ myFormat.format(st.getStoPrice()));
                txt9.setText("");
                txt10.setText("");
                txt11.setText("");

                btnSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(detail.this, Scratch.class);
                        intent.putExtra("keySto", st.getStoID().toString());
                        intent.putExtra("isSto",  true);
                        intent.putExtra("keyName",""+name);
                        intent.putExtra("keyBID",""+BldID);
                        startActivity(intent);
                        finish();
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent search = new Intent(android.content.Intent.ACTION_VIEW);
                        String strUrl = "http://www.google.com/search?q=" + st.getStoName();

                        search.setData(Uri.parse(strUrl));
                        startActivity(search);
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        send();
                    }
                });
            }
        }
        else if(info.equals("5")) {
            String s = getIntent().getStringExtra("id");
            psu = dbHelper.getAllPSU(cek,s,"",false,"",false,"");
            for(final PSU ps : psu) {
                txt1.setText("PSU Name : "+ps.getPsuName());
                txt2.setText("PSU Type : "+ps.getPsuType());
                txt3.setText("Rank : "+ps.getPsuRank());
                txt4.setText("Power : "+ps.getPsuVolt().toString() +" W");
                txt5.setText("Pin : "+ps.getPsuPin());
                txt6.setText("Size : "+ps.getPsuSize());
                txt7.setText("Price : Rp "+ myFormat.format(ps.getPsuPrice()));
                txt8.setText("");
                txt9.setText("");
                txt10.setText("");
                txt11.setText("");

                btnSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(detail.this, Scratch.class);
                        intent.putExtra("keyPsu", ps.getPsuID().toString());
                        intent.putExtra("isPsu",  true);
                        intent.putExtra("keyName",""+name);
                        intent.putExtra("keyBID",""+BldID);
                        startActivity(intent);
                        finish();
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent search = new Intent(android.content.Intent.ACTION_VIEW);
                        String strUrl = "http://www.google.com/search?q=" + ps.getPsuName();
                        search.setData(Uri.parse(strUrl));
                        startActivity(search);
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        send();
                    }
                });
            }
        }
        else if(info.equals("6")) {
            String s = getIntent().getStringExtra("id");
            cs = dbHelper.getAllCase(cek,s,false,"",false,"",false,"");
            for(final Case cse : cs) {
                Integer priceT = cse.getCasePrice();
                txt1.setText("Case Name : "+cse.getCaseName());
                txt2.setText("Case Size : "+cse.getCaseSize());
                txt3.setText("Expansion Slot : "+cse.getCaseExSlot().toString()+" slot");
                txt4.setText("Storage Place : "+cse.getCaseSto35().toString()+"x 3.5 HDD, "+cse.getCaseSto25().toString()+"x 2.5 SSD");
                txt5.setText("Price : Rp "+ myFormat.format(priceT));
                txt6.setText("");
                txt7.setText("");
                txt8.setText("");
                txt9.setText("");
                txt10.setText("");
                txt11.setText("");

                btnSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(detail.this, Scratch.class);
                        intent.putExtra("keyCs", cse.getCaseID().toString());
                        intent.putExtra("isCs",  true);
                        intent.putExtra("keyName",""+name);
                        intent.putExtra("keyBID",""+BldID);
                        startActivity(intent);
                        finish();
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent search = new Intent(android.content.Intent.ACTION_VIEW);
                        String strUrl = "http://www.google.com/search?q=" + cse.getCaseName();

                        search.setData(Uri.parse(strUrl));
                        startActivity(search);
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        send();
                    }
                });
            }
        }
        else {

        }

    }

    private void send(){
        Intent intent = new Intent(detail.this, choose.class);
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
        final   String ramCap = getIntent().getStringExtra("ramCap");

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
        intent.putExtra("ramCap",""+ramCap);
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

    public void sendPreview(String strpro, String strmb, String strram, String strvga
            , String strsto, String strpsu, String strcs, String name, String id, String price){
        Intent intent = new Intent(detail.this, preview.class);

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

}
