package com.example.taskmanagerkanban.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.repository.UserDBRepository;
import com.example.taskmanagerkanban.controller.activity.TaskListActivity;
import com.example.taskmanagerkanban.controller.activity.SignUPActivity;
import com.example.taskmanagerkanban.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;


public class LoginFragment extends Fragment {
    private TextInputEditText mEditText_userName,mEditText_pass;
    private Button mButton_login,mButton_signUp;
    private UserDBRepository mRepository;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository=UserDBRepository.getInstance(getActivity());
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        setListeners();
        return view;
    }
    private void findViews(View view){
        mEditText_userName=view.findViewById(R.id.userName_input);
        mEditText_pass=view.findViewById(R.id.pass_input);
        mButton_login=view.findViewById(R.id.login_btn);
        mButton_signUp=view.findViewById(R.id.sign_up_btn);
    }
    private void setListeners(){
        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNAme=mEditText_userName.getText().toString().trim();
                String passWord=mEditText_pass.getText().toString().trim();
                if (userNAme.isEmpty() || passWord.isEmpty()){
                    Toast.makeText(getActivity(), R.string.fill_please, Toast.LENGTH_SHORT).show();
                }
                else if (checkUserInfo(userNAme,passWord)){
                    Toast.makeText(getActivity(), "خوش آمدید", Toast.LENGTH_SHORT).show();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent= TaskListActivity.newIntent(getActivity());
                            startActivity(intent);
                        }
                    },2000);

                }
                else {
                    Toast.makeText(getActivity(),R.string.wrong_user_info, Toast.LENGTH_SHORT).show();
                }

            }
        });
        mButton_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= SignUPActivity.newIntent(getActivity());
                startActivity(intent);

            }
        });
    }
    private boolean checkUserInfo(String userName,String pass){
        List<User> users=mRepository.getList();
        if (users==null)
            return false;
        User user=mRepository.get(userName);
        if (user==null){
            return false;
        }
        if (user.getPassWord().equals(pass))
            return true;
        return false;
    }
}