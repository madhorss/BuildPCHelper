package com.example.best.buildpc.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.best.buildpc.DbHelper;
import com.example.best.buildpc.R;
import com.example.best.buildpc.checkInet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;
    String strJson,strJson2;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String json_url;
    Integer inttrack;
    String track;
    AlertDialog.Builder builder;
    AnimationDrawable load;
    ImageView upDB;
    ImageButton btnGen,btnScr,btnUpg,btnCol,btnSav,btnUpd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(getApplicationContext());
        dbHelper.createDataBase();
    }

    public void generate(View view) {
        Intent intent = new Intent(this, generate.class);
        startActivity(intent);
    }
    public void scratch(View view) {
        Intent intent = new Intent(this, Scratch.class);
        startActivity(intent);
    }
    public void upgrade(View view) {
        Intent intent = new Intent(this, upgrade.class);
        startActivity(intent);
    }
    public void collection(View view) {
        Intent intent = new Intent(this, collection.class);
        startActivity(intent);
    }
    public void saved(View view) {
        Intent intent = new Intent(this, saved.class);
        startActivity(intent);
    }

    public void getJSON(View view){
        if(checkInet.isConnected(getBaseContext())){
            //begin update
            json_url = "https://testovento.000webhostapp.com/json_get_data1.php";
            new BackgroundTask().execute();
            //animations
            upDB= findViewById(R.id.btnUpdateDB);
            upDB.setImageResource(R.drawable.loading);
            load =(AnimationDrawable) upDB.getDrawable();
            load.start();
            //disable button
            btnGen=findViewById(R.id.btnGenerate);
            btnScr=findViewById(R.id.btnScratch);
            btnUpg=findViewById(R.id.btnUpgrade);
            btnCol=findViewById(R.id.btncollection);
            btnSav=findViewById(R.id.btnSaved);
            btnUpd=findViewById(R.id.btnUpdateDB);

            btnGen.setEnabled(false);
            btnScr.setEnabled(false);
            btnUpg.setEnabled(false);
            btnCol.setEnabled(false);
            btnSav.setEnabled(false);
            btnUpd.setEnabled(false);
        }else {
            Toast.makeText(getApplicationContext(),"Turn On Your Internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((strJson=bufferedReader.readLine())!=null){
                    stringBuilder.append(strJson+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            strJson2 = result;
            try {
                parseJSON();
                Toast.makeText(getApplicationContext(),"List Updated",Toast.LENGTH_LONG).show();
                upDB.setImageResource(R.drawable.updatedb);
                //load.stop();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void parseJSON() throws JSONException {
        if(strJson2==null){
            Toast.makeText(getApplicationContext(),"First Get JSON",Toast.LENGTH_SHORT).show();
        }
        else {

            jsonObject = new JSONObject(strJson2);
            jsonArray = jsonObject.getJSONArray("Processor");

            int count = 0;
            Integer proID,proClockSpd, proCache, proCore, proClockMaxSpd, proThread,
                    proRamMaxSpd, proRamChan,proTdp,proPrice;
            String proName, proSocket, proIntGpu;

            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                proID = JO.getInt("proID");
                proName = JO.getString("proName");
                proSocket = JO.getString("proSocket");
                proClockSpd = JO.getInt("proClockSpd");
                proClockMaxSpd = JO.getInt("proClockMaxSpd");
                proCache = JO.getInt("proCache");
                proCore = JO.getInt("proCore");
                proThread = JO.getInt("proThread");
                proRamChan = JO.getInt("proRamChan");
                proRamMaxSpd = JO.getInt("proRamMaxSpd");
                proIntGpu = JO.getString("proIntGpu");
                proTdp = JO.getInt("proTdp");
                proPrice = JO.getInt("proPrice");

                boolean isInserted = dbHelper.insertPro(proID.toString(),
                        proName, proSocket,proClockSpd.toString(),proClockMaxSpd.toString(), proCache.toString(),
                        proCore.toString(), proThread.toString(), proRamChan.toString(), proRamMaxSpd.toString(),
                        proIntGpu, proTdp.toString(), proPrice.toString() );
                if(!isInserted) {
                    isInserted = dbHelper.updatePro(proID.toString(),
                            proName, proSocket,proClockSpd.toString(),proClockMaxSpd.toString(), proCache.toString(),
                            proCore.toString(), proThread.toString(), proRamChan.toString(), proRamMaxSpd.toString(),
                            proIntGpu, proTdp.toString(), proPrice.toString() );
                }
                count++;
            }

            //jsonObject = new JSONObject(strJson2);
            jsonArray = jsonObject.getJSONArray("Motherboard");

            count = 0;
            Integer mbID, mbRamSlot, mbRamMaxSpd, mbPrice, mbRamMaxCap;
            String mbName, mbProSocket, mbChipset, mbPin, mbRamVer, mbSlot, mbSize;

            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);

                mbID= JO.getInt("mbID");
                mbName= JO.getString("mbName");
                mbProSocket= JO.getString("mbProSocket");
                mbChipset= JO.getString("mbChipset");
                mbRamVer= JO.getString("mbRamVer");
                mbRamSlot = JO.getInt("mbRamSlot");
                mbRamMaxCap = JO.getInt("mbRamMaxCap");
                mbRamMaxSpd= JO.getInt("mbRamMaxSpd");
                mbSlot= JO.getString("mbSlot");
                mbSize= JO.getString("mbSize");
                mbPin= JO.getString("mbPin");
                mbPrice= JO.getInt("mbPrice");

                boolean isInserted = dbHelper.insertMb(mbID.toString(), mbName, mbProSocket,mbChipset,
                        mbRamVer, mbRamSlot.toString(), mbRamMaxCap.toString(), mbRamMaxSpd.toString(),
                        mbSlot, mbSize, mbPin, mbPrice.toString());
                if(!isInserted) {
                    isInserted = dbHelper.updateMb(mbID.toString(), mbName, mbProSocket,mbChipset,
                            mbRamVer, mbRamSlot.toString(), mbRamMaxCap.toString(), mbRamMaxSpd.toString(),
                            mbSlot, mbSize, mbPin, mbPrice.toString());
                }/*else{
                    Toast.makeText(MainActivity.this,"MB Inserted",Toast.LENGTH_LONG).show();
                }*/
                count++;
            }

            //jsonObject = new JSONObject(strJson2);
            jsonArray = jsonObject.getJSONArray("RAM");

            count = 0;
            String ramName, ramVer;
            Integer ramID, ramCap, ramSpd, ramPrice;

            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                ramID = JO.getInt("ramID");
                ramName = JO.getString("ramName");
                ramCap = JO.getInt("ramCap");
                ramVer = JO.getString("ramVer");
                ramSpd = JO.getInt("ramSpd");
                ramPrice = JO.getInt("ramPrice");

                boolean isInserted = dbHelper.insertRam(ramID.toString(),ramName,ramCap.toString(),ramVer,ramSpd.toString(),ramPrice.toString());//ramID.toString(), ramName, ramCap.toString(),ramVer, ramSpd.toString(), ramPrice.toString()

                if(!isInserted) {
                    isInserted = dbHelper.updateRam(ramID.toString(),ramName,ramCap.toString(),ramVer,ramSpd.toString(),ramPrice.toString());
                }/*else {
                    Toast.makeText(MainActivity.this,"RAM Inserted",Toast.LENGTH_LONG).show();
                }*/
                count++;
            }

         //   jsonObject = new JSONObject(strJson2);
            jsonArray = jsonObject.getJSONArray("VGA");

            count = 0;
            String  vgaName, vgaInterface, vgaPin, vgaMemType;
            Integer vgaID,vgaCoreSpd, vgaCoreMaxSpd, vgaCoreUnit, vgaSlotUsed, vgaTdp, vgaSugPow, vgaMemSpd,vgaMemCap,vgaPrice;

            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);

                vgaID = JO.getInt("vgaID");
                vgaName = JO.getString("vgaName");
                vgaCoreUnit = JO.getInt("vgaCoreUnit");
                vgaCoreSpd = JO.getInt("vgaCoreSpd");
                vgaCoreMaxSpd = JO.getInt("vgaCoreMaxSpd");
                vgaSlotUsed = JO.getInt("vgaSlotUsed");
                vgaInterface = JO.getString("vgaInterface");
                vgaPin = JO.getString("vgaPin");
                vgaTdp = JO.getInt("vgaTdp");
                vgaSugPow = JO.getInt("vgaSugPow");
                vgaMemSpd = JO.getInt("vgaMemSpd");
                vgaMemCap = JO.getInt("vgaMemCap");
                vgaMemType = JO.getString("vgaMemType");
                vgaPrice  = JO.getInt("vgaPrice");

                boolean isInserted = dbHelper.insertVga(vgaID.toString(), vgaName,
                        vgaCoreUnit.toString(), vgaCoreSpd.toString(), vgaCoreMaxSpd.toString(),
                        vgaSlotUsed.toString(), vgaInterface, vgaPin, vgaTdp.toString(), vgaSugPow.toString(),
                        vgaMemSpd.toString(),  vgaMemCap.toString(), vgaMemType, vgaPrice.toString());
                if(!isInserted) {
                    isInserted = dbHelper.updateVga(vgaID.toString(), vgaName,
                            vgaCoreUnit.toString(), vgaCoreSpd.toString(), vgaCoreMaxSpd.toString(),
                            vgaSlotUsed.toString(), vgaInterface, vgaPin, vgaTdp.toString(), vgaSugPow.toString(),
                            vgaMemSpd.toString(),  vgaMemCap.toString(), vgaMemType, vgaPrice.toString());
                }/*else{
                    Toast.makeText(MainActivity.this,"VGA Inserted",Toast.LENGTH_LONG).show();
                }*/
                count++;
            }

           // jsonObject = new JSONObject(strJson2);
            jsonArray = jsonObject.getJSONArray("Storage");

            count = 0;
            String stoName, stoSize, stoInterface;
            Integer stoID, stoCap, stoRotSpd, stoRead, stoWrite,  stoPrice;

            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);

                stoID = JO.getInt("stoID");
                stoName = JO.getString("stoName");
                stoCap = JO.getInt("stoCap");
                stoRotSpd = JO.getInt("stoRotSpd");
                stoRead = JO.getInt("stoRead");
                stoWrite = JO.getInt("stoWrite");
                stoInterface = JO.getString("stoInterface");
                stoSize = JO.getString("stoSize");
                stoPrice = JO.getInt("stoPrice");


                boolean isInserted = dbHelper.insertSto(stoID.toString(), stoName, stoCap.toString(), stoRotSpd.toString(),
                        stoRead.toString(), stoWrite.toString(), stoInterface,
                        stoSize, stoPrice.toString());
                if(!isInserted) {
                    isInserted = dbHelper.updateSto(stoID.toString(), stoName, stoCap.toString(), stoRotSpd.toString(),
                            stoRead.toString(), stoWrite.toString(), stoInterface,
                            stoSize, stoPrice.toString());
                }/*else{
                    Toast.makeText(MainActivity.this,"STO Inserted",Toast.LENGTH_LONG).show();
                }*/
                count++;
            }

      //      jsonObject = new JSONObject(strJson2);
            jsonArray = jsonObject.getJSONArray("PSU");

            count = 0;
            String  psuName,psuType,psuRank, psuSize,psuPin;
            Integer psuID, psuVolt, psuPrice;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);

                psuID = JO.getInt("psuID");
                psuName = JO.getString("psuName");
                psuType = JO.getString("psuType");
                psuRank = JO.getString("psuRank");
                psuVolt = JO.getInt("psuVolt");
                psuPin = JO.getString("psuPin");
                psuSize = JO.getString("psuSize");
                psuPrice = JO.getInt("psuPrice");

                boolean isInserted = dbHelper.insertPsu(psuID.toString(),
                        psuName, psuType, psuRank, psuVolt.toString()
                        , psuPin, psuSize, psuPrice.toString());
                if(!isInserted) {
                    isInserted = dbHelper.updatePsu(psuID.toString(),
                            psuName, psuType, psuRank, psuVolt.toString()
                            , psuPin, psuSize, psuPrice.toString());
                }/*else {
                    Toast.makeText(MainActivity.this,"PSU Inserted",Toast.LENGTH_LONG).show();
                }*/
                count++;
            }

   //         jsonObject = new JSONObject(strJson2);
            jsonArray = jsonObject.getJSONArray("Casing");

            count = 0;
            String  caseName, caseSize;
            Integer caseID, caseExSlot, casePrice,caseDrive, caseSto35, caseSto25;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);

                caseID = JO.getInt("caseID");
                caseName = JO.getString("caseName");
                caseSize = JO.getString("caseSize");
                caseExSlot = JO.getInt("caseExSlot");
                caseDrive = JO.getInt("caseDrive");
                caseSto35 = JO.getInt("caseSto35");
                caseSto25 = JO.getInt("caseSto25");
                casePrice = JO.getInt("casePrice");

                boolean isInserted = dbHelper.insertCase(caseID.toString(), caseName, caseSize,
                        caseExSlot.toString(), caseDrive.toString(),caseSto35.toString(),caseSto25.toString(), casePrice.toString());

                if(!isInserted) {
                    isInserted = dbHelper.updateCase(caseID.toString(), caseName, caseSize,
                            caseExSlot.toString(), caseDrive.toString(),caseSto35.toString(),caseSto25.toString(), casePrice.toString());
                }/*else {
                    Toast.makeText(MainActivity.this,"CASE Inserted",Toast.LENGTH_LONG).show();
                }*/
                count++;
            }

 //           jsonObject = new JSONObject(strJson2);
            jsonArray = jsonObject.getJSONArray("build");

            count = 0;
            String Date, buildName;
            Integer buildID, totPrice;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                buildID = JO.getInt("buildID");
                buildName = JO.getString("buildName");
                mbID = JO.getInt("mbID");
                proID = JO.getInt("proID");
                ramID = JO.getInt("ramID");
                vgaID = JO.getInt("vgaID");
                stoID = JO.getInt("stoID");
                psuID = JO.getInt("psuID");
                caseID = JO.getInt("caseID");
                Date = JO.getString("Date");
                totPrice = JO.getInt("totPrice");

                boolean isInserted = dbHelper.insertBuild(buildID.toString(), buildName, mbID.toString(), proID.toString()
                        , ramID.toString(), vgaID.toString(), stoID.toString()
                        , psuID.toString(), caseID.toString(), Date, totPrice.toString());

                if(!isInserted) {
                    isInserted = dbHelper.updateBuild(buildID.toString(), buildName, mbID.toString(), proID.toString()
                            , ramID.toString(), vgaID.toString(), stoID.toString()
                            , psuID.toString(), caseID.toString(), Date, totPrice.toString());
                }
                count++;
            }
            //enable button
            btnGen=findViewById(R.id.btnGenerate);
            btnScr=findViewById(R.id.btnScratch);
            btnUpg=findViewById(R.id.btnUpgrade);
            btnCol=findViewById(R.id.btncollection);
            btnSav=findViewById(R.id.btnSaved);
            btnUpd=findViewById(R.id.btnUpdateDB);

            btnGen.setEnabled(true);
            btnScr.setEnabled(true);
            btnUpg.setEnabled(true);
            btnCol.setEnabled(true);
            btnSav.setEnabled(true);
            btnUpd.setEnabled(true);
        }
    }

    @Override
    public void onResume() {
        Intent killScratch = new Intent("setDef");
        sendBroadcast(killScratch);
        super.onResume();
    }
}









