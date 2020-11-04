package com.example.taskmanagerkanban.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagerkanban.controller.activity.SingleFragmentActivity;
import com.example.taskmanagerkanban.controller.fragment.UsersListFragment;

public class UsersListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,UsersListActivity.class);
        return intent;
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