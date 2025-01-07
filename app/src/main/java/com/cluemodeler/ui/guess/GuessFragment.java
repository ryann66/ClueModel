package com.cluemodeler.ui.guess;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cluemodeler.databinding.FragmentGuessBinding;

public class GuessFragment extends Fragment {

    private FragmentGuessBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GuessViewModel guessViewModel =
                new ViewModelProvider(this).get(GuessViewModel.class);

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