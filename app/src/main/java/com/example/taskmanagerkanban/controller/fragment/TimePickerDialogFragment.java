package com.example.taskmanagerkanban.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.taskmanagerkanban.R;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerDialogFragment extends DialogFragment {
    private static final String ARG_TIME_DATE ="date" ;
    public static final String EXTRA_TIME_PICKER_DATE = "com.example.taskmanagerkanban.timePickerDate";
    private Date mTime;
    private TimePicker mTimePicker;
    private TextView mTextView_save,mTextView_cancel;
    public TimePickerDialogFragment() {
        // Required empty public constructor
    }

    public static TimePickerDialogFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME_DATE,date);
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTime= (Date) getArguments().getSerializable(ARG_TIME_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.fragment_time_picker_dialog,null);
        findViews(view);
        initViews();
        setListeners();
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("choose time");
        AlertDialog dialog=builder.create();
        return dialog;

    }
    private void findViews(View view){
        mTimePicker=view.findViewById(R.id.time_picker);
        mTextView_save=view.findViewById(R.id.save_time_btn);
        mTextView_cancel=view.findViewById(R.id.cancel_time_btn);
    }
    private void initViews(){
        initTimePicker();
    }
    private void initTimePicker(){
        if (mTime !=null){

        }
        // TODO: 10/28/2020 set time in time picker
    }
    private void setListeners(){
        mTextView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date selectedTime=extractTimeFromTimePicker();
                sendResult(selectedTime);
                getDialog().dismiss();
            }
        });
        mTextView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }
    private Date extractTimeFromTimePicker(){
        int hour=mTimePicker.getCurrentHour();
        int minute=mTimePicker.getCurrentMinute();
        Calendar calendar=Calendar.getInstance();
/*        if (mTime==null)
            mTime=new Time();*/
        calendar.setTime(mTime);
        GregorianCalendar gregorianCalendar=new GregorianCalendar(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                hour,
                minute);
        return gregorianCalendar.getTime();


    }
    private void sendResult(Date date){
        Fragment fragment=getTargetFragment();
        Intent intent=new Intent();
        intent.putExtra(EXTRA_TIME_PICKER_DATE,date);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}