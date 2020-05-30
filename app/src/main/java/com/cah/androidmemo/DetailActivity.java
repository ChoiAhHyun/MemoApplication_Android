package com.cah.androidmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailActivity extends AppCompatActivity {

    private TextView tv_title, tv_content;
    private String id;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        id = getIntent().getStringExtra("id");
        db = FirebaseFirestore.getInstance();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        db.collection("memo").document(id)
                .get().addOnCompleteListener(onCompleteListener);
    }

    private void init() {
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
    }

    OnCompleteListener<DocumentSnapshot> onCompleteListener = new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                tv_title.setText(document.getString("title"));
                tv_content.setText(document.getString("content"));
            } else {
                Log.d("DetailActivity", "Cached get failed: ", task.getException());
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.delete:
                deleteData();
                return true;
            case R.id.edit:
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteData() {
        db.collection("memo").document(id)
                .delete().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    OnSuccessListener<Void> onSuccessListener = new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            finish();
        }
    };

    OnFailureListener onFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d("DetailActivity", "Error updating document", e);
        }
    };
}
