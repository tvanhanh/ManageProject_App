package com.example.do_an_cs3.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Model.Deparments;
import com.example.do_an_cs3.R;

import java.util.List;

public class DeparmentAdapter extends RecyclerView.Adapter<DeparmentAdapter.DeparmentViewHolder> {

    private List<Deparments> mDeparment;

    public DeparmentAdapter(List<Deparments> deparmentsList) {
        this.mDeparment = deparmentsList;
    }

    @NonNull
    @Override
    public DeparmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department, parent, false);
        return new DeparmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeparmentViewHolder holder, int position) {
        Deparments deparment = mDeparment.get(position);
        if (deparment != null) {
            holder.tvdeparment_name.setText(deparment.getName());
            holder.tvcomplete_job.setText(deparment.getCompleteJob());
            holder.tvnumber.setText(deparment.getNumber());
        }
    }

    @Override
    public int getItemCount() {
        return mDeparment != null ? mDeparment.size() : 0;
    }

    public class DeparmentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvdeparment_name;
        private TextView tvnumber;
        private TextView tvcomplete_job;

        public DeparmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvdeparment_name = itemView.findViewById(R.id.deparment_name);
            tvcomplete_job = itemView.findViewById(R.id.job_complete);
            tvnumber = itemView.findViewById(R.id.number);
        }
    }
}

