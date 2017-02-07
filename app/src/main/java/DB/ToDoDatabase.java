package DB;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 DB Flow Database settings
 */

@Database(name = ToDoDatabase.NAME, version = ToDoDatabase.VERSION)
public class ToDoDatabase {

    public static final String NAME = "ToDoDatabase";

    public static final int VERSION = 1;
}
