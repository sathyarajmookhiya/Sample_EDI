package com.muthusoft.edi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muthusoft.edi.R;
import com.muthusoft.edi.model.Users;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Users> list;
    private Activity activity;

    public RecyclerAdapter(Activity activity, List<Users> list) {
        this.list = list;
        this.activity = activity;
    }

    public Users getItem(int i) {
        return list.get(i);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.card_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {

        viewHolder.cardtitle.setText(list.get(position).getEmail());
        viewHolder.cardimage.setText(list.get(position).getMobile());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * View holder to display each RecylerView item
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardimage;
        TextView cardtitle;


        public ViewHolder(View itemView) {
            super(itemView);

            cardtitle = (TextView) itemView.findViewById(R.id.cardtitle);
            cardimage = (TextView) itemView.findViewById(R.id.cardimage);
        }
    }
}