package com.example.ecommarce.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommarce.Util.DataModel;
import com.example.ecommarce.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class addProductDetail extends AppCompatActivity {

    CircleImageView imgAddProduct;
    TextView txtAddImage;
    EditText edtAddPrdName, edtAddBrandName, edtAddPrdCapacity, edtAddPrdPrice, edtAddPrdDetail;
    Button btnPublish;
    Bitmap bitmap;
    Uri filepath;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_detail);

        //hide title
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imgAddProduct = findViewById(R.id.imgAddProduct);
        edtAddPrdName = findViewById(R.id.edtAddProductName);
        edtAddBrandName = findViewById(R.id.edtAddBrandName);
        edtAddPrdCapacity = findViewById(R.id.edtAddProductCapacity);
        edtAddPrdPrice = findViewById(R.id.edtAddProductPrice);
        edtAddPrdDetail = findViewById(R.id.edtAddProductDetail);
        txtAddImage = findViewById(R.id.txtAddImage);
        btnPublish = findViewById(R.id.btnPublish);

        //Intialize Firebase
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Product");
        storageReference = firebaseStorage.getReference("" + edtAddPrdName.getText().toString().trim() + new Random().nextInt(1000));

        //add Image
        txtAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseimage();

            }
        });

        //Upload Data
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtAddPrdName.getText().toString().trim();
                String brandname = edtAddBrandName.getText().toString().trim().toUpperCase();
                String capacity = edtAddPrdCapacity.getText().toString().trim();
                String price = edtAddPrdPrice.getText().toString();
                String detail = edtAddPrdDetail.getText().toString().trim();

                //Validation
                if (name.length() == 0 || brandname.length() == 0 || capacity.length() == 0 || detail.length() == 0) {
                    Toast.makeText(addProductDetail.this, "Please Complete All field", Toast.LENGTH_SHORT).show();
                } else {
                    insertDatatoFirebase(name, brandname, capacity, price, detail);
                }

            }
        });
    }

    private void emptyEditText() {
        edtAddPrdName.setText("");
        edtAddPrdDetail.setText("");
        edtAddPrdPrice.setText("");
        edtAddPrdCapacity.setText("");
        edtAddBrandName.setText("");
        imgAddProduct.setImageResource(R.drawable.productimg);
    }

    //Upload Data to Firebase
    private void insertDatatoFirebase(String name, String brandname, String capacity, String price, String detail) {
        dialog = new ProgressDialog(addProductDetail.this);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.setTitle("Publish Product");
        dialog.setMessage("Uploading Product");
        dialog.show();

        storageReference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DataModel dataModel = new DataModel(uri.toString(), name, brandname, capacity, price, detail);
                        databaseReference.push().setValue(dataModel);
                        dialog.dismiss();
                        Toast.makeText(addProductDetail.this, "Product Publish Successfully", Toast.LENGTH_SHORT).show();
                        emptyEditText();
                        startActivity(new Intent(addProductDetail.this, admindisplay.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addProductDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        emptyEditText();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addProductDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                emptyEditText();

            }
        });
    }

    //Image Permission For User
    private void browseimage() {
        Dexter.withContext(addProductDetail.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select Product Image"), 101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    //Image Convert to uri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            filepath = data.getData();
            if (filepath != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    imgAddProduct.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(addProductDetail.this,admindisplay.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}