package com.aariyan.stockmover.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.stockmover.Model.QueueModel;
import com.aariyan.stockmover.R;

import java.util.List;
import java.util.Queue;

public class NotMovedInAdapter extends RecyclerView.Adapter<NotMovedInAdapter.ViewHolder> {

    private List<QueueModel> list;
    private Context context;
    public NotMovedInAdapter(Context context, List<QueueModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_not_moved_in,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        QueueModel model = list.get(index);
        try {

            holder.itemLocation.setText(model.getLocation());
            holder.itemName.setText(model.getBarcode());

        }catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.barCode);
            itemLocation = itemView.findViewById(R.id.location);
        }
    }
}
