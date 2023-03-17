package com.example.socialcompass;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.UUID;

public class PreferencesActivity extends AppCompatActivity {

    private FriendViewModel viewModel;
    private TextView addFriendButton;
    private EditText newFriend;
    private static String myID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        viewModel = new ViewModelProvider(this)
                .get(FriendViewModel.class);

        loadMyID();

        this.newFriend = this.findViewById(R.id.editUUID);
        this.addFriendButton = this.findViewById(R.id.add_Btn);

        addFriendButton.setOnClickListener(this::onAddClicked);
    }

    public void onSubmitClicked(View view){
        finish();
    }


    public void onAddClicked(View view) {
        String uuid = newFriend.getText().toString();
        newFriend.setText("");
        viewModel.addFriend(uuid);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveMyID();
    }

    public void loadMyID(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        myID = preferences.getString("MyID", "");

    }
    public void saveMyID(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (myID == null)
        {
            myID = UUID.randomUUID().toString();
        }

        editor.putString("MyID", myID);

        editor.apply();
    }

    public static String getMyID() {
        return myID;
    }
}