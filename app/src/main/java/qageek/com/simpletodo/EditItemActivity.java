package qageek.com.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {
    private int position;
    private Long itemID;
    private TodoItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        position = getIntent().getIntExtra("position", 0);

        itemID =  Long.parseLong(getIntent().getStringExtra("itemID"));
        item = TodoItem.load(TodoItem.class, itemID);
        String itemBody = item.getBody();

        EditText et = (EditText) findViewById(R.id.etItemName);
        et.requestFocus();
        et.setText(itemBody);
        et.setSelection(itemBody.length());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds todoItems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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

    public void onSave(View view){
        EditText etNewItem = (EditText) findViewById(R.id.etItemName);
        item.setBody(etNewItem.getText().toString());
        item.save();
                
        Intent data = new Intent();
        data.putExtra("position", position);
        data.putExtra("itemID", item.getId().toString());

        setResult(RESULT_OK, data);
        finish();
    }
}
