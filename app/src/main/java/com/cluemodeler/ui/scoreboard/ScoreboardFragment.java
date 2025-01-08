package com.cluemodeler.ui.scoreboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cluemodeler.databinding.FragmentScoreboardBinding;
import com.cluemodeler.model.ImmutableScorecard;

public class ScoreboardFragment extends Fragment {

    private FragmentScoreboardBinding binding;
    private ImmutableScorecard card;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScoreboardViewModel scoreboardViewModel =
                new ViewModelProvider(this).get(ScoreboardViewModel.class);
        card = scoreboardViewModel.getScorecard();

        binding = FragmentScoreboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}