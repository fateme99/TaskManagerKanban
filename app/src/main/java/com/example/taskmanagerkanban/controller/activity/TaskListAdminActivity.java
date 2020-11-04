package com.example.taskmanagerkanban.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.controller.activity.SingleFragmentActivity;
import com.example.taskmanagerkanban.controller.fragment.EditAminFragment;
import com.example.taskmanagerkanban.controller.fragment.TaskListAdminFragment;


public class TaskListAdminActivity extends SingleFragmentActivity implements EditAminFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return TaskListAdminFragment.newInstance();
    }

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,TaskListAdminActivity.class);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_admin_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:
                Intent intent=LoginActivity.newIntent(this);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void updateTaskListAdapter() {
        TaskListAdminFragment fragment= (TaskListAdminFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container_fragment);
        fragment.updateAdapter();

    }
}