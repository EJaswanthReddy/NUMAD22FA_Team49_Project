package com.example.numad22fa_team49_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.numad22fa_team49_project.models.GeneralProductHome;
import com.squareup.picasso.Picasso;

public class ProductViewActivity extends AppCompatActivity {

    ImageView productImage;
    TextView productName, productCost, productDescription, productRatingText;
    RatingBar productRating;
    GeneralProductHome product;
    Button addToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        product = (GeneralProductHome) getIntent().getSerializableExtra("product_info");
        Log.d("TAG_89", "onCreate: "+product.getName());

        productImage = findViewById(R.id.product_view_image);
        productName = findViewById(R.id.product_view_name);
        productDescription = findViewById(R.id.product_view_description);
        productCost = findViewById(R.id.product_view_cost);
        productRatingText = findViewById(R.id.product_view_rating_text);
        productRating = findViewById(R.id.product_view_rating);
        addToCart = findViewById(R.id.add_to_cart_button);

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productCost.setText(product.getPrice());
        productRatingText.setText(product.getRating());
        productRating.setRating(Float.parseFloat(product.getRating()));
        Picasso.get().load(product.getImage_uri()).into(productImage);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}