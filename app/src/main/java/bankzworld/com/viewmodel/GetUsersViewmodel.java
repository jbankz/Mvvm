package bankzworld.com.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import bankzworld.com.data.User;
import bankzworld.com.data.UserDatabase;

public class GetUsersViewmodel extends AndroidViewModel {

    private UserDatabase userDatabase;

    public static LiveData<List<User>> usersList;

    // Constructor
    public GetUsersViewmodel(@NonNull Application application) {
        super(application);
        userDatabase = UserDatabase.getDatabase(this.getApplication());
        usersList = userDatabase.userDao().loadAllUsers();
    }

    public LiveData<List<User>> getEntries() {
        return usersList;
    }

}
