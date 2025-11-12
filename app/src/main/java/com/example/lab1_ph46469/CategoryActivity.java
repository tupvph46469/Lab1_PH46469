package com.example.lab1_ph46469;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1_ph46469.DAO.CatDAO;
import com.example.lab1_ph46469.DTO.CatDTO;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private ListView lvCategory;
    private CatDAO catDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        lvCategory = findViewById(R.id.lvCategory);
        catDAO = new CatDAO(this);

        List<CatDTO> list = catDAO.getAllCats();
        ArrayAdapter<CatDTO> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvCategory.setAdapter(adapter);
    }
}
