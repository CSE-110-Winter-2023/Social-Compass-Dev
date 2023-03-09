package com.example.socialcompass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class PreferencesActivity extends AppCompatActivity {

    private FriendViewModel viewModel;
    private Button addFriendButton;
    private EditText newFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        viewModel = new ViewModelProvider(this)
                .get(FriendViewModel.class);

        this.newFriend = this.findViewById(R.id.editUUID);
        this.addFriendButton = this.findViewById(R.id.add_Btn);

        addFriendButton.setOnClickListener(this::onAddClicked);
    }


    public void onAddClicked(View view) {
        String uuid = findViewById(R.id.editUUID).toString();
        newFriend.setText("");
        viewModel.addFriend(uuid);
    }
}