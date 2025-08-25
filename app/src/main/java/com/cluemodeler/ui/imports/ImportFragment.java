package com.cluemodeler.ui.imports;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;
import com.cluemodeler.ModelActivity;
import com.cluemodeler.R;
import com.cluemodeler.databinding.FragmentImportBinding;

import com.cluemodeler.model.*;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Iterator;

public class ImportFragment extends Fragment {

    private FragmentImportBinding binding;

    private PlayerList plist;

    private Model model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = ((ModelActivity) requireActivity()).getModel();
        plist = ((ModelActivity) requireActivity()).getPlist();
        Player last = ((ModelActivity) requireActivity()).getLasttoplay();
        Player self = ((ModelActivity) requireActivity()).getSelf();

        // find next to play
        Player next = plist.nextPlayer(last);

        // set up internal player list
        String[] parr = new String[plist.getPlayerCount() - 1];
        Iterator<Player> iter = plist.iterator();
        int idx = 0;
        for (int i = 0; i < parr.length; i++) {
            Player p = iter.next();
            if (p.equals(self)) {
                i--;
            } else {
                parr[i] = p.name();
                if (p.equals(next)) idx = i;
            }
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

        binding.spinnerAsk.setOnItemSelectedListener(new AskChangeListener());
        binding.spinnerAsk.setSelection(idx);

        binding.button.setOnClickListener(new ButtonUpdateListener());

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
        parr[0] = getString(R.string.no_player);
        ArrayAdapter<String> adp = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parr);
        binding.spinnerAnswered.setAdapter(adp);
        binding.spinnerAnswered.setSelection(0);
    }

    public static <T> String[] toStringArr(T[] obj) {
        String[] ret = new String[obj.length];
        for (int i = 0; i < obj.length; i++) {
            ret[i] = obj[i].toString();
        }
        return ret;
    }

    private class AskChangeListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            setAnswerer();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class ButtonUpdateListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // update last to play
            ((ModelActivity) requireActivity()).setLasttoplay(plist.get(binding.spinnerAsk.getSelectedItem().toString()));

            Card wep = Card.toCard(binding.spinnerWeapon.getSelectedItem().toString());
            Card per = Card.toCard(binding.spinnerPerson.getSelectedItem().toString());
            Card loc = Card.toCard(binding.spinnerLocation.getSelectedItem().toString());

            Player ask = plist.get(binding.spinnerAsk.getSelectedItem().toString());
            Player ans = null;
            if (binding.spinnerAnswered.getSelectedItemPosition() != 0) ans = plist.get(binding.spinnerAnswered.getSelectedItem().toString());

            Query q = new Query(ask, ans, new Card[]{wep, per, loc}, null);

            try {
                model.addQuery(q);

                BottomNavigationView bar = requireActivity().findViewById(R.id.nav_view);
                bar.setSelectedItemId(R.id.navigation_scoreboard);
            } catch (IllegalStateException ise) {
                AlertDialog.Builder dial = new AlertDialog.Builder(getContext());
                dial.setCancelable(true);
                dial.setTitle(getString(R.string.model_error));
                dial.setMessage(ise.getMessage());
                dial.setIcon(R.drawable.ic_dialog_error);
                dial.show();
            }
        }
    }
}