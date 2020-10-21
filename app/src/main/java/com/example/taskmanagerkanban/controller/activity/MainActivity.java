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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.controller.fragment.MainFragment;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.model.TaskState;

import com.example.taskmanagerkanban.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mPager2;
    private FloatingActionButton mButton_plus;
    private TaskRepository mTaskRepository;
    private TextInputEditText mTitle,mDesc;
    private Button mDate,mClock;
    private CheckBox mCheckBox;

    public Intent newIntent(Context context){
        return new Intent(context,MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTaskRepository=TaskRepository.getInstance(this);
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
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                final View customDialog= LayoutInflater.from(MainActivity.this).inflate(R.layout.add_task,null);
                builder.setView(customDialog);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTitle=customDialog.findViewById(R.id.title_dialog);
                        mDesc=customDialog.findViewById(R.id.desc_dialog);
                        mDate=customDialog.findViewById(R.id.date_dialog);
                        mClock=customDialog.findViewById(R.id.clock_dialog);
                        mCheckBox=customDialog.findViewById(R.id.checkbox_dialog);
                        Task task=getDialogViews();
                        if (task!=null){
                            mTaskRepository.insert(task);
                        }



                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO close
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
    }
    private void initView(){

        TaskAdapter taskAdapter=new TaskAdapter(this);
        mPager2.setAdapter(taskAdapter);
        manageTab();


    }

    private Task getDialogViews(){
        if (mTitle.getText().toString().isEmpty() || mDesc.getText().toString().isEmpty()
            /*||mDate.getText().toString().isEmpty() || mClock.getText().toString().isEmpty()*/){
            Toast.makeText(this, "please fill the inputs", Toast.LENGTH_SHORT).show();
            return null;
        }
        else {
            Task task=new Task(mTitle.getText().toString(),mDesc.getText().toString(),mDate.getText().toString(),
                    mClock.getText().toString(),mCheckBox.isChecked() ? "DONE" : "TODO");
            return task;
        }
    }



    private class TaskAdapter extends FragmentStateAdapter{


        public TaskAdapter(@NonNull FragmentActivity fragmentActivity ) {
            super(fragmentActivity);

        }




        @NonNull
        @Override
        public Fragment createFragment(int position) {

            return MainFragment.newInstance(position);
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
}