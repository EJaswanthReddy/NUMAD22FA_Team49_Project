package com.example.numad22fa_team49_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.numad22fa_team49_project.adapters.GeneralProductHomeAdapter;
import com.example.numad22fa_team49_project.adapters.NewOrderRecyclerViewAdapter;
import com.example.numad22fa_team49_project.models.GeneralProductHome;
import com.example.numad22fa_team49_project.models.NewOrderModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellerProfileActivity extends AppCompatActivity {

    FloatingActionButton addProduct;
    TextView viewNewOrders, sellerName;
    String name;
    SharedPreferences sharedPreferences;
    FirebaseAuth mAuth;
    DatabaseReference mReference;

    ArrayList<GeneralProductHome> productArrayList;
    RecyclerView sellerProductsRecyclerView;
    GeneralProductHomeAdapter adapter;

    ArrayList<NewOrderModel> orderModels;
    RecyclerView newOrdersRecyclerView;
    NewOrderRecyclerViewAdapter newOrderRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
        name = getIntent().getStringExtra("username_registration");

        addProduct = findViewById(R.id.add_product);
        viewNewOrders = findViewById(R.id.newOrdersViewAll);
        sellerName = findViewById(R.id.seller_name);
        sellerProductsRecyclerView = findViewById(R.id.seller_added_products);
        newOrdersRecyclerView = findViewById(R.id.new_orders_recycler_view);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("storeHunt", MODE_PRIVATE);
        SharedPreferences.Editor editSharedPreferences = sharedPreferences.edit();

        editSharedPreferences.putString("sellerId", mAuth.getUid());
        editSharedPreferences.apply();

        Log.d("TAG_123", "onCreate: "+mAuth.getUid());
        mReference = FirebaseDatabase.getInstance().getReference("seller").child(mAuth.getUid());

        productArrayList = new ArrayList<>();
        adapter = new GeneralProductHomeAdapter(this,productArrayList);
        sellerProductsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        sellerProductsRecyclerView.setAdapter(adapter);

        orderModels = new ArrayList<>();
        newOrderRecyclerViewAdapter = new NewOrderRecyclerViewAdapter(this, orderModels);
        newOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newOrdersRecyclerView.setAdapter(newOrderRecyclerViewAdapter);


        if(!TextUtils.isEmpty(name)){
            sellerName.setText("Hi "+name);
            mReference.child("name").setValue(name);
        }else{
            mReference.child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    SellerModel model = new SellerModel();
                    String name = "";
//                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                         model = snapshot.getValue(SellerModel.class);
//                        Log.d("TAG_672", "onDataChange: "+dataSnapshot.getKey());
                    if(snapshot.getValue()!=null){
                        name = snapshot.getValue().toString();
                    }
                    sellerName.setText("Hi "+name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            mReference.child("products").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        GeneralProductHome products = dataSnapshot.getValue(GeneralProductHome.class);
                        productArrayList.add(products);
                    }
                    Log.d("TAG_342", "onDataChange: "+productArrayList.size());
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mReference.child("orders").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot data : snapshot.getChildren()){
                        NewOrderModel order = data.getValue(NewOrderModel.class);
                        orderModels.add(order);
                    }
                    newOrderRecyclerViewAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellerProfileActivity.this, AddProductActivity.class));
            }
        });

        viewNewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellerProfileActivity.this, ViewNewOrdersSellerViewActivity.class));
            }
        });


    }
}