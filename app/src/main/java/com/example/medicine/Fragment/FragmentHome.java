package com.example.medicine.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.Category.Category;
import com.example.medicine.Category.CategoryAdapter;
import com.example.medicine.R;
import com.example.medicine.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    CategoryAdapter adapter;
    ArrayList<Category> categories;
    FragmentHomeBinding homeBinding;
    RecyclerView rvMedicine;
    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        // Tạo categories
        rvMedicine = view.findViewById(R.id.rvMedicine);
        categories = Category.getMockData();
        adapter = new CategoryAdapter(categories, view.getContext());
        rvMedicine.setAdapter(adapter);
        rvMedicine.setLayoutManager(new GridLayoutManager(view.getContext(), 2));


        return view;
    }

}