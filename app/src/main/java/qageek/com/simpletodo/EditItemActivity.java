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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        position = getIntent().getIntExtra("position", 0);
        String text = getIntent().getStringExtra("text");
        EditText et = (EditText) findViewById(R.id.etItemName);
        et.requestFocus();
        et.setText(text);
        et.setSelection(text.length());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        Intent data = new Intent();
        data.putExtra("name", etNewItem.getText().toString());
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
