package com.masai.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.masai.todolist.Adapter.ToDoAdapter;
import com.masai.todolist.model.ToDoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tasks extends AppCompatActivity implements onDialogCloseListener{
    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private FirebaseFirestore firestore;
    private ToDoAdapter toDoAdapter;
    private List<ToDoModel> mList;
    private Query query;
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        initViews();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Tasks.this));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

        mList = new ArrayList<>();
        toDoAdapter = new ToDoAdapter(mList,Tasks.this);
        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(new TouchHelper(toDoAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(toDoAdapter);
        showData();

    }

    private void showData() {
         query = firestore.collection("task").orderBy("time", Query.Direction.DESCENDING);
         listenerRegistration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                for(DocumentChange documentChange: value.getDocumentChanges()){
                    if(documentChange.getType()== DocumentChange.Type.ADDED){
                        String id = documentChange.getDocument().getId();
                        ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class).withId(id);

                        mList.add(toDoModel);
                        toDoAdapter.notifyDataSetChanged();
                    }
                }
                listenerRegistration.remove();


            }
        });

    }

    private void initViews() {
        recyclerView =findViewById(R.id.RecyclerViewTask);
        mFab =findViewById(R.id.TaskFloatingButton);
        firestore =FirebaseFirestore.getInstance();

    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList.clear();
        showData();
        toDoAdapter.notifyDataSetChanged();
    }
}