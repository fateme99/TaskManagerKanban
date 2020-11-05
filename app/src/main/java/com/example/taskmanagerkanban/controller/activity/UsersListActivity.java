package com.example.taskmanagerkanban.controller.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.taskmanagerkanban.controller.activity.SingleFragmentActivity;
import com.example.taskmanagerkanban.controller.fragment.UsersListFragment;

public class UsersListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,UsersListActivity.class);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            /*doMySearch(query);*/
        }
    }

    @Override
    protected Fragment createFragment() {
        return UsersListFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}