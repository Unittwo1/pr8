package com.example.mobiles4.UI.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mobiles4.Data.DataSources.SP.SPDS;
import com.example.mobiles4.R;
import com.example.mobiles4.UI.VM.TaskListViewModel;

import java.util.ArrayList;

public class LeftFragment extends Fragment {
    private int count;
    public LeftFragment() {
        super(R.layout.fragment_left);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.listView);
        TaskListViewModel list = new ViewModelProvider(getActivity()).get(TaskListViewModel.class);
        Bundle bundle = getArguments();
        if (bundle.getBoolean("CreateList")) {
            list.getUiState().observe(getViewLifecycleOwner(), uiState -> {
                list.clearTaskList();
            });
            if (bundle.containsKey("qCount")) {
                count = bundle.getInt("qCount");
            } else {
                count = 200;
            }
            for (int i = 0; i < count; i++) {
                list.getUiState().observe(getViewLifecycleOwner(), uiState -> {
                list.addToList(
                        list.createRandomMathematicalTask());
                });
            }
        }
        bundle.putBoolean("CreateList", false);
        ListViewAdapter adapter = new ListViewAdapter(
                getContext(),
                R.layout.list_view_adapter,
                list.getUiState().getValue()
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("getElement", position);

                Navigation.findNavController(view).navigate(R.id.a_answer, bundle1);
            }
        });
        Button bSaveTasks = view.findViewById(R.id.b_save_tasks);
        bSaveTasks.setOnClickListener(v -> {
            SPDS.saveList(getActivity(), list.getUiState().getValue().size(), list.getUiState().getValue());
        });
        Button bCheckTasks = view.findViewById(R.id.b_check_tasks);
        bCheckTasks.setOnClickListener(v -> {
            SPDS.checkList(getActivity());
        });
    }
}
