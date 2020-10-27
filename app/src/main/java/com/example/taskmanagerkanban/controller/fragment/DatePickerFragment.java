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
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.taskmanagerkanban.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_USER_SELECTED_DATE ="com.example.taskmanagerkanban.dateSelected" ;
    private static final String ARG_DATE_TASK = "dateOfTask";
    private DatePicker mDatePicker;
    private Date mDate;
    private TextView mTextView_save,mTextView_cancel;
    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE_TASK,date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate= (Date) getArguments().getSerializable(ARG_DATE_TASK);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.fragment_dialog_date_picker,null);
        findViews(view);
        setListeners();
        initView();
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setTitle("Choose Date")
                .setView(view);
        AlertDialog dialog=builder.create();
        return dialog;

    }

    private void findViews(View view){
        mDatePicker=view.findViewById(R.id.date_picker);
        mTextView_save=view.findViewById(R.id.save_date_btn);
        mTextView_cancel=view.findViewById(R.id.cancel_date_btn);
    }

    private void setListeners(){
        mTextView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date selectedDate=extractDateFromDatePicker();
                sendResult(selectedDate);
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
    private Date extractDateFromDatePicker(){
        int year=mDatePicker.getYear();
        int month=mDatePicker.getMonth();
        int dayOfMonth=mDatePicker.getDayOfMonth();
        GregorianCalendar calendar=new GregorianCalendar(year,month,dayOfMonth);
        return calendar.getTime();
    }
    private void sendResult(Date date){
        Fragment fragment=getTargetFragment();
        Intent intent=new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_DATE,date);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
    private void initView(){
        if (mDate !=null)
            initDatePicker();
    }
    private void initDatePicker(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mDate);
        mDatePicker.init(
                calendar.get(Calendar.YEAR) ,
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }


}