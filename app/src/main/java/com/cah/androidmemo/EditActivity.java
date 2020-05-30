package com.cah.androidmemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditActivity extends AppCompatActivity {

    private EditText et_title, et_content;
    private String id;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        id = getIntent().getStringExtra("id");
        getData();
        init();
    }

    private void getData() {
        db = FirebaseFirestore.getInstance();
        db.collection("memo").document(id)
                .get().addOnCompleteListener(onCompleteListener);
    }

    private void init() {
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
    }

    OnCompleteListener<DocumentSnapshot> onCompleteListener = new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                et_title.setText(document.getString("title"));
                et_content.setText(document.getString("content"));
            } else {
                Log.d("EditActivity", "Error getting documents.", task.getException());
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.save:
                sendData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendData() {
        db.collection("memo").document(id).update("title", et_title.getText().toString(),
                "content", et_content.getText().toString(),
                "datetime", Timestamp.now())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
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
            Log.d("EditActivity", "Error updating document", e);
        }
    };
}
