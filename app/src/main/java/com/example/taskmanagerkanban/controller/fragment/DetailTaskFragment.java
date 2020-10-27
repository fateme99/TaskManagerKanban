package com.example.taskmanagerkanban.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.repository.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DetailTaskFragment extends DialogFragment {
    private static final String ARGS_TASK = "task";
    private static final int REQUEST_CODE_DATEPICKER = 0;
    private static final String FRAGMENT_TAG_DATEPICKER ="datePicker" ;
    private TextView mTextView_save,mTextView_edit,mTextView_delete;
    private RadioButton mRadioButton_todo,mRadioButton_doing,mRadioButton_done;
    private EditText mEditText_title,mEditText_desc;
    private Button mButton_date,mButton_clock;
    private Task mTask;
    private TaskRepository mTaskRepository;
    public DetailTaskFragment() {
        // Required empty public constructor
    }

    public static DetailTaskFragment newInstance(Task task) {
        
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK,task);
        DetailTaskFragment fragment = new DetailTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode!= Activity.RESULT_OK     ||  data==null)
            return;
        if (requestCode==REQUEST_CODE_DATEPICKER){
            mTask.setDate((Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE));
            mButton_date.setText(getStringForDate());
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask= (Task) getArguments().getSerializable(ARGS_TASK);
        mTaskRepository=TaskRepository.getInstance(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.fragment_dialog_detail,null);
        findViews(view);
        setValues();
        setListeners();
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("Detail page");
        AlertDialog dialog=builder.create();
        return dialog;

    }
    private void findViews(View view){
        mEditText_title=view.findViewById(R.id.title_dialog);
        mEditText_desc=view.findViewById(R.id.desc_dialog);
        mButton_date=view.findViewById(R.id.date_dialog);
        mRadioButton_todo=view.findViewById(R.id.todo_add_radioBtn);
        mRadioButton_doing=view.findViewById(R.id.doing_add_radioBtn);
        mRadioButton_done=view.findViewById(R.id.done_add_radioBtn);
        mTextView_save=view.findViewById(R.id.save_btn);
        mTextView_edit=view.findViewById(R.id.edit_btn);
        mTextView_delete=view.findViewById(R.id.delete_btn);
        mButton_clock=view.findViewById(R.id.clock_dialog);
    }
    private void setValues(){

        mEditText_title.setText(mTask.getTitle());
        mEditText_desc.setText(mTask.getDescription());
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("MMM dd,yyyy");
        String dateString=simpleDateFormat.format(mTask.getDate());
        mButton_date.setText(dateString);
        switch (mTask.getTaskState()){
            case "TODO":
                mRadioButton_todo.setChecked(true);
                break;
            case "DOING":
                mRadioButton_doing.setChecked(true);
                break;
            default:
                mRadioButton_done.setChecked(true);
        }
        // TODO: 10/27/2020 set clock
    }
    private void setListeners(){
        mButton_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment=DatePickerFragment.newInstance(mTask.getDate());
                fragment.setTargetFragment(DetailTaskFragment.this,REQUEST_CODE_DATEPICKER);
                fragment.show(getActivity().getSupportFragmentManager(),FRAGMENT_TAG_DATEPICKER);
            }
        });
        mTextView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogView();
                mTaskRepository.insert(mTask);
                Toast.makeText(getActivity(), "insert new task successfully.", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });
        mTextView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogView();
                mTaskRepository.updateTask(mTask);
                Toast.makeText(getActivity(), "edited successfully", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();

            }
        });
        mTextView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTaskRepository.deleteTask(mTask.getId());
                getDialog().dismiss();
            }
        });
    }
    private void getDialogView(){
        mTask.setTitle(mEditText_title.getText().toString());
        mTask.setDescription(mEditText_desc.getText().toString());
        getTaskState();
        // TODO: 10/28/2020 set clock
    }
    private void getTaskState(){
        if (mRadioButton_todo.isChecked())
            mTask.setTaskState("TODO");
        else if (mRadioButton_doing.isChecked())
            mTask.setTaskState("DOING");
        else 
            mTask.setTaskState("DONE");
    }
    private String getStringForDate() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd , yyyy");
        return simpleDateFormat.format(mTask.getDate());
    }
}