package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentAddTaskBinding;
import edu.uncc.assignment06.models.Task;

public class AddTaskFragment extends Fragment {

    AddTaskListener mListener;
    FragmentAddTaskBinding binding;

    String priorityStr = "N/A", category = "N/A", name;
    int priority;

    public AddTaskFragment() {
        // Required empty public constructor
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setPriorityStr(String priorityStr) {
        this.priorityStr = priorityStr;
    }

    public void setCategory(String category){
        this.category = category;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewPriority.setText(priorityStr);
        binding.textViewCategory.setText(category);

        binding.buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToCategory();
            }
        });

        binding.buttonSelectPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToPriority();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(priorityStr.equals("N/A")){
                    Toast.makeText(getActivity(), "Please select a priority", Toast.LENGTH_SHORT).show();
                } else if(category.equals("N/A")){
                    Toast.makeText(getActivity(), "Please select a category", Toast.LENGTH_SHORT).show();
                }else {
                    name = binding.editTextName.getText().toString();
                    Task task = new Task(name, category, priorityStr, priority);
                    mListener.sendTask(task);
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddTaskListener) context;
    }

    public interface AddTaskListener{
        void sendTask(Task task);
        void goToPriority();
        void goToCategory();
    }
}