package com.cluemodeler.ui.imports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cluemodeler.ModelActivity;
import com.cluemodeler.databinding.FragmentImportBinding;

import com.cluemodeler.model.Model;

public class ImportFragment extends Fragment {

    private FragmentImportBinding binding;
    private Model model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ImportViewModel importViewModel =
                new ViewModelProvider(this).get(ImportViewModel.class);
        model = ((ModelActivity) getActivity()).getModel();

        binding = FragmentImportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}