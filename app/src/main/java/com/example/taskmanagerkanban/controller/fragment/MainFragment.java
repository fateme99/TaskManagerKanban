package com.example.taskmanagerkanban.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.model.TaskState;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    public static final String ARG_TASK_STATE="com.example.taskmanagerkanban.taskState";
    private RecyclerView mRecyclerView;
    private TaskState mTaskState;
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
        mTaskState= (TaskState) getArguments().getSerializable(ARG_TASK_STATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_main, container, false);
        findViews(view);
        initView();
        return view;
    }
    private void findViews(View view){
        mRecyclerView=view.findViewById(R.id.recyclerView);
    }
    private void initView(){
        //TODO : get tasks
        List<Task>tasks=new ArrayList<>();
        tasks.add(new Task("tamrin 1" , "desc1","1394/5/14","23:14"));
        tasks.add(new Task("tamrin 2" , "desc1","1394/5/14","23:14"));
        tasks.add(new Task("tamrin 3" , "desc1","1394/5/14","23:14"));
        tasks.add(new Task("tamrin 4" , "desc1","1394/5/14","23:14"));
        tasks.add(new Task("tamrin 5" , "desc1","1394/5/14","23:14"));
        tasks.add(new Task("tamrin 6" , "desc1","1394/5/14","23:14"));
        tasks.add(new Task("tamrin 7" , "desc1","1394/5/14","23:14"));
        tasks.add(new Task("tamrin 8" , "desc1","1394/5/14","23:14"));
        //TODO : delete above
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new TaskAdapter(tasks));
    }
    private class TaskHolder extends RecyclerView.ViewHolder {
        private TextView mTextView_title,mTextView_time;
        private ImageView mImageView_pic;
        private Task mTask;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextView_title=itemView.findViewById(R.id.titile_item);
            mTextView_time=itemView.findViewById(R.id.time_item);
            mImageView_pic=itemView.findViewById(R.id.pic_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO : show details
                }
            });

        }
        public void bindTask(Task task){
            mTask=task;
            mTextView_title.setText(task.getTitle());
            mTextView_time.setText(task.getDate());

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
}