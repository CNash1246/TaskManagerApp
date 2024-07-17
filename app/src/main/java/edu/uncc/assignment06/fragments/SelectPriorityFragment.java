package edu.uncc.assignment06.fragments;

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
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectPriorityBinding;
import edu.uncc.assignment06.models.Data;

public class SelectPriorityFragment extends Fragment {

    FragmentSelectPriorityBinding binding;
    SelectPriorityListener mListener;
    List<String> prioritys = Arrays.asList(Data.priorities);
    PriorityAdapter adapter;

    public SelectPriorityFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectPriorityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new PriorityAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelPriority();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectPriorityListener) context;
    }

    class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder>{
        @NonNull
        @Override
        public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.selection_list_item, parent, false);
            return new PriorityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {
            String priority = prioritys.get(position);
            holder.setupUI(priority);
        }

        @Override
        public int getItemCount() {
            return prioritys.size();
        }

        class PriorityViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            String mPriority;
            public PriorityViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.sendPriority(mPriority);
                    }
                });
            }

            public void setupUI(String priority){
                textView.setText(priority);
                mPriority = priority;
            }
        }
    }

    public interface SelectPriorityListener{
        void sendPriority(String priority);
        void cancelPriority();
    }
}