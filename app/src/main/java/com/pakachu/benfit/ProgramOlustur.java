package com.pakachu.benfit;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class ProgramOlustur extends Fragment {

    Button btn_olustur_ekle, btn_olustur_tableSil, btn_olustur_detayEkle,
            btn_olustur_detaySil, btn_olustur_hareketEkle, btn_olustur_hareketSil,
            btn_olustur_cikart, btn_olustur_temizle, btn_olustur_kompleTemizle, btn_olustur_hareketAktar,
            btn_olustur_yerel;
    Spinner spin_olustur_tables, spin_olustur_gunler, spin_olustur_butunDetaylar;
    DBHandler_Programlar dbHandlerProgramlar;
    DBHandler_LOCAL dbHandlerLOCAL;
    EditText et_olustur_tablename, et_olustur_detay, et_olustur_hareket;
    ListView lv_olustur_hareketler, lv_olustur_butunHareketler;
    String secilenButunHareket = "";
    String secilenHareket = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_program_olustur, container, false);

        INITIALIZE(view);
        GetirTables();
        DetaylariGetirToSpinner();
        ButunHareketleriGetirToList();

        btn_olustur_yerel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultAktar();
                Toast.makeText(getActivity(), "Yerel liste hazırlandı.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_olustur_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_olustur_tablename.getText().toString().trim().equals("")) {
                    dbHandlerProgramlar.addData(et_olustur_tablename.getText().toString());
                    String sql = "CREATE TABLE " + et_olustur_tablename.getText().toString().trim() + " " +
                            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " pazartesi TEXT, " +
                            " sali TEXT, " +
                            " carsamba TEXT, " +
                            " persembe TEXT, " +
                            " cuma TEXT, " +
                            " cumartesi TEXT, " +
                            " pazar TEXT) ";
                    dbHandlerProgramlar.executeSQL(sql);
                    Toast.makeText(getActivity(), et_olustur_tablename.getText() + " eklendi.", Toast.LENGTH_SHORT).show();
                    et_olustur_tablename.setText(null);
                    GetirTables();
                } else {
                    Toast.makeText(getActivity(), "Program adı boş olamaz!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_olustur_tableSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spin_olustur_tables.getSelectedItem() != null) {

                    String sql = "DROP TABLE IF EXISTS " + spin_olustur_tables.getSelectedItem().toString();
                    dbHandlerProgramlar.executeSQL(sql);
                    String sql2 = "DELETE FROM programlarim WHERE ad='" + spin_olustur_tables.getSelectedItem().toString() + "'";
                    dbHandlerProgramlar.executeSQL(sql2);

                    Toast.makeText(getActivity(), spin_olustur_tables.getSelectedItem() + " silindi.", Toast.LENGTH_SHORT).show();
                    GetirTables();
                    HareketleriGetirToList();
                } else
                    Toast.makeText(getActivity(), "Silinebilecek program mevcut değil!", Toast.LENGTH_SHORT).show();

            }
        });


        btn_olustur_hareketAktar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!secilenButunHareket.equals("")) {
                    if (spin_olustur_gunler.getSelectedItem() != null) {
                        String detay = "";
                        if (spin_olustur_butunDetaylar.getCount() > 0)
                            detay = spin_olustur_butunDetaylar.getSelectedItem().toString();
                        if (detay.equals(""))
                            dbHandlerProgramlar.addData(spin_olustur_tables.getSelectedItem().toString(), spin_olustur_gunler.getSelectedItem().toString(), secilenButunHareket);
                        else
                            dbHandlerProgramlar.addData(spin_olustur_tables.getSelectedItem().toString(), spin_olustur_gunler.getSelectedItem().toString(), secilenButunHareket + "\n(" + detay + ")");

                        Toast.makeText(getActivity(), secilenButunHareket + "\n" + detay + "\n" + spin_olustur_gunler.getSelectedItem().toString() + " ya aktarıldı.", Toast.LENGTH_SHORT).show();
                        HareketleriGetirToList();
                    } else
                        Toast.makeText(getActivity(), "Gün seçmeniz gerekiyor!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Aktarmak için lütfen hareket seçin!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_olustur_cikart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!secilenHareket.equals("")) {
                    String sql = "DELETE FROM " + spin_olustur_tables.getSelectedItem().toString() + " WHERE " + spin_olustur_gunler.getSelectedItem().toString() + "='" + secilenHareket + "'";
                    dbHandlerProgramlar.executeSQL(sql);
                    Toast.makeText(getActivity(), secilenHareket + " hareketi silindi.", Toast.LENGTH_SHORT).show();
                    HareketleriGetirToList();
                } else
                    Toast.makeText(getActivity(), "Silmek için hareketler listesinden hareket seçmeniz gerekiyor!", Toast.LENGTH_SHORT).show();

            }
        });

        btn_olustur_temizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lv_olustur_hareketler.getCount() > 0) {
                    String sql = "UPDATE " + spin_olustur_tables.getSelectedItem().toString() + " SET " + spin_olustur_gunler.getSelectedItem().toString() + "=NULL";
                    dbHandlerProgramlar.executeSQL(sql);
                    Toast.makeText(getActivity(), spin_olustur_gunler.getSelectedItem().toString() + " günü temizlendi.", Toast.LENGTH_SHORT).show();
                    HareketleriGetirToList();
                } else {
//                    Toast.makeText(getActivity(), "Silinecek hareket mevcut değil!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_olustur_kompleTemizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spin_olustur_tables.getSelectedItem() != null) {
                    String sql = "UPDATE " + spin_olustur_tables.getSelectedItem().toString() + " SET pazartesi=NULL, sali=NULL, carsamba=NULL, persembe=NULL, cuma=NULL, cumartesi=NULL, pazar=NULL";
                    dbHandlerProgramlar.executeSQL(sql);
                    Toast.makeText(getActivity(), "Bütün günler temizlendi.", Toast.LENGTH_SHORT).show();
                    HareketleriGetirToList();
                } else {
//                    Toast.makeText(getActivity(), "Komple temizlik için lütfen program seçin!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_olustur_hareketEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_olustur_hareket.getText().toString().trim().equals("")) {
                    dbHandlerLOCAL.addDataTo_FirstTable(et_olustur_hareket.getText().toString().trim());
                    Toast.makeText(getActivity(), "Yeni Hareket Eklendi.", Toast.LENGTH_SHORT).show();
                    et_olustur_hareket.setText("");
                    ButunHareketleriGetirToList();
                } else
                    Toast.makeText(getActivity(), "Hareket alanı boş bırakılamaz!", Toast.LENGTH_SHORT).show();

            }
        });

        lv_olustur_hareketler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                secilenHareket = (String) lv_olustur_hareketler.getItemAtPosition(position);

                //SET BACKGROUND COLOR
                for (int i = 0; i < lv_olustur_hareketler.getChildCount(); i++) {
                    if (position == i) {
                        lv_olustur_hareketler.getChildAt(i).setBackgroundColor(Color.GRAY);

                    } else {
                        lv_olustur_hareketler.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }


            }
        });

        lv_olustur_butunHareketler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                secilenButunHareket = (String) lv_olustur_butunHareketler.getItemAtPosition(position);

                //SET BACKGROUND COLOR
                for (int i = 0; i < lv_olustur_butunHareketler.getChildCount(); i++) {
                    if (position == i) {
                        lv_olustur_butunHareketler.getChildAt(i).setBackgroundColor(Color.GRAY);

                    } else {
                        lv_olustur_butunHareketler.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }


            }
        });

        btn_olustur_hareketSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!secilenButunHareket.equals("")) {
                    String sql = "DELETE FROM hareketler WHERE hareket='" + secilenButunHareket + "'";
                    dbHandlerLOCAL.executeSQL(sql);
                    Toast.makeText(getActivity(), secilenButunHareket + " hareketi silindi.", Toast.LENGTH_SHORT).show();
                    ButunHareketleriGetirToList();
                } else
                    Toast.makeText(getActivity(), "Silmek için bütün hareketler listesinden hareket seçmeniz gerekiyor!", Toast.LENGTH_SHORT).show();

            }
        });

        btn_olustur_detayEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_olustur_detay.getText().toString().trim().equals("")) {
                    dbHandlerLOCAL.addDataTo_SecondTable(et_olustur_detay.getText().toString().trim());
                    Toast.makeText(getActivity(), "Yeni Detay Eklendi.", Toast.LENGTH_SHORT).show();
                    et_olustur_detay.setText("");
                    DetaylariGetirToSpinner();
                } else
                    Toast.makeText(getActivity(), "Detay alanı boş bırakılamaz!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_olustur_detaySil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spin_olustur_butunDetaylar.getSelectedItem() != null) {
                    String sql = "DELETE FROM detaylar WHERE detay='" + spin_olustur_butunDetaylar.getSelectedItem().toString() + "'";
                    dbHandlerLOCAL.executeSQL(sql);
                    Toast.makeText(getActivity(), "Detay silindi.", Toast.LENGTH_SHORT).show();
                    DetaylariGetirToSpinner();
                } else
                    Toast.makeText(getActivity(), "Silinebilecek detay mevcut değil!", Toast.LENGTH_SHORT).show();

            }
        });

        spin_olustur_tables.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetirGunlerToSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_olustur_gunler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HareketleriGetirToList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void GetirTables() {
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        for (String s : dbHandlerProgramlar.getTables()
        ) {
            itemsAdapter.add(s);
        }
        spin_olustur_tables.setAdapter(itemsAdapter);
        if (dbHandlerProgramlar.getTables().size() > 0) {
            GetirGunlerToSpinner();
        } else {
            Toast.makeText(getActivity(), "Herhangi bir program mevcut değil! Lütfen yeni oluşturun.", Toast.LENGTH_SHORT).show();
            spin_olustur_gunler.setAdapter(null);
        }
    }

    private void GetirGunlerToSpinner() {
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        Cursor c = dbHandlerProgramlar.getData(spin_olustur_tables.getSelectedItem().toString());
        for (String s : c.getColumnNames()
        ) {
            if (!s.equals("id"))
                itemsAdapter.add(s);
        }
        spin_olustur_gunler.setAdapter(itemsAdapter);
        HareketleriGetirToList();
    }

    private void HareketleriGetirToList() {
        secilenHareket = "";
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        if (spin_olustur_tables.getCount() > 0) {
            Cursor c = dbHandlerProgramlar.getData(spin_olustur_tables.getSelectedItem().toString());
            int i = spin_olustur_gunler.getSelectedItemPosition() + 1;

            while (c.moveToNext()) {

                if (c.getString(i) != null)
                    itemsAdapter.add(c.getString(i));

            }

            if (!itemsAdapter.isEmpty()) {
                lv_olustur_hareketler.setAdapter(itemsAdapter);
            } else {
//                Toast.makeText(getActivity(), "Hareket listesinde herhangi bir hareket mevcut değil. Lütfen yeni hareket aktarın.", Toast.LENGTH_SHORT).show();
                lv_olustur_hareketler.setAdapter(null);
            }

        } else {
//            Toast.makeText(getActivity(), "Hareket listesi için lütfen program oluşturun.", Toast.LENGTH_SHORT).show();
            lv_olustur_hareketler.setAdapter(null);
        }

    }


    private void ButunHareketleriGetirToList() {
        secilenButunHareket = "";
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        Cursor c = dbHandlerLOCAL.getDataFrom("hareketler");
        while (c.moveToNext()) {
            itemsAdapter.add(c.getString(1));
        }
        lv_olustur_butunHareketler.setAdapter(itemsAdapter);
        if (lv_olustur_butunHareketler.getCount() == 0)
            Toast.makeText(getActivity(), "Bütün Hareketler listesinde herhangi bir hareket mevcut değil. Lütfen yeni hareket ekleyin.", Toast.LENGTH_SHORT).show();

    }

    private void DefaultAktar() {
        secilenButunHareket = "";
        dbHandlerLOCAL.addDefaultData();
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        Cursor c = dbHandlerLOCAL.getDataFrom("hareketler");
        while (c.moveToNext()) {
            itemsAdapter.add(c.getString(1));
        }
        lv_olustur_butunHareketler.setAdapter(itemsAdapter);

    }

    private void DetaylariGetirToSpinner() {
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        Cursor c = dbHandlerLOCAL.getDataFrom("detaylar");
        while (c.moveToNext()) {
            itemsAdapter.add(c.getString(1));
        }
        spin_olustur_butunDetaylar.setAdapter(itemsAdapter);
        if (spin_olustur_butunDetaylar.getCount() == 0)
            Toast.makeText(getActivity(), "Detay listesinde herhangi bir detay mevcut değil. Lütfen yeni detay ekleyin.", Toast.LENGTH_SHORT).show();

    }

    private void INITIALIZE(View view) {
        dbHandlerProgramlar = new DBHandler_Programlar(getActivity());
        dbHandlerLOCAL = new DBHandler_LOCAL(getActivity());

        btn_olustur_hareketAktar = view.findViewById(R.id.btn_olustur_hareketAktar);
        btn_olustur_ekle = view.findViewById(R.id.btn_olustur_tableOlustur);
        btn_olustur_tableSil = view.findViewById(R.id.btn_olustur_tableSil);
        btn_olustur_detayEkle = view.findViewById(R.id.btn_olustur_detayEkle);
        btn_olustur_detaySil = view.findViewById(R.id.btn_olustur_detaySil);
        btn_olustur_hareketEkle = view.findViewById(R.id.btn_olustur_hareketEkle);
        btn_olustur_hareketSil = view.findViewById(R.id.btn_olustur_hareketSil);
        btn_olustur_cikart = view.findViewById(R.id.btn_olustur_cikart);
        btn_olustur_temizle = view.findViewById(R.id.btn_olustur_temizle);
        btn_olustur_kompleTemizle = view.findViewById(R.id.btn_olustur_kompleTemizle);
        btn_olustur_yerel = view.findViewById(R.id.btn_olustur_yerel);

        et_olustur_detay = view.findViewById(R.id.et_olustur_detay);
        et_olustur_tablename = view.findViewById(R.id.et_olustur_tableName);
        et_olustur_hareket = view.findViewById(R.id.et_olustur_hareket);

        lv_olustur_hareketler = view.findViewById(R.id.lv_olustur_hareketler);
        lv_olustur_butunHareketler = view.findViewById(R.id.lv_olustur_butunHareketler);

        spin_olustur_tables = view.findViewById(R.id.spin_olustur_tables);
        spin_olustur_gunler = view.findViewById(R.id.spin_olustur_gunler);
        spin_olustur_butunDetaylar = view.findViewById(R.id.spin_olustur_butunDetaylar);



    }





}

