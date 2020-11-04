package com.example.taskmanagerkanban.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.controller.activity.TaskListActivity;
import com.example.taskmanagerkanban.controller.activity.TaskListAdminActivity;
import com.example.taskmanagerkanban.model.User;
import com.example.taskmanagerkanban.repository.UserDBRepository;


public class SignUpFragment extends Fragment {
    private EditText mEditText_username, mEditText_pass, mEditText_repass, mEditText_email, mEditText_phone;
    private Button mButton_signUp;
    private UserDBRepository mRepository;
    private CheckBox mCheckBox_isManager;

    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = UserDBRepository.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        findViews(view);
        setListeners();
        return view;
    }

    private void findViews(View view) {
        mEditText_username = view.findViewById(R.id.user_input);
        mEditText_pass = view.findViewById(R.id.passWord_input);
        mEditText_repass = view.findViewById(R.id.repass_input);

        mButton_signUp = view.findViewById(R.id.signUp_btn);
        mCheckBox_isManager = view.findViewById(R.id.isManager_checkBox);
    }

    private void setListeners() {
        mButton_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mEditText_username.getText().toString().trim();
                String pass = mEditText_pass.getText().toString().trim();
                String rePass = mEditText_repass.getText().toString().trim();
                if (userName.isEmpty() || pass.isEmpty() || rePass.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.fill_please, Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(rePass)) {
                        final boolean isManager = mCheckBox_isManager.isChecked();
                        final User user = new User(userName, pass, isManager);
                        mRepository.insert(user);
                        Toast.makeText(getActivity(), "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (isManager) {
                                    Intent intent = TaskListAdminActivity.newIntent(getActivity());
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    Intent intent = TaskListActivity.newIntent(getActivity(),user);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                                getActivity().finish();
                            }
                        }, 2000);

                    }
                }
            }
        });
    }
}