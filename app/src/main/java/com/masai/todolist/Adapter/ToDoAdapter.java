package com.masai.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.masai.todolist.AddNewTask;
import com.masai.todolist.R;
import com.masai.todolist.Tasks;
import com.masai.todolist.model.ToDoModel;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoModel> todoList;
    private Tasks activity;
    private FirebaseFirestore firestore;

    public ToDoAdapter(List<ToDoModel> todoList, Tasks activity) {
        this.todoList = todoList;
        this.activity = activity;

    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.task_itemlayout,parent,false);
        firestore = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    public void deleteTask(int position){
        ToDoModel toDoModel =todoList.get(position);
        firestore.collection("task").document(toDoModel.TaskId).delete();
        todoList.remove(position);
        notifyItemRemoved(position);
    }
    public Context getContext(){
        return activity;

    }

    public void editingTask(int position){
        ToDoModel toDoModel =todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("task",toDoModel.getTask());
        bundle.putString("due",toDoModel.getDue());
        bundle.putString("id",toDoModel.TaskId);

        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(),addNewTask.getTag());
    }

    @Override
    public void onBindViewHolder(@NonNull  ToDoAdapter.MyViewHolder holder, int position) {
        ToDoModel toDoModel = todoList.get(position);
        holder.mCheckBox.setText(toDoModel.getTask());
        holder.mDueDateTv.setText("Due On "+ toDoModel.getDue());
        holder.mCheckBox.setChecked(toBoolean(toDoModel.getStatus()));

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    firestore.collection("task").document(toDoModel.TaskId).update("status",1);
                }
                else{
                    firestore.collection("task").document(toDoModel.TaskId).update("status",0);
                }
            }
        });

    }
    private boolean toBoolean(int status){
        return  status !=0;
    }


    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCheckBox;
        private TextView mDueDateTv;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);

            mDueDateTv = itemView.findViewById(R.id.dateTask);
            mCheckBox = itemView.findViewById(R.id.TaskTodoCheckBox);
        }
    }
}
