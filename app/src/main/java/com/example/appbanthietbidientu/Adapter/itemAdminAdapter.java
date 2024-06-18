package com.example.appbanthietbidientu.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Item_admin;

import java.util.List;

public class itemAdminAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Item_admin> ListItemAdmin;

    public itemAdminAdapter(Context context, int layout, List<Item_admin> listItemAdmin) {
        this.context = context;
        this.layout = layout;
        ListItemAdmin = listItemAdmin;
    }

    @Override
    public int getCount() {
        return ListItemAdmin.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        ImageView imgview;
        TextView textView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layout, parent, false);

            viewHolder.imgview = convertView.findViewById(R.id.anhitem); // Thay R.id.imganh bằng ID của ImageView trong layout của bạn
            viewHolder.textView = convertView.findViewById(R.id.contentItem); // Thay R.id.textview bằng ID của TextView trong layout của bạn

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Item_admin item = ListItemAdmin.get(position);

        viewHolder.imgview.setImageResource(item.getIcon());
        viewHolder.textView.setText(item.getText());

        return convertView;
    }

}
