package com.example.lab1_ph46469;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1_ph46469.DAO.CatDAO;
import com.example.lab1_ph46469.DAO.ProductDAO;
import com.example.lab1_ph46469.DTO.CatDTO;
import com.example.lab1_ph46469.DTO.ProductDTO;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ListView lvProduct;
    private ProductDAO productDAO;
    private CatDAO catDAO;
    private List<ProductDTO> list;
    private ProductAdapter adapter;
    private Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        lvProduct = findViewById(R.id.lvProduct);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        productDAO = new ProductDAO(this);
        catDAO = new CatDAO(this);

        loadData();

        btnAddProduct.setOnClickListener(v -> showAddDialog());
    }

    private void loadData() {
        list = productDAO.getAllProducts();
        adapter = new ProductAdapter(this, list, productDAO);
        lvProduct.setAdapter(adapter);
    }

    private void showAddDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_product, null);
        EditText edName = dialogView.findViewById(R.id.edProductName);
        EditText edPrice = dialogView.findViewById(R.id.edProductPrice);
        Spinner spCat = dialogView.findViewById(R.id.spCategory);

        List<CatDTO> catList = catDAO.getAllCats();
        ArrayAdapter<CatDTO> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, catList);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCat.setAdapter(catAdapter);

        new AlertDialog.Builder(this)
                .setTitle("Thêm sản phẩm mới")
                .setView(dialogView)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String name = edName.getText().toString();
                    double price = Double.parseDouble(edPrice.getText().toString());
                    CatDTO selectedCat = (CatDTO) spCat.getSelectedItem();

                    ProductDTO p = new ProductDTO(0, name, price, selectedCat.getId());
                    productDAO.insertProduct(p);
                    loadData();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}