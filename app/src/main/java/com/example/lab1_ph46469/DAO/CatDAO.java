package com.example.lab1_ph46469.DAO;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab1_ph46469.DTO.CatDTO;
import com.example.lab1_ph46469.DbHelper.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class CatDAO {
    private SQLiteDatabase db;

    public CatDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertCat(CatDTO cat) {
        ContentValues values = new ContentValues();
        values.put("name", cat.getName());
        return db.insert("tb_cat", null, values);
    }

    public int updateCat(CatDTO cat) {
        ContentValues values = new ContentValues();
        values.put("name", cat.getName());
        return db.update("tb_cat", values, "id=?", new String[]{String.valueOf(cat.getId())});
    }

    public int deleteCat(int id) {
        return db.delete("tb_cat", "id=?", new String[]{String.valueOf(id)});
    }

    public List<CatDTO> getAllCats() {
        List<CatDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tb_cat", null);
        if (c.moveToFirst()) {
            do {
                CatDTO cat = new CatDTO();
                cat.setId(c.getInt(0));
                cat.setName(c.getString(1));
                list.add(cat);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
}
