package com.example.calmo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassModelViewAdapter extends RecyclerView.Adapter<ClassModelViewAdapter.MyViewHolder> {
    private final RecycleClassViewInterface recycleClassViewInterface;
    Context context;
    ArrayList<ClassModel> classModels;

    public ClassModelViewAdapter(Context context, ArrayList<ClassModel>classModels,RecycleClassViewInterface recycleClassViewInterface){
        this.context = context;
        this.classModels = classModels;
        this.recycleClassViewInterface = recycleClassViewInterface;
    }

    @NonNull
    @Override
    public ClassModelViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.class_list_item,parent,false);
        return new ClassModelViewAdapter.MyViewHolder(view, recycleClassViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassModelViewAdapter.MyViewHolder holder, int position) {

        holder.className.setText(classModels.get(position).getClassName());

    }

    @Override
    public int getItemCount() {
        return classModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView className;
        public MyViewHolder(@NonNull View itemView, RecycleClassViewInterface recycleClassViewInterface) {
            super(itemView);
            className = itemView.findViewById(R.id.classListName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recycleClassViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recycleClassViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
