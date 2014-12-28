package qageek.com.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ArrayList<TodoItem> todoItems;
    ItemsAdapter itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoItems = TodoItem.all();
        itemsAdapter = new ItemsAdapter(this, todoItems);

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds todoItems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){

            Long todoItemID = Long.parseLong(data.getExtras().getString("itemID"));
            TodoItem todoItem = TodoItem.load(TodoItem.class, todoItemID);

            itemsAdapter.notifyDataSetChanged();
            Toast.makeText(this, todoItem.getBody(), Toast.LENGTH_SHORT).show();
        }

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                        todoItems.get(position).delete();
                        todoItems.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        launchEditItemView(todoItems.get(position));
                    }
                }
        );
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        TodoItem todoItem = new TodoItem(etNewItem.getText().toString());
        todoItem.save();
        itemsAdapter.add(todoItem);
        etNewItem.setText("");
    }

    public void launchEditItemView(TodoItem item){
        Intent editIntent = new Intent(this, EditItemActivity.class);
        editIntent.putExtra("itemID", item.getId().toString());
        startActivityForResult(editIntent, REQUEST_CODE);
    }
}
