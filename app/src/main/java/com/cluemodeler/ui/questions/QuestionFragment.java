package com.cluemodeler.ui.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cluemodeler.ModelActivity;
import com.cluemodeler.R;
import com.cluemodeler.databinding.FragmentQuestionsBinding;

import com.cluemodeler.model.Card;
import com.cluemodeler.model.Model;
import com.cluemodeler.model.Player;
import com.cluemodeler.model.PlayerList;
import com.cluemodeler.ui.imports.ImportViewModel;

import java.util.Iterator;
import java.util.Objects;

import static com.cluemodeler.ui.imports.ImportFragment.toStringArr;

public class QuestionFragment extends Fragment {

    private FragmentQuestionsBinding binding;
    private Model model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ImportViewModel importViewModel =
                new ViewModelProvider(this).get(ImportViewModel.class);
        model = ((ModelActivity) Objects.requireNonNull(getActivity())).getModel();
        PlayerList plist = (PlayerList) ((ModelActivity) getActivity()).getPlist();
        Player self = (Player) ((ModelActivity) getActivity()).getSelf();

        // set up internal player list
        String[] parr = new String[plist.getPlayerCount()];
        parr[0] = getString(R.string.no_player);
        Iterator<Player> iter = plist.iterator();
        for (int i = 1; i < parr.length; i++) {
            Player p = iter.next();
            if (p.equals(self)) {
                i--;
            } else {
                parr[i] = p.name();
            }
        }

        binding = FragmentQuestionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayAdapter<String> adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parr);
        binding.spinnerAnswered.setAdapter(adp);


        adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, toStringArr(Card.WEAPONS.toArray()));
        binding.spinnerWeapon.setAdapter(adp);
        adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, toStringArr(Card.PEOPLE.toArray()));
        binding.spinnerPerson.setAdapter(adp);
        adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, toStringArr(Card.LOCATIONS.toArray()));
        binding.spinnerLocation.setAdapter(adp);

        AnswerOnChangeListener aocl = new AnswerOnChangeListener();
        binding.spinnerAnswered.setOnItemSelectedListener(aocl);
        binding.spinnerPerson.setOnItemSelectedListener(aocl);
        binding.spinnerWeapon.setOnItemSelectedListener(aocl);
        binding.spinnerLocation.setOnItemSelectedListener(aocl);
        setAnswer();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setAnswer() {
        ArrayAdapter<String> adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[]{
                binding.spinnerWeapon.getSelectedItem().toString(),
                binding.spinnerPerson.getSelectedItem().toString(),
                binding.spinnerLocation.getSelectedItem().toString()
        });
        binding.spinnerAnswer.setAdapter(adp);

        binding.spinnerAnswer.setVisibility(binding.spinnerAnswered.getSelectedItemPosition() == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private class AnswerOnChangeListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            setAnswer();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}