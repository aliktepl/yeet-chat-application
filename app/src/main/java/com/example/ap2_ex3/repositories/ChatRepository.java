package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.example.ap2_ex3.database.ChatDB;
import com.example.ap2_ex3.database.ChatDao;
import com.example.ap2_ex3.entities.Chat;
import java.util.List;
public class ChatRepository {
    private ChatDao dao;
    private LiveData<List<Chat>> allChats;

    public ChatRepository(Application application) {
        ChatDB db = ChatDB.getInstance(application);
        dao = db.chatDao();
        allChats = dao.getAllChats();
    }

    public void insert(Chat chat) {
        new InsertChatAsyncTask(dao).execute(chat);
    }

    public void update(Chat chat) {
        new UpdateChatAsyncTask(dao).execute(chat);
    }

    public void delete(Chat chat) {
        new DeleteChatAsyncTask(dao).execute(chat);
    }

    public LiveData<List<Chat>> getAllChats() {
        return allChats;
    }

    private static class InsertChatAsyncTask extends AsyncTask<Chat, Void, Void> {
        private ChatDao dao;
        private InsertChatAsyncTask(ChatDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Chat... chats) {
            dao.Insert(chats[0]);
            return null;
        }
    }

    private static class UpdateChatAsyncTask extends AsyncTask<Chat, Void, Void> {
        private ChatDao dao;
        private UpdateChatAsyncTask(ChatDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Chat... chats) {
            dao.Update(chats[0]);
            return null;
        }
    }

    private static class DeleteChatAsyncTask extends AsyncTask<Chat, Void, Void> {
        private ChatDao dao;
        private DeleteChatAsyncTask(ChatDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Chat... chats) {
            dao.Delete(chats[0]);
            return null;
        }
    }
}
