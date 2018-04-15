package bankzworld.com.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import bankzworld.com.data.User;
import bankzworld.com.data.UserDatabase;

public class NewUserViewmodel extends AndroidViewModel {

    private static final String TAG = NewUserViewmodel.class.getSimpleName();

    private UserDatabase userDatabase;

    // Constructor
    public NewUserViewmodel(@NonNull Application application) {
        super(application);
        userDatabase = UserDatabase.getDatabase(this.getApplication());
    }

    // adds users to the database
    public void addUsers(final User user) {
        new AddUserAsyncTask(userDatabase).execute(user);
    }

    private static class AddUserAsyncTask extends AsyncTask<User, Void, User> {

        private UserDatabase db;

        public AddUserAsyncTask(UserDatabase userDatabase) {
            this.db = userDatabase;
        }

        @Override
        protected User doInBackground(User... users) {
            db.userDao().insertUser(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
        }
    }
}
