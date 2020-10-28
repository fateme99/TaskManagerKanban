package com.example.taskmanagerkanban.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagerkanban.controller.activity.SingleFragmentActivity;
import com.example.taskmanagerkanban.controller.fragment.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance();
    }
}