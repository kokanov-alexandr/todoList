package com.example.todolist.screens.main;

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
import com.example.todolist.screens.details.TaskDetailsActivity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder> {

    private SortedList<Task> sortedList;

    public Adapter() {
        InitSortedList();
    }
    private void InitSortedList() {
        this.sortedList = new SortedList<Task>(Task.class, new SortedList.Callback<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                System.out.println("!!!");
                if (o2.isDone || o1.isDone) {
                    System.out.println("done");
                }
                if (!o2.isDone && o1.isDone) {
                        return 1;
                }
                if (o2.isDone && !o1.isDone) {
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
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindTask(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Task> tasks) {
        sortedList.replaceAll(tasks);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        CheckBox  isCompleted;
        View delete;

        Task task;
        boolean silentUpdate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.task_text);
            isCompleted = itemView.findViewById(R.id.isCompleted);
            delete = itemView.findViewById(R.id.delete);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskDetailsActivity.start((Activity)itemView.getContext(), task);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().getTaskDao().delete(task);
                }
            });

            isCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                    if (!silentUpdate) {
                        task.isDone = checked;
                        App.getInstance().getTaskDao().update(task);
                    }
                    updateStrokeOut();
                }
            });
        }

        public void bindTask(Task task) {
            this.task = task;
            taskText.setText(task.text);
            updateStrokeOut();
            silentUpdate = true;
            isCompleted.setChecked(task.isDone);
            silentUpdate = false;
        }

        private void updateStrokeOut() {
            if (task.isDone) {
                taskText.setPaintFlags(taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                taskText.setPaintFlags(taskText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

    }
}
