package qageek.com.simpletodo;

public class TodoItem {
    private int id;
    private String body;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
