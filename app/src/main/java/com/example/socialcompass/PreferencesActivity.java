package com.example.socialcompass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class PreferencesActivity extends AppCompatActivity {

    private FriendViewModel viewModel;
    private TextView addFriendButton;
    private EditText newFriend;
    public static int REQUEST_CODE = 7;

    private int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        viewModel = new ViewModelProvider(this)
                .get(FriendViewModel.class);

        this.newFriend = this.findViewById(R.id.editUUID);
        this.addFriendButton = this.findViewById(R.id.add_Btn);

        addFriendButton.setOnClickListener(this::onAddClicked);

        result = RESULT_FIRST_USER;
    }

    public void onSubmitClicked(View view){
        setResult(this.result);
        finish();
    }


    public void onAddClicked(View view) {
        String uuid = ((TextView) findViewById(R.id.editUUID)).getText().toString();
        newFriend.setText("");
        viewModel.addFriend(uuid);

        /* If names were added, CompassView needs to refresh it's list */
        this.result = RESULT_OK;
    }
}