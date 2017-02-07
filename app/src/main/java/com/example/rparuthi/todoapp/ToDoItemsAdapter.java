package com.example.rparuthi.todoapp;

import android.content.ClipData;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rparuthi.todoapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import DB.Models.ToDoItem;

import static DB.Models.ToDoItem_Table.dueDate;
import static android.R.attr.canPerformGestures;
import static android.R.attr.data;

/**
 Custom Adapter for ToDoItems
 */

public class ToDoItemsAdapter extends BaseAdapter {

    private Context context;
    private List<ToDoItem> items;


    public ToDoItemsAdapter(Context ctx, List<ToDoItem>  items){
        this.context = ctx;
        this.items = items;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public ToDoItem getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_view_row_items, parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        ToDoItem currentItem = getItem(position);

        viewHolder.todoItem.setText(currentItem.getItemText());

       /* String dueDate = "";
        if(currentItem.getDueDate() != null) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            dueDate = dateFormat.format(currentItem.getDueDate());
        }*/

        // returns the view for the current row
        return convertView;
    }

    private class ViewHolder{
        TextView todoItem;

        public ViewHolder(View view){
            todoItem = (TextView)view.findViewById(R.id.text_view_item);
        }
    }

}

