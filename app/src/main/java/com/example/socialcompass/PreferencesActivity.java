package com.example.socialcompass;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        String newDisplayName = ((TextView) findViewById(R.id.editDisplayName)).getText().toString();
        Intent intent=new Intent();
        if(newDisplayName.equals("")){
            intent.putExtra("newDisplayName", "error");
        } else {
            intent.putExtra("newDisplayName", newDisplayName);
        }

        setResult(this.result, intent);
        finish();
    }


    public void onAddClicked(View view) {
        String uuid = ((TextView) findViewById(R.id.editUUID)).getText().toString();
        newFriend.setText("");

        for(var l : viewModel.getFriendsSync()){
            if(l.friendID.equals(uuid)){
                Log.i("two", "alreadyyyyyyyyy exist" + uuid);
                return;
            }
        }
//        for(var l : viewModel.getFriendsSync()){Log.i("two", l.friendID);}
        viewModel.addFriend(uuid);

        /* If names were added, CompassView needs to refresh it's list */
        this.result = RESULT_OK;
    }
}