package com.android.citizens.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.citizens.R;
import com.android.citizens.model.Citizen;
import com.android.citizens.ui.CitizenActivity;

import java.util.List;

public class CitizenAdapter extends RecyclerView.Adapter<CitizenAdapter.ViewHolder> {

    private Context mContext;
    private List<Citizen> mCitizenList;

    public CitizenAdapter(Context context, List<Citizen> citizenList) {
        mContext = context;
        mCitizenList = citizenList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.citizen_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Citizen citizen = mCitizenList.get(position);
        holder.txtViewName.setText(String.format("%s %s", citizen.getLastName(), citizen.getFirstName()));
        holder.txtViewGender.setText(citizen.getGender());
        holder.txtViewAge.setText(String.valueOf(citizen.getAge()));

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, CitizenActivity.class);
            intent.putExtra("citizen", citizen);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mCitizenList == null ? 0 : mCitizenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtViewName;
        private TextView txtViewGender;
        private TextView txtViewAge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtViewName = itemView.findViewById(R.id.txtViewName);
            txtViewGender = itemView.findViewById(R.id.txtViewGender);
            txtViewAge = itemView.findViewById(R.id.txtViewAge);
        }
    }
}
