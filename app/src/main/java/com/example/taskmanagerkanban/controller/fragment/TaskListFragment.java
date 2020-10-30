package com.example.taskmanagerkanban.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.controller.activity.TaskListActivity;
import com.example.taskmanagerkanban.controller.fragment.DetailTaskFragment.Callbacks;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class TaskListFragment extends Fragment  {

    private static final String ARG_KEY_TASKSTATE="com.example.taskmanagerkanban.taskState";
    private static final String TAG_FRAGMENT_DETAIL ="detailTask" ;
    private FrameLayout mFrameLayout_recycler,mFrameLayout_empty;
    private RecyclerView mRecyclerView;
    private TaskRepository mTaskRepository;
    private int mPosition;
    private TaskAdapter mAdapter;
    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance(int  position) {

        Bundle args = new Bundle();
        TaskListFragment fragment = new TaskListFragment();
        args.putInt(ARG_KEY_TASKSTATE,position);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition=getArguments().getInt(ARG_KEY_TASKSTATE);
        mTaskRepository=TaskRepository.getInstance(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_main, container, false);
        findViews(view);
        initView();
        setListeners();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: call in on pause ");
        
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
        Log.d(TAG, "onResume: ");
    }

    private void findViews(View view){
        mRecyclerView=view.findViewById(R.id.recyclerView);
        mFrameLayout_recycler=view.findViewById(R.id.recyclerLayout);
        mFrameLayout_empty=view.findViewById(R.id.empty_layout);

    }
    private void initView(){

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateAdapter();
    }
    private void setListeners(){

    }
    private class TaskHolder extends RecyclerView.ViewHolder {
        private TextView mTextView_title,mTextView_time,mTextView_pic;

        private Task mTask;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextView_title=itemView.findViewById(R.id.titile_item);
            mTextView_time=itemView.findViewById(R.id.time_item);
            mTextView_pic=itemView.findViewById(R.id.pic_item_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                DetailTaskFragment fragment=DetailTaskFragment.newInstance(mTask);

                fragment.show(getActivity().getSupportFragmentManager(),TAG_FRAGMENT_DETAIL);


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
        }


    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>{

        private List<Task>mTasks=new ArrayList<>();

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
                    .inflate(R.layout.task_item,parent,false);
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
        Log.d(TAG, "updateAdapter: yes");
        String state;
        switch (mPosition){
            case 2:
                state="DONE";
                break;
            case 1:
                state="DOING";
                break;
            default:
                state="TODO";
        }
        mTaskRepository=TaskRepository.getInstance(getActivity());
        List<Task>tasks=mTaskRepository.getTasks(state);
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