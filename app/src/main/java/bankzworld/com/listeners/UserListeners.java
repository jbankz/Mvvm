package bankzworld.com.listeners;

import java.util.List;

import bankzworld.com.data.User;

public interface UserListeners {

    void onSuccess(List<User> usersList);

    void onDeleteSuccess(String message);
}
