package com.example.ap2_ex3.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.ap2_ex3.entities.Chat;

@Database(entities = {Chat.class}, version = 1)
public abstract class ChatDB extends RoomDatabase {
    private static ChatDB instance;
    public abstract ChatDao chatDao();
    public static synchronized ChatDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ChatDB.class, "node_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private ChatDao chatDao;
        private PopulateDBAsyncTask(ChatDB db) {
            chatDao = db.chatDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
