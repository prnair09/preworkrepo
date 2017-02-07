package DB.Models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import DB.ToDoDatabase;

/**
 * Model for ToDoITem
 */
@Table(database = ToDoDatabase.class)
public class ToDoItem extends BaseModel implements Serializable{
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String itemText;

    @Column
    Date dueDate;

    public void setItemText(String itemText){
        this.itemText = itemText;
    }

    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
    }

    public String getItemText(){return itemText;}

    public Date getDueDate() {return dueDate;}


    public List<ToDoItem> getItems()
    {
        return SQLite.select().from(ToDoItem.class).queryList();
    }
}
