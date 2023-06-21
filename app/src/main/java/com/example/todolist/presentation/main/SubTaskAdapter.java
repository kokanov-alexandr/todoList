package com.example.todolist.presentation.main;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.models.SubTask;
import com.example.todolist.presentation.details.SubTaskDetailsActivity;

import java.util.List;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.TaskViewHolder> {
    private SortedList<SubTask> sortedList;

    public SubTaskAdapter() {
        sortedList = new SortedList<>(SubTask.class, new SortedList.Callback<SubTask>() {
            @Override
            public int compare(SubTask o1, SubTask o2) {
                if (!o2.isImportant && o1.isImportant){
                    return -1;
                }
                if (o2.isImportant && !o1.isImportant){
                    return 1;
                }
                if (!o2.isCompleted && o1.isCompleted) {
                    return 1;
                }
                if (o2.isCompleted && !o1.isCompleted) {
                    return -1;
                }
                return (int) (o2.creationTime - o1.creationTime);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(SubTask oldItem, SubTask newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(SubTask item1, SubTask item2) {
                return item1.id == item2.id;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subtask, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindDone(sortedList.get(position));
        holder.bindImportant(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<SubTask> subTasks) {
        sortedList.replaceAll(subTasks);
    }
    static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskText;
        CheckBox isCompleted;
        View delete;
        CheckBox isImportant;
        SubTask subTask;
        boolean middleUpdate;

        public TaskViewHolder(@NonNull final View itemView) {
            super(itemView);

            taskText = itemView.findViewById(R.id.subTaskText);
            isCompleted = itemView.findViewById(R.id.subTaskIsCompleted);
            delete = itemView.findViewById(R.id.subTaskdelete);
            isImportant = itemView.findViewById(R.id.subTaskIsImportant);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SubTaskDetailsActivity.start((Activity) itemView.getContext(), subTask, subTask.listId);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getSubTaskDao().delete(subTask);
                }
            });

            isImportant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean choose) {
                    if(!middleUpdate){
                        subTask.isImportant = choose;
                        App.getInstance().getSubTaskDao().update(subTask);
                    }
                }
            });

            isCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (!middleUpdate) {
                        subTask.isCompleted = checked;
                        App.getInstance().getSubTaskDao().update(subTask);
                    }
                    crossOutTask();
                }
            });
        }

        public void bindDone(SubTask subTask) {
            this.subTask = subTask;
            taskText.setText(subTask.text);
            crossOutTask();
            middleUpdate = true;
            isCompleted.setChecked(subTask.isCompleted);
            middleUpdate = false;
        }

        private void crossOutTask(){
            if (subTask.isCompleted) {
                taskText.setPaintFlags(taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                taskText.setPaintFlags(taskText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
        public void bindImportant(SubTask subTask) {
            this.subTask = subTask;
            middleUpdate = true;
            isImportant.setChecked(subTask.isImportant);
            middleUpdate = false;
        }
    }
}
