package com.example.taskmanagerkanban.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.model.User;
import com.example.taskmanagerkanban.repository.TaskRepository;
import com.example.taskmanagerkanban.repository.UserDBRepository;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class UsersListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FrameLayout mFrameLayout_recycler,mFrameLayout_empty;
    private UserAdapter mAdapter;

    private UserDBRepository mUserDBRepository;
    private TaskRepository mTaskRepository;
    public UsersListFragment() {
        // Required empty public constructor
    }


    public static UsersListFragment newInstance() {

        Bundle args = new Bundle();

        UsersListFragment fragment = new UsersListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserDBRepository=UserDBRepository.getInstance(getContext());
        mTaskRepository=TaskRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_users_list, container, false);
        findViews(view);
        initView();
        return view;
    }
    private void findViews(View view){
        mRecyclerView=view.findViewById(R.id.recyclerView);
        mFrameLayout_recycler=view.findViewById(R.id.recyclerLayout);
        mFrameLayout_empty=view.findViewById(R.id.empty_layout);
    }
    private void initView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateAdapter();
    }

    private class UserHolder extends RecyclerView.ViewHolder {
        private TextView mTextView_time,mTextView_userName,mTextView_numOfTask;
        private ImageView mImageView_pic_user,mImageView_delete;
        private User mUser;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            mTextView_time=itemView.findViewById(R.id.date_item);
            mTextView_numOfTask=itemView.findViewById(R.id.numOfTask_item);
            mTextView_userName=itemView.findViewById(R.id.userName_item);
            mImageView_pic_user=itemView.findViewById(R.id.user_pic_item);
            mImageView_delete=itemView.findViewById(R.id.delete_item);

            mImageView_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mUserDBRepository.delete(mUser);
                    updateAdapter();
                }
            });

        }
        public void bindUser(User user){
            mUser=user;

            String dateString = getStringForTimeView();
            mTextView_time.setText(dateString);
            mTextView_userName.setText(user.getUserName());
            int num=mTaskRepository.countTasks(mUser.getUUID());
            mTextView_numOfTask.setText("num of tasks : "+num);
            // TODO: 11/4/2020 setUser pic
        }
        @NotNull
        private String getStringForTimeView() {
            SimpleDateFormat format=new SimpleDateFormat("MMM dd , yyyy  hh : mm a");
            return format.format(mUser.getDate_signUp());
        }



    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder>{

        private List<User> mUsers =new ArrayList<>();

        public UserAdapter(List<User> users) {
            mUsers = users;
        }

        public List<User> getUsers() {
            return mUsers;
        }

        public void setUsers(List<User> users) {
            mUsers = users;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getContext())
                    .inflate(R.layout.user_item,parent,false);
            UserHolder userHolder=new UserHolder(view);
            return userHolder;
        }

        @Override
        public void onBindViewHolder(UserHolder holder, int position) {
            User user= mUsers.get(position);
            holder.bindUser(user);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }
    public void updateAdapter(){

        List<User>users=mUserDBRepository.getUsers();
        if (users.size()==0){
            mFrameLayout_empty.setVisibility(View.VISIBLE);
            mFrameLayout_recycler.setVisibility(View.GONE);
        }
        else {
            mFrameLayout_empty.setVisibility(View.GONE);
            mFrameLayout_recycler.setVisibility(View.VISIBLE);
            if(mAdapter==null){
                mAdapter=new UserAdapter(users);
                mRecyclerView.setAdapter(mAdapter);
            }
            mAdapter.setUsers(users);
            mAdapter.notifyDataSetChanged();

        }
    }

}