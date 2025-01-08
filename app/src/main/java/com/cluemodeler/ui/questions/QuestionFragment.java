package com.cluemodeler.ui.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cluemodeler.databinding.FragmentQuestionsBinding;

import com.cluemodeler.model.Model;

public class QuestionFragment extends Fragment {

    private FragmentQuestionsBinding binding;
    private Model model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QuestionViewModel questionViewModel =
                new ViewModelProvider(this).get(QuestionViewModel.class);
        model = questionViewModel.getModel();

        binding = FragmentQuestionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}