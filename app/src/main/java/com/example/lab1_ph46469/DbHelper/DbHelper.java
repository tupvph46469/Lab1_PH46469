package com.example.lab1_ph46469.DbHelper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "QLSP.db";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Category
        String createTableCat = "CREATE TABLE tb_cat (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT)";
        db.execSQL(createTableCat);

        // Tạo bảng Product
        String createTableProduct = "CREATE TABLE tb_product (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "price REAL, " +
                "id_cat INTEGER REFERENCES tb_cat(id))";
        db.execSQL(createTableProduct);

        // Dữ liệu mẫu cho mèo 🐱
        db.execSQL("INSERT INTO tb_cat (name) VALUES ('Thức ăn'), ('Phụ kiện'), ('Đồ chơi')");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_product");
        db.execSQL("DROP TABLE IF EXISTS tb_cat");
        onCreate(db);
    }
}
