package qageek.com.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TodoItemDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todoListDatabase";
    private static final String TABLE_TODO = "todo_items";
    private static final String KEY_ID = "id";
    private static final String KEY_BODY = "body";

    public TodoItemDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Construct a table for todo items
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BODY + " TEXT)";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {
            // Wipe older tables if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            // Create tables again
            onCreate(db);
        }
    }

    public void addTodoItem(TodoItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BODY, item.getBody());
        db.insert(TABLE_TODO, null, values);
        db.close();
    }

    public TodoItem getTodoItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TODO,
                new String[] { KEY_ID, KEY_BODY },
                KEY_ID + "= ?", new String[]{ String.valueOf(id) },
                null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        TodoItem item = new TodoItem(cursor.getString(1));
        item.setId(cursor.getColumnIndexOrThrow(KEY_ID));
        return item;
    }

    public List<TodoItem> getAllTodoItems(){
        List<TodoItem> todoItems = new ArrayList<TodoItem>();

        String selectQuery = "Select * From " + TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
               TodoItem item = new TodoItem(cursor.getString(1));
                item.setId(cursor.getInt(0));
                todoItems.add(item);
            } while (cursor.moveToNext());
        }

        return todoItems;
    }

    public int updateTodoItem(TodoItem item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BODY, item.getBody());

        int result = db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] {String.valueOf(item.getId())});

        db.close();
        return result;
    }

    public void deleteTodoItem(TodoItem item){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[]{String.valueOf(item.getId())});

        db.close();
    }
}
