package com.example.tanners.ibmconvo;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ConvoAdapter extends RecyclerView.Adapter<ConvoAdapter.ConvoViewHolder> {
    private Context mContext;
    private ArrayList<String> responses;

    public ConvoAdapter(Context mContext) {
        this.mContext = mContext;
        responses = new  ArrayList<String>();
    }

    public void updateAdapter(String response) {
//        int start = responses.size();
        responses.add(response);
//        notifyItemRangeInserted(start - 1, responses.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return responses == null ? 0 : responses.size();
    }

    @Override
    public void onBindViewHolder(ConvoViewHolder holder, int position) {
        String mItem = responses.get(position);
        holder.response.setText(mItem);

    }


    @Override
    public ConvoAdapter.ConvoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_layout, parent, false);
            return new ConvoViewHolder(view);
    }

    public class ConvoViewHolder extends RecyclerView.ViewHolder {
        public TextView response;

        public ConvoViewHolder(View view) {
            super(view);
            response = view.findViewById(R.id.item);
        }
    }
}