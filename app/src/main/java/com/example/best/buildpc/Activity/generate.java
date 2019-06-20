package com.example.best.buildpc.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.best.buildpc.DbHelper;
import com.example.best.buildpc.Factory.Case;
import com.example.best.buildpc.Factory.Motherboard;
import com.example.best.buildpc.Factory.PSU;
import com.example.best.buildpc.Factory.Processor;
import com.example.best.buildpc.Factory.RAM;
import com.example.best.buildpc.Factory.Storage;
import com.example.best.buildpc.Factory.VGA;
import com.example.best.buildpc.Factory.build;
import com.example.best.buildpc.NumTextWatcher;
import com.example.best.buildpc.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class generate extends AppCompatActivity {
    List<build> bld = new ArrayList<>();
    List<Processor> pro = new ArrayList<>();
    List<Motherboard> mb = new ArrayList<>();
    List<RAM> ram = new ArrayList<>();
    List<VGA> vga = new ArrayList<>();
    List<Storage> sto = new ArrayList<>();
    List<PSU> psu = new ArrayList<>();
    List<Case> cs = new ArrayList<>();

    List<Processor> proNew = new ArrayList<>();
    List<Motherboard> mbNew = new ArrayList<>();
    List<RAM> ramNew = new ArrayList<>();
    List<VGA> vgaNew = new ArrayList<>();
    List<Storage> stoNew = new ArrayList<>();

    DbHelper dbHelper;
    RadioButton rdOffice,rdEditing,rdGaming, rdIntel,rdAmd,rdAsus,rdMsi,rdGigabyte,rdAsrock, rdHDD,rdSSD, rdPurNotSure,rdProNotSure,rdStoNotSure,rdMbNotSure;
    RadioGroup rgPur,rgPro,rgSto,rgMb;
    EditText edBudget;
    Button btnGenerate;
    Double bgtPro,bgtMb,bgtRam,bgtVga,bgtSto,bgtPsu,bgtCase;
    String proFac,mbFac,stoTypeInput;
    Boolean isGene;
    Integer numData, numCol;
    Integer mbRamCap;
    Double[][] proWNDM, mbWNDM,ramWNDM,vgaWNDM,stoWNDM,psuWNDM,caseWNDM;
    Integer[] rank;
    Double remaBudget;

    String proID, proSocket, proCore,proClockSpd, proRamMax,proMaxClockSpd;
    Integer proTDPpsu, proPriceT;
    String mbID, mbRamVer, mbRamSpd, mbSlot, mbSizeCs, mbProSoc;
    Integer mbPriceT, mbTDPpsu, mbMaxCap;
    String ramID, ramMbVer;
    Integer  ramMbCap, ramTDPpsu, ramPriceT, ramMbSpd;
    String vgaID, vgaCoreOld, vgaCoreSpdOld, vgaMemOld, vgaMemSpdOld;
    Integer vgaTDP, vgaPriceT;
    String stoID, stoName;
    Integer stoPriceT,stoCap, stoTDPpsu;
    String psuID;
    Integer psuPower, psuPriceT;
    String csID,csSize;
    Integer csPriceT;

    Integer totPrice;
    Double bud;
    Integer needPower;
    Integer freeTDP;

    Boolean mbMax,ramMax,proMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        dbHelper = new DbHelper(getApplicationContext());
        dbHelper.createDataBase();

        rdOffice = findViewById(R.id.rdOffice);
        rdEditing = findViewById(R.id.rdEditing);
        rdGaming = findViewById(R.id.rdGaming);
        rdIntel = findViewById(R.id.rdIntel);
        rdAmd = findViewById(R.id.rdAmd);
        rdAsus = findViewById(R.id.rdAsus);
        rdMsi = findViewById(R.id.rdMsi);
        rdGigabyte = findViewById(R.id.rdGigabyte);
        rdAsrock = findViewById(R.id.rdAsrock);

        rdHDD = findViewById(R.id.rdHDD);
        rdSSD = findViewById(R.id.rdSSD);

        edBudget = findViewById(R.id.edBudget);
        rdPurNotSure = findViewById(R.id.rdPurNotSure);
        rdProNotSure = findViewById(R.id.rdProNotSure);
        rdMbNotSure = findViewById(R.id.rdMbNotSure);
        rdStoNotSure = findViewById(R.id.rdStoNotSure);

        rgPur = findViewById(R.id.rgPur);
        rgPro = findViewById(R.id.rgPro);
        rgSto = findViewById(R.id.rgSto);
        rgMb = findViewById(R.id.rgMb);

        edBudget.addTextChangedListener(new NumTextWatcher(edBudget));

        btnGenerate = findViewById(R.id.btnGenerate);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            String budget = edBudget.getText().toString();
            if (rgPur.getCheckedRadioButtonId() == -1 || rgPro.getCheckedRadioButtonId() == -1 || rgMb.getCheckedRadioButtonId() == -1 || rgSto.getCheckedRadioButtonId() == -1 || budget.equals("")){
                if (rgPur.getCheckedRadioButtonId() == -1){
                    Toast.makeText(generate.this,"Please select your PC Purpose",Toast.LENGTH_LONG).show();
                }
                else if (rgPro.getCheckedRadioButtonId() == -1){
                    Toast.makeText(generate.this,"Please select your Processor brand",Toast.LENGTH_LONG).show();
                }
                else if (rgMb.getCheckedRadioButtonId() == -1){
                    Toast.makeText(generate.this,"Please select your Motherboard brand",Toast.LENGTH_LONG).show();
                }
                else if (rgSto.getCheckedRadioButtonId() == -1){
                    Toast.makeText(generate.this,"Please select your Storage Form",Toast.LENGTH_LONG).show();
                }
                else if (budget.equals("")){
                    Toast.makeText(generate.this,"Please fill your Budget",Toast.LENGTH_LONG).show();
                }
            }
            else{
                bud =Double.parseDouble(edBudget.getText().toString().replace(",",""));

                //check purpose radio button checked by user
                if (rdOffice.isChecked() || rdPurNotSure.isChecked()){
                    if (bud >= 15000000){
                       bgtPro = bud * 25.0/100; // budget Processor
                       bgtMb = bud * 20.0/100; // budget Motherboard
                       bgtRam = bud * 15.0/100; // budget RAM
                       bgtVga = bud * 15.0/100; // budget VGA
                       bgtSto = bud * 15.0/100; // budget Storage
                       bgtPsu = bud * 5.0/100; // budget Power suppy
                       bgtCase = bud * 5.0/100; // budget Case
                   }else {
                       bgtPro = bud * 20.0 / 100;
                       bgtMb = bud * 20.0 / 100;
                       bgtRam = bud * 15.0 / 100;
                       bgtVga = bud * 15.0 / 100;
                       bgtSto = bud * 10.0 / 100;
                       bgtPsu = bud * 10.0 / 100;
                       bgtCase = bud * 10.0 / 100;
                   }
                }
                else if (rdEditing.isChecked()){
                    if (bud >= 15000000){
                        bgtPro= bud * 30.0/100;
                        bgtMb= bud * 10.0/100;
                        bgtRam= bud * 20.0/100;
                        bgtVga= bud * 20.0/100;
                        bgtSto= bud * 10.0/100;
                        bgtPsu= bud * 5.0/100;
                        bgtCase= bud * 5.0/100;
                    }else {
                        bgtPro = bud * 30.0 / 100;
                        bgtMb = bud * 15.0 / 100;
                        bgtRam = bud * 12.0 / 100;
                        bgtVga = bud * 13.0 / 100;
                        bgtSto = bud * 10.0 / 100;
                        bgtPsu = bud * 10.0 / 100;
                        bgtCase = bud * 10.0 / 100;
                    }
                }
                else if (rdGaming.isChecked()){
                    if (bud >= 15000000){
                        bgtPro = bud * 28.0/100;
                        bgtMb = bud * 15.0/100;
                        bgtRam = bud * 13.0/100;
                        bgtVga = bud * 26.0/100;
                        bgtSto = bud * 8.0/100;
                        bgtPsu = bud * 5.0/100;
                        bgtCase = bud * 5.0/100;
                    }else{
                        bgtPro = bud * 25.0/100;
                        bgtMb = bud * 12.5/100;
                        bgtRam = bud * 12.5/100;
                        bgtVga = bud * 26.0/100;
                        bgtSto = bud * 8.0/100;
                        bgtPsu = bud * 8.0/100;
                        bgtCase = bud * 8.0/100;
                    }
                }

                //check Processor brand radio button checked by user
                if (rdIntel.isChecked()){
                    proFac="1";
                    isGene=true;
                }else if (rdAmd.isChecked()){
                    proFac="2";
                    isGene=true;
                }else if (rdProNotSure.isChecked()){
                    proFac="0";
                    isGene=true;
                }

            // -------Generating Processor--------------------------------------//
                //filter Processor
                pro =  dbHelper.getAllPro(false, "",false,"",false,""
                        ,isGene,proFac,bgtPro.toString(),false,false,"");
                        //qry = "select * from Processor where proID like '"+proCode+"%' and proPrice <= "+proBudget;
                //handle null
                if(pro.size()==0){
                    Toast.makeText(generate.this,"Sorry We cant generate your PC. try adding more budget.",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    //Running TOPSIS for processor
                    //Creating Decision Matrix
                    proWNDM = getPro(pro);
                    //Get rank based on TOPSIS Result
                    rank=getRank(proWNDM,pro.size(),9);
                    //Getting Chosen processor data
                    proID = pro.get(rank[0]).getProID().toString(); // processor ID
                    proSocket = pro.get(rank[0]).getProSocket(); // processor Socket
                    proCore =  pro.get(rank[0]).getProCore().toString();
                    proClockSpd = pro.get(rank[0]).getProClockSpd().toString();
                    proMaxClockSpd = pro.get(rank[0]).getProClockMaxSpd().toString();
                    proRamMax =  pro.get(rank[0]).getProRamMaxSpd().toString();
                    proTDPpsu = pro.get(rank[0]).getProTdp(); // Processor power
                    proPriceT = pro.get(rank[0]).getProPrice(); // processor price

                    remaBudget = bgtPro - proPriceT; // Remaining budget of processor

                }

            //----- End of generate Processor--------------------------------------//
            //----- Generating Motherboard ----------------------------------------//
                if (rdAsus.isChecked()){
                    mbFac="Asus";
                }else if (rdMsi.isChecked()){
                    mbFac="MSI";
                }else if (rdGigabyte.isChecked()){
                    mbFac="Gigabyte";
                }else if (rdAsrock.isChecked()){
                    mbFac="ASRock";
                }else if (rdMbNotSure.isChecked()){
                    mbFac="0";
                }
                bgtMb = bgtMb + remaBudget;
                //filter Motherboard
                mb = dbHelper.getAllMB(false,"",false, proSocket,false,"",
                        "",false,"",false,"",true,bgtMb.toString(),false,"",mbFac);

                //handle null
                if(mb.size()==0){
                    Toast.makeText(generate.this,"Sorry We cant generate your PC. try adding more budget.",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    mbWNDM = getMb(mb);
                    rank=getRank(mbWNDM,mb.size(),5);

                    mbID = mb.get(rank[0]).getMbID().toString();
                    mbRamVer = mb.get(rank[0]).getMbRamVer();
                    mbRamSpd = mb.get(rank[0]).getMbRamMaxSpd().toString();
                    mbSlot = mb.get(rank[0]).getMbSlot();
                    mbSizeCs = mb.get(rank[0]).getMbSize();
                    mbProSoc = mb.get(rank[0]).getMbProSocket();
                    mbPriceT = mb.get(rank[0]).getMbPrice();
                    mbMaxCap = mb.get(rank[0]).getMbRamMaxCap();
                    mbTDPpsu = 0;
                    if(mbSizeCs.equals("E-ATX")){
                        mbTDPpsu = 55;
                    }else if (mbSizeCs.equals("ATX")){
                        mbTDPpsu =40;
                    }else if (mbSizeCs.equals("Micro-Atx")){
                        mbTDPpsu =30;
                    }else if (mbSizeCs.equals("Mini-ITX")){
                        mbTDPpsu = 15;
                    }
                    remaBudget = bgtMb - mbPriceT;

                }
            //----- End of generate Motherboard --------------------------------------//
            //----- Generating RAM --------------------------------------------------//
                bgtRam = bgtRam + remaBudget;
                //filter RAM
                ram = dbHelper.getAllRAM(false,"",false,mbRamVer,mbRamSpd
                        ,false,"",false,"","",true,bgtRam.toString(),mbMaxCap.toString());

                if(ram.size()==0){
                    Toast.makeText(generate.this,"Sorry We cant generate your PC. try adding more budget.",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    ramWNDM = getRam(ram);
                    rank=getRank(ramWNDM,ram.size(),3);

                    ramID = ram.get(rank[0]).getRamID().toString();
                    ramMbCap = ram.get(rank[0]).getRamCap();
                    ramMbSpd = ram.get(rank[0]).getRamSpd();
                    ramMbVer = ram.get(rank[0]).getRamVer();
                    ramPriceT = ram.get(rank[0]).getRamPrice();
                    ramTDPpsu =5;
                    remaBudget = bgtRam - ramPriceT;
                }
            // ----- End of generate RAM --------------------------------------//
            //----- Generating VGA --------------------------------------------------//
                bgtVga = bgtVga + remaBudget;
                //filter VGA
                vga = dbHelper.getAllVGA(false,"",false,"",true,bgtVga.toString());

                if(vga.size()==0){
                    Toast.makeText(generate.this,"Sorry We cant generate your PC. try adding more budget.",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    vgaWNDM = getVga(vga);
                    rank=getRank(vgaWNDM,vga.size(),8);

                    vgaID = vga.get(rank[0]).getVgaID().toString();
                    vgaTDP = vga.get(rank[0]).getVgaTdp();
                    vgaPriceT = vga.get(rank[0]).getVgaPrice();
                    vgaCoreOld = vga.get(0).getVgaCoreUnit().toString();
                    vgaCoreSpdOld = vga.get(0).getVgaCoreSpd().toString();
                    vgaMemOld = vga.get(0).getVgaMemCap().toString();
                    vgaMemSpdOld = vga.get(0).getVgaMemSpd().toString();

                    remaBudget = bgtVga - vgaPriceT;
                }
            //----- End of generate VGA --------------------------------------//
            //----- Generating Storage --------------------------------------------------//
                // get user input for Sto
                if (rdHDD.isChecked()){
                    stoTypeInput="HDD";
                }else if (rdSSD.isChecked()){
                    stoTypeInput="SSD";
                }else if (rdStoNotSure.isChecked()){
                    stoTypeInput="NOT";
                }
                //filter Sto
                sto = dbHelper.getAllSto(false,"",false,"",false,mbSlot,true,bgtSto.toString(),stoTypeInput,false,"");

                if(sto.size()==0){
                    Toast.makeText(generate.this,"Sorry We cant generate your PC. try adding more budget.",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    stoWNDM = getSto(sto);
                    rank=getRank(stoWNDM,sto.size(),5);

                    stoID = sto.get(rank[0]).getStoID().toString();
                    stoName = sto.get(rank[0]).getStoName();
                    stoPriceT = sto.get(rank[0]).getStoPrice();
                    stoCap = sto.get(rank[0]).getStoCap();
                    stoTDPpsu = 0 ;
                    if(stoName.contains("HDD")){
                        stoTDPpsu = 9;
                    }else if (stoName.contains("SSHD")){
                        stoTDPpsu =6;
                    }else if (stoName.contains("SSD")){
                        stoTDPpsu =3;
                    }

                    //remaBudget = bgtSto - stoPriceT;
                }
            //----- End of generate Storage -----------------------------------------//
            //----- Generating PSU --------------------------------------------------//
                needPower = proTDPpsu + vgaTDP + stoTDPpsu + mbTDPpsu + ramTDPpsu;
                //ceker.setText(""+needPower.toString());
                //filter PSU
                psu = dbHelper.getAllPSU(false,"",needPower.toString(),false,"",true,bgtPsu.toString());
                if(psu.size()==0){
                    Toast.makeText(generate.this,"Sorry We cant generate your PC. try adding more budget.",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    psuWNDM = getPsu(psu);

                    rank=getRank(psuWNDM,psu.size(),4);
                    psuID = psu.get(rank[0]).getPsuID().toString();
                    psuPower = psu.get(rank[0]).getPsuVolt();
                    psuPriceT = psu.get(rank[0]).getPsuPrice();
                    //remaBudget = bgtPsu - psuPriceT;
                }
            //----- End of generate PSU --------------------------------------//
            //----- Generating Casing --------------------------------------------------//
                //filter Casing
                cs = dbHelper.getAllCase(false,"",false,mbSizeCs,false,"",true,bgtCase.toString());
                if(cs.size()==0){
                    Toast.makeText(generate.this,"Sorry We cant generate your PC. try adding more budget.",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    caseWNDM = getCase(cs);
                    rank=getRank(caseWNDM,cs.size(),5);
                    csID = cs.get(rank[0]).getCaseID().toString();
                    csSize = cs.get(rank[0]).getCaseSize();
                    csPriceT =cs.get(rank[0]).getCasePrice();
                }
                //----- End of generate Casing --------------------------------------//

                remaBudget = remaBudget + (bgtPsu - psuPriceT) + (bgtSto - stoPriceT) + (bgtCase - csPriceT);
                //remaBudget = bud - proPriceT - mbPriceT - ramPriceT - vgaPriceT - stoPriceT - psuPriceT - csPriceT;

//==== R E M A I N D E R BUDGET ALLOCATION ==================================================================
                int num =0;
                if (rdOffice.isChecked() || rdPurNotSure.isChecked()){
                    while (num<4){
                        bgtPro = proPriceT + remaBudget;
                        extraPro();
                        bgtMb = mbPriceT + remaBudget;
                        extraMb();
                        bgtRam=ramPriceT + remaBudget;
                        extraRam();
                        bgtVga = vgaPriceT + remaBudget;
                        extraVga();
                        checkCompaPsuCaseSto();
                        num++;
                    }
                }
                else if (rdEditing.isChecked()){
                    while (num<4){
                        bgtPro = proPriceT + remaBudget;
                        extraPro();
                        bgtRam=ramPriceT + remaBudget;
                        extraRam();
                        bgtVga = vgaPriceT + remaBudget;
                        extraVga();
                        bgtMb = mbPriceT + remaBudget;
                        extraMb();
                        checkCompaPsuCaseSto();
                        num++;
                    }
                }
                else if (rdGaming.isChecked()){
                    while (num<4){
                        bgtVga = vgaPriceT + remaBudget;
                        extraVga();
                        bgtRam=ramPriceT + remaBudget;
                        extraRam();
                        bgtPro = proPriceT + remaBudget;
                        extraPro();
                        bgtMb = mbPriceT + remaBudget;
                        extraMb();
                        checkCompaPsuCaseSto();
                        num++;
                    }
                }
//==== END R E M A I N D E R BUDGET ALLOCATION ==================================================================

                totPrice = proPriceT + mbPriceT + ramPriceT + vgaPriceT + stoPriceT + psuPriceT + csPriceT;
        //=====-- Intent value for Preview -----------------------------------------------------=================================//
                Intent intent = new Intent(generate.this, preview.class);
                intent.putExtra("info", "0");
                intent.putExtra("keyPro","" + proID );
                intent.putExtra("keyMb", "" + mbID);
                intent.putExtra("keyRam", "" + ramID);
                intent.putExtra("keyVga", "" + vgaID);
                intent.putExtra("keySto", "" + stoID);
                intent.putExtra("keyPsu", "" + psuID);
                intent.putExtra("keyCs", "" + csID);

                bld =  dbHelper.getAllBuild(false,false,"");
                Integer nume = 1;
                String name = "Generated Build "+ nume.toString();
                String id = "2"+nume;
                for(int x=0;x<bld.size();x++){
                    if (name.equals(bld.get(x).getBuildName())){
                       nume++;
                        name = "Generated Build "+ nume.toString();
                        id = "2"+nume;
                        x=0;
                    }
                }

                intent.putExtra("keyName",""+name);
                intent.putExtra("keyBID",""+id);
                intent.putExtra("prc",""+totPrice.toString());

                startActivity(intent);
                finish();
            } //-- END ELSE
            }
        }); //-- END CLICK FUNCTION
    }

///========//============//=============

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
            mbWNDM [counter][0] = mb.get(counter).getMbRamSlot() / mbRamSlot * mbWeight;
            mbWNDM [counter][1] = mb.get(counter).getMbRamMaxCap() / mbRamMaxCap * mbWeight;
            mbWNDM [counter][2] = mb.get(counter).getMbRamMaxSpd() / mbRamMaxSpd *mbWeight;
            if (mb.get(counter).getMbSize().equals("Mini-ITX")){
                sizeInNum = 1;
            } else if (mb.get(counter).getMbSize().equals("Micro-ATX")){
                sizeInNum = 3;
            } else if (mb.get(counter).getMbSize().equals("ATX")){
                sizeInNum = 5;
            } else if (mb.get(counter).getMbSize().equals("E-ATX")){
                sizeInNum = 7;
            }
            mbWNDM [counter][3] = sizeInNum / mbSize * mbWeight;
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
            } else if (vga.get(counter).getVgaMemType().equals("DDR5") ){
                typeInNum = 3;
            } else if (vga.get(counter).getVgaMemType().equals("DDR5X")|| vga.get(counter).getVgaMemType().equals("HBM2")){
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

            vgaWNDM [counter][0] = vga.get(counter).getVgaCoreUnit() / vgaCoreUnit * 0.1875;
            vgaWNDM [counter][1] = vga.get(counter).getVgaCoreSpd() / vgaCoreSpd * 0.1875;
            vgaWNDM [counter][2] = vga.get(counter).getVgaCoreMaxSpd() / vgaCoreMaxSpd * 0.0766;
            vgaWNDM [counter][3] = vga.get(counter).getVgaMemSpd() / vgaSpd * 0.1875;
            vgaWNDM [counter][4] = vga.get(counter).getVgaMemCap() / vgaCap * 0.1875;
            if (vga.get(counter).getVgaMemType().equals("DDR3")){
                typeInNum = 1;
            } else if (vga.get(counter).getVgaMemType().equals("DDR4")){
                typeInNum = 2;
            } else if (vga.get(counter).getVgaMemType().equals("DDR5") || vga.get(counter).getVgaMemType().equals("HBM2")){
                typeInNum = 3;
            } else if (vga.get(counter).getVgaMemType().equals("DDR6")){
                typeInNum = 4;
            }
            vgaWNDM [counter][5] = typeInNum / vgaType * 0.0766;
            vgaWNDM [counter][6] = vga.get(counter).getVgaTdp() / vgaTdp * 0.0766;
            vgaWNDM [counter][7] = vga.get(counter).getVgaPrice() / vgaPrice * 0.02;
        }

        return  vgaWNDM;
    }

    public Double[][] getSto(List<Storage> sto){
        Double stoInterface = 0.0;
        Double stoCap = 0.0;
        Double stoType = 0.0;
        Double stoSize = 0.0;
        Double stoPrice = 0.0;

        numData = sto.size();
        numCol= 5;
        Integer interfaceInNum = 0;
        Integer typeInNum = 0;
        Integer sizeInNum = 0;

        for (Integer counter = 0; counter < numData; counter++) {

            if (sto.get(counter).getStoInterface().equals("SATA")){
                interfaceInNum = 1;
            } else if (sto.get(counter).getStoInterface().equals("SATA2")){
                interfaceInNum = 2;
            } else if (sto.get(counter).getStoInterface().equals("SATA3")){
                interfaceInNum = 3;
            } else if (sto.get(counter).getStoInterface().equals("PCIe Gen 3.0 x4")){
                interfaceInNum = 4;
            }

            if (sto.get(counter).getStoName().contains("HDD")){
                typeInNum = 1;
            } else if (sto.get(counter).getStoName().contains("SSHD")){
                typeInNum = 5;
            } else if (sto.get(counter).getStoName().contains("SSD")){
                typeInNum = 10;
            }

            if (sto.get(counter).getStoSize().equals("3.5")){
                sizeInNum = 1;
            } else if (sto.get(counter).getStoSize().equals("2.5")){
                sizeInNum = 3;
            } else if (sto.get(counter).getStoSize().equals("M.2")){
                sizeInNum = 5;
            }

            stoInterface = stoInterface + Math.pow(interfaceInNum,2);
            stoCap = stoCap + Math.pow(sto.get(counter).getStoCap(),2);
            stoType = stoType + Math.pow(typeInNum,2);
            stoSize = stoSize + Math.pow(sizeInNum,2);
            stoPrice = stoPrice + Math.pow(sto.get(counter).getStoPrice(),2);
        }

        stoInterface = Math.sqrt(stoInterface);
        stoCap = Math.sqrt(stoCap);
        stoType = Math.sqrt(stoType);
        stoSize = Math.sqrt(stoSize);
        stoPrice = Math.sqrt(stoPrice);


        Double stoWeight = 0.98 / 4.0;

        //calculate stoWNDM
        stoWNDM = new Double[numData][numCol];
        for (Integer counter = 0; counter <numData; counter++) {
            if (sto.get(counter).getStoInterface().equals("SATA")){
                interfaceInNum = 1;
            } else if (sto.get(counter).getStoInterface().equals("SATA2")){
                interfaceInNum = 2;
            } else if (sto.get(counter).getStoInterface().equals("SATA3")){
                interfaceInNum = 3;
            } else if (sto.get(counter).getStoInterface().equals("PCIe Gen 3.0 x4")){
                interfaceInNum = 4;
            }

            if (sto.get(counter).getStoName().contains("HDD")){
                typeInNum = 1;
            } else if (sto.get(counter).getStoName().contains("SSHD")){
                typeInNum = 5;
            } else if (sto.get(counter).getStoName().contains("SSD")){
                typeInNum = 10;
            }

            if (sto.get(counter).getStoSize().equals("3.5")){
                sizeInNum = 1;
            } else if (sto.get(counter).getStoSize().equals("2.5")){
                sizeInNum = 3;
            } else if (sto.get(counter).getStoSize().equals("M.2")){
                sizeInNum = 5;
            }

            stoWNDM [counter][0] = interfaceInNum / stoInterface * stoWeight;
            stoWNDM [counter][1] = sto.get(counter).getStoCap() / stoCap * stoWeight;
            stoWNDM [counter][2] = typeInNum / stoType * stoWeight;
            stoWNDM [counter][3] = sizeInNum / stoSize * stoWeight;
            stoWNDM [counter][4] = sto.get(counter).getStoPrice() / stoPrice * 0.02;
        }
        return stoWNDM;
    }

    public Double[][] getPsu(List<PSU> psu){
        Double psuType = 0.0;
        Double psuRank = 0.0;
        Double psuVolt = 0.0;
        Double psuPrice = 0.0;

        numData = psu.size();
        numCol= 4;
        Integer typeInNum = 0;
        Integer rankInNum = 0;

        for (Integer counter = 0; counter < numData; counter++) {

            if (psu.get(counter).getPsuType().equals("Non-Modular")){
                typeInNum = 1;
            } else if (psu.get(counter).getPsuType().equals("Semi-Modular")){
                typeInNum = 2;
            } else if (psu.get(counter).getPsuType().equals("Modular")){
                typeInNum = 3;
            }

            if (psu.get(counter).getPsuRank().equals("80+")){
                rankInNum = 1;
            } else if (psu.get(counter).getPsuRank().equals("Bronze")){
                rankInNum = 2;
            } else if (psu.get(counter).getPsuRank().equals("Gold")){
                rankInNum = 3;
            } else if (psu.get(counter).getPsuRank().equals("Platinum")){
                rankInNum = 4;
            }
            psuType = psuType + Math.pow(typeInNum,2);
            psuRank = psuRank + Math.pow(rankInNum,2);
            psuVolt = psuVolt + Math.pow(psu.get(counter).getPsuVolt(),2);
            psuPrice = psuPrice + Math.pow(psu.get(counter).getPsuPrice(),2);

        }

        psuType = Math.sqrt(psuType);
        psuRank = Math.sqrt(psuRank);
        psuVolt = Math.sqrt(psuVolt);
        psuPrice = Math.sqrt(psuPrice);

        Double psuWeight = 0.9 / 3.0;

        //calculate mbWNDM
        psuWNDM = new Double[numData][numCol];
        for (Integer counter = 0; counter <numData; counter++) {
            if (psu.get(counter).getPsuType().equals("Non-Modular")){
                typeInNum = 1;
            } else if (psu.get(counter).getPsuType().equals("Semi-Modular")){
                typeInNum = 2;
            } else if (psu.get(counter).getPsuType().equals("Modular")){
                typeInNum = 3;
            }

            if (psu.get(counter).getPsuRank().equals("80+")){
                rankInNum = 1;
            } else if (psu.get(counter).getPsuRank().equals("Bronze")){
                rankInNum = 2;
            } else if (psu.get(counter).getPsuRank().equals("Gold")){
                rankInNum = 3;
            } else if (psu.get(counter).getPsuRank().equals("Platinum")){
                rankInNum = 4;
            }

            psuWNDM [counter][0] = typeInNum / psuType * psuWeight;
            psuWNDM [counter][1] = rankInNum / psuRank * psuWeight;
            psuWNDM [counter][2] = psu.get(counter).getPsuVolt() / psuVolt * psuWeight;
            psuWNDM [counter][3] = psu.get(counter).getPsuPrice() / psuPrice * 0.1;
        }
        return psuWNDM;
    }

    public Double[][] getCase(List<Case> cs){
        Double csExSlot = 0.0;
        Double csDrive = 0.0;
        Double cs35 = 0.0;
        Double cs25 = 0.0;
        Double csPrice = 0.0;

        numData = cs.size();
        numCol= 5;

        for (Integer counter = 0; counter < numData; counter++) {
            csExSlot = csExSlot + Math.pow(cs.get(counter).getCaseExSlot(),2);
            csDrive = csDrive + Math.pow(cs.get(counter).getCaseDrive(),2);
            cs35 = cs35 + Math.pow(cs.get(counter).getCaseSto35(),2);
            cs25 = cs25 + Math.pow(cs.get(counter).getCaseSto25(),2);
            csPrice = csPrice + Math.pow(cs.get(counter).getCasePrice(),2);
        }
        csExSlot = Math.sqrt(csExSlot);
        csDrive = Math.sqrt(csDrive);
        cs35 = Math.sqrt(cs35);
        cs25 = Math.sqrt(cs25);
        csPrice = Math.sqrt(csPrice);


        Double csWeight = 1 / 5.0;

        //calculate caseWNDM
        caseWNDM = new Double[numData][numCol];
        for (Integer counter = 0; counter <numData; counter++) {
            caseWNDM [counter][0] = cs.get(counter).getCaseExSlot() / csExSlot * csWeight;
            caseWNDM [counter][1] = cs.get(counter).getCaseDrive() / csDrive * csWeight;
            caseWNDM [counter][2] = cs.get(counter).getCaseSto35() / cs35 * csWeight;
            caseWNDM [counter][3] = cs.get(counter).getCaseSto25() / cs25 * csWeight;
            caseWNDM [counter][4] = cs.get(counter).getCaseSto25() / csPrice * csWeight;
        }

        return caseWNDM;
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
            //S+ and S-
            sPlus [counter] = Math.sqrt(sPlus[counter]);
            sMinus [counter] = Math.sqrt(sMinus[counter]);
            // calculate performance score for each component
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

//============================
    public void extraPro(){
        if (proFac.equals("0")) { // core>   coreSpd>
            proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                    + "' and proSocket = '" + mbProSoc
                    + "' and proCore>'" + proCore + "' and proClockSpd >'" + proClockSpd + "' and proPrice <=" + bgtPro);
        } else {
            proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                    + "' and proSocket = '" + mbProSoc + "' and proID like'" + proFac + "%' and proCore>'" + proCore
                    + "' and proClockSpd >'" + proClockSpd + "' and proPrice <=" + bgtPro);
        }
        if(proNew.size()==0){
            if (proFac.equals("0")) { // core>   coreSpd>=
                proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                        + "' and proSocket = '" + mbProSoc
                        + "' and proCore>'" + proCore + "' and proClockSpd >='" + proClockSpd + "' and proPrice <=" + bgtPro);
            } else {
                proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                        + "' and proSocket = '" + mbProSoc + "' and proID like'" + proFac + "%' and proCore>'" + proCore
                        + "' and proClockSpd >='" + proClockSpd + "' and proPrice <=" + bgtPro);
            }
            if(proNew.size()==0){
                if (proFac.equals("0")) { // core>=   coreSpd>
                    proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                            + "' and proSocket = '" + mbProSoc
                            + "' and proCore>='" + proCore + "' and proClockSpd >'" + proClockSpd + "' and proPrice <=" + bgtPro);
                } else {
                    proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                            + "' and proSocket = '" + mbProSoc + "' and proID like'" + proFac + "%' and proCore>='" + proCore
                            + "' and proClockSpd >'" + proClockSpd + "' and proPrice <=" + bgtPro);
                }
                if(proNew.size()==0){
                    if (proFac.equals("0")) { // core>=   MAXcoreSpd>
                        proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                                + "' and proSocket = '" + mbProSoc
                                + "' and proCore>='" + proCore + "' and proClockMaxSpd >'" + proMaxClockSpd + "' and proPrice <=" + bgtPro);
                    } else {
                        proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                                + "' and proSocket = '" + mbProSoc + "' and proID like'" + proFac + "%' and proCore>='" + proCore
                                + "' and proClockMaxSpd >'" + proMaxClockSpd + "' and proPrice <=" + bgtPro);
                    }
                    if(proNew.size()==0){
                        if (proFac.equals("0")) { // NO socket core> Spd>
                            proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                                    + "' and proCore>'" + proCore + "' and proClockSpd >'" + proClockSpd + "' and proPrice <=" + bgtPro);
                        } else {
                            proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID + "' and proID like'" + proFac
                                    + "%' and proCore>'" + proCore + "' and proClockSpd >'" + proClockSpd + "' and proPrice <=" + bgtPro);
                        }
                        if (proNew.size() == 0) {
                            if (proFac.equals("0")) { // NO socket core> Spd>=
                                proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                                        + "' and proCore>'" + proCore + "' and proClockSpd >='" + proClockSpd + "' and proPrice <=" + bgtPro);
                            } else {
                                proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID + "' and proID like'" + proFac
                                        + "%' and proCore>'" + proCore + "' and proClockSpd >='" + proClockSpd + "' and proPrice <=" + bgtPro);
                            }
                            if (proNew.size() == 0) {
                                if (proFac.equals("0")) { // NO socket core>= Spd>
                                    proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                                            + "' and proCore>='" + proCore + "' and proClockSpd >'" + proClockSpd + "' and proPrice <=" + bgtPro);
                                } else {
                                    proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID + "' and proID like'" + proFac
                                            + "%' and proCore>='" + proCore + "' and proClockSpd >'" + proClockSpd + "' and proPrice <=" + bgtPro);
                                }
                                if (proNew.size() == 0) {
                                    if (proFac.equals("0")) { // NO socket core>= MAXSpd>
                                        proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID
                                                + "' and proCore>='" + proCore + "' and proClockMaxSpd >'" + proMaxClockSpd + "' and proPrice <=" + bgtPro);
                                    } else {
                                        proNew = dbHelper.gettAllCPU("Select * from Processor where proID not like '" + proID + "' and proID like'" + proFac
                                                + "%' and proCore>='" + proCore + "' and proClockMaxSpd >'" + proMaxClockSpd + "' and proPrice <=" + bgtPro);
                                    }
                                    if (proNew.size() == 0) {
                                        // do nothing
                                    }else {
                                        proWNDM = getPro(proNew);
                                        Integer[] rank = getRank(proWNDM, proNew.size(), 9);
                                        if (proNew.get(rank[0]).getProSocket().equals(proSocket)) {
                                            setNewPro();
                                            remaBudget = bgtPro - proPriceT; // Remaining budget of processor
                                        }else {
                                            bgtMb = mbPriceT + (bgtPro-proNew.get(rank[0]).getProPrice());
                                            mbNew = dbHelper.getAllMB(false, mbID, false, proNew.get(rank[0]).getProSocket(), false
                                                    , "", mbRamSpd, false, "", false, ""
                                                    , false, bgtMb.toString(), true, mbMaxCap.toString(),mbFac);

                                            if (mbNew.size() == 0) {      //handle null
                                            } else {
                                                setNewMb();
                                                //when new MB exist then save the new PRO
                                                setNewPro();
                                            }
                                        }
                                    }
                                }else{
                                    proWNDM = getPro(proNew);
                                    Integer[] rank = getRank(proWNDM, proNew.size(), 9);
                                    if (proNew.get(rank[0]).getProSocket().equals(proSocket)) {
                                        setNewPro();
                                        remaBudget = bgtPro - proPriceT; // Remaining budget of processor
                                    }else {
                                        bgtMb = mbPriceT + (bgtPro-proNew.get(rank[0]).getProPrice());
                                        mbNew = dbHelper.getAllMB(false, mbID, false, proNew.get(rank[0]).getProSocket(), false
                                                , "", mbRamSpd, false, "", false, ""
                                                , false, bgtMb.toString(), true, mbMaxCap.toString(),mbFac);

                                        if (mbNew.size() == 0) {      //handle null
                                        } else {
                                            setNewMb();
                                            //when new MB exist then save the new PRO
                                            setNewPro();
                                        }
                                    }
                                }
                            }else {
                                proWNDM = getPro(proNew);
                                Integer[] rank = getRank(proWNDM, proNew.size(), 9);
                                if (proNew.get(rank[0]).getProSocket().equals(proSocket)) {
                                    setNewPro();
                                    remaBudget = bgtPro - proPriceT; // Remaining budget of processor
                                }else {
                                    bgtMb = mbPriceT + (bgtPro-proNew.get(rank[0]).getProPrice());
                                    mbNew = dbHelper.getAllMB(false, mbID, false, proNew.get(rank[0]).getProSocket(), false
                                            , "", mbRamSpd, false, "", false, ""
                                            , false, bgtMb.toString(), true, mbMaxCap.toString(),mbFac);

                                    if (mbNew.size() == 0) {      //handle null
                                    } else {
                                        setNewMb();
                                        //when new MB exist then save the new PRO
                                        setNewPro();
                                    }
                                }
                            }
                        }else{
                            proWNDM = getPro(proNew);
                            Integer[] rank = getRank(proWNDM, proNew.size(), 9);
                            if (proNew.get(rank[0]).getProSocket().equals(proSocket)) {
                                setNewPro();
                                remaBudget = bgtPro - proPriceT; // Remaining budget of processor
                            }else {
                                bgtMb = mbPriceT + (bgtPro-proNew.get(rank[0]).getProPrice());
                                mbNew = dbHelper.getAllMB(false, mbID, false, proNew.get(rank[0]).getProSocket(), false
                                        , "", mbRamSpd, false, "", false, ""
                                        , false, bgtMb.toString(), true, mbMaxCap.toString(),mbFac);

                                if (mbNew.size() == 0) {      //handle null
                                } else {
                                    setNewMb();
                                    //when new MB exist then save the new PRO
                                    setNewPro();
                                }
                            }
                        }
                    }else{
                        setNewPro();
                        remaBudget = bgtPro - proPriceT; // Remaining budget of processor
                    }
                }else {
                    setNewPro();
                    remaBudget = bgtPro - proPriceT; // Remaining budget of processor
                }
            }else{
                setNewPro();
                remaBudget = bgtPro - proPriceT; // Remaining budget of processor
            }
        }else {
            setNewPro();
            remaBudget = bgtPro - proPriceT; // Remaining budget of processor
        }

        //----- End of generate new Processor--------------------------------------//
    }

    public void extraMb(){
        //filter Motherboard

        // maxCap> maxSpd>
        mbNew = dbHelper.getAllMB(false, mbID, false, proSocket, false
                , "", mbRamSpd, false, "", false, ""
                , false, bgtMb.toString(), true, mbMaxCap.toString(),mbFac);

        if (mbNew.size() == 0) {      //handle null
            // maxCap> maxSpd>
            mbNew = dbHelper.getAllMB(false, mbID, false, proSocket, false
                    , "1", mbRamSpd, false, "", false, ""
                    , false, bgtMb.toString(), true, mbMaxCap.toString(),mbFac);
            if (mbNew.size() == 0) {
                mbNew = dbHelper.getAllMB(false, mbID, false, proSocket, false
                        , "2", mbRamSpd, false, "", false, ""
                        , false, bgtMb.toString(), true, mbMaxCap.toString(),mbFac);
                if (mbNew.size() == 0) {
                    // do nothing // no better option
                }else {
                    setNewMb();
                }
            }else{
                setNewMb();
            }
        } else {
            setNewMb();
        }
    }

    public void extraRam(){
        // cap> spd>
        ramNew = dbHelper.getAllRAM(false, ramID, false, mbRamVer, mbRamSpd
                , false, "1", false, ramMbSpd.toString(), ramMbCap.toString(), true,bgtRam.toString(), mbMaxCap.toString());

        //handle null
        if (ramNew.size()==0){
            // cap> spd>=
            ramNew = dbHelper.getAllRAM(false, ramID, false, mbRamVer, mbRamSpd
                    , false, "2", false, ramMbSpd.toString(), ramMbCap.toString(), true,bgtRam.toString(), mbMaxCap.toString());
            if (ramNew.size()==0){
                // cap>= spd>
                ramNew = dbHelper.getAllRAM(false, ramID, false, mbRamVer, mbRamSpd
                        , false, "3", false, ramMbSpd.toString(), ramMbCap.toString(), true,bgtRam.toString(), mbMaxCap.toString());
                if (ramNew.size()==0){
                    //do Nothing // no better part
                }else {
                    setNewRam();
                }
            }else {
                setNewRam();
            }
        }else {
            setNewRam();
        }
    }

    public void extraVga(){
        // coreUnit> coreSpd> MemCap> memSpd>
        vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >'"+vgaCoreSpdOld+"' and vgaCoreUnit >'"+vgaCoreOld
                +"' and vgaMemCap >'"+vgaMemOld+"' and vgaMemSpd >'"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);

        if (vgaNew.size()==0){     //handle null
            //coreUnit> coreSpd> MemCap> memSpd>=
            vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >'"+vgaCoreSpdOld+"' and vgaCoreUnit >'"+vgaCoreOld
                    +"' and vgaMemCap >'"+vgaMemOld+"' and vgaMemSpd >='"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
            if (vgaNew.size()==0){
                //coreUnit> coreSpd> MemCap>= memSpd>
                vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >'"+vgaCoreSpdOld+"' and vgaCoreUnit >'"+vgaCoreOld
                        +"' and vgaMemCap >='"+vgaMemOld+"' and vgaMemSpd >'"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                if (vgaNew.size()==0){
                    //coreUnit> coreSpd>= MemCap> memSpd>
                    vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >='"+vgaCoreSpdOld+"' and vgaCoreUnit >'"+vgaCoreOld
                            +"' and vgaMemCap >'"+vgaMemOld+"' and vgaMemSpd >'"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                    if (vgaNew.size()==0){
                        //coreUnit>= coreSpd> MemCap> memSpd>
                        vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >'"+vgaCoreSpdOld+"' and vgaCoreUnit >='"+vgaCoreOld
                                +"' and vgaMemCap >'"+vgaMemOld+"' and vgaMemSpd >'"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                        if (vgaNew.size()==0){
                            //coreUnit> coreSpd> MemCap>= memSpd>=
                            vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >'"+vgaCoreSpdOld+"' and vgaCoreUnit >'"+vgaCoreOld
                                    +"' and vgaMemCap >='"+vgaMemOld+"' and vgaMemSpd >='"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                            if (vgaNew.size()==0){
                                //coreUnit> coreSpd>= MemCap> memSpd>=
                                vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >='"+vgaCoreSpdOld+"' and vgaCoreUnit >'"+vgaCoreOld
                                        +"' and vgaMemCap >'"+vgaMemOld+"' and vgaMemSpd >='"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                                if (vgaNew.size()==0){
                                    //coreUnit> coreSpd>= MemCap> memSpd>=
                                    vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >='"+vgaCoreSpdOld+"' and vgaCoreUnit >'"+vgaCoreOld
                                            +"' and vgaMemCap >'"+vgaMemOld+"' and vgaMemSpd >='"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                                    if (vgaNew.size()==0){
                                        //coreUnit>= coreSpd> MemCap> memSpd>=
                                        vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >'"+vgaCoreSpdOld+"' and vgaCoreUnit >='"+vgaCoreOld
                                                +"' and vgaMemCap >'"+vgaMemOld+"' and vgaMemSpd >='"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                                        if (vgaNew.size()==0){
                                            //coreUnit> coreSpd>= MemCap>= memSpd>
                                            vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >='"+vgaCoreSpdOld+"' and vgaCoreUnit >'"+vgaCoreOld
                                                    +"' and vgaMemCap >='"+vgaMemOld+"' and vgaMemSpd >='"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                                            if (vgaNew.size()==0){
                                                //coreUnit>= coreSpd> MemCap>= memSpd>
                                                vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >'"+vgaCoreSpdOld+"' and vgaCoreUnit >='"+vgaCoreOld
                                                        +"' and vgaMemCap >='"+vgaMemOld+"' and vgaMemSpd >'"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                                                if (vgaNew.size()==0){
                                                    //coreUnit>= coreSpd>= MemCap> memSpd>
                                                    vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >='"+vgaCoreSpdOld+"' and vgaCoreUnit >='"+vgaCoreOld
                                                            +"' and vgaMemCap >'"+vgaMemOld+"' and vgaMemSpd >'"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                                                    if (vgaNew.size()==0){
                                                        //coreUnit> coreSpd>= MemCap>= memSpd>=
                                                        vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >='"+vgaCoreSpdOld+"' and vgaCoreUnit >'"+vgaCoreOld
                                                                +"' and vgaMemCap >='"+vgaMemOld+"' and vgaMemSpd >='"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                                                        if (vgaNew.size()==0){
                                                            //coreUnit>= coreSpd> MemCap>= memSpd>=
                                                            vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >'"+vgaCoreSpdOld+"' and vgaCoreUnit >='"+vgaCoreOld
                                                                    +"' and vgaMemCap >='"+vgaMemOld+"' and vgaMemSpd >='"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                                                            if (vgaNew.size()==0){
                                                                //coreUnit>= coreSpd>= MemCap> memSpd>=
                                                                vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >='"+vgaCoreSpdOld+"' and vgaCoreUnit >='"+vgaCoreOld
                                                                        +"' and vgaMemCap >'"+vgaMemOld+"' and vgaMemSpd >='"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                                                                if (vgaNew.size()==0){
                                                                    //coreUnit>= coreSpd>= MemCap>= memSpd>
                                                                    vgaNew = dbHelper.getAllGPU("Select * from VGA where vgaID not like '"+vgaID+"' and vgaCoreSpd >='"+vgaCoreSpdOld+"' and vgaCoreUnit >='"+vgaCoreOld
                                                                            +"' and vgaMemCap >='"+vgaMemOld+"' and vgaMemSpd >'"+vgaMemSpdOld+"' and vgaPrice <="+bgtVga);
                                                                    if (vgaNew.size()==0){
                                                                        //do nothing no better part
                                                                    }else {setNewVga();}
                                                                }else {setNewVga();}
                                                            }else {setNewVga();}
                                                        }else {setNewVga();}
                                                    }else {setNewVga();}
                                                }else {setNewVga();}
                                            }else {setNewVga();}
                                        }else {setNewVga();}
                                    }else {setNewVga();}
                                }else {setNewVga();}
                            }else {setNewVga();}
                        }else {setNewVga();}
                    }else {setNewVga();}
                }else {setNewVga();}
            }else {setNewVga();}
        }else {setNewVga();}
    }

    public void checkCompaPsuCaseSto(){
        //check STO, CASING and PSU
        needPower = proTDPpsu+mbTDPpsu+ramTDPpsu+vgaTDP;
        if (needPower>psuPower){
            bgtPsu=psuPriceT+remaBudget;
            psu = dbHelper.getAllPSU(false,"",needPower.toString(),false,"",true,bgtPsu.toString());
            if(psu.size()==0){
                bgtPsu=bgtPsu+500000;
                psu = dbHelper.getAllPSU(false,"",needPower.toString(),false,"",true,bgtPsu.toString());
                if(psu.size()==0){
                    //very rarely happen
                }else {
                    psuWNDM = getPsu(psu);

                    rank=getRank(psuWNDM,psu.size(),4);
                    psuID = psu.get(rank[0]).getPsuID().toString();
                    psuPower = psu.get(rank[0]).getPsuVolt();
                    psuPriceT = psu.get(rank[0]).getPsuPrice();
                    //remaBudget = bgtPsu - psuPriceT;
                }
            }else {
                psuWNDM = getPsu(psu);

                rank=getRank(psuWNDM,psu.size(),4);
                psuID = psu.get(rank[0]).getPsuID().toString();
                psuPower = psu.get(rank[0]).getPsuVolt();
                psuPriceT = psu.get(rank[0]).getPsuPrice();
                //remaBudget = bgtPsu - psuPriceT;
            }
        }

        if(csSize.equals(mbSizeCs) || csSize.equals("E-ATX") && mbSizeCs.equals("ATX")
                || csSize.equals("E-ATX") && mbSizeCs.equals("Micro-ATX") || csSize.equals("E-ATX") && mbSizeCs.equals("Mini-ITX")
                || csSize.equals("ATX") && mbSizeCs.equals("Micro-ATX") || csSize.equals("ATX") && mbSizeCs.equals("Mini-ITX")
                || csSize.equals("Micro-ATX") && mbSizeCs.equals("Mini-ITX") ){
            //do nothing
        }else{
            bgtCase = csPriceT+remaBudget;
            cs = dbHelper.getAllCase(false,"",false,mbSizeCs,false,"",true,bgtCase.toString());
            if(cs.size()==0){
            }else{
                caseWNDM = getCase(cs);
                rank=getRank(caseWNDM,cs.size(),5);
                csID = cs.get(rank[0]).getCaseID().toString();
                csSize = cs.get(rank[0]).getCaseSize();
                csPriceT =cs.get(rank[0]).getCasePrice();
            }
        }

        if(stoName.contains("M.2")){
            if(mbSlot.contains("M.2")){
                //do nothing all good
            }else{
                if (rdHDD.isChecked()){
                    stoTypeInput="HDD";
                }else if (rdSSD.isChecked()){
                    stoTypeInput="SSD";
                }else if (rdStoNotSure.isChecked()){
                    stoTypeInput="NOT";
                }
                bgtSto = stoPriceT+remaBudget;
                //filter Sto
                sto = dbHelper.getAllSto(false,"",false,"",false,mbSlot,true,bgtSto.toString(),stoTypeInput,false,"");

                stoWNDM = getSto(sto);
                rank=getRank(stoWNDM,sto.size(),5);
                stoID = sto.get(rank[0]).getStoID().toString();
                stoName = sto.get(rank[0]).getStoName();
                stoPriceT = sto.get(rank[0]).getStoPrice();
                stoCap = sto.get(rank[0]).getStoCap();
                stoTDPpsu = 0 ;
                if(stoName.contains("HDD")){
                    stoTDPpsu = 9;
                }else if (stoName.contains("SSHD")){
                    stoTDPpsu =6;
                }else if (stoName.contains("SSD")){
                    stoTDPpsu =3;
                }
            }
        }
    }
//============================
    public void setNewPro(){
        proWNDM = getPro(proNew);
        Integer[] rank = getRank(proWNDM, proNew.size(), 9);
        //Getting Chosen processor data
        pro.clear();
        pro.addAll(proNew);
        proNew.clear();
        proID = pro.get(rank[0]).getProID().toString(); // processor ID
        proSocket = pro.get(rank[0]).getProSocket(); // processor Socket
        proCore = pro.get(rank[0]).getProCore().toString();
        proClockSpd = pro.get(rank[0]).getProClockSpd().toString();
        proMaxClockSpd = pro.get(rank[0]).getProClockMaxSpd().toString();
        proRamMax = pro.get(rank[0]).getProRamMaxSpd().toString();
        proTDPpsu = pro.get(rank[0]).getProTdp(); // Processor power
        proPriceT = pro.get(rank[0]).getProPrice(); // processor price
    }

    public void setNewMb(){
        mbWNDM = getMb(mbNew);
        rank = getRank(mbWNDM, mbNew.size(), 5);
        mb.clear();
        mb.addAll(mbNew);
        mbNew.clear();
        mbID = mb.get(rank[0]).getMbID().toString();
        mbRamVer = mb.get(rank[0]).getMbRamVer();
        mbRamSpd = mb.get(rank[0]).getMbRamMaxSpd().toString();
        mbSlot = mb.get(rank[0]).getMbSlot();
        mbSizeCs = mb.get(rank[0]).getMbSize();
        mbProSoc = mb.get(rank[0]).getMbProSocket();
        mbPriceT = mb.get(rank[0]).getMbPrice();
        mbMaxCap = mb.get(rank[0]).getMbRamMaxCap();
        mbTDPpsu = 0;
        if (mbSizeCs.equals("E-ATX")) {
            mbTDPpsu = 55;
        } else if (mbSizeCs.equals("ATX")) {
            mbTDPpsu = 40;
        } else if (mbSizeCs.equals("Micro-ATX")) {
            mbTDPpsu = 30;
        } else if (mbSizeCs.equals("Mini-ITX")) {
            mbTDPpsu = 15;
        }
        remaBudget = bgtMb - mbPriceT;
    }

    public void setNewRam(){
        ramWNDM = getRam(ramNew);
        Integer[] rank = getRank(ramWNDM, ramNew.size(), 3);
        ram.clear();
        ram.addAll(ramNew);
        ramNew.clear();
        ramID = ram.get(rank[0]).getRamID().toString();
        ramMbCap = ram.get(rank[0]).getRamCap();
        ramMbSpd = ram.get(rank[0]).getRamSpd();
        ramMbVer = ram.get(rank[0]).getRamVer();
        ramPriceT = ram.get(rank[0]).getRamPrice();
        ramTDPpsu =5;
        remaBudget = bgtRam - ramPriceT;
    }

    public void setNewVga(){
        vgaWNDM = getVga(vgaNew);
        Integer[] rank=getRank(vgaWNDM,vgaNew.size(),8);
        vga.clear();
        vga.addAll(vgaNew);
        vgaNew.clear();
        vgaID = vga.get(rank[0]).getVgaID().toString();
        vgaTDP = vga.get(rank[0]).getVgaTdp();
        vgaPriceT = vga.get(rank[0]).getVgaPrice();
        vgaCoreOld = vga.get(0).getVgaCoreUnit().toString();
        vgaCoreSpdOld = vga.get(0).getVgaCoreSpd().toString();
        vgaMemOld = vga.get(0).getVgaMemCap().toString();
        vgaMemSpdOld = vga.get(0).getVgaMemSpd().toString();

        remaBudget = bgtVga - vgaPriceT;
    }
//============================

}
