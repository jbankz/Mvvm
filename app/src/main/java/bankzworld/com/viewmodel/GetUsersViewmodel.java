package bankzworld.com.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import bankzworld.com.data.User;
import bankzworld.com.data.UserDatabase;
import bankzworld.com.listeners.UserListeners;

import static android.content.ContentValues.TAG;

public class GetUsersViewmodel extends AndroidViewModel {

    private UserDatabase userDatabase;

    static UserListeners userListeners;
    public static List<User> usersList;

    // Listeners method
    public static void setListener(UserListeners listener) {
        GetUsersViewmodel.userListeners = listener;
    }

    // Constructor
    public GetUsersViewmodel(@NonNull Application application) {
        super(application);
        userDatabase = UserDatabase.getDatabase(this.getApplication());
    }

    public void getUsers() {
        new RetrieveUsers(userDatabase).execute();
    }

    private static class RetrieveUsers extends AsyncTask<Void, Void, Void> {

        private UserDatabase db;

        public RetrieveUsers(UserDatabase userDatabase) {
            this.db = userDatabase;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            usersList = db.userDao().findAllUsers();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: " + usersList);
            userListeners.onSuccess(usersList);
        }
    }
    
    public void deleteUser(int id) {
        new DeleteUser(userDatabase).execute(usersList.get(id));
    }

    // background thread for performing a delete request from the room
    class DeleteUser extends AsyncTask<User, Void, Void> {

        private UserDatabase db;

        public DeleteUser(UserDatabase userDatabase) {
            this.db = userDatabase;
        }

        @Override
        protected Void doInBackground(User... user) {
            db.userDao().deleteUser(user[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            userListeners.onDeleteSuccess("Success");
        }
    }


}
