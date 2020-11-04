package com.example.taskmanagerkanban.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TaskListAdminFragment extends Fragment  {

    private static final String FRAGMENT_TAG_EDIT = "editDialog" ;
    private RecyclerView mRecyclerView;
    private TaskRepository mTaskRepository;
    private UserDBRepository mUserDBRepository;

    private FrameLayout mFrameLayout_recycler,mFrameLayout_empty;
    private TaskAdapter mAdapter;
    public TaskListAdminFragment() {
        // Required empty public constructor
    }

    public static TaskListAdminFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TaskListAdminFragment fragment = new TaskListAdminFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository=TaskRepository.getInstance(getActivity());
        mUserDBRepository=UserDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_task_list_admin, container, false);
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




    private class TaskHolder extends RecyclerView.ViewHolder {
        private TextView mTextView_title,mTextView_time,mTextView_pic,mTextView_userName,mTextView_state;
        private ImageView mImageView_edit,mImageView_delete;
        private Task mTask;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextView_title=itemView.findViewById(R.id.titile_item);
            mTextView_time=itemView.findViewById(R.id.time_item);
            mTextView_pic=itemView.findViewById(R.id.pic_item_txt);
            mTextView_userName=itemView.findViewById(R.id.user_item);
            mImageView_edit=itemView.findViewById(R.id.edit_item);
            mImageView_delete=itemView.findViewById(R.id.delete_item);
            mTextView_state=itemView.findViewById(R.id.state_item);
            mImageView_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditAminFragment editAminFragment=EditAminFragment.newInstance(mTask);
                    editAminFragment.show(getActivity().getSupportFragmentManager(),FRAGMENT_TAG_EDIT);
                }
            });
            mImageView_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTaskRepository.deleteTask(mTask.getId());
                    updateAdapter();
                }
            });

        }
        public void bindTask(Task task){
            mTask=task;
            mTextView_title.setText(task.getTitle());
            SimpleDateFormat format=new SimpleDateFormat("MMM dd , yyyy  hh : mm a");
            String dateString=format.format(task.getDate());
            mTextView_time.setText(dateString);
            mTextView_pic.setText(mTask.getTitle().charAt(0)+"");
            User user=mUserDBRepository.getUser(mTask.getUser_id());
            mTextView_userName.setText(user.getUserName());
            mTextView_state.setText(task.getTaskState());
        }


    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>{

        private List<Task> mTasks=new ArrayList<>();

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getContext())
                    .inflate(R.layout.admin_item,parent,false);
            TaskHolder taskHolder=new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task=mTasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
    public void updateAdapter(){

        List<Task>tasks=mTaskRepository.getTasks();
        if (tasks.size()==0){
            mFrameLayout_empty.setVisibility(View.VISIBLE);
            mFrameLayout_recycler.setVisibility(View.GONE);
        }
        else {
            mFrameLayout_empty.setVisibility(View.GONE);
            mFrameLayout_recycler.setVisibility(View.VISIBLE);
            if(mAdapter==null){
                mAdapter=new TaskAdapter(tasks);
                mRecyclerView.setAdapter(mAdapter);
            }
            mAdapter.setTasks(tasks);
            mAdapter.notifyDataSetChanged();

        }
    }


}