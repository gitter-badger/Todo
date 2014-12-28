package qageek.com.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<TodoItem> {
    public ItemsAdapter(Context context, ArrayList<TodoItem> items) {
        super(context, android.R.layout.simple_list_item_1, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TodoItem item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }

        TextView todoBody = (TextView) convertView.findViewById(R.id.todoBody);

        todoBody.setText(item.getBody());

        return convertView;
    }
}