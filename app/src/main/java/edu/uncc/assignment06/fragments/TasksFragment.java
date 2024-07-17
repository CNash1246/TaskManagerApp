package edu.uncc.assignment06.fragments;

import static edu.uncc.assignment06.models.Data.categories;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentTasksBinding;
import edu.uncc.assignment06.models.Data;
import edu.uncc.assignment06.models.Task;

public class TasksFragment extends Fragment {

    FragmentTasksBinding binding;
    private ArrayList<Task> mTasks = new ArrayList<>();
    TaskAdapter adapter;
    TasksListener mListener;

    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);;
        mTasks = mListener.getAllTasks();

        adapter = new TaskAdapter(mTasks,mListener);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewSortIndicator.setText("Sort by Priority (ASC)");
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task t1, Task t2) {
                        return -1 * (t1.getPriority() - (t2.getPriority()));
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewSortIndicator.setText("Sort by Priority (DESC)");
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task t1, Task t2) {
                        return (t1.getPriority() - (t2.getPriority()));
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearTasks();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TasksListener) context;
    }

    class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
        TasksListener mListener;
        public TaskAdapter(ArrayList<Task> tasks, TasksListener mListener){
            mTasks = tasks;
            this.mListener = mListener;
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.task_list_item, parent, false);
            return new TaskViewHolder(view,mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.setupUI(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName, textViewCategory, textViewPriority;
            Task task;
            TasksListener listener;
            ImageView delete;

            public TaskViewHolder(@NonNull View itemView, TasksListener listener) {
                super(itemView);
                this.listener = listener;
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewCategory = itemView.findViewById(R.id.textViewCategory);
                textViewPriority = itemView.findViewById(R.id.textViewPriority);

                itemView.findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       mListener.deleteTask(task);
                       notifyDataSetChanged();
                    }
                });


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.gotoTaskDetails(task);
                        notifyDataSetChanged();
                    }
                });

            }

            public void setupUI(Task task) {
                textViewName.setText(task.getName());
                textViewPriority.setText(task.getPriorityStr());
                textViewCategory.setText(task.getCategory());
                this.task = task;
            }
        }
    }

    public interface TasksListener{
        ArrayList<Task> getAllTasks();
        void gotoAddTask();
        void gotoTaskDetails(Task task);
        void clearTasks();
        void deleteTask(Task task);
    }
}