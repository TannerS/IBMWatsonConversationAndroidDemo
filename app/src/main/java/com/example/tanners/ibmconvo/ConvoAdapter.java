package com.example.tanners.ibmconvo;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ConvoAdapter extends RecyclerView.Adapter<ConvoAdapter.ConvoViewHolder> {

    public static class DataWrapper {
        private boolean response;
        private String input;

        public DataWrapper() {
        }

        public DataWrapper(boolean response, String input) {
            this.response = response;
            this.input = input;
        }

        public boolean isResponse() {
            return response;
        }

        public void setResponse(boolean response) {
            this.response = response;
        }

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }
    }


    private Context mContext;
    private ArrayList<DataWrapper> responses;

    public ConvoAdapter(Context mContext) {
        this.mContext = mContext;
        responses = new  ArrayList<DataWrapper>();
    }

    public void updateAdapter(DataWrapper response) {
        int start = responses.size();
        responses.add(response);
        notifyItemRangeInserted(start, responses.size());
    }

    @Override
    public int getItemCount() {
        return responses == null ? 0 : responses.size();
    }

    @Override
    public void onBindViewHolder(ConvoViewHolder holder, int position) {
        String mItem = responses.get(position).getInput();

        if(!responses.get(position).isResponse())
        {
            holder.response.setGravity(Gravity.RIGHT);
            holder.response.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
        }

        else {
            holder.response.setGravity(Gravity.LEFT);
            holder.response.setGravity(View.TEXT_ALIGNMENT_VIEW_START);

        }

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