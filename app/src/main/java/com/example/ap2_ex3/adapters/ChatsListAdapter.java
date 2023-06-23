package com.example.ap2_ex3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Chat;

import java.util.List;

public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ChatViewHolder> {

    class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
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

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Chat clickedChat = chats.get(position);
                showDeleteChatPopup(clickedChat);
                return true;
            }
            return false;
        }
    }

    private final LayoutInflater mInflater;
    private List<Chat> chats;
    private Context mContext;

    public ChatsListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        if (chats != null) {
            final Chat current = chats.get(position);
            holder.tvDisplayName.setText(current.getDisplayName());
            holder.ivPic.setImageResource(current.getPicture());
            holder.tvLastMsg.setText(current.getLastMsg());
            holder.tvLastMsgTime.setText(current.getLastMsgTime().toString());
        }
    }

    public void setChats(List<Chat> c) {
        chats = c;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (chats != null) {
            return chats.size();
        } else return 0;
    }

    public List<Chat> getChats() {
        return chats;
    }


    // TODO - need to delete all the messages with the specific contact
    private void showDeleteChatPopup(Chat chat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Delete Chat");
        builder.setMessage("Are you sure you want to delete this chat?");
        builder.setPositiveButton("Delete", (dialogInterface, i) -> {
            int position = chats.indexOf(chat);
            if (position != -1) {
                chats.remove(position);
                notifyItemRemoved(position);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
