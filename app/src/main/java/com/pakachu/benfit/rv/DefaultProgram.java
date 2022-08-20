package com.pakachu.benfit.rv;

import android.app.Activity;

import com.pakachu.benfit.DBHandler_Programlar;

import java.util.ArrayList;

public class DefaultProgram {
    public void STANDARTSPLIT(Activity activity, int size) {
        DBHandler_Programlar dbHandler_programlar = new DBHandler_Programlar(activity);
        if (size == 0) {
            dbHandler_programlar.addData("standartsplit");
            String createsql = "CREATE TABLE standartsplit " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " pazartesi TEXT, " +
                    " sali TEXT, " +
                    " carsamba TEXT, " +
                    " persembe TEXT, " +
                    " cuma TEXT, " +
                    " cumartesi TEXT, " +
                    " pazar TEXT) ";
            dbHandler_programlar.executeSQL(createsql);
            String[] insertPazartesi = new String[9];
            insertPazartesi[0] = "INSERT INTO standartsplit (pazartesi) VALUES ('ısınma')";
            insertPazartesi[1] = "INSERT INTO standartsplit (pazartesi) VALUES ('bench press')";
            insertPazartesi[2] = "INSERT INTO standartsplit (pazartesi) VALUES ('incline dumbell press')";
            insertPazartesi[3] = "INSERT INTO standartsplit (pazartesi) VALUES ('chest press')";
            insertPazartesi[4] = "INSERT INTO standartsplit (pazartesi) VALUES ('cable crossover')";
            insertPazartesi[5] = "INSERT INTO standartsplit (pazartesi) VALUES ('machine fly')";
            insertPazartesi[6] = "INSERT INTO standartsplit (pazartesi) VALUES ('bicep curl')";
            insertPazartesi[7] = "INSERT INTO standartsplit (pazartesi) VALUES ('hammer curl')";
            insertPazartesi[8] = "INSERT INTO standartsplit (pazartesi) VALUES ('kardiyo')";
            for (String s : insertPazartesi
            ) {
                dbHandler_programlar.executeSQL(s);
            }
            String[] insertSALI = new String[9];
            insertSALI[0] = "INSERT INTO standartsplit (sali) VALUES ('ısınma')";
            insertSALI[1] = "INSERT INTO standartsplit (sali) VALUES ('barfiks')";
            insertSALI[2] = "INSERT INTO standartsplit (sali) VALUES ('machine row')";
            insertSALI[3] = "INSERT INTO standartsplit (sali) VALUES ('lat pull')";
            insertSALI[4] = "INSERT INTO standartsplit (sali) VALUES ('seated cable row')";
            insertSALI[5] = "INSERT INTO standartsplit (sali) VALUES ('hyperextension')";
            insertSALI[6] = "INSERT INTO standartsplit (sali) VALUES ('dumbell skullcrusher')";
            insertSALI[7] = "INSERT INTO standartsplit (sali) VALUES ('close grip bench press')";
            insertSALI[8] = "INSERT INTO standartsplit (sali) VALUES ('kardiyo')";
            for (String s : insertSALI
            ) {
                dbHandler_programlar.executeSQL(s);
            }
            String[] insertCARSAMBA = new String[8];
            insertCARSAMBA[0] = "INSERT INTO standartsplit (carsamba) VALUES ('ısınma')";
            insertCARSAMBA[1] = "INSERT INTO standartsplit (carsamba) VALUES ('lunge')";
            insertCARSAMBA[2] = "INSERT INTO standartsplit (carsamba) VALUES ('squat')";
            insertCARSAMBA[3] = "INSERT INTO standartsplit (carsamba) VALUES ('leg press')";
            insertCARSAMBA[4] = "INSERT INTO standartsplit (carsamba) VALUES ('leg extension')";
            insertCARSAMBA[5] = "INSERT INTO standartsplit (carsamba) VALUES ('leg curl')";
            insertCARSAMBA[6] = "INSERT INTO standartsplit (carsamba) VALUES ('calf raise')";
            insertCARSAMBA[7] = "INSERT INTO standartsplit (carsamba) VALUES ('kardiyo')";
            for (String s : insertCARSAMBA
            ) {
                dbHandler_programlar.executeSQL(s);
            }
            String[] insertPERSEMBE = new String[10];
            insertPERSEMBE[0] = "INSERT INTO standartsplit (persembe) VALUES ('ısınma')";
            insertPERSEMBE[1] = "INSERT INTO standartsplit (persembe) VALUES ('dips')";
            insertPERSEMBE[2] = "INSERT INTO standartsplit (persembe) VALUES ('dumbell shoulder press')";
            insertPERSEMBE[3] = "INSERT INTO standartsplit (persembe) VALUES ('lateral raise')";
            insertPERSEMBE[4] = "INSERT INTO standartsplit (persembe) VALUES ('front raise')";
            insertPERSEMBE[5] = "INSERT INTO standartsplit (persembe) VALUES ('rear delt fly')";
            insertPERSEMBE[6] = "INSERT INTO standartsplit (persembe) VALUES ('preacher curl')";
            insertPERSEMBE[7] = "INSERT INTO standartsplit (persembe) VALUES ('cable curl')";
            insertPERSEMBE[8] = "INSERT INTO standartsplit (persembe) VALUES ('rope pushdown')";
            insertPERSEMBE[9] = "INSERT INTO standartsplit (persembe) VALUES ('cable pushdown')";
            for (String s : insertPERSEMBE
            ) {
                dbHandler_programlar.executeSQL(s);
            }
            dbHandler_programlar.executeSQL("INSERT INTO standartsplit (cuma) VALUES ('off')");
            dbHandler_programlar.executeSQL("INSERT INTO standartsplit (cumartesi) VALUES ('off')");
            dbHandler_programlar.executeSQL("INSERT INTO standartsplit (pazar) VALUES ('off')");
        }
    }
    public void STANDARTFULLBODY(Activity activity, int size) {
        DBHandler_Programlar dbHandler_programlar = new DBHandler_Programlar(activity);
        if (size == 0) {
            dbHandler_programlar.addData("standartfullbody");
            String createsql = "CREATE TABLE standartfullbody " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " pazartesi TEXT, " +
                    " sali TEXT, " +
                    " carsamba TEXT, " +
                    " persembe TEXT, " +
                    " cuma TEXT, " +
                    " cumartesi TEXT, " +
                    " pazar TEXT) ";
            dbHandler_programlar.executeSQL(createsql);
            String[] insertPazartesi = new String[8];
            insertPazartesi[0] = "INSERT INTO standartfullbody (pazartesi) VALUES ('ısınma')";
            insertPazartesi[1] = "INSERT INTO standartfullbody (pazartesi) VALUES ('bench press')";
            insertPazartesi[2] = "INSERT INTO standartfullbody (pazartesi) VALUES ('chest press')";
            insertPazartesi[3] = "INSERT INTO standartfullbody (pazartesi) VALUES ('lat pull')";
            insertPazartesi[4] = "INSERT INTO standartfullbody (pazartesi) VALUES ('seated cable row')";
            insertPazartesi[5] = "INSERT INTO standartfullbody (pazartesi) VALUES ('squat')";
            insertPazartesi[6] = "INSERT INTO standartfullbody (pazartesi) VALUES ('leg press')";
            insertPazartesi[7] = "INSERT INTO standartfullbody (pazartesi) VALUES ('kardiyo')";
            for (String s : insertPazartesi
            ) {
                dbHandler_programlar.executeSQL(s);
            }
            String[] insertSALI = new String[8];
            insertSALI[0] = "INSERT INTO standartfullbody (sali) VALUES ('ısınma')";
            insertSALI[1] = "INSERT INTO standartfullbody (sali) VALUES ('incline dumbell press')";
            insertSALI[2] = "INSERT INTO standartfullbody (sali) VALUES ('dumbell fly')";
            insertSALI[3] = "INSERT INTO standartfullbody (sali) VALUES ('dumbell row')";
            insertSALI[4] = "INSERT INTO standartfullbody (sali) VALUES ('machine row')";
            insertSALI[5] = "INSERT INTO standartfullbody (sali) VALUES ('lunge')";
            insertSALI[6] = "INSERT INTO standartfullbody (sali) VALUES ('leg press')";
            insertSALI[7] = "INSERT INTO standartfullbody (sali) VALUES ('kardiyo')";
            for (String s : insertSALI
            ) {
                dbHandler_programlar.executeSQL(s);
            }
            String[] insertCARSAMBA = new String[8];
            insertCARSAMBA[0] = "INSERT INTO standartfullbody (carsamba) VALUES ('ısınma')";
            insertCARSAMBA[1] = "INSERT INTO standartfullbody (carsamba) VALUES ('chest press')";
            insertCARSAMBA[2] = "INSERT INTO standartfullbody (carsamba) VALUES ('dumbell shoulder press')";
            insertCARSAMBA[3] = "INSERT INTO standartfullbody (carsamba) VALUES ('lat pull')";
            insertCARSAMBA[4] = "INSERT INTO standartfullbody (carsamba) VALUES ('seated cable row')";
            insertCARSAMBA[5] = "INSERT INTO standartfullbody (carsamba) VALUES ('hyperextension')";
            insertCARSAMBA[6] = "INSERT INTO standartfullbody (carsamba) VALUES ('squat')";
            insertCARSAMBA[7] = "INSERT INTO standartfullbody (carsamba) VALUES ('kardiyo')";
            for (String s : insertCARSAMBA
            ) {
                dbHandler_programlar.executeSQL(s);
            }
            String[] insertPERSEMBE = new String[8];
            insertPERSEMBE[0] = "INSERT INTO standartfullbody (persembe) VALUES ('ısınma')";
            insertPERSEMBE[1] = "INSERT INTO standartfullbody (persembe) VALUES ('dumbell shoulder press')";
            insertPERSEMBE[2] = "INSERT INTO standartfullbody (persembe) VALUES ('lateral raise')";
            insertPERSEMBE[3] = "INSERT INTO standartfullbody (persembe) VALUES ('rear delt fly')";
            insertPERSEMBE[4] = "INSERT INTO standartfullbody (persembe) VALUES ('facepull')";
            insertPERSEMBE[5] = "INSERT INTO standartfullbody (persembe) VALUES ('leg extension')";
            insertPERSEMBE[6] = "INSERT INTO standartfullbody (persembe) VALUES ('leg curl')";
            insertPERSEMBE[7] = "INSERT INTO standartfullbody (persembe) VALUES ('kardiyo')";
            for (String s : insertPERSEMBE
            ) {
                dbHandler_programlar.executeSQL(s);
            }
            dbHandler_programlar.executeSQL("INSERT INTO standartfullbody (cuma) VALUES ('off')");
            dbHandler_programlar.executeSQL("INSERT INTO standartfullbody (cumartesi) VALUES ('off')");
            dbHandler_programlar.executeSQL("INSERT INTO standartfullbody (pazar) VALUES ('off')");
        }
} }
