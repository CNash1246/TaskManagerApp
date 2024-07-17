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

import java.util.Arrays;
import java.util.List;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectCategoryBinding;
import edu.uncc.assignment06.models.Data;

public class SelectCategoryFragment extends Fragment {
    FragmentSelectCategoryBinding binding;
    List<String> categories = Arrays.asList(Data.categories);
    CategoryAdapter adapter;

    public SelectCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CategoryAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelCategory();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectCategoryListener) context;
    }
    class CategoryAdapter extends RecyclerView.Adapter<SelectCategoryFragment.CategoryAdapter.CategoryViewHolder> {
        @NonNull
        @Override
        public SelectCategoryFragment.CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.selection_list_item, parent, false);
            return new SelectCategoryFragment.CategoryAdapter.CategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectCategoryFragment.CategoryAdapter.CategoryViewHolder holder, int position) {
            String category = categories.get(position);
            holder.setupUI(category);
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        class CategoryViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            String mCategories;

            public CategoryViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.sendCategory(mCategories);
                    }
                });
            }

            public void setupUI(String categories) {
                textView.setText(categories);
                mCategories = categories;
            }
        }
    }
    SelectCategoryListener mListener;
    public interface SelectCategoryListener{
        void sendCategory(String category);
        void cancelCategory();
    }
}