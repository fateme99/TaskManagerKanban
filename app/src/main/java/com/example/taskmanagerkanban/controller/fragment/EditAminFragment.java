package com.example.taskmanagerkanban.controller.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.repository.TaskRepository;
import com.example.taskmanagerkanban.repository.UserDBRepository;

import java.text.SimpleDateFormat;


public class EditAminFragment extends DialogFragment {
    private static final int REQUEST_CODE_DATE_PICKER = 0;
    private static final int REQUEST_CODE_TIME_PICKER = 1;
    private static final String FRAGMENT_TAG_DATE_PICKER ="datePicker" ;
    private static final String FRAGMENT_TAG_TIME_PICKER ="timePicker" ;
    private Callbacks mCallbacks;
    private Task mTask;
    private UserDBRepository mUserDBRepository;
    private TaskRepository mTaskRepository;
    private static final String ARG_TASK = "task";
    private TextView mTextView_edit,mTextView_cancel;
    private RadioButton mRadioButton_todo,mRadioButton_doing,mRadioButton_done;
    private EditText mEditText_title,mEditText_desc,mEditText_userName;
    private Button mButton_date,mButton_clock;
    public EditAminFragment() {
        // Required empty public constructor
    }

    public static EditAminFragment newInstance(Task task) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK,task);
        EditAminFragment fragment = new EditAminFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditAminFragment.Callbacks)
            mCallbacks = (Callbacks) context;
        else {
            throw new ClassCastException(context.toString()
                    + " must implement Callbacks");
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask= (Task) getArguments().getSerializable(ARG_TASK);
        mUserDBRepository=UserDBRepository.getInstance(getContext());
        mTaskRepository=TaskRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dialog_edit_amin, container, false);
        findViews(view);
        setValues();
        setListeners();
        return view;
    }

    private void findViews(View view){
        mTextView_cancel=view.findViewById(R.id.cancel_btn);
        mTextView_edit=view.findViewById(R.id.edit_btn);
        mEditText_title=view.findViewById(R.id.title_dialog);
        mEditText_desc=view.findViewById(R.id.desc_dialog);
        mButton_date=view.findViewById(R.id.date_dialog);
        mRadioButton_todo=view.findViewById(R.id.todo_add_radioBtn);
        mRadioButton_doing=view.findViewById(R.id.doing_add_radioBtn);
        mRadioButton_done=view.findViewById(R.id.done_add_radioBtn);
        mButton_clock=view.findViewById(R.id.clock_dialog);
        mEditText_userName=view.findViewById(R.id.userName_dialog);
    }
    private void setValues(){
        mEditText_title.setText(mTask.getTitle());
        mEditText_desc.setText(mTask.getDescription());
        mButton_date.setText(getStringForDate());
        mButton_clock.setText(getStringForClock());

        mEditText_userName.setText(mUserDBRepository.getUser(mTask.getUser_id()).getUserName());
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
    }
    private void setListeners(){
        mButton_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment=DatePickerFragment.newInstance(mTask.getDate());
                fragment.setTargetFragment(EditAminFragment.this, REQUEST_CODE_DATE_PICKER);
                fragment.show(getActivity().getSupportFragmentManager(), FRAGMENT_TAG_DATE_PICKER);
            }
        });
        mButton_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialogFragment fragment=TimePickerDialogFragment.newInstance(mTask.getDate());
                fragment.setTargetFragment(EditAminFragment.this, REQUEST_CODE_TIME_PICKER);
                fragment.show(getActivity().getSupportFragmentManager(),FRAGMENT_TAG_TIME_PICKER);
            }
        });
        mTextView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogView();
                mTaskRepository.updateTask(mTask);
                Toast.makeText(getActivity(), "edited successfully", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
                mCallbacks.updateTaskListAdapter();
            }
        });
        mTextView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }
    private String getStringForDate() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd , yyyy");
        return simpleDateFormat.format(mTask.getDate());
    }
    private String getStringForClock(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh : mm a");
        return simpleDateFormat.format(mTask.getDate());
    }
    private void getDialogView(){
        mTask.setTitle(mEditText_title.getText().toString());
        mTask.setDescription(mEditText_desc.getText().toString());
        getTaskState();
        mTask.setUser_id(mUserDBRepository.getUser(mEditText_userName.getText().toString()).getUUID());
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
    public interface Callbacks{
        void updateTaskListAdapter();
    }

}