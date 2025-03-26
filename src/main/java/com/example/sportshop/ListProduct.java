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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportshop.Common.Common;
import com.example.sportshop.Interface.ItemClickListener;
import com.example.sportshop.Model.Category;
import com.example.sportshop.Model.Product;
import com.example.sportshop.Model.User;
import com.example.sportshop.Prevalent.Prevalent;
import com.example.sportshop.ViewHolder.CategoryViewHolder;
import com.example.sportshop.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListProduct extends AppCompatActivity {
    RecyclerView recyclerViewCategory, recyclerViewProduct;
    DatabaseReference table_category;
    DatabaseReference table_product;
    NavigationView navigationView;
    CircleImageView imgUser;
    TextView txtFullName, txtEmail;
    ImageView imgUpload;
    DrawerLayout drawerLayout;
    //Search
    EditText searchView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        // Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_category = database.getReference("Category");
        table_product = database.getReference("Products");
        //set current user
        //load category
        recyclerViewCategory = findViewById(R.id.categoryRecyclerView);
        recyclerViewCategory.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        //load product
        recyclerViewProduct = findViewById(R.id.productRecyclerView);
        GridLayoutManager layoutManagerGrid = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerViewProduct.setLayoutManager(layoutManagerGrid);
        ((TextView) findViewById(R.id.tvHelloUser)).setText(Common.currentUser.getName());

        loadCategory();
        loadProduct("");
        NavSettup();

        //ToDo:Search
        searchView = findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                // Intent sang Activity khác
                Intent intent = new Intent(ListProduct.this, SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadCategory() {

        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(table_category, Category.class)
                        .build();

        FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter
                = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
                CategoryViewHolder holder = new CategoryViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position, @NonNull Category category) {
                categoryViewHolder.tvCategoryName.setText(category.getName());
                Picasso.get().load(category.getImage()).into(categoryViewHolder.imgCategory);
                Category clickItem = category;
                categoryViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Lấy danh mục được click
                        String selectedCategory = category.getName();
                        // Gọi phương thức loadProduct() với danh mục đã chọn hoặc rỗng
                        loadProduct(selectedCategory.isEmpty() ? "" : selectedCategory);
                        Toast.makeText(ListProduct.this, clickItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerViewCategory.setAdapter(adapter);
        adapter.startListening();
    }


    // bản khác
    private void loadProduct(String category) {
        Query query;

        if (category.isEmpty()) {
            // Hiển thị tất cả sản phẩm nếu không có danh mục được chọn
            query = table_product;
        } else {
            // Hiển thị sản phẩm theo danh mục được chọn
            query = table_product.orderByChild("CategoryId").equalTo(category);
        }
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {


            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position, @NonNull Product product) {
                String productId = getRef(position).getKey();
                productViewHolder.tvProductName.setText(product.getName());
                Picasso.get().load(product.getImage()).into(productViewHolder.imgProduct);
                productViewHolder.tvPrice.setText(product.getPrice());
                productViewHolder.rate.setRating(Float.parseFloat(product.getRate()));
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
                        String saveCurrentTime, saveCurrentDate;

                        Calendar calForDate = Calendar.getInstance();
                        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                        saveCurrentDate = currentDate.format(calForDate.getTime());

                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                        saveCurrentTime = currentTime.format(calForDate.getTime());

                        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("FavoriteList");

                        final HashMap<String, Object> cartMap = new HashMap<>();
                        cartMap.put("ProductId", productId);
                        cartMap.put("name", productViewHolder.tvProductName.getText().toString());
                        cartMap.put("price", productViewHolder.tvPrice.getText().toString());
                        cartMap.put("rate", product.getRate());
                        cartMap.put("date", saveCurrentDate);
                        cartMap.put("time", saveCurrentTime);
                        cartMap.put("Image", product.getImage());


                        String cartItemId = cartListRef.child("FavoriteView")
                                .child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                                .push().getKey();
                        cartListRef.child("FavoriteView")
                                .child(Prevalent.currentOnlineUser.getPhone())
                                .child("Products").child(cartItemId).setValue(cartMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ListProduct.this, "Added to Favorite ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
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
        //TODO: upload profile

        findViewById(R.id.img_menu).setOnClickListener(v -> ShowNavigationBar());
    }

    private void ShowNavigationBar() {
        findViewById(R.id.img_menu).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
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