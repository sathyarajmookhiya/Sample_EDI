package com.muthusoft.edi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.muthusoft.edi.R;
import com.muthusoft.edi.model.Users;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 26-10-2016.
 */
public class UserAdapter extends BaseAdapter {

    private List<Users> mData = new ArrayList<Users>();
    private LayoutInflater mInflater;

    public UserAdapter(Context context, List<Users> data) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Users getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.row_profile, null);
            holder.textView = (TextView) convertView.findViewById(R.id.username);
            holder.textView1 = (TextView) convertView.findViewById(R.id.useremil);
            holder.textView2 = (TextView) convertView.findViewById(R.id.usermobile);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(mData.get(position).getName());
        holder.textView1.setText(mData.get(position).getEmail());
        holder.textView2.setText(mData.get(position).getMobile());

        return convertView;
    }

    private static class ViewHolder {
        public TextView textView;
        public TextView textView1;
        public TextView textView2;

    }

}
