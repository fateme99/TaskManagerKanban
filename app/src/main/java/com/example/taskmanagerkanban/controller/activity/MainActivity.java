package com.example.taskmanagerkanban.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.controller.fragment.MainFragment;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.utils.TaskState;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TableLayout mTableLayout;
    private ViewPager2 mPager2;
    public Intent newIntent(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initView();
    }
    private void findViews(){
        //mTableLayout=findViewById(R.id.tab);
        mPager2=findViewById(R.id.viewPager);
    }
    private void initView(){
        TaskAdapter taskAdapter=new TaskAdapter(this,TaskState.TODO);
        mPager2.setAdapter(taskAdapter);
    }
    private class TaskAdapter extends FragmentStateAdapter{
        TaskState mTaskState;

        public TaskAdapter(@NonNull FragmentActivity fragmentActivity , TaskState taskState) {
            super(fragmentActivity);
            mTaskState=taskState;
        }




        @NonNull
        @Override
        public Fragment createFragment(int position) {
            MainFragment mainFragment=MainFragment.newInstance(mTaskState);
            return mainFragment;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}