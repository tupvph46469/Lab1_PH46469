package com.example.lab1_ph46469.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab1_ph46469.DTO.ProductDTO;
import com.example.lab1_ph46469.DbHelper.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private SQLiteDatabase db;

    public ProductDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertProduct(ProductDTO product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("id_cat", product.getId_cat());
        return db.insert("tb_product", null, values);
    }

    public int updateProduct(ProductDTO product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("id_cat", product.getId_cat());
        return db.update("tb_product", values, "id=?", new String[]{String.valueOf(product.getId())});
    }

    public int deleteProduct(int id) {
        return db.delete("tb_product", "id=?", new String[]{String.valueOf(id)});
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tb_product", null);
        if (c.moveToFirst()) {
            do {
                ProductDTO p = new ProductDTO();
                p.setId(c.getInt(0));
                p.setName(c.getString(1));
                p.setPrice(c.getDouble(2));
                p.setId_cat(c.getInt(3));
                list.add(p);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
}

