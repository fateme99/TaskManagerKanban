package com.example.taskmanagerkanban.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.utils.TaskState;


public class MainFragment extends Fragment {
    public static final String ARG_TASK_STATE="com.example.taskmanagerkanban.taskState";

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(TaskState taskState) {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        args.putSerializable(ARG_TASK_STATE,taskState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}