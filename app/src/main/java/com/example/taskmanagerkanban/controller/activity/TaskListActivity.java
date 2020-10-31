package com.example.taskmanagerkanban.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.controller.fragment.AddTaskFragment;
import com.example.taskmanagerkanban.controller.fragment.DetailTaskFragment;
import com.example.taskmanagerkanban.controller.fragment.LoginFragment;
import com.example.taskmanagerkanban.controller.fragment.TaskListFragment;

import com.example.taskmanagerkanban.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.util.UUID;

public class TaskListActivity extends AppCompatActivity implements DetailTaskFragment.Callbacks
        , AddTaskFragment.Callbacks {
    private static final String TAG_ADD_TASK ="addTask" ;
    private static final String EXTRA_TASK_LIST_FRAGMENT_OBJ = "com.example.taskmanagerkanban.mTaskListFragment";
    private TabLayout mTabLayout;
    private ViewPager2 mPager2;

    private TaskListFragment mTaskListFragment;
    private FloatingActionButton mButton_plus;

    public static Intent newIntent(Context context){
        return new Intent(context, TaskListActivity.class);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(EXTRA_TASK_LIST_FRAGMENT_OBJ, (Serializable) mTaskListFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.user_log_out,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:
                Intent intent=LoginActivity.newIntent(this);
                startActivity(intent);
                return true;
            case R.id.delete_all_item:

                final AlertDialog alertDialog=new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Are sure ?");
                alertDialog.setMessage("this action will remove all of your task !");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TaskRepository.getInstance(TaskListActivity.this).deleteAll
                                ((UUID) getIntent().getSerializableExtra(LoginFragment.EXTRA_USER_ID));
                        updateTaskListAdapter();
                        dialogInterface.dismiss();
                    }

                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();
        initView();
        if (savedInstanceState!=null)
            mTaskListFragment= (TaskListFragment) savedInstanceState.
                    getSerializable(EXTRA_TASK_LIST_FRAGMENT_OBJ);
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
                AddTaskFragment addTaskFragment=AddTaskFragment.newInstance(
                        (UUID) getIntent().getSerializableExtra(LoginFragment.EXTRA_USER_ID));
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

            mTaskListFragment=TaskListFragment.newInstance(position, (UUID) getIntent().getSerializableExtra(LoginFragment.EXTRA_USER_ID));
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