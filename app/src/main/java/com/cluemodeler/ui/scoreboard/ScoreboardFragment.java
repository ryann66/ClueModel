package com.cluemodeler.ui.scoreboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cluemodeler.ModelActivity;
import com.cluemodeler.databinding.FragmentScoreboardBinding;
import com.cluemodeler.model.ImmutableScorecard;
import com.cluemodeler.model.PlayerList;

public class ScoreboardFragment extends Fragment {

    private FragmentScoreboardBinding binding;
    private ImmutableScorecard card;
    private PlayerList players;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScoreboardViewModel scoreboardViewModel =
                new ViewModelProvider(this).get(ScoreboardViewModel.class);
        card = ((ModelActivity) getActivity()).getScorecard();
        players = ((ModelActivity) getActivity()).getPlist();

        binding = FragmentScoreboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // make all unused tiles invisible
        if (players.getPlayerCount() < 6) {
            binding.name6.setVisibility(View.INVISIBLE);
            binding.vbar6.setVisibility(View.INVISIBLE);
            binding.rope6.setVisibility(View.INVISIBLE);
            binding.candle6.setVisibility(View.INVISIBLE);
            binding.pipe6.setVisibility(View.INVISIBLE);
            binding.pistol6.setVisibility(View.INVISIBLE);
            binding.dagger6.setVisibility(View.INVISIBLE);
            binding.wrench6.setVisibility(View.INVISIBLE);
            binding.green6.setVisibility(View.INVISIBLE);
            binding.mustard6.setVisibility(View.INVISIBLE);
            binding.peacock6.setVisibility(View.INVISIBLE);
            binding.white6.setVisibility(View.INVISIBLE);
            binding.plum6.setVisibility(View.INVISIBLE);
            binding.scarlet6.setVisibility(View.INVISIBLE);
            binding.courtyard6.setVisibility(View.INVISIBLE);
            binding.garage6.setVisibility(View.INVISIBLE);
            binding.game6.setVisibility(View.INVISIBLE);
            binding.bedroom6.setVisibility(View.INVISIBLE);
            binding.bathroom6.setVisibility(View.INVISIBLE);
            binding.office6.setVisibility(View.INVISIBLE);
            binding.kitchen6.setVisibility(View.INVISIBLE);
            binding.dining6.setVisibility(View.INVISIBLE);
            binding.living6.setVisibility(View.INVISIBLE);
            if (players.getPlayerCount() < 5) {
                binding.name5.setVisibility(View.INVISIBLE);
                binding.vbar5.setVisibility(View.INVISIBLE);
                binding.rope5.setVisibility(View.INVISIBLE);
                binding.candle5.setVisibility(View.INVISIBLE);
                binding.pipe5.setVisibility(View.INVISIBLE);
                binding.pistol5.setVisibility(View.INVISIBLE);
                binding.dagger5.setVisibility(View.INVISIBLE);
                binding.wrench5.setVisibility(View.INVISIBLE);
                binding.green5.setVisibility(View.INVISIBLE);
                binding.mustard5.setVisibility(View.INVISIBLE);
                binding.peacock5.setVisibility(View.INVISIBLE);
                binding.white5.setVisibility(View.INVISIBLE);
                binding.plum5.setVisibility(View.INVISIBLE);
                binding.scarlet5.setVisibility(View.INVISIBLE);
                binding.courtyard5.setVisibility(View.INVISIBLE);
                binding.garage5.setVisibility(View.INVISIBLE);
                binding.game5.setVisibility(View.INVISIBLE);
                binding.bedroom5.setVisibility(View.INVISIBLE);
                binding.bathroom5.setVisibility(View.INVISIBLE);
                binding.office5.setVisibility(View.INVISIBLE);
                binding.kitchen5.setVisibility(View.INVISIBLE);
                binding.dining5.setVisibility(View.INVISIBLE);
                binding.living5.setVisibility(View.INVISIBLE);
                if (players.getPlayerCount() < 4) {
                    binding.name4.setVisibility(View.INVISIBLE);
                    binding.vbar4.setVisibility(View.INVISIBLE);
                    binding.rope4.setVisibility(View.INVISIBLE);
                    binding.candle4.setVisibility(View.INVISIBLE);
                    binding.pipe4.setVisibility(View.INVISIBLE);
                    binding.pistol4.setVisibility(View.INVISIBLE);
                    binding.dagger4.setVisibility(View.INVISIBLE);
                    binding.wrench4.setVisibility(View.INVISIBLE);
                    binding.green4.setVisibility(View.INVISIBLE);
                    binding.mustard4.setVisibility(View.INVISIBLE);
                    binding.peacock4.setVisibility(View.INVISIBLE);
                    binding.white4.setVisibility(View.INVISIBLE);
                    binding.plum4.setVisibility(View.INVISIBLE);
                    binding.scarlet4.setVisibility(View.INVISIBLE);
                    binding.courtyard4.setVisibility(View.INVISIBLE);
                    binding.garage4.setVisibility(View.INVISIBLE);
                    binding.game4.setVisibility(View.INVISIBLE);
                    binding.bedroom4.setVisibility(View.INVISIBLE);
                    binding.bathroom4.setVisibility(View.INVISIBLE);
                    binding.office4.setVisibility(View.INVISIBLE);
                    binding.kitchen4.setVisibility(View.INVISIBLE);
                    binding.dining4.setVisibility(View.INVISIBLE);
                    binding.living4.setVisibility(View.INVISIBLE);
                }
            }
        }

        // set names

        // set values

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}