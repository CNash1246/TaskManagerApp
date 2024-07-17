package edu.uncc.assignment06;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

import edu.uncc.assignment06.fragments.AddTaskFragment;
import edu.uncc.assignment06.fragments.SelectCategoryFragment;
import edu.uncc.assignment06.fragments.SelectPriorityFragment;
import edu.uncc.assignment06.fragments.TaskDetailsFragment;
import edu.uncc.assignment06.fragments.TasksFragment;
import edu.uncc.assignment06.models.Data;
import edu.uncc.assignment06.models.Task;

public class MainActivity extends AppCompatActivity implements TasksFragment.TasksListener, TaskDetailsFragment.TaskDetailsListener
            , AddTaskFragment.AddTaskListener, SelectPriorityFragment.SelectPriorityListener, SelectCategoryFragment.SelectCategoryListener {

    private ArrayList<Task> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTasks.addAll(Data.sampleTestTasks); //adding for testing

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new TasksFragment(), "task_fragment")
                .commit();
    }


    @Override
    public ArrayList<Task> getAllTasks() {
        return mTasks;
    }

    @Override
    public void gotoAddTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddTaskFragment(), "add_task_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoTaskDetails(Task task) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, TaskDetailsFragment.newInstance(task))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clearTasks() {
        mTasks.clear();
    }

    @Override
    public void deleteTask(Task task) {
        mTasks.remove(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void backDetails() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendTask(Task task) {
        TasksFragment fragment = (TasksFragment) getSupportFragmentManager().findFragmentByTag("task_fragment");
        if(fragment != null){
            mTasks.add(task);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goToPriority() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.rootView, new SelectPriorityFragment())
                .commit();
    }

    @Override
    public void goToCategory() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.rootView,new SelectCategoryFragment())
                .commit();
    }

    @Override
    public void sendPriority(String priority) {
        AddTaskFragment fragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("add_task_fragment");
        if(fragment != null){
            fragment.setPriorityStr(priority);
            if(priority.equals("Very High")){
                fragment.setPriority(1);
            } else if(priority.equals("High")){
                fragment.setPriority(2);
            } else if(priority.equals("Medium")){
                fragment.setPriority(3);
            } else if(priority.equals("Low")){
                fragment.setPriority(4);
            } else if(priority.equals("Very Low")){
                fragment.setPriority(5);
            }
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelPriority() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendCategory(String category) {
        AddTaskFragment fragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("add_task_fragment");
        if(fragment != null){
            fragment.setCategory(category);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelCategory() {
        getSupportFragmentManager().popBackStack();
    }
}