package com.example.rparuthi.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //ArrayList to store the items
    private ArrayList<String> items;
    //ArrayAdapter to display the contents in ListView
    private ArrayAdapter<String> itemsAdapter;

    private ListView lvItems;

    private EditText etNewItem;

    private final int  REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        //Get the instance of lvItems using id
        lvItems = (ListView)findViewById(R.id.lvItems);
        //Initiate ArrayList
        items = new ArrayList<>();
        //Set itemsAdapter
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        //Set lvItem adapter to itemsAdapter
        lvItems.setAdapter(itemsAdapter);

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
                    public boolean onItemLongClick(AdapterView<?> adapter,View item, int index, long id)
                    {
                        //Remove the item.
                        items.remove(index);
                        //Notifies to itemsAdapter which will refresh the listView
                        writeItems();
                        itemsAdapter.notifyDataSetChanged();
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
    private void onEditItem(View item, int index, long id)
    {
        //Create an intent for opening a new activity
        Intent intent = new Intent(this,EditItemActivity.class);

        //Get the text value based on the click index
        String editText = items.get(index).toString();

        //Add data to the bundle for access in the edit item activity
        intent.putExtra("text",editText);
        intent.putExtra("index",index);

        //Open the second activity
        startActivityForResult(intent,REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Check for resultCode and requestCode
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            //Retrieve the value from result extras
            items.set(data.getIntExtra("index",0),data.getStringExtra("editToDo"));

            //Notifies to itemsAdapter which will refresh the listView
            this.itemsAdapter.notifyDataSetChanged();

            //Write items to file.
            writeItems();
        }

    }

    //Function called when AddItem button is clicked
    public void onAddItem(View v)
    {
        //Get the instance of etNewItem by using the id
        etNewItem = (EditText)findViewById(R.id.etNewItem);

        //Get the text value from etNewItem
        String itemText = etNewItem.getText().toString();

        //Add the itemText value to itemAdapter
        itemsAdapter.add(itemText);

        writeItems();
        //Clear the value in itemText
        etNewItem.setText("");
    }

    //TODO::Create a factory so that the Datastore can be easily changed.
    //Read Items from text file
    private void readItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");

        try
        {
            items = new ArrayList<String>(FileUtils.readLines(todoFile,"UTF-8"));
        }
        catch (IOException e)
        {
            items = new ArrayList<String>();

        }

    }

    //Write Items to text file
    private void writeItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");

        try
        {
            FileUtils.writeLines(todoFile,items);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
