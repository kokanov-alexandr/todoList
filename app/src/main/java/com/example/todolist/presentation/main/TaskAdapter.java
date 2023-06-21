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
import com.example.todolist.models.Task;
import com.example.todolist.presentation.details.TaskDetailsActivity;

import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ListTaskViewHolder> {

    private SortedList<Task> sortedList;

    public TaskAdapter() {

        sortedList = new SortedList<>(Task.class, new SortedList.Callback<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
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
            public boolean areContentsTheSame(Task oldItem, Task newItem) {
                return oldItem.equals(newItem);
            }
            @Override
            public boolean areItemsTheSame(Task item1, Task item2) {
                return item1.id == item2.id;
            }
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }
            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeInserted(position, count);
            }
            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }


    @NonNull
    @Override
    public ListTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListTaskViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListTaskViewHolder holder, int position) {
        holder.bindDone(sortedList.get(position));
        holder.bindImportant(sortedList.get(position));
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Task> listTask) {
        sortedList.replaceAll(listTask);
    }

    static class ListTaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        View delete;
        Task task;

        CheckBox isCompleted;
        CheckBox isImportant;
        boolean middleUpdate;

        public ListTaskViewHolder(@NonNull final View itemView) {
            super(itemView);

            taskText = itemView.findViewById(R.id.taskText);

            delete = itemView.findViewById(R.id.taskDelete);

            isCompleted = itemView.findViewById(R.id.taskIsCompleted);
            isImportant = itemView.findViewById(R.id.taskIsImportant);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskDetailsActivity.start((Activity) itemView.getContext(), task);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getTaskDao().delete(task);
                }
            });

            isImportant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean choose) {
                    if (!middleUpdate) {
                        task.isImportant = choose;
                        App.getInstance().getTaskDao().update(task);
                    }
                }
            });

            isCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (!middleUpdate) {
                        task.isCompleted = checked;
                        App.getInstance().getTaskDao().update(task);
                    }
                    crossOutTask();
                }
            });

        }
        private void crossOutTask(){
            if(task.isCompleted){
                taskText.setPaintFlags(taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                taskText.setPaintFlags(taskText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

            }
        }

        public void bind(Task listTask) {
            this.task = listTask;
            taskText.setText(listTask.text);
        }

        public void bindDone(Task task) {
            this.task = task;
            taskText.setText(task.text);
            crossOutTask();
            middleUpdate = true;
            isCompleted.setChecked(task.isCompleted);
            middleUpdate = false;
        }

        public void bindImportant(Task task) {
            this.task = task;
            middleUpdate = true;
            isImportant.setChecked(task.isImportant);
            middleUpdate = false;

        }
    }

}