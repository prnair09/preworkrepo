package com.example.rparuthi.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.rparuthi.todoapp.R.id.etEditItem;
import static com.example.rparuthi.todoapp.R.id.textView;

public class EditItemActivity extends AppCompatActivity{

    //EditText instance
    private EditText etEditItem;

    private int editIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //Get the instance of EditText
        etEditItem = (EditText) findViewById(R.id.etEditItem);

        //Get the value of click index from intent
        editIndex = getIntent().getIntExtra("index",0);

        //Get the text value from intent
        String editText = getIntent().getStringExtra("text");

        //Set the text in EditText Field
        etEditItem.setText(editText);

        //Set the cursor position to end of text
        etEditItem.setSelection(editText.length());

    }

    //Function called on Save
    public void onSave(View v)
    {
        //Create intent data
        Intent data = new Intent();

        //Pass required data back as a result
        data.putExtra("editToDo",etEditItem.getText().toString());
        data.putExtra("index",editIndex);

        //Return the data with OK result

        setResult(RESULT_OK,data); //set result code and bundle data for response

        //close the activity and pass data to parent
        finish();

    }
}
