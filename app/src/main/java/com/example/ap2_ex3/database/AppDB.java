package com.example.ap2_ex3.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.entities.User;

@Database(entities = {Chat.class, User.class, Message.class}, version = 4)
public abstract class AppDB extends RoomDatabase {
    private static AppDB instance;
    public abstract UserDao userDao();
    public abstract MessageDao messageDao();
    public abstract ChatDao chatDao();
    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDB.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
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
        private PopulateDBAsyncTask(AppDB db) {
            chatDao = db.chatDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
