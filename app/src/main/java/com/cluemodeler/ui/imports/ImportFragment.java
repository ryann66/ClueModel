package com.cluemodeler.ui.imports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cluemodeler.ModelActivity;
import com.cluemodeler.R;
import com.cluemodeler.databinding.FragmentImportBinding;

import com.cluemodeler.model.Card;
import com.cluemodeler.model.Model;
import com.cluemodeler.model.Player;
import com.cluemodeler.model.PlayerList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class ImportFragment extends Fragment {

    private FragmentImportBinding binding;

    private Player self;
    private PlayerList plist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ImportViewModel importViewModel =
                new ViewModelProvider(this).get(ImportViewModel.class);
        Model model = ((ModelActivity) getActivity()).getModel();
        plist = (PlayerList) ((ModelActivity) getActivity()).getPlist();
        Player last = (Player) ((ModelActivity) getActivity()).getLasttoplay();
        self = (Player) ((ModelActivity) getActivity()).getSelf();
        String[] parr = new String[plist.getPlayerCount()];
        Iterator<Player> iter = plist.iterator();
        for (int i = 0; i < parr.length; i++) {
            parr[i] = iter.next().name();
        }

        binding = FragmentImportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayAdapter<String> adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parr);
        binding.spinnerAsk.setAdapter(adp);
        adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, toStringArr(Card.WEAPONS.toArray()));
        binding.spinnerWeapon.setAdapter(adp);
        adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, toStringArr(Card.PEOPLE.toArray()));
        binding.spinnerPerson.setAdapter(adp);
        adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, toStringArr(Card.LOCATIONS.toArray()));
        binding.spinnerLocation.setAdapter(adp);

        setAnswerer();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setAnswerer() {
        // set contents of answerer field
        String[] parr = new String[plist.getPlayerCount()];
        Iterator<Player> iter = plist.iterator();
        for (int i = 1; i < parr.length; i++) {
            String p = iter.next().name();
            if (p.equals(binding.spinnerAsk.getSelectedItem().toString())) {
                // skip item
                i--;
                continue;
            }
            parr[i] = p;
        }
        parr[0] = "None";// todo: link to resource
        ArrayAdapter<String> adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parr);
        binding.spinnerAsk.setAdapter(adp);

        // set visibility of answer field
        // todo
    }

    private static <T> String[] toStringArr(T[] obj) {
        String[] ret = new String[obj.length];
        for (int i = 0; i < obj.length; i++) {
            ret[i] = obj[i].toString();
        }
        return ret;
    }
}