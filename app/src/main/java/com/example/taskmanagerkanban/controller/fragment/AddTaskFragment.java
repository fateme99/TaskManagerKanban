package com.example.taskmanagerkanban.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.repository.TaskRepository;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class AddTaskFragment extends DialogFragment {

    private static final String FRAGMENT_TAG_DATEPICKER ="Datepicker" ;
    private static final int REQUEST_CODE_DATE_PICKER =0 ;
    private TextInputEditText mTitle,mDesc;
    private Button mDate_btn, mClock_btn;
    private TextView mTextView_save,mTextView_cancel;
    private RadioButton mRadio_todo,mRadio_doing,mRadio_done;
    private TaskRepository mTaskRepository;
    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode!=Activity.RESULT_OK || data==null)
            return;
        if (requestCode==REQUEST_CODE_DATE_PICKER  ){
            Date user_selected_date= (Date) data.getSerializableExtra(
                    DatePickerFragment.EXTRA_USER_SELECTED_DATE);
            updateTask(user_selected_date);

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository=TaskRepository.getInstance(getActivity());

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.fragment_add_task,null);
        findViews(view);
        setListeners();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Task")
                .setView(view);
        AlertDialog dialog=builder.create();
        return dialog;
    }
    private void findViews(View view){
        mTitle=view.findViewById(R.id.title_dialog);
        mDesc=view.findViewById(R.id.desc_dialog);
        mDate_btn =view.findViewById(R.id.date_dialog);
        mClock_btn =view.findViewById(R.id.clock_dialog);
        mRadio_todo=view.findViewById(R.id.todo_add_radioBtn);
        mRadio_doing=view.findViewById(R.id.doing_add_radioBtn);
        mRadio_done=view.findViewById(R.id.done_add_radioBtn);
        mTextView_save=view.findViewById(R.id.save_btn);
        mTextView_cancel=view.findViewById(R.id.cancel_btn);
    }
    private void setListeners(){
        mDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment=DatePickerFragment.newInstance(null);
                datePickerFragment.setTargetFragment(AddTaskFragment.this,REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getActivity().getSupportFragmentManager(),FRAGMENT_TAG_DATEPICKER);
            }
        });

        mTextView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: save btn pressed");
                Task task=getDialogViews();
                if (task!=null){
                    mTaskRepository.insert(task);
                    Toast.makeText(getActivity(), "add task successfully", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }
            }
        });
        mTextView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }
    private Task getDialogViews(){
        if (mTitle.getText().toString().isEmpty() || mDesc.getText().toString().isEmpty()
            /*||mDate.getText().toString().isEmpty() || mClock.getText().toString().isEmpty()*/){
            Toast.makeText(getActivity(), "please fill the inputs", Toast.LENGTH_SHORT).show();
            return null;
        }
        else {
            String status = getStatusOfTask();
            //TODO : edit date
            Task task=new Task(mTitle.getText().toString(),mDesc.getText().toString(),null,
                    mClock_btn.getText().toString(),status);
            return task;
        }
    }

    @NotNull
    private String getStatusOfTask() {
        String status;
        if (mRadio_todo.isChecked())
            status="TODO";
        else if (mRadio_doing.isChecked())
            status="DOING";
        else
            status="DONE";
        return status;
    }
    private void updateTask(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd,yyyy");
        String dateString=simpleDateFormat.format(date);
        mDate_btn.setText(dateString);
    }
}