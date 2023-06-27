package com.example.ap2_ex3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.entities.Message;

import java.util.List;
import java.util.Objects;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>{

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMsgUser, tvMsgSender;
        private final LinearLayout layoutMsgUser, layoutMsgSender;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutMsgUser = itemView.findViewById(R.id.layoutMsgUser);
            layoutMsgSender = itemView.findViewById(R.id.layoutMsgSender);
            tvMsgUser = itemView.findViewById(R.id.tvMsgUser);
            tvMsgSender = itemView.findViewById(R.id.tvMsgSender);
        }
    }

    private final LayoutInflater mInflater;

    private final String currentUser;
    private List<Message> messages;

    public MessageListAdapter(Context context, String currentUser) {
        this.currentUser = currentUser;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageListAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.message_item, parent, false);
        return new MessageListAdapter.MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapter.MessageViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            if (Objects.equals(current.getSender(), currentUser)) {
                holder.layoutMsgUser.setVisibility(View.VISIBLE);
                holder.layoutMsgSender.setVisibility(View.GONE);
                holder.tvMsgUser.setText(current.getContent());
            } else {
                holder.layoutMsgUser.setVisibility(View.GONE);
                holder.layoutMsgSender.setVisibility(View.VISIBLE);
                holder.tvMsgSender.setText(current.getContent());
            }
        }
    }
    public void setMessages(List<Message> m) {
        messages = m;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        else return 0;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
