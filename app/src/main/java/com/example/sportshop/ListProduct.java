package com.example.sportshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportshop.Common.Common;
import com.example.sportshop.Interface.ItemClickListener;
import com.example.sportshop.Model.Product;
import com.example.sportshop.Model.User;
import com.example.sportshop.Prevalent.Prevalent;
import com.example.sportshop.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListProduct extends AppCompatActivity {
    RecyclerView recyclerViewProduct;
    DatabaseReference table_product;
    NavigationView navigationView;
    CircleImageView imgUser;
    TextView txtFullName, txtEmail;
    ImageView imgUpload;
    DrawerLayout drawerLayout;
    EditText searchView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_product = database.getReference("Products");
        recyclerViewProduct = (RecyclerView) findViewById(R.id.productRecyclerView);
        GridLayoutManager layoutManagerGrid = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerViewProduct.setLayoutManager(layoutManagerGrid);
        ((TextView) findViewById(R.id.tvHelloUser)).setText(Common.currentUser.getName());

        loadProduct();
        NavSettup();

        searchView = findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                Intent intent = new Intent(ListProduct.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadProduct() {
        Query query;

        query = table_product;

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();
        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position, @NonNull Product product) {
                String productId = getRef(position).getKey();
                productViewHolder.tvProductName.setText(product.getName());
                Picasso.get().load(product.getImage()).into(productViewHolder.imgProduct);
                productViewHolder.tvPrice.setText(product.getPrice());
                Product clickItem = product;
                product.setId(productId);

                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListProduct.this, DetailSport.class);
                        intent.putExtra("ProductId", productId);
                        startActivity(intent);
                    }
                });

                productViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(ListProduct.this, clickItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

                productViewHolder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final HashMap<String, Object> cartMap = new HashMap<>();
                        cartMap.put("ProductId", productId);
                        cartMap.put("name", productViewHolder.tvProductName.getText().toString());
                        cartMap.put("price", productViewHolder.tvPrice.getText().toString());
                        cartMap.put("Image", product.getImage());
                    }
                });
            }
        };
        recyclerViewProduct.setAdapter(adapter);
        adapter.startListening();
    }

    private void NavSettup() {
        navigationView = findViewById(R.id.navbar);
        imgUser = navigationView.getHeaderView(0).findViewById(R.id.img_user);
        txtFullName = navigationView.getHeaderView(0).findViewById(R.id.txt_nav_name);
        txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txt_user_name);
        imgUpload = navigationView.getHeaderView(0).findViewById(R.id.img_upload);
        drawerLayout = findViewById(R.id.drawer_layout);

        User user = Common.currentUser;
        Picasso.get().load(user.getImage()).into(imgUser);
        txtFullName.setText(user.getName());
        txtEmail.setText(user.getEmail());

        findViewById(R.id.img_menu).setOnClickListener(v -> ShowNavigationBar());
    }

    private void ShowNavigationBar() {
        findViewById(R.id.img_menu).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));
    }

    public void profile(MenuItem item) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void changePass(MenuItem item) {
        Intent intent = new Intent(this, ChangePasswordAct.class);
        startActivity(intent);
    }

    public void viewCart(MenuItem item) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void clickLogout(MenuItem item) {
        Prevalent.currentOnlineUser = null;
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    public void listProducts(MenuItem item) {
        Intent intent = new Intent(this, ListProduct.class);
        startActivity(intent);
    }

    public void home(MenuItem item) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}