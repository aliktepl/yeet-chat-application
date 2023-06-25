package com.example.ap2_ex3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Chat;

import java.util.List;

public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ChatViewHolder> {

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDisplayName;
        private final TextView tvLastMsg;
        private final TextView tvLastMsgTime;
        private final ImageView ivPic;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisplayName = itemView.findViewById(R.id.tvDisplayName);
            tvLastMsg = itemView.findViewById(R.id.tvLastMsg);
            tvLastMsgTime = itemView.findViewById(R.id.tvLastMsgTime);
            ivPic = itemView.findViewById(R.id.ivPic);

        }
    }

    private final LayoutInflater mInflater;
    private List<Chat> chats;

    public ChatsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
//        if (chats != null) {
//            final Chat currChat = chats.get(position);
//            final User currUser = viewModel.getUser(currChat.getRecipient()).getValue();
//            final Message lstMsg = viewModel.getMessage(currChat.getLst_msg_id()).getValue();
//            holder.tvDisplayName.setText(currUser.getDisplayName());
//            holder.ivPic.setImageResource(Integer.parseInt(currUser.getProfPic()));
//            holder.tvLastMsg.setText(lstMsg.getContent());
//            holder.tvLastMsgTime.setText(lstMsg.getCreated());
//        }
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (chats != null) {
            return chats.size();
        }
        else return 0;
    }

    public List<Chat> getChats() {
        return chats;
    }

}
