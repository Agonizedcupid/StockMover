package com.aariyan.stockmover.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.stockmover.Model.QueueModel;
import com.aariyan.stockmover.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class NotUploadedAdapter extends RecyclerView.Adapter<NotUploadedAdapter.ViewHolder> {

    private Context context;
    private List<QueueModel> list;

    public NotUploadedAdapter(Context context, List<QueueModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_not_uploaded, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        QueueModel model = list.get(index);
        try {
            holder.itemLocation.setText(model.getLocation());
//            else if (model.getMoveIn().equals("0")){
//                holder.itemType.setText("Move From");
//            }
            if (model.getMoveFrom().equals("1")) {
                holder.itemType.setText("Move From");
            } else if (model.getMoveIn().equals("1")) {
                holder.itemType.setText("Move In");
            }
            else if (model.getMoveIn().equals("1") && model.getMoveFrom().equals("1")) {
                holder.itemType.setText("Both");
            }


            holder.itemName.setText(model.getBarcode());
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName, itemType, itemLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.barCode);
            itemType = itemView.findViewById(R.id.type);
            itemLocation = itemView.findViewById(R.id.location);
        }
    }
}
