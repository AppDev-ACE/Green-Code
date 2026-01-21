package com.example.helloworld;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Message> msgList= new ArrayList<>();
    private String currentUserId = "user1";

    public ChatAdapter(ArrayList<Message> msgList,String currentUserId){
        this.msgList = msgList;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        if (msgList.get(position).getSenderId().equals(currentUserId)){
            return 1;
        }
        else{
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receiver,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message msg = msgList.get(position);
        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).textView.setText(msg.getMessage());
        } else {
            ((ReceiverViewHolder) holder).textView.setText(msg.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    static class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public SenderViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.textMessage);
        }
    }

    static class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ReceiverViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.textMessageRec);
        }
    }
}
