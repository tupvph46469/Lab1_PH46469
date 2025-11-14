package com.example.lab1_ph46469;

import android.app.AlertDialog;
import android.os.Bundle;
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

        addSampleData();

        loadData();

        btnAddProduct.setOnClickListener(v -> showAddDialog());
    }

    private void addSampleData() {
        if (catDAO.getAllCats().isEmpty()) {
            catDAO.insertCat(new CatDTO(1, "Mèo Ba Tư"));
            catDAO.insertCat(new CatDTO(2, "Mèo Anh Lông Ngắn"));
        }

        if (productDAO.getAllProducts().isEmpty()) {
            productDAO.insertProduct(new ProductDTO(1, "Mèo Ba Tư con", 1000.0, 1));
            productDAO.insertProduct(new ProductDTO(2, "Mèo Anh Lông Ngắn con", 1200.0, 2));
        }
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
                    String name = edName.getText().toString().trim();
                    String priceStr = edPrice.getText().toString().trim();

                    if (name.isEmpty() || priceStr.isEmpty()) {
                        Toast.makeText(this, "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        double price = Double.parseDouble(priceStr);
                        CatDTO selectedCat = (CatDTO) spCat.getSelectedItem();

                        if (selectedCat == null) {
                            Toast.makeText(this, "Vui lòng chọn một danh mục", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ProductDTO p = new ProductDTO(0, name, price, selectedCat.getId());
                        productDAO.insertProduct(p);
                        loadData();
                        Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Giá phải là một con số", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
