package com.cluemodeler.ui.guess;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cluemodeler.databinding.FragmentGuessBinding;

import com.cluemodeler.model.Strategy;

public class GuessFragment extends Fragment {

    private FragmentGuessBinding binding;
    private Strategy strategy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GuessViewModel guessViewModel =
                new ViewModelProvider(this).get(GuessViewModel.class);
        strategy = guessViewModel.getStrategy();

        binding = FragmentGuessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}