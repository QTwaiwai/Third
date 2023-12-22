package com.example.third;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.InnerHolder> {
    private ArrayList<Data> data;
    public RvAdapter(ArrayList<Data> data){
        this.data=data;
    }
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.tvData.setText(data.get(position).getData());
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class InnerHolder extends RecyclerView.ViewHolder {
        TextView tvData;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.rv_data);
        }
    }
}

