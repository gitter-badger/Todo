package qageek.com.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;

@Table(name = "TodoItems")
public class TodoItem extends Model {

    @Column(name = "Body")
    public String body;

    public TodoItem(){
        super();
    }

    public TodoItem(String body){
        super();
        setBody(body);
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public static ArrayList<TodoItem> all() {
        return new Select()
                .all()
                .from(TodoItem.class)
                .execute();
    }
}