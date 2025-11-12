package com.example.lab1_ph46469;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.lab1_ph46469.DAO.CatDAO;
import com.example.lab1_ph46469.DAO.ProductDAO;
import com.example.lab1_ph46469.DTO.CatDTO;
import com.example.lab1_ph46469.DTO.ProductDTO;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<ProductDTO> list;
    private ProductDAO productDAO;

    public ProductAdapter(Context context, List<ProductDTO> list, ProductDAO productDAO) {
        this.context = context;
        this.list = list;
        this.productDAO = productDAO;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return list.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        ProductDTO p = list.get(position);
        tvName.setText(p.getName());
        tvPrice.setText("Giá: " + p.getPrice() + "₫");

        btnEdit.setOnClickListener(v -> showEditDialog(p));
        btnDelete.setOnClickListener(v -> {
            productDAO.deleteProduct(p.getId());
            list.remove(position);
            notifyDataSetChanged();
        });

        return view;
    }

    private void showEditDialog(ProductDTO product) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_product, null);
        EditText edName = dialogView.findViewById(R.id.edProductName);
        EditText edPrice = dialogView.findViewById(R.id.edProductPrice);
        Spinner spCat = dialogView.findViewById(R.id.spCategory);

        CatDAO catDAO = new CatDAO(context);
        ArrayAdapter<CatDTO> catAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, catDAO.getAllCats());
        spCat.setAdapter(catAdapter);

        edName.setText(product.getName());
        edPrice.setText(String.valueOf(product.getPrice()));

        new AlertDialog.Builder(context)
                .setTitle("Cập nhật sản phẩm")
                .setView(dialogView)
                .setPositiveButton("Cập nhật", (dialog, which) -> {
                    product.setName(edName.getText().toString());
                    product.setPrice(Double.parseDouble(edPrice.getText().toString()));
                    CatDTO selectedCat = (CatDTO) spCat.getSelectedItem();
                    product.setId_cat(selectedCat.getId());

                    productDAO.updateProduct(product);
                    notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
