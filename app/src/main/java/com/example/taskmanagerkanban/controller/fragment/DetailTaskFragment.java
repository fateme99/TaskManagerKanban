package com.example.taskmanagerkanban.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerkanban.R;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.repository.TaskRepository;
import com.example.taskmanagerkanban.utils.PictureUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;


public class DetailTaskFragment extends DialogFragment {
    private static final String ARGS_TASK = "task";
    private static final String ARGS_USER_ID = "userId";
    private static final int REQUEST_CODE_DATE_PICKER = 0;
    private static final int REQUEST_CODE_TIME_PICKER = 1;
    private static final int REQUEST_CODE_IMAGE_CAPTURE =2 ;
    private static final String FRAGMENT_TAG_DATE_PICKER ="datePicker" ;
    private static final String FRAGMENT_TAG_TIME_PICKER = "timePicker" ;
    private static final String TAG_CALLBACK = "callback";
    private static final String AUTHORITY = "com.example.taskmanagerkanban.fileProvide";
    private File mFile_photo;

    private Callbacks mCallbacks;
    private TextView mTextView_save,mTextView_edit,mTextView_delete;
    private RadioButton mRadioButton_todo,mRadioButton_doing,mRadioButton_done;
    private EditText mEditText_title,mEditText_desc;
    private Button mButton_date,mButton_clock;
    private ImageView mImageView_photo;
    private ImageButton mImageButton_camera;
    private Task mTask;
    private UUID mUserId;
    private TaskRepository mTaskRepository;
    public DetailTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Callbacks){
            mCallbacks= (Callbacks) context;
            Log.d(TAG_CALLBACK, "onAttach: is instance");
        }else {
            throw new ClassCastException(("must implement callbacks"));
        }


    }

    public static DetailTaskFragment newInstance(Task task, UUID userId) {
        
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK,task);
        args.putSerializable(ARGS_USER_ID,userId);
        DetailTaskFragment fragment = new DetailTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode!= Activity.RESULT_OK     ||  data==null)
            return;
        if (requestCode== REQUEST_CODE_DATE_PICKER){
            mTask.setDate((Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE));
            mButton_date.setText(getStringForDate());
        }
        else if (requestCode==REQUEST_CODE_TIME_PICKER){
            mTask.setDate((Date) data.getSerializableExtra(TimePickerDialogFragment.EXTRA_TIME_PICKER_DATE));
            mButton_clock.setText(getStringForClock());
        }
        else if (requestCode==REQUEST_CODE_IMAGE_CAPTURE){
            getActivity().revokeUriPermission(generateUriForFile(),
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask= (Task) getArguments().getSerializable(ARGS_TASK);
        mUserId= (UUID) getArguments().getSerializable(ARGS_USER_ID);
        mTaskRepository=TaskRepository.getInstance(getActivity());
        mFile_photo=mTaskRepository.getPhotoFile(mTask);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.fragment_dialog_detail,null);
        findViews(view);
        setValues();
        setListeners();
        updatePhotoView();
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("Detail page");
        AlertDialog dialog=builder.create();
        return dialog;

    }
    private void findViews(View view){
        mEditText_title=view.findViewById(R.id.title_dialog);
        mEditText_desc=view.findViewById(R.id.desc_dialog);
        mButton_date=view.findViewById(R.id.date_dialog);
        mRadioButton_todo=view.findViewById(R.id.todo_add_radioBtn);
        mRadioButton_doing=view.findViewById(R.id.doing_add_radioBtn);
        mRadioButton_done=view.findViewById(R.id.done_add_radioBtn);
        mTextView_save=view.findViewById(R.id.save_btn);
        mTextView_edit=view.findViewById(R.id.edit_btn);
        mTextView_delete=view.findViewById(R.id.delete_btn);
        mButton_clock=view.findViewById(R.id.clock_dialog);
        mImageView_photo=view.findViewById(R.id.photo_imageView);
        mImageButton_camera=view.findViewById(R.id.camera_imageButton);
    }
    private void setValues(){

        mEditText_title.setText(mTask.getTitle());
        mEditText_desc.setText(mTask.getDescription());

        mButton_date.setText(getStringForDate());
        mButton_clock.setText(getStringForClock());
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
        // TODO: 11/6/2020 set pic

    }
    private void setListeners(){
        mButton_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment=DatePickerFragment.newInstance(mTask.getDate());
                fragment.setTargetFragment(DetailTaskFragment.this, REQUEST_CODE_DATE_PICKER);
                fragment.show(getActivity().getSupportFragmentManager(), FRAGMENT_TAG_DATE_PICKER);
            }
        });
        mButton_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialogFragment fragment=TimePickerDialogFragment.newInstance(mTask.getDate());
                fragment.setTargetFragment(DetailTaskFragment.this, REQUEST_CODE_TIME_PICKER);
                fragment.show(getActivity().getSupportFragmentManager(),FRAGMENT_TAG_TIME_PICKER);
            }
        });
        mTextView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogView();
                mTaskRepository.insert(new Task(
                        mTask.getTitle(),mTask.getDescription(),mTask.getDate(),mTask.getTaskState(),mUserId));
                Toast.makeText(getActivity(), "insert new task successfully.", Toast.LENGTH_SHORT)
                        .show();
                getDialog().dismiss();
                mCallbacks.updateTaskListAdapter();
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
        mTextView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTaskRepository.deleteTask(mTask.getId());
                Toast.makeText(getActivity(), "deleted successfully", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
                mCallbacks.updateTaskListAdapter();

            }
        });
        mImageButton_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    if (mFile_photo !=null &&
                            takePictureIntent.resolveActivity(getActivity().getPackageManager()) !=null){
                        Uri photoUri= generateUriForFile();
                        List<ResolveInfo>activities=getActivity().getPackageManager()
                                .queryIntentActivities(
                                        takePictureIntent,
                                        PackageManager.MATCH_DEFAULT_ONLY);
                        for (ResolveInfo activity:activities) {
                            getActivity().grantUriPermission(
                                    activity.activityInfo.packageName,
                                    photoUri,
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            );
                        }
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                        startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
                    }


                } catch (ActivityNotFoundException e) {
                    Log.e(TAG, e.getMessage(),e );
                }
                /*Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);*/
            }
        });
    }

    private Uri generateUriForFile() {
        return FileProvider.getUriForFile(getContext(),AUTHORITY,mFile_photo);
    }

    private void getDialogView(){
        mTask.setTitle(mEditText_title.getText().toString());
        mTask.setDescription(mEditText_desc.getText().toString());
        getTaskState();

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
    private String getStringForDate() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd , yyyy");
        return simpleDateFormat.format(mTask.getDate());
    }
    private String getStringForClock(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh : mm a");
        return simpleDateFormat.format(mTask.getDate());
    }
    private void updatePhotoView(){
        if (mFile_photo==null || !mFile_photo.exists())
            return;
        Bitmap bitmap= PictureUtils.getScaledBitmap(mFile_photo.getAbsolutePath(),getActivity());
        mImageView_photo.setImageBitmap(bitmap);
    }

    public interface Callbacks{
        void updateTaskListAdapter();
    }
}