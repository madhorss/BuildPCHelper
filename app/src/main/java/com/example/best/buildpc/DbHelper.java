package com.example.best.buildpc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.Nullable;

import com.example.best.buildpc.Factory.Case;
import com.example.best.buildpc.Factory.Motherboard;
import com.example.best.buildpc.Factory.PSU;
import com.example.best.buildpc.Factory.Processor;
import com.example.best.buildpc.Factory.RAM;
import com.example.best.buildpc.Factory.Storage;
import com.example.best.buildpc.Factory.VGA;
import com.example.best.buildpc.Factory.build;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper{

    private static String DBPATH="";
    private static String DBNAME="buildDB.db";
    private SQLiteDatabase mDataBase;
    private Context mContext=null;

    private static final String BUILD_ID ="buildID";
    private static final String PRO_ID = "proID";
    private static final String MB_ID = "mbID";
    private static final String RAM_ID = "ramID";
    private static final String VGA_ID = "vgaID";
    private static final String PSU_ID = "psuID";
    private static final String STO_ID = "stoID";
    private static final String CS_ID = "caseID";
    private static final String TOT = "totPrice";
    private static final String BUILD_NAME = "buildName";
    private static final String DATE = "Date";

    public DbHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
        if (Build.VERSION.SDK_INT>=17)
            DBPATH = context.getApplicationInfo().dataDir+"/databases/";
        else
            DBPATH="/data/data/"+context.getPackageName()+"/databases/";

        mContext=context;
    }

    @Override
    public synchronized void close() {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean checkDataBase(){
        SQLiteDatabase tempDB = null;
        try{
            String path= DBPATH+DBNAME;
            tempDB=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
        }
        catch (Exception ex){}
        if(tempDB!=null)
            tempDB.close();
        return tempDB!=null?true:false;
    }

    public void copyDataBase(){
        try{
            //Open local db as the input stream
            InputStream myInput=mContext.getAssets().open(DBNAME);
            // Path to the just created empty db
            String outputFileName = DBPATH+DBNAME;
            //Open the empty db as the output stream
            OutputStream myOutput= new FileOutputStream(outputFileName);
            //transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while((length=myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
            }
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDataBase(){
        String path =DBPATH+DBNAME;
        mDataBase =SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDataBase(){
        boolean isDBExist = checkDataBase();
        if(isDBExist){

        }
        else{
            this.getReadableDatabase();
            try{
                copyDataBase();
            }
            catch (Exception ex){

            }
        }
    }

    //___________--------------INSERT-------------________________________

    public Boolean insertPro(String proID, String proName ,String proSocket,String proClockSpd, String proClockMaxSpd
                            ,String proCache,String proCore, String proThread,String proRamChan, String proRamMaxSpd
                            ,String proIntGpu,String proTdp,String proPrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("proID", proID );
        cv.put("proName", proName );
        cv.put("proSocket", proSocket );
        cv.put("proClockSpd", proClockSpd );
        cv.put("proClockMaxSpd", proClockMaxSpd );
        cv.put("proCache", proCache );
        cv.put("proCore", proCore );
        cv.put("proThread", proThread );
        cv.put("proRamChan", proRamChan );
        cv.put("proRamMaxSpd", proRamMaxSpd );
        cv.put("proIntGpu", proIntGpu );
        cv.put("proTdp", proTdp );
        cv.put("proPrice",proPrice);

        long result = db.insert("Processor", null, cv);

        if (result == -1)
            return false;
        else
            return true;

    }


    public Boolean insertMb(String mbID, String mbName ,String mbProSocket,String mbChipset
                            ,String mbRamVer,String mbRamSlot, String mbRamMaxCap,String mbRamMaxSpd
                            ,String mbSlot,String mbSize,String mbPin
                            ,String mbPrice){

                        SQLiteDatabase db = this.getWritableDatabase();
                        ContentValues cv = new ContentValues();

                        cv.put("mbID", mbID );
                        cv.put("mbName", mbName );
                        cv.put("mbProSocket", mbProSocket );
                        cv.put("mbChipset", mbChipset );
                        cv.put("mbRamVer", mbRamVer );
                        cv.put("mbRamSlot", mbRamSlot );
                        cv.put("mbRamMaxCap", mbRamMaxCap );
                        cv.put("mbRamMaxSpd", mbRamMaxSpd );
                        cv.put("mbSlot", mbSlot );
                        cv.put("mbSize", mbSize );
                        cv.put("mbPin", mbPin );
                        cv.put("mbPrice", mbPrice);

                        long result = db.insert("Motherboard", null, cv);

                        if (result == -1)
                            return false;
                        else
                            return true;

                    }

    public Boolean insertRam(String ramID, String ramName, String ramCap, String ramVer
                            , String ramSpd, String ramPrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ramID", ramID );
        cv.put("ramName", ramName );
        cv.put("ramCap", ramCap );
        cv.put("ramVer", ramVer );
        cv.put("ramSpd", ramSpd );
        cv.put("ramPrice", ramPrice );

        long result = db.insert("RAM", null, cv);

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean insertVga(String vgaID, String vgaName, String vgaCoreUnit,String vgaCoreSpd,String vgaCoreMaxSpd,String vgaSlotUsed
                            ,String vgaInterface,String vgaPin,String vgaTdp
                            ,String vgaSugPow,String vgaMemSpd
                            ,String vgaMemCap, String vgaMemType, String vgaPrice ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("vgaID", vgaID );
        cv.put("vgaName", vgaName );
        cv.put("vgaCoreUnit", vgaCoreUnit );
        cv.put("vgaCoreSpd", vgaCoreSpd );
        cv.put("vgaCoreMaxSpd", vgaCoreMaxSpd );
        cv.put("vgaSlotUsed", vgaSlotUsed );
        cv.put("vgaInterface", vgaInterface );
        cv.put("vgaPin", vgaPin );
        cv.put("vgaTdp", vgaTdp );
        cv.put("vgaSugPow", vgaSugPow );
        cv.put("vgaMemSpd", vgaMemSpd );
        cv.put("vgaMemCap", vgaMemCap);
        cv.put("vgaMemType", vgaMemType);
        cv.put("vgaPrice", vgaPrice);

        long result = db.insert("VGA", null, cv);

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean insertSto(String stoID, String stoName ,String stoCap, String stoRotSpd,String stoRead
                            ,String stoWrite,String stoInterface,String stoSize
                            ,String stoPrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("stoID", stoID );
        cv.put("stoName", stoName );
        cv.put("stoCap", stoCap );
        cv.put("stoRotSpd", stoRotSpd );
        cv.put("stoRead", stoRead );
        cv.put("stoWrite", stoWrite );
        cv.put("stoInterface", stoInterface );
        cv.put("stoSize", stoSize );
        cv.put("stoPrice", stoPrice );

        long result = db.insert("Storage", null, cv);

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean insertPsu(String psuID, String psuName ,String psuType,String psuRank
                            ,String psuVolt,String psuPin,String psuSize
                            ,String psuPrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("psuID", psuID );
        cv.put("psuName", psuName );
        cv.put("psuType", psuType );
        cv.put("psuRank", psuRank );
        cv.put("psuVolt", psuVolt );
        cv.put("psuPin", psuPin );
        cv.put("psuSize", psuSize );
        cv.put("psuPrice", psuPrice );

        long result = db.insert("PSU", null, cv);

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean insertCase(String caseID, String caseName ,String caseSize,String caseExSlot
                            ,String caseDrive,String caseSto35, String caseSto25,String casePrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("caseID", caseID );
        cv.put("caseName", caseName );
        cv.put("caseSize", caseSize );
        cv.put("caseExSlot", caseExSlot );
        cv.put("caseDrive", caseDrive );
        cv.put("caseSto35", caseSto35 );
        cv.put("caseSto25", caseSto25 );
        cv.put("casePrice", casePrice );

        long result = db.insert("Casing", null, cv);

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean insertBuild(String bd, String name ,String mb ,String pro
            ,String ram,String vga,String sto
            ,String psu,String cs
            ,String dt, String tot){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BUILD_ID, bd );
        cv.put(BUILD_NAME, name );
        cv.put(MB_ID, mb );
        cv.put(PRO_ID, pro );
        cv.put(RAM_ID, ram );
        cv.put(VGA_ID, vga );
        cv.put(STO_ID, sto );
        cv.put(PSU_ID, psu );
        cv.put(CS_ID, cs );
        cv.put(DATE, dt);
        cv.put(TOT, tot);

        long result = db.insert("build", null, cv);

        if (result == -1)
            return false;
        else
            return true;

    }

    //___________--------------DELETE----------------________________________*/

    public Boolean deleteBuild(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        long result = db.delete("build","buildID = ?", new String[]{id});

        if (result == -1)
            return false;
        else
            return true;
    }

    //___________-----------UPDATE------------________________________*/

    public Boolean updatePro(String proID, String proName ,String proSocket,String proClockSpd, String proClockMaxSpd
            ,String proCache,String proCore, String proThread,String proRamChan, String proRamMaxSpd
            ,String proIntGpu,String proTdp,String proPrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("proID", proID );
        cv.put("proName", proName );
        cv.put("proSocket", proSocket );
        cv.put("proClockSpd", proClockSpd );
        cv.put("proClockMaxSpd", proClockMaxSpd );
        cv.put("proCache", proCache );
        cv.put("proCore", proCore );
        cv.put("proThread", proThread );
        cv.put("proRamChan", proRamChan );
        cv.put("proRamMaxSpd", proRamMaxSpd );
        cv.put("proIntGpu", proIntGpu );
        cv.put("proTdp", proTdp );
        cv.put("proPrice",proPrice);

        long result = db.update("Processor",cv,"proID = ?", new String[]{proID});

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean updateMb(String mbID, String mbName ,String mbProSocket,String mbChipset
            ,String mbRamVer,String mbRamSlot, String mbRamMaxCap,String mbRamMaxSpd
            ,String mbSlot,String mbSize,String mbPin
            ,String mbPrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("mbID", mbID );
        cv.put("mbName", mbName );
        cv.put("mbProSocket", mbProSocket );
        cv.put("mbChipset", mbChipset );
        cv.put("mbRamVer", mbRamVer );
        cv.put("mbRamSlot", mbRamSlot );
        cv.put("mbRamMaxCap", mbRamMaxCap );
        cv.put("mbRamMaxSpd", mbRamMaxSpd );
        cv.put("mbSlot", mbSlot );
        cv.put("mbSize", mbSize );
        cv.put("mbPin", mbPin );
        cv.put("mbPrice", mbPrice);

        long result = db.update("Motherboard",cv,"mbID = ?", new String[]{mbID});

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean updateRam(String ramID, String ramName, String ramCap, String ramVer
            , String ramSpd, String ramPrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ramID", ramID );
        cv.put("ramName", ramName );
        cv.put("ramCap", ramCap );
        cv.put("ramVer", ramVer );
        cv.put("ramSpd", ramSpd );
        cv.put("ramPrice", ramPrice );

        long result = db.update("RAM",cv,"ramID = ?", new String[]{ramID});

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean updateVga(String vgaID, String vgaName, String vgaCoreUnit,String vgaCoreSpd,String vgaCoreMaxSpd,String vgaSlotUsed
            ,String vgaInterface,String vgaPin,String vgaTdp
            ,String vgaSugPow,String vgaMemSpd
            ,String vgaMemCap, String vgaMemType, String vgaPrice ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("vgaID", vgaID );
        cv.put("vgaName", vgaName );
        cv.put("vgaCoreUnit", vgaCoreUnit );
        cv.put("vgaCoreSpd", vgaCoreSpd );
        cv.put("vgaCoreMaxSpd", vgaCoreMaxSpd );
        cv.put("vgaSlotUsed", vgaSlotUsed );
        cv.put("vgaInterface", vgaInterface );
        cv.put("vgaPin", vgaPin );
        cv.put("vgaTdp", vgaTdp );
        cv.put("vgaSugPow", vgaSugPow );
        cv.put("vgaMemSpd", vgaMemSpd );
        cv.put("vgaMemCap", vgaMemCap);
        cv.put("vgaMemType", vgaMemType);
        cv.put("vgaPrice", vgaPrice);

        long result = db.update("VGA",cv,"vgaID = ?", new String[]{vgaID});

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean updateSto(String stoID, String stoName ,String stoCap, String stoRotSpd,String stoRead
            ,String stoWrite,String stoInterface,String stoSize
            ,String stoPrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("stoID", stoID );
        cv.put("stoName", stoName );
        cv.put("stoCap", stoCap );
        cv.put("stoRotSpd", stoRotSpd );
        cv.put("stoRead", stoRead );
        cv.put("stoWrite", stoWrite );
        cv.put("stoInterface", stoInterface );
        cv.put("stoSize", stoSize );
        cv.put("stoPrice", stoPrice );

        long result = db.update("Storage",cv,"stoID = ?", new String[]{stoID});


        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean updatePsu(String psuID, String psuName ,String psuType,String psuRank
            ,String psuVolt,String psuPin,String psuSize
            ,String psuPrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("psuID", psuID );
        cv.put("psuName", psuName );
        cv.put("psuType", psuType );
        cv.put("psuRank", psuRank );
        cv.put("psuVolt", psuVolt );
        cv.put("psuPin", psuPin );
        cv.put("psuSize", psuSize );
        cv.put("psuPrice", psuPrice );

        long result = db.update("PSU",cv,"psuID = ?", new String[]{psuID});

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean updateCase(String caseID, String caseName ,String caseSize,String caseExSlot
            ,String caseDrive,String caseSto35, String caseSto25,String casePrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("caseID", caseID );
        cv.put("caseName", caseName );
        cv.put("caseSize", caseSize );
        cv.put("caseExSlot", caseExSlot );
        cv.put("caseDrive", caseDrive );
        cv.put("caseSto35", caseSto35 );
        cv.put("caseSto25", caseSto25 );
        cv.put("casePrice", casePrice );

        long result = db.update("Casing",cv,"caseID = ?", new String[]{caseID});

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean updateBuild(String bd, String name ,String mb,String pro
            ,String ram,String vga,String sto
            ,String psu,String cs
            ,String dt, String tot){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BUILD_ID, bd );
        cv.put(BUILD_NAME, name );
        cv.put(MB_ID, mb );
        cv.put(PRO_ID, pro );
        cv.put(RAM_ID, ram );
        cv.put(VGA_ID, vga );
        cv.put(STO_ID, sto );
        cv.put(PSU_ID, psu );
        cv.put(CS_ID, cs );
        cv.put(DATE, dt);
        cv.put(TOT, tot);

        long result = db.update("build",cv,"buildID = ?", new String[]{bd});

        if (result == -1)
            return false;
        else
            return true;
    }

    //___________-------------------------------________________________*/

    ////----------------------GET DATA----------------========================

    public List<Processor> getAllPro(Boolean cek, String id
                                    , Boolean isMbPicked, String  MbProSoc, Boolean search
                                    , String src,Boolean isGenerate, String proCode, String proBudget, Boolean xtra, Boolean isRamPicked, String ramver) { //,
        List<Processor> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String qry;
        try {
            if(cek){
                qry = "select * from Processor where proID = "+id;
            }else if(isMbPicked && isRamPicked && search){
                qry = "select * from Processor where proSocket = \""+ MbProSoc +"\" and proName like \"%"+src+"%\"";
            }
            else if(isMbPicked && search){
                qry = "select * from Processor where proSocket = \""+ MbProSoc +"\" and proName like \"%"+src+"%\"";
            }
            else if(isRamPicked && search){
                if (ramver.equals("DDR4")){
                    qry = "select * from processor where proName like '%"+src+"%' and  proSocket in ('LGA2066','LGA1151','TR4','AM4')";
                }else{
                    qry = "select * from processor where proName like '%"+src+"%' and proSocket not in ('LGA2066','LGA1151','TR4','AM4')";
                }
            }
            else if(isMbPicked && isRamPicked){
                qry = "select * from Processor where proSocket = \""+ MbProSoc +"\"";
            }
            else if(isRamPicked){
                if (ramver.equals("DDR4")){
                    qry = "select * from processor where proSocket in ('LGA2066','LGA1151','TR4','AM4')";
                }else{
                    qry = "select * from processor where proSocket not in ('LGA2066','LGA1151','TR4','AM4')";
                }
            }

            else if(isMbPicked){
                qry = "select * from Processor where proSocket = \""+ MbProSoc +"\"";
            }
            else if(search){
                qry = "select * from Processor where proName like \"%"+src+"%\"";
            }
            else if(isGenerate){
                if (proCode.equals("0")){
                    qry = "select * from Processor where proPrice <= "+proBudget;
                }else{
                    qry = "select * from Processor where proID like '"+proCode+"%' and proPrice <= "+proBudget;
                }
            }
            else if(xtra){
                    qry = "select * from Processor where proSocket =  \""+ MbProSoc +"\" and proPrice <= "+proBudget;
            }
            else{
                qry = "select * from Processor";
            }
            c = db.rawQuery(qry,null);
            if (c == null) return null;

            c.moveToFirst();
            do {
                Processor pr = new Processor(c.getInt(c.getColumnIndex("proID"))
                                            ,c.getString(c.getColumnIndex("proName"))
                                            ,c.getString(c.getColumnIndex("proSocket"))
                                            ,c.getDouble(c.getColumnIndex("proClockSpd"))
                                            ,c.getDouble(c.getColumnIndex("proClockMaxSpd"))
                                            ,c.getDouble(c.getColumnIndex("proCache"))
                                            ,c.getInt(c.getColumnIndex("proCore"))
                                            ,c.getInt(c.getColumnIndex("proThread"))
                                            ,c.getInt(c.getColumnIndex("proRamChan"))
                                            ,c.getInt(c.getColumnIndex("proRamMaxSpd"))
                                            ,c.getString(c.getColumnIndex("proIntGpu"))
                                            ,c.getInt(c.getColumnIndex("proTdp"))
                                            ,c.getInt(c.getColumnIndex("proPrice")));

                temp.add(pr);
            } while (c.moveToNext());
            c.close();
        }
        catch(Exception e){
        }
        db.close();
        return temp;
    }

    public List<Processor> gettAllCPU(String query){
        List<Processor> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery(query,null);
            if (c == null) return null;

            c.moveToFirst();
            do {
                Processor pr = new Processor(c.getInt(c.getColumnIndex("proID"))
                        ,c.getString(c.getColumnIndex("proName"))
                        ,c.getString(c.getColumnIndex("proSocket"))
                        ,c.getDouble(c.getColumnIndex("proClockSpd"))
                        ,c.getDouble(c.getColumnIndex("proClockMaxSpd"))
                        ,c.getDouble(c.getColumnIndex("proCache"))
                        ,c.getInt(c.getColumnIndex("proCore"))
                        ,c.getInt(c.getColumnIndex("proThread"))
                        ,c.getInt(c.getColumnIndex("proRamChan"))
                        ,c.getInt(c.getColumnIndex("proRamMaxSpd"))
                        ,c.getString(c.getColumnIndex("proIntGpu"))
                        ,c.getInt(c.getColumnIndex("proTdp"))
                        ,c.getInt(c.getColumnIndex("proPrice")));

                temp.add(pr);
            } while (c.moveToNext());
            c.close();
        }
        catch(Exception e){
        }
        db.close();
        return temp;
    }

    public List<Motherboard> getAllMB(Boolean cek, String id
                                    , Boolean isProPicked, String pro
                                    , Boolean isRamPicked, String RamVer, String RamSpd,
                                      Boolean isStoPicked, String StoSlot, Boolean search, String src
                                    , Boolean isGenerate, String mbBudget, Boolean xtra, String RamCap, String mbFac) {
        List<Motherboard> temp = new ArrayList<Motherboard>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String qry;
        try {
            if(cek){
                qry = "select * from Motherboard where mbID = "+id;
            }
            else if (isStoPicked && isRamPicked && isProPicked){
                if (StoSlot.equals("M.2")){
                    qry = "select * from Motherboard where mbSlot like '%"+StoSlot+"%' and mbProSocket ='"+pro+"' and mbRamMaxSpd >= '"+RamSpd
                            +"' and mbRamMaxCap >= '"+RamCap+"'";
                }else{
                    qry = "select * from Motherboard where mbProSocket ='"+pro
                            +"' and mbRamMaxSpd >= '"+RamSpd
                            +"' and mbRamMaxCap >= '"+RamCap+"'";
                }
            }
            else if (isRamPicked && isProPicked && isStoPicked && search){
                if (StoSlot.equals("M.2")){
                    qry = "select * from Motherboard where mbSlot like '%"+StoSlot+"%' and mbProSocket ='"+pro+"' and mbRamMaxSpd >= '"+RamSpd
                            +"' and mbRamMaxCap >= '"+RamCap+"' and mbName like \"%"+src+"%\"";
                }else{
                    qry = "select * from Motherboard where mbProSocket ='"+pro
                            +"' and mbRamMaxSpd >= '"+RamSpd
                            +"' and mbRamMaxCap >= '"+RamCap+"' and mbName like \"%"+src+"%\"";
                }
            }
            else if (isRamPicked && isProPicked && search){
                qry = "select * from Motherboard where mbRamMaxSpd >= \""+RamSpd+"\" and mbProSocket = \""+pro+"\" " +
                        "and mbRamMaxCap >= '"+RamCap+"' and mbName like \"%"+src+"%\"";
            }
            else if (isRamPicked && isStoPicked  && search ){
                if (StoSlot.equals("M.2")){
                    qry = "select * from Motherboard where mbSlot like '%"+StoSlot+"%' and mbRamVer = '"+RamVer
                            +"' and mbRamMaxSpd >= '"+RamSpd
                            +"' and mbRamMaxCap >= '"+RamCap+"' and mbName like \"%"+src+"%\"";
                }else{
                    qry = "select * from Motherboard where mbRamVer = '"+RamVer
                            +"' and mbRamMaxSpd >= '"+RamSpd
                            +"' and mbRamMaxCap >= '"+RamCap+"' and mbName like \"%"+src+"%\"";
                }
            }
            else if (isStoPicked && isProPicked && search){
                if (StoSlot.equals("M.2")){
                    qry = "select * from Motherboard where mbSlot like '%"+StoSlot+"%' and mbProSocket ='"+pro+"' and mbName like \"%"+src+"%\"";
                }else{
                    qry = "select * from Motherboard where mbProSocket ='"+pro+"' and mbName like \"%"+src+"%\"";
                }
            }
            else if (isProPicked && search){
                qry = "select * from Motherboard where mbProSocket = \""+pro+"\" and mbName like \"%"+src+"%\"";
            }
            else if (isRamPicked && search){
                qry = "select * from Motherboard where mbRamVer = \""+RamVer
                        +"\" and mbRamMaxSpd >= \""+RamSpd
                        +"\"and mbName like \"%"+src+"%\" and mbRamMaxCap >= '"+RamCap+"'";
            }
            else if (search){
                qry = "select * from Motherboard where mbName like \"%"+src+"%\"";
            }
            else if (isStoPicked && search){
                if (StoSlot.equals("M.2")){
                    qry = "select * from Motherboard where mbSlot like '%"+StoSlot+"%' and mbName like \"%"+src+"%\"";
                }else{
                    qry = "select * from Motherboard where mbName like \"%"+src+"%\"";
                }
            }
            else if (isStoPicked && isProPicked){
                if (StoSlot.equals("M.2")){
                    qry = "select * from Motherboard where mbSlot like '%"+StoSlot+"%' and mbProSocket ='"+pro+"'";
                }else{
                    qry = "select * from Motherboard where mbProSocket ='"+pro+"'";
                }
            }
            else if (isStoPicked && isRamPicked){
                if (StoSlot.equals("M.2")){
                    qry = "select * from Motherboard where mbSlot like '%"+StoSlot+"%' and mbRamVer = '"+RamVer
                            +"' and mbRamMaxSpd >= '"+RamSpd
                            +"' and mbRamMaxCap >= '"+RamCap+"'";
                }else{
                    qry = "select * from Motherboard where mbRamVer = '"+RamVer
                            +"' and mbRamMaxSpd >= '"+RamSpd
                            +"' and mbRamMaxCap >= '"+RamCap+"'";
                }
            }
            else if (isRamPicked && isProPicked){
                qry = "select * from Motherboard where mbRamMaxSpd >= \""+RamSpd+"\" and mbProSocket = \""+pro+"\" and mbRamMaxCap >= '"+RamCap+"'";
            }
            else if (isProPicked){
                qry = "select * from Motherboard where mbProSocket = \""+pro+"\"";
            }
            else if (isRamPicked){
                qry = "select * from Motherboard where mbRamVer = \""+RamVer+"\" and mbRamMaxSpd >= \""+RamSpd+"\" and mbRamMaxCap >= '"+RamCap+"'";
            }
            else if (isStoPicked){
                if (StoSlot.equals("M.2")){
                    qry = "select * from Motherboard where mbSlot like '%"+StoSlot+"%'";
                }else{
                    qry = "select * from Motherboard";
                }
            }
            else if (isGenerate){
                if (mbFac!="0"){
                    qry = "select * from Motherboard where mbProSocket = '"+pro+"' and mbName like '"+mbFac+"%' and mbPrice <="+mbBudget;
                }else {
                    qry = "select * from Motherboard where mbProSocket = '"+pro+"' and mbPrice <="+mbBudget;
                }
            }
            else if (xtra){
                if (RamVer.equals("")){ //gene xtra
                    if (mbFac!="0"){
                        qry = "select * from Motherboard where mbID not like '"+id+"' and mbName like '"+mbFac+"%' and mbProSocket = \""+pro
                                +"\" and mbRamMaxCap >"+ RamCap+" and mbRamMaxSpd >"+RamSpd+" and mbPrice <="+mbBudget;
                    }else {
                        qry = "select * from Motherboard where mbID not like '"+id+"' and mbProSocket = \""+pro
                                +"\" and mbRamMaxCap >"+ RamCap+" and mbRamMaxSpd >"+RamSpd+" and mbPrice <="+mbBudget;
                    }
                }else if(RamVer.equals("1")){ //gene xtra
                    if (mbFac!="0"){
                        qry = "select * from Motherboard where mbID not like '"+id+"' and mbName like '"+mbFac+"%' and mbProSocket = \""+pro
                                +"\" and mbRamMaxCap >"+ RamCap+" and mbRamMaxSpd >="+RamSpd+" and mbPrice <="+mbBudget;
                    }else {
                        qry = "select * from Motherboard where mbID not like '"+id+"' and mbProSocket = \""+pro
                                +"\" and mbRamMaxCap >"+ RamCap+" and mbRamMaxSpd >="+RamSpd+" and mbPrice <="+mbBudget;
                    }
                }else if (RamVer.equals("2")){ //gene xtra
                    if (mbFac!="0"){
                        qry = "select * from Motherboard where mbID not like '"+id+"' and mbName like '"+mbFac+"%' and mbProSocket = \""+pro
                                +"\" and mbRamMaxCap >="+ RamCap+" and mbRamMaxSpd >"+RamSpd+" and mbPrice <="+mbBudget;
                    }else {
                        qry = "select * from Motherboard where mbID not like '"+id+"' and mbProSocket = \""+pro
                                +"\" and mbRamMaxCap >="+ RamCap+" and mbRamMaxSpd >"+RamSpd+" and mbPrice <="+mbBudget;
                    }
                }
                else {  //Upgrade
                    if(mbFac.equals("")){
                        if (StoSlot.equals("M.2")){
                            qry = "select * from Motherboard where mbID not like '"+id+"' and mbProSocket = \""+pro+"\" and mbSlot like '%"+StoSlot+"%' " +
                                    "and mbRamMaxCap >"+ RamCap+" and mbSize in ("+RamVer+") and mbRamMaxSpd >"+RamSpd+" and mbPrice <="+mbBudget;
                        }else{
                            qry = "select * from Motherboard where mbID not like '"+id+"' and mbProSocket = \""+pro+"\" and mbRamMaxCap >'"+ RamCap+"' and mbRamMaxSpd >'"+RamSpd+"' and mbSize in ("+RamVer+") and mbPrice <="+mbBudget;
                        }
                    }else {
                        if (StoSlot.equals("M.2")){
                            qry = "select * from Motherboard where mbID not like '"+id+"' and mbProSocket = \""+pro+"\" and mbSlot like '%"+StoSlot+"%' " +
                                    "and mbRamMaxCap >="+ RamCap+" and mbSize in ("+RamVer+") and mbRamMaxSpd >"+RamSpd+" and mbPrice <="+mbBudget;
                        }else{
                            qry = "select * from Motherboard where mbID not like '"+id+"' and mbProSocket = \""+pro+"\" and mbRamMaxCap >='"+ RamCap+"' and mbRamMaxSpd >'"+RamSpd+"' and mbSize in ("+RamVer+") and mbPrice <="+mbBudget;
                        }
                    }
                }
            }
            else {
                qry = "select * from Motherboard";
            }
            c = db.rawQuery(qry, null);
            if (c == null) return null;

            c.moveToFirst();
            do {
                Motherboard mb = new Motherboard(c.getInt(c.getColumnIndex("mbID"))
                                                ,c.getString(c.getColumnIndex("mbName"))
                                                ,c.getString(c.getColumnIndex("mbProSocket"))
                                                ,c.getString(c.getColumnIndex("mbChipset"))
                                                ,c.getString(c.getColumnIndex("mbRamVer"))
                                                ,c.getInt(c.getColumnIndex("mbRamSlot"))
                                                ,c.getInt(c.getColumnIndex("mbRamMaxCap"))
                                                ,c.getInt(c.getColumnIndex("mbRamMaxSpd"))
                                                ,c.getString(c.getColumnIndex("mbSlot"))
                                                ,c.getString(c.getColumnIndex("mbSize"))
                                                ,c.getString(c.getColumnIndex("mbPin"))
                                                ,c.getInt(c.getColumnIndex("mbPrice")));
                temp.add(mb);

            } while (c.moveToNext());
            c.close();

        }
        catch(Exception e){

        }
        db.close();
        return temp;


    }

    public List<RAM> getAllRAM(Boolean cek, String id
                              , Boolean isMbPicked, String ramVer, String ramSpeed, Boolean search, String src
                                , Boolean isProPicked, String proRamSpeed, String proRamVer
                                , Boolean isGenerate, String ramBudget , String mbMaxCap) {
        List<RAM> temp = new ArrayList<RAM>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        String qry;

        try {
            if(cek == true){
                qry = "select * from RAM where ramID = "+id;
            }
            else if(isProPicked && isMbPicked && search){
                qry = "select * from RAM where ramVer = \""+ ramVer +"\" and ramCap <="+ mbMaxCap +" AND ramSpd <= "+ramSpeed+" and ramName like \"%"+src+"%\"";
            }
            else if(isProPicked && isMbPicked ){
                qry = "select * from RAM where ramVer = \""+ ramVer +"\" and ramCap <="+ mbMaxCap +" AND ramSpd <= "+ramSpeed; //+"\"";
            }

            else if(isMbPicked && search){
                qry = "select * from RAM where ramVer = \""+ ramVer +"\" and ramCap <="+ mbMaxCap +" AND ramSpd <= "+ramSpeed+" and ramName like \"%"+src+"%\"";
            }
            else if(isProPicked && search){
                qry = "select * from RAM where ramVer = \""+ proRamVer +"\" AND and ramName like \"%"+src+"%\"";
            }
            else if(isMbPicked ){
                qry = "select * from RAM where ramVer = \""+ ramVer +"\" and ramCap <="+ mbMaxCap +" AND ramSpd <= "+ramSpeed; //+"\"";
            }
            else if(isProPicked ){
                qry = "select * from RAM where ramVer = \""+ proRamVer +"\"";
            }
            else if(search ){
                qry = "select * from RAM where ramName like \"%"+src+"%\"";
            }
            else if(isGenerate){
                if(id.equals("")){
                    qry = "select * from RAM where ramVer = \""+ ramVer +"\" and ramCap <"+mbMaxCap+" AND ramSpd <= "+ramSpeed
                            +" AND ramSpd <= "+ramSpeed+" and ramCap <="+ mbMaxCap +" and ramPrice <="+ramBudget;
                }else {
                    if(src.equals("1")){
                        qry = "select * from RAM where ramID not like '"+id+"' and ramCap >"+proRamVer+" AND ramSpd > "+proRamSpeed+" and ramVer = \""+ ramVer
                                +"\" AND ramSpd <= "+ramSpeed+" and ramCap <="+ mbMaxCap +" and ramPrice <="+ramBudget;
                    }else if(src.equals("2")){
                        qry = "select * from RAM where ramID not like '"+id+"' and ramCap >"+proRamVer+" AND ramSpd >= "+proRamSpeed+" and ramVer = \""+ ramVer
                                +"\" AND ramSpd <= "+ramSpeed+" and ramCap <="+ mbMaxCap +" and ramPrice <="+ramBudget;
                    }else {
                        qry = "select * from RAM where ramID not like '"+id+"' and ramCap >="+proRamVer+" AND ramSpd > "+proRamSpeed+" and ramVer = \""+ ramVer
                                +"\" AND ramSpd <= "+ramSpeed+" and ramCap <="+ mbMaxCap +" and ramPrice <="+ramBudget;
                    }
                }
            }
            else {
                qry = "select * from RAM" ;
            }
            c = db.rawQuery(qry, null);
            if (c == null) return null;
            c.moveToFirst();
            do {
                RAM rm = new RAM(c.getInt(c.getColumnIndex("ramID"))
                                ,c.getString(c.getColumnIndex("ramName"))
                                ,c.getInt(c.getColumnIndex("ramCap"))
                                ,c.getString(c.getColumnIndex("ramVer"))
                                ,c.getInt(c.getColumnIndex("ramSpd"))
                                ,c.getInt(c.getColumnIndex("ramPrice")));
                temp.add(rm);

            } while (c.moveToNext());
            c.close();

        }
        catch(Exception e){

        }
        db.close();
        return temp;


    }

    public List<VGA> getAllVGA(Boolean cek, String id, Boolean search, String src, Boolean isGenerate, String vgaBudget) {
        List<VGA> temp = new ArrayList<VGA>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        String qry;
        try {
            if(cek){
                qry = "select * from VGA where vgaID = "+id;
            } else if(search){
                qry = "select * from VGA where vgaName like \"%"+src+"%\"";
            }else if(isGenerate){
                if(id.equals("")) {
                    qry = "select * from VGA where vgaPrice <= " + vgaBudget;
                }else {
                    qry = "select * from VGA vgaID not like '"+id+"' and where vgaPrice <= " + vgaBudget;
                }
            }else {
                qry = "select * from VGA" ;
            }
            c = db.rawQuery(qry, null);
            if (c == null) return null;

            c.moveToFirst();
            do {
                VGA vg = new VGA(c.getInt(c.getColumnIndex("vgaID"))
                                ,c.getString(c.getColumnIndex("vgaName"))
                                ,c.getInt(c.getColumnIndex("vgaCoreUnit"))
                                ,c.getInt(c.getColumnIndex("vgaCoreSpd"))
                        ,c.getInt(c.getColumnIndex("vgaCoreMaxSpd"))
                                ,c.getInt(c.getColumnIndex("vgaSlotUsed"))
                                ,c.getString(c.getColumnIndex("vgaInterface"))
                                ,c.getString(c.getColumnIndex("vgaPin"))
                                ,c.getInt(c.getColumnIndex("vgaTdp"))
                                ,c.getInt(c.getColumnIndex("vgaSugPow"))
                                ,c.getInt(c.getColumnIndex("vgaMemSpd"))
                                ,c.getInt(c.getColumnIndex("vgaMemCap"))
                                ,c.getString(c.getColumnIndex("vgaMemType"))
                                ,c.getInt(c.getColumnIndex("vgaPrice")));
                temp.add(vg);

            } while (c.moveToNext());
            c.close();

        }
        catch(Exception e){

        }
        db.close();
        return temp;


    }
    public List<VGA> getAllGPU(String qry) {
        List<VGA> temp = new ArrayList<VGA>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        try {
            c = db.rawQuery(qry, null);
            if (c == null) return null;

            c.moveToFirst();
            do {
                VGA vg = new VGA(c.getInt(c.getColumnIndex("vgaID"))
                        ,c.getString(c.getColumnIndex("vgaName"))
                        ,c.getInt(c.getColumnIndex("vgaCoreUnit"))
                        ,c.getInt(c.getColumnIndex("vgaCoreSpd"))
                        ,c.getInt(c.getColumnIndex("vgaCoreMaxSpd"))
                        ,c.getInt(c.getColumnIndex("vgaSlotUsed"))
                        ,c.getString(c.getColumnIndex("vgaInterface"))
                        ,c.getString(c.getColumnIndex("vgaPin"))
                        ,c.getInt(c.getColumnIndex("vgaTdp"))
                        ,c.getInt(c.getColumnIndex("vgaSugPow"))
                        ,c.getInt(c.getColumnIndex("vgaMemSpd"))
                        ,c.getInt(c.getColumnIndex("vgaMemCap"))
                        ,c.getString(c.getColumnIndex("vgaMemType"))
                        ,c.getInt(c.getColumnIndex("vgaPrice")));
                temp.add(vg);

            } while (c.moveToNext());
            c.close();

        }
        catch(Exception e){

        }
        db.close();
        return temp;


    }

    public List<Storage> getAllSto(Boolean cek, String id, Boolean search, String src, Boolean isMbPicked
            , String mbSlot, Boolean isGenerate, String stoBudget, String Type, Boolean xtra, String stoCap) {
        List<Storage> temp = new ArrayList<Storage>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        String qry;
        try {
            if(cek){
                qry = "select * from Storage where stoID = "+id;
            }else if(isMbPicked){
                String slotM  = "M.2";
                if ( mbSlot.toLowerCase().contains(slotM.toLowerCase()) ) {
                    qry = "select * from Storage";
                } else {
                    qry = "select * from Storage where stoName not like '%M.2%'";
                }

            }else if(search) {
                qry = "select * from Storage where stoName like \"%" + src + "%\"";

            }else if(isGenerate) {
                String slotM = "M.2";
                if (mbSlot.toLowerCase().contains(slotM.toLowerCase())) {
                    if (Type.equals("HDD")) {
                        qry = "select * from Storage where stoName like 'HDD%' and stoPrice <=" + stoBudget;
                    } else if (Type.equals("SSD")) {
                        qry = "select * from Storage where stoName like 'SSD%' and stoPrice <=" + stoBudget;
                    } else  {
                        qry = "select * from Storage stoPrice <=" + stoBudget;
                    }
                } else {
                    if (Type.equals("HDD")) {
                        qry = "select * from Storage where stoName like 'HDD%' and stoName not like '%M.2%' and stoPrice <=" + stoBudget;
                    } else if (Type.equals("SSD")) {
                        qry = "select * from Storage where stoName like 'SSD%' and stoName  not like '%M.2%' and stoPrice <=" + stoBudget;
                    } else {
                        qry = "select * from Storage where stoName not like '%M.2%' and stoPrice <=" + stoBudget;
                    }
                }
            }else if(xtra) {
                String slotM = "M.2";
                if (mbSlot.toLowerCase().contains(slotM.toLowerCase())) {
                    if (Type.equals("HDD")) {
                        qry = "select * from Storage where stoName like 'HDD%' and stoCap >"+stoCap+" and stoPrice <=" + stoBudget;
                    } else if (Type.equals("SSD")) {
                        qry = "select * from Storage where stoName like 'SSD%' and stoCap >"+stoCap+" and stoPrice <=" + stoBudget;
                    } else  {
                        qry = "select * from Storage stoPrice <=" + stoBudget+"and stoCap >"+stoCap;
                    }
                } else {
                    if (Type.equals("HDD")) {
                        qry = "select * from Storage where stoName like 'HDD%' and stoName not like '%M.2%' and stoPrice <=" + stoBudget+"and stoCap >"+stoCap;
                    } else if (Type.equals("SSD")) {
                        qry = "select * from Storage where stoName like 'SSD%' and stoName  not like '%M.2%' and stoPrice <=" + stoBudget+"and stoCap >"+stoCap;
                    } else {
                        qry = "select * from Storage where stoName not like '%M.2%' and stoPrice <=" + stoBudget+"and stoCap >"+stoCap;
                    }
                }
            }else {
                qry = "select * from Storage";
            }
            c = db.rawQuery(qry, null);
            if (c == null) return null;
            c.moveToFirst();
            do {
                Storage st = new Storage(c.getInt(c.getColumnIndex("stoID"))
                        ,c.getString(c.getColumnIndex("stoName"))
                        ,c.getInt(c.getColumnIndex("stoCap"))
                        ,c.getInt(c.getColumnIndex("stoRotSpd"))
                        ,c.getInt(c.getColumnIndex("stoRead"))
                        ,c.getInt(c.getColumnIndex("stoWrite"))
                        ,c.getString(c.getColumnIndex("stoInterface"))
                        ,c.getString(c.getColumnIndex("stoSize"))
                        ,c.getInt(c.getColumnIndex("stoPrice")));
                temp.add(st);

            } while (c.moveToNext());
            c.close();

        }
        catch(Exception e){

        }
        db.close();
        return temp;


    }

    public List<PSU> getAllPSU(Boolean cek, String id, String needPow, Boolean search, String src, Boolean isGenerate, String psuBudget) {
        List<PSU> temp = new ArrayList<PSU>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        String qry;
        try {
            if(cek){
                qry = "select * from PSU where psuID= "+id;
            }
            else if(search) {
                qry = "select * from PSU where psuName like \"%"+src+"%\"";
            }
            else if(isGenerate) {
                qry = "select * from PSU where psuVolt >= \""+needPow+"\" and psuPrice <="+psuBudget;
            }
            else if(!needPow.equals("0") && !needPow.equals("") && needPow!=null) {
                qry = "select * from PSU where psuVolt >= "+needPow;
            }

            else {
                qry = "select * from PSU";
            }
            c = db.rawQuery(qry, null);
            if (c == null) return null;

            c.moveToFirst();
            do {
                PSU psu = new PSU(c.getInt(c.getColumnIndex("psuID"))
                        ,c.getString(c.getColumnIndex("psuName"))
                        ,c.getString(c.getColumnIndex("psuType"))
                        ,c.getString(c.getColumnIndex("psuRank"))
                        ,c.getInt(c.getColumnIndex("psuVolt"))
                        ,c.getString(c.getColumnIndex("psuPin"))
                        ,c.getString(c.getColumnIndex("psuSize"))
                        ,c.getInt(c.getColumnIndex("psuPrice")));
                temp.add(psu);

            } while (c.moveToNext());
            c.close();

        }
        catch(Exception e){

        }
        db.close();
        return temp;
    }

    public List<Case> getAllCase(Boolean cek, String id, Boolean isMbPicked, String  MbSize,
                                 Boolean search, String src ,Boolean isGenerate, String csBudget)  {
        List<Case> temp = new ArrayList<Case>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String Size="";
        if(MbSize.equals("E-ATX")){
            Size="'E-ATX'";
        }else if(MbSize.equals("ATX")){
            Size="'E-ATX','ATX'";
        }else if(MbSize.equals("Micro-ATX")){
            Size="'E-ATX','ATX','Micro-ATX'";
        }else if(MbSize.equals("Mini-ITX")){
            Size="'E-ATX','ATX','Micro-ATX','Mini-ITX'";
        }

        String qry;
        try {
            if(cek){
                qry = "select * from Casing where caseID = "+id;
            }
            else if(isMbPicked && search){
                qry = "select * from Casing where caseSize in ("+ Size +") and caseName like \"%"+src+"%\"";
            }
            else if(isMbPicked){
                qry = "select * from Casing where caseSize in ("+ Size +")";
            }
            else if(search){
                qry = "select * from Casing where caseName like \"%"+src+"%\""; //// combination
            }else if(isGenerate){
                qry = "select * from Casing where caseSize in ("+ Size +") and casePrice <="+csBudget;
            }
            else {
                qry = "select * from Casing";

            }
            c = db.rawQuery(qry, null);

            if (c == null) return null;

            c.moveToFirst();
            do {
                Case cs = new Case(c.getInt(c.getColumnIndex("caseID"))
                        ,c.getString(c.getColumnIndex("caseName"))
                        ,c.getString(c.getColumnIndex("caseSize"))
                        ,c.getInt(c.getColumnIndex("caseExSlot"))
                        ,c.getInt(c.getColumnIndex("caseDrive"))
                        ,c.getInt(c.getColumnIndex("caseSto35"))
                        ,c.getInt(c.getColumnIndex("caseSto25"))
                        ,c.getInt(c.getColumnIndex("casePrice")));
                temp.add(cs);

            } while (c.moveToNext());
            c.close();
        }
        catch(Exception e){

        }
        db.close();
        return temp;

    }

    public List<build> getAllBuild(Boolean cek, Boolean search, String src) {
        List<build> temp = new ArrayList<build>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            if(cek) {
                c = db.rawQuery("select * from build where buildID like '3%'", null);
            }else if(search && cek) {
                c = db.rawQuery("select * from build where buildName like \"%"+src+"%\" and buildID like '3%'", null);
            }
            else if(search && !cek) {
                c = db.rawQuery("select * from build where buildName like \"%"+src+"%\" and buildID like '1%' or buildName like '%"+src+"%' and buildID like '2%'", null);
            }
            else {
                c = db.rawQuery("select * from build where buildID like '1%' or buildID like '2%'", null);
            }
            if (c == null) return null;

            c.moveToFirst();
            do {
                build bd = new build(c.getInt(c.getColumnIndex("buildID"))
                        ,c.getString(c.getColumnIndex("buildName"))
                        ,c.getInt(c.getColumnIndex("mbID"))
                        ,c.getInt(c.getColumnIndex("proID"))
                        ,c.getInt(c.getColumnIndex("ramID"))
                        ,c.getInt(c.getColumnIndex("vgaID"))
                        ,c.getInt(c.getColumnIndex("stoID"))
                        ,c.getInt(c.getColumnIndex("psuID"))
                        ,c.getInt(c.getColumnIndex("caseID"))
                        ,c.getString(c.getColumnIndex("Date"))
                        ,c.getInt(c.getColumnIndex("totPrice")));
                temp.add(bd);

            } while (c.moveToNext());
            c.close();
        }
        catch(Exception e){
        }
        db.close();
        return temp;
    }

    ////----------------------------------------------

}
