package com.example.taskmanagerkanban.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Toast;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.controller.fragment.AddTaskFragment;
import com.example.taskmanagerkanban.controller.fragment.DetailTaskFragment;
import com.example.taskmanagerkanban.controller.fragment.TaskListFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskListActivity extends AppCompatActivity implements DetailTaskFragment.Callbacks {
    private static final String TAG_ADD_TASK ="addTask" ;
    private TabLayout mTabLayout;
    private ViewPager2 mPager2;

    private TaskListFragment mTaskListFragment;
    private FloatingActionButton mButton_plus;

    public static Intent newIntent(Context context){
        return new Intent(context, TaskListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();
        initView();
    }
    private void findViews(){
        mTabLayout =findViewById(R.id.tab);
        mPager2=findViewById(R.id.viewPager);
        mButton_plus=findViewById(R.id.plus_btn);


    }
    private void setListeners(){
        mButton_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskFragment addTaskFragment=AddTaskFragment.newInstance();
                addTaskFragment.show(getSupportFragmentManager(),TAG_ADD_TASK);
            }
        });

    }
    private void initView(){

        TaskAdapter taskAdapter=new TaskAdapter(this);
        mPager2.setAdapter(taskAdapter);
        manageTab();


    }




    private class TaskAdapter extends FragmentStateAdapter{


        public TaskAdapter(@NonNull FragmentActivity fragmentActivity ) {
            super(fragmentActivity);

        }




        @NonNull
        @Override
        public Fragment createFragment(int position) {

            mTaskListFragment=TaskListFragment.newInstance(position);
            return mTaskListFragment;
        }

        @Override
        public int getItemCount() {
            return 3;
        }



    }
    private void manageTab() {
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(
                mTabLayout,
                mPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position){
                            case 0:
                                tab.setText("TO DO");

                                break;
                            case 1:
                                tab.setText("DOING");
                                break;
                            case 2:
                                tab.setText("DONE");


                        }
                    }
                });
        tabLayoutMediator.attach();
    }

    @Override
    public void updateTaskListAdapter() {
        mTaskListFragment.updateAdapter();
    }
}