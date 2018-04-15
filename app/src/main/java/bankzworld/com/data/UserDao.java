package bankzworld.com.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    // inserts into the database
    @Insert(onConflict = REPLACE)
    void insertUser(User user);

    // gets all items from the database
    @Query("SELECT * FROM User")
    List<User> findAllUsers();

    // deletes an item from the database
    @Delete
    void deleteUser(User user);

}
