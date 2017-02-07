package com.example.rparuthi.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DB.Models.ToDoItem;

public class MainActivity extends AppCompatActivity {


    private ToDoItemsAdapter toDoItemsAdapter;

    //List to store the items
    private List<ToDoItem> itemsNew;

    private ListView lvItems;

    private EditText etNewItem;

    private final int  REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsNew = SQLite.select().from(ToDoItem.class).queryList();

        toDoItemsAdapter = new ToDoItemsAdapter(this,itemsNew);

        lvItems = (ListView)findViewById(R.id.lvItems);

        lvItems.setAdapter(toDoItemsAdapter);

        //Add Listeners to ListView
        SetUpListViewListener();


    }

    //Method for ListView listener
    private void SetUpListViewListener()
    {
        //LongClick
        lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener(){
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter,View view, int index, long id)
                {
                    ToDoItem item = itemsNew.get(index);
                    item.delete();
                    itemsNew.remove(index);

                    //Notifies to itemsAdapter which will refresh the listView
                    toDoItemsAdapter.notifyDataSetChanged();
                    return true;
                }
            }
        );

        //ItemClick
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                 @Override
                    public void onItemClick(AdapterView<?> adapter,View item, int index,long id)
                 {
                    onEditItem(item,index,id);
                 }
            }
        );

    }

    //Function called on ItemClick in ListView
    private void onEditItem(View view, int index, long id)
    {
        //Create an intent for opening a new activity
        Intent intent = new Intent(this,EditItemActivity.class);

        //Get the text value based on the click index
        ToDoItem item = itemsNew.get(index);

        //Add data to the bundle for access in the edit item activity
        intent.putExtra("todoItem",item);
        intent.putExtra("index",index);

        //Open the second activity
        startActivityForResult(intent,REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Check for resultCode and requestCode
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            ToDoItem updatedItem = (ToDoItem) data.getSerializableExtra("editedToDoItem");
            //Retrieve the value from result extras
            itemsNew.set (data.getIntExtra("index",0), updatedItem);

            this.toDoItemsAdapter.notifyDataSetChanged();

            updatedItem.save();
        }

    }

    //Function called when AddItem button is clicked
    public void onAddItem(View v)
    {
        //Get the instance of etNewItem by using the id
        etNewItem = (EditText)findViewById(R.id.etNewItem);

        //Get the text value from etNewItem
        String itemText = etNewItem.getText().toString();

        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setItemText(itemText);
        toDoItem.save();

        itemsNew.add(toDoItem);
        toDoItemsAdapter.notifyDataSetChanged();

        //Clear the value in itemText
        etNewItem.setText("");
    }

}
