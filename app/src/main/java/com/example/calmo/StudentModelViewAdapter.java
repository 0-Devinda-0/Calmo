package com.example.calmo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentModelViewAdapter extends RecyclerView.Adapter<StudentModelViewAdapter.MyViewHolder2> {
    private final RecycleStudentViewInterface recycleStudentViewInterface;
    Context context;
    ArrayList<StudentModel> studentModels;
    AlertDialog.Builder confirmAlert;

    public StudentModelViewAdapter(Context context, ArrayList<StudentModel>studentModels,RecycleStudentViewInterface recycleStudentViewInterface){
        this.context = context;
        this.studentModels = studentModels;
        this.recycleStudentViewInterface = recycleStudentViewInterface;
    }

    @NonNull
    @Override
    public StudentModelViewAdapter.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.student_list_item,parent,false);
        return new StudentModelViewAdapter.MyViewHolder2(view, recycleStudentViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull StudentModelViewAdapter.MyViewHolder2 holder, int position) {
        String studentName = studentModels.get(position).getFirstName() +" "+  studentModels.get(position).getLastName();
        holder.studentName.setText(studentName);
        int studentId = studentModels.get(position).id;
        StudentModel model = studentModels.get(position);
        holder.studentId.setText("Student ID:  " + studentId);
        if(model.type == 1){
            holder.icon.setVisibility(View.INVISIBLE);
            holder.icon_bg.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {

        return studentModels.size();
    }


    public int getId(int position){

        return studentModels.get(position).id;
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView studentName,studentId;
        String firsName;
        public ImageView icon, icon_bg;
        public MyViewHolder2(@NonNull View itemView, RecycleStudentViewInterface recycleStudentViewInterface) {
            super(itemView);


            studentName = itemView.findViewById(R.id.studentListName);
            studentId = itemView.findViewById(R.id.studentIdView);
            icon = itemView.findViewById(R.id.examPic2);
            icon_bg = itemView.findViewById(R.id.examPic24);
//            icon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                        if(recycleStudentViewInterface != null){
//                            int pos = getAdapterPosition();
//
//                            if(pos != RecyclerView.NO_POSITION){
//                                recycleStudentViewInterface.onItemClick(pos);
//                            }
//
//                                view.setVisibility(View.INVISIBLE);
//
//                    Log.i("CLICK_LISTENER","youClicked "+ pos);
//                }
//            };
//            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recycleStudentViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recycleStudentViewInterface.onItemClick(pos);
                        }

                        icon.setVisibility(View.INVISIBLE);
                        icon_bg.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }
}
