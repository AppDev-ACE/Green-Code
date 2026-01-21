package com.example.helloworld;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView btnSend;
    EditText editTxtInput;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ArrayList<Message> msgList = new ArrayList<>();
        ChatAdapter adapter;

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnSend = (ImageView) findViewById(R.id.btnSend);
        editTxtInput = (EditText) findViewById(R.id.editTxtInput);

        adapter = new ChatAdapter(msgList,"user2");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("chats")
                        .orderBy("timestamp", Query.Direction.ASCENDING)
                                .addSnapshotListener((value,error) -> {
                                    if (error != null) return;

                                    msgList.clear();
                                    for (DocumentSnapshot doc: value.getDocuments()){
                                        Message msg = doc.toObject(Message.class);
                                        msgList.add(msg);
                                    }
                                    adapter.notifyDataSetChanged(); // refresh RecyclerView
                                    recyclerView.scrollToPosition(msgList.size() - 1);
                                });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editTxtInput.getText().toString();
                if (!msg.isEmpty()){
                    Message message = new Message(msg,"user2",System.currentTimeMillis());
                    db.collection("chats")
                                    .add(message);
                    editTxtInput.setText("");
                }
            }
        });
    }
}