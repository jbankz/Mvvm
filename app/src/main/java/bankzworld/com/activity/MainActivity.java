package bankzworld.com.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import bankzworld.com.R;
import bankzworld.com.adapter.UsersAdapter;
import bankzworld.com.data.User;
import bankzworld.com.listeners.UserListeners;
import bankzworld.com.viewmodel.GetUsersViewmodel;
import bankzworld.com.viewmodel.NewUserViewmodel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements UserListeners {

    RecyclerView recyclerView;
    @BindView(R.id.users_name)
    TextInputEditText mUserName;
    @BindView(R.id.users_skill)
    TextInputEditText mSkill;

    GetUsersViewmodel getUsersViewmodel;
    NewUserViewmodel newUserViewmodel;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialises the views
        ButterKnife.bind(this);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        //Set up the ViewModel
        getUsersViewmodel = ViewModelProviders.of(this).get(GetUsersViewmodel.class);
        newUserViewmodel = ViewModelProviders.of(this).get(NewUserViewmodel.class);

        // sets listeners
        getUsersViewmodel.setListener(this);

        // retrieves users
        getUsersViewmodel.getUsers();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mUserName.getText().toString();
                String skill = mSkill.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    mUserName.setError("required");
                } else if (TextUtils.isEmpty(skill)) {
                    mSkill.setError("required");
                } else {
                    User user = new User(mUserName.getText().toString().trim(), mSkill.getText().toString().trim());
                    newUserViewmodel.addUsers(user);

                    // clears the input text
                    mUserName.setText("");
                    mSkill.setText("");

                    // retrieves users
                    getUsersViewmodel.getUsers();
                }
            }
        });

        onSwipeToDelete();
    }

    // deletes an item from the list
    public void onSwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                // get the view holder item tag and store it in an integer variable
                int id = viewHolder.getAdapterPosition();

                // deletes user using the id
                getUsersViewmodel.deleteUser(id);

            }
        }).attachToRecyclerView(recyclerView);
    }

    // override this method in other to receive from the listener
    @Override
    public void onSuccess(List<User> usersList) {
        recyclerView.setAdapter(new UsersAdapter(usersList));
    }

    @Override
    public void onDeleteSuccess(String message) {
        getUsersViewmodel.getUsers();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
