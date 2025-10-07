package com.cluemodeler.ui.scoreboard;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cluemodeler.ModelActivity;
import com.cluemodeler.R;
import com.cluemodeler.databinding.FragmentScoreboardBinding;
import com.cluemodeler.model.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ScoreboardFragment extends Fragment {

    private FragmentScoreboardBinding binding;
    private ImmutableScorecard card;
    private PlayerList players;

    private Button undobutton;

    private final TileOnClickListener tocl = new TileOnClickListener();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        card = ((ModelActivity) requireActivity()).getScorecard();
        players = ((ModelActivity) requireActivity()).getPlist();

        binding = FragmentScoreboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        undobutton = binding.undoButton;
        undobutton.setEnabled(card.isDirty());
        undobutton.setOnClickListener(new UndoOnClickListener());

        // make all unused tiles GONE
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

        buildScorecardView();

        return root;
    }

    private void buildScorecardView() {
        // set names
        // set values
        Iterator<Player> piter = players.iterator();
        Player p = piter.next();
        ImmutableScorecard.ImmutablePlayerScorecard pcard = card.get(p);

        boolean[] murder = new boolean[Card.values().length];
        for (int i = 0; i < Card.values().length; i++) {
            murder[i] = card.isMurder(Card.values()[i]);
        }

        binding.name.setText(p.name());
        setupButton(pcard.get(Card.ROPE), murder[Card.ROPE.ordinal()], binding.rope);
        setupButton(pcard.get(Card.CANDLESTICK), murder[Card.CANDLESTICK.ordinal()], binding.candle);
        setupButton(pcard.get(Card.LEAD_PIPE), murder[Card.LEAD_PIPE.ordinal()], binding.pipe);
        setupButton(pcard.get(Card.PISTOL), murder[Card.PISTOL.ordinal()], binding.pistol);
        setupButton(pcard.get(Card.DAGGER), murder[Card.DAGGER.ordinal()], binding.dagger);
        setupButton(pcard.get(Card.WRENCH), murder[Card.WRENCH.ordinal()], binding.wrench);
        setupButton(pcard.get(Card.GREEN), murder[Card.GREEN.ordinal()], binding.green);
        setupButton(pcard.get(Card.MUSTARD), murder[Card.MUSTARD.ordinal()], binding.mustard);
        setupButton(pcard.get(Card.PEACOCK), murder[Card.PEACOCK.ordinal()], binding.peacock);
        setupButton(pcard.get(Card.WHITE), murder[Card.WHITE.ordinal()], binding.white);
        setupButton(pcard.get(Card.PLUM), murder[Card.PLUM.ordinal()], binding.plum);
        setupButton(pcard.get(Card.SCARLET), murder[Card.SCARLET.ordinal()], binding.scarlet);
        setupButton(pcard.get(Card.COURTYARD), murder[Card.COURTYARD.ordinal()], binding.courtyard);
        setupButton(pcard.get(Card.GARAGE), murder[Card.GARAGE.ordinal()], binding.garage);
        setupButton(pcard.get(Card.GAME_ROOM), murder[Card.GAME_ROOM.ordinal()], binding.game);
        setupButton(pcard.get(Card.BEDROOM), murder[Card.BEDROOM.ordinal()], binding.bedroom);
        setupButton(pcard.get(Card.BATHROOM), murder[Card.BATHROOM.ordinal()], binding.bathroom);
        setupButton(pcard.get(Card.OFFICE), murder[Card.OFFICE.ordinal()], binding.office);
        setupButton(pcard.get(Card.KITCHEN), murder[Card.KITCHEN.ordinal()], binding.kitchen);
        setupButton(pcard.get(Card.DINING_ROOM), murder[Card.DINING_ROOM.ordinal()], binding.dining);
        setupButton(pcard.get(Card.LIVING_ROOM), murder[Card.LIVING_ROOM.ordinal()], binding.living);

        if (piter.hasNext()) {
            p = piter.next();
            pcard = card.get(p);

            binding.name2.setText(p.name());
            setupButton(pcard.get(Card.ROPE), murder[Card.ROPE.ordinal()], binding.rope2);
            setupButton(pcard.get(Card.CANDLESTICK), murder[Card.CANDLESTICK.ordinal()], binding.candle2);
            setupButton(pcard.get(Card.LEAD_PIPE), murder[Card.LEAD_PIPE.ordinal()], binding.pipe2);
            setupButton(pcard.get(Card.PISTOL), murder[Card.PISTOL.ordinal()], binding.pistol2);
            setupButton(pcard.get(Card.DAGGER), murder[Card.DAGGER.ordinal()], binding.dagger2);
            setupButton(pcard.get(Card.WRENCH), murder[Card.WRENCH.ordinal()], binding.wrench2);
            setupButton(pcard.get(Card.GREEN), murder[Card.GREEN.ordinal()], binding.green2);
            setupButton(pcard.get(Card.MUSTARD), murder[Card.MUSTARD.ordinal()], binding.mustard2);
            setupButton(pcard.get(Card.PEACOCK), murder[Card.PEACOCK.ordinal()], binding.peacock2);
            setupButton(pcard.get(Card.WHITE), murder[Card.WHITE.ordinal()], binding.white2);
            setupButton(pcard.get(Card.PLUM), murder[Card.PLUM.ordinal()], binding.plum2);
            setupButton(pcard.get(Card.SCARLET), murder[Card.SCARLET.ordinal()], binding.scarlet2);
            setupButton(pcard.get(Card.COURTYARD), murder[Card.COURTYARD.ordinal()], binding.courtyard2);
            setupButton(pcard.get(Card.GARAGE), murder[Card.GARAGE.ordinal()], binding.garage2);
            setupButton(pcard.get(Card.GAME_ROOM), murder[Card.GAME_ROOM.ordinal()], binding.game2);
            setupButton(pcard.get(Card.BEDROOM), murder[Card.BEDROOM.ordinal()], binding.bedroom2);
            setupButton(pcard.get(Card.BATHROOM), murder[Card.BATHROOM.ordinal()], binding.bathroom2);
            setupButton(pcard.get(Card.OFFICE), murder[Card.OFFICE.ordinal()], binding.office2);
            setupButton(pcard.get(Card.KITCHEN), murder[Card.KITCHEN.ordinal()], binding.kitchen2);
            setupButton(pcard.get(Card.DINING_ROOM), murder[Card.DINING_ROOM.ordinal()], binding.dining2);
            setupButton(pcard.get(Card.LIVING_ROOM), murder[Card.LIVING_ROOM.ordinal()], binding.living2);

            if (piter.hasNext()) {
                p = piter.next();
                pcard = card.get(p);

                binding.name3.setText(p.name());
                setupButton(pcard.get(Card.ROPE), murder[Card.ROPE.ordinal()], binding.rope3);
                setupButton(pcard.get(Card.CANDLESTICK), murder[Card.CANDLESTICK.ordinal()], binding.candle3);
                setupButton(pcard.get(Card.LEAD_PIPE), murder[Card.LEAD_PIPE.ordinal()], binding.pipe3);
                setupButton(pcard.get(Card.PISTOL), murder[Card.PISTOL.ordinal()], binding.pistol3);
                setupButton(pcard.get(Card.DAGGER), murder[Card.DAGGER.ordinal()], binding.dagger3);
                setupButton(pcard.get(Card.WRENCH), murder[Card.WRENCH.ordinal()], binding.wrench3);
                setupButton(pcard.get(Card.GREEN), murder[Card.GREEN.ordinal()], binding.green3);
                setupButton(pcard.get(Card.MUSTARD), murder[Card.MUSTARD.ordinal()], binding.mustard3);
                setupButton(pcard.get(Card.PEACOCK), murder[Card.PEACOCK.ordinal()], binding.peacock3);
                setupButton(pcard.get(Card.WHITE), murder[Card.WHITE.ordinal()], binding.white3);
                setupButton(pcard.get(Card.PLUM), murder[Card.PLUM.ordinal()], binding.plum3);
                setupButton(pcard.get(Card.SCARLET), murder[Card.SCARLET.ordinal()], binding.scarlet3);
                setupButton(pcard.get(Card.COURTYARD), murder[Card.COURTYARD.ordinal()], binding.courtyard3);
                setupButton(pcard.get(Card.GARAGE), murder[Card.GARAGE.ordinal()], binding.garage3);
                setupButton(pcard.get(Card.GAME_ROOM), murder[Card.GAME_ROOM.ordinal()], binding.game3);
                setupButton(pcard.get(Card.BEDROOM), murder[Card.BEDROOM.ordinal()], binding.bedroom3);
                setupButton(pcard.get(Card.BATHROOM), murder[Card.BATHROOM.ordinal()], binding.bathroom3);
                setupButton(pcard.get(Card.OFFICE), murder[Card.OFFICE.ordinal()], binding.office3);
                setupButton(pcard.get(Card.KITCHEN), murder[Card.KITCHEN.ordinal()], binding.kitchen3);
                setupButton(pcard.get(Card.DINING_ROOM), murder[Card.DINING_ROOM.ordinal()], binding.dining3);
                setupButton(pcard.get(Card.LIVING_ROOM), murder[Card.LIVING_ROOM.ordinal()], binding.living3);

                if (piter.hasNext()) {
                    p = piter.next();
                    pcard = card.get(p);

                    binding.name4.setText(p.name());
                    setupButton(pcard.get(Card.ROPE), murder[Card.ROPE.ordinal()], binding.rope4);
                    setupButton(pcard.get(Card.CANDLESTICK), murder[Card.CANDLESTICK.ordinal()], binding.candle4);
                    setupButton(pcard.get(Card.LEAD_PIPE), murder[Card.LEAD_PIPE.ordinal()], binding.pipe4);
                    setupButton(pcard.get(Card.PISTOL), murder[Card.PISTOL.ordinal()], binding.pistol4);
                    setupButton(pcard.get(Card.DAGGER), murder[Card.DAGGER.ordinal()], binding.dagger4);
                    setupButton(pcard.get(Card.WRENCH), murder[Card.WRENCH.ordinal()], binding.wrench4);
                    setupButton(pcard.get(Card.GREEN), murder[Card.GREEN.ordinal()], binding.green4);
                    setupButton(pcard.get(Card.MUSTARD), murder[Card.MUSTARD.ordinal()], binding.mustard4);
                    setupButton(pcard.get(Card.PEACOCK), murder[Card.PEACOCK.ordinal()], binding.peacock4);
                    setupButton(pcard.get(Card.WHITE), murder[Card.WHITE.ordinal()], binding.white4);
                    setupButton(pcard.get(Card.PLUM), murder[Card.PLUM.ordinal()], binding.plum4);
                    setupButton(pcard.get(Card.SCARLET), murder[Card.SCARLET.ordinal()], binding.scarlet4);
                    setupButton(pcard.get(Card.COURTYARD), murder[Card.COURTYARD.ordinal()], binding.courtyard4);
                    setupButton(pcard.get(Card.GARAGE), murder[Card.GARAGE.ordinal()], binding.garage4);
                    setupButton(pcard.get(Card.GAME_ROOM), murder[Card.GAME_ROOM.ordinal()], binding.game4);
                    setupButton(pcard.get(Card.BEDROOM), murder[Card.BEDROOM.ordinal()], binding.bedroom4);
                    setupButton(pcard.get(Card.BATHROOM), murder[Card.BATHROOM.ordinal()], binding.bathroom4);
                    setupButton(pcard.get(Card.OFFICE), murder[Card.OFFICE.ordinal()], binding.office4);
                    setupButton(pcard.get(Card.KITCHEN), murder[Card.KITCHEN.ordinal()], binding.kitchen4);
                    setupButton(pcard.get(Card.DINING_ROOM), murder[Card.DINING_ROOM.ordinal()], binding.dining4);
                    setupButton(pcard.get(Card.LIVING_ROOM), murder[Card.LIVING_ROOM.ordinal()], binding.living4);

                    if (piter.hasNext()) {
                        p = piter.next();
                        pcard = card.get(p);

                        binding.name5.setText(p.name());
                        setupButton(pcard.get(Card.ROPE), murder[Card.ROPE.ordinal()], binding.rope5);
                        setupButton(pcard.get(Card.CANDLESTICK), murder[Card.CANDLESTICK.ordinal()], binding.candle5);
                        setupButton(pcard.get(Card.LEAD_PIPE), murder[Card.LEAD_PIPE.ordinal()], binding.pipe5);
                        setupButton(pcard.get(Card.PISTOL), murder[Card.PISTOL.ordinal()], binding.pistol5);
                        setupButton(pcard.get(Card.DAGGER), murder[Card.DAGGER.ordinal()], binding.dagger5);
                        setupButton(pcard.get(Card.WRENCH), murder[Card.WRENCH.ordinal()], binding.wrench5);
                        setupButton(pcard.get(Card.GREEN), murder[Card.GREEN.ordinal()], binding.green5);
                        setupButton(pcard.get(Card.MUSTARD), murder[Card.MUSTARD.ordinal()], binding.mustard5);
                        setupButton(pcard.get(Card.PEACOCK), murder[Card.PEACOCK.ordinal()], binding.peacock5);
                        setupButton(pcard.get(Card.WHITE), murder[Card.WHITE.ordinal()], binding.white5);
                        setupButton(pcard.get(Card.PLUM), murder[Card.PLUM.ordinal()], binding.plum5);
                        setupButton(pcard.get(Card.SCARLET), murder[Card.SCARLET.ordinal()], binding.scarlet5);
                        setupButton(pcard.get(Card.COURTYARD), murder[Card.COURTYARD.ordinal()], binding.courtyard5);
                        setupButton(pcard.get(Card.GARAGE), murder[Card.GARAGE.ordinal()], binding.garage5);
                        setupButton(pcard.get(Card.GAME_ROOM), murder[Card.GAME_ROOM.ordinal()], binding.game5);
                        setupButton(pcard.get(Card.BEDROOM), murder[Card.BEDROOM.ordinal()], binding.bedroom5);
                        setupButton(pcard.get(Card.BATHROOM), murder[Card.BATHROOM.ordinal()], binding.bathroom5);
                        setupButton(pcard.get(Card.OFFICE), murder[Card.OFFICE.ordinal()], binding.office5);
                        setupButton(pcard.get(Card.KITCHEN), murder[Card.KITCHEN.ordinal()], binding.kitchen5);
                        setupButton(pcard.get(Card.DINING_ROOM), murder[Card.DINING_ROOM.ordinal()], binding.dining5);
                        setupButton(pcard.get(Card.LIVING_ROOM), murder[Card.LIVING_ROOM.ordinal()], binding.living5);

                        if (piter.hasNext()) {
                            p = piter.next();
                            pcard = card.get(p);

                            binding.name6.setText(p.name());
                            setupButton(pcard.get(Card.ROPE), murder[Card.ROPE.ordinal()], binding.rope6);
                            setupButton(pcard.get(Card.CANDLESTICK), murder[Card.CANDLESTICK.ordinal()], binding.candle6);
                            setupButton(pcard.get(Card.LEAD_PIPE), murder[Card.LEAD_PIPE.ordinal()], binding.pipe6);
                            setupButton(pcard.get(Card.PISTOL), murder[Card.PISTOL.ordinal()], binding.pistol6);
                            setupButton(pcard.get(Card.DAGGER), murder[Card.DAGGER.ordinal()], binding.dagger6);
                            setupButton(pcard.get(Card.WRENCH), murder[Card.WRENCH.ordinal()], binding.wrench6);
                            setupButton(pcard.get(Card.GREEN), murder[Card.GREEN.ordinal()], binding.green6);
                            setupButton(pcard.get(Card.MUSTARD), murder[Card.MUSTARD.ordinal()], binding.mustard6);
                            setupButton(pcard.get(Card.PEACOCK), murder[Card.PEACOCK.ordinal()], binding.peacock6);
                            setupButton(pcard.get(Card.WHITE), murder[Card.WHITE.ordinal()], binding.white6);
                            setupButton(pcard.get(Card.PLUM), murder[Card.PLUM.ordinal()], binding.plum6);
                            setupButton(pcard.get(Card.SCARLET), murder[Card.SCARLET.ordinal()], binding.scarlet6);
                            setupButton(pcard.get(Card.COURTYARD), murder[Card.COURTYARD.ordinal()], binding.courtyard6);
                            setupButton(pcard.get(Card.GARAGE), murder[Card.GARAGE.ordinal()], binding.garage6);
                            setupButton(pcard.get(Card.GAME_ROOM), murder[Card.GAME_ROOM.ordinal()], binding.game6);
                            setupButton(pcard.get(Card.BEDROOM), murder[Card.BEDROOM.ordinal()], binding.bedroom6);
                            setupButton(pcard.get(Card.BATHROOM), murder[Card.BATHROOM.ordinal()], binding.bathroom6);
                            setupButton(pcard.get(Card.OFFICE), murder[Card.OFFICE.ordinal()], binding.office6);
                            setupButton(pcard.get(Card.KITCHEN), murder[Card.KITCHEN.ordinal()], binding.kitchen6);
                            setupButton(pcard.get(Card.DINING_ROOM), murder[Card.DINING_ROOM.ordinal()], binding.dining6);
                            setupButton(pcard.get(Card.LIVING_ROOM), murder[Card.LIVING_ROOM.ordinal()], binding.living6);
                        }
                    }
                }
            }
        }
    }

    private void setupButton(Knowledge k, boolean murder, ImageButton img) {
        int id = switch (k.t) {
            case HAS -> R.drawable.scoreboard_has;
            case NO_HAS -> R.drawable.scoreboard_no_has;
            case MIGHT_HAVE -> k == Knowledge.MIGHT_HAVE_DEFAULT ? R.drawable.scoreboard_unknown : R.drawable.scoreboard_might_have;
            case KNOWN -> R.drawable.scoreboard_known;
        };

        img.setImageResource(id);
        if (id == R.drawable.scoreboard_might_have) {
            img.setClickable(true);
            img.setOnClickListener(tocl);
        }
        else img.setClickable(false);
        if (murder) {
            img.setBackground(new ColorDrawable(ContextCompat.getColor(requireContext(), R.color.red)));
            img.setClickable(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class TileOnClickListener implements AdapterView.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            String str = getResources().getResourceEntryName(id);

            char num = str.charAt(str.length() - 1);
            int idx = switch (num) {
                case '2' -> {
                    str = str.substring(0, str.length() - 1);
                    yield 1;
                }
                case '3' -> {
                    str = str.substring(0, str.length() - 1);
                    yield 2;
                }
                case '4' -> {
                    str = str.substring(0, str.length() - 1);
                    yield 3;
                }
                case '5' -> {
                    str = str.substring(0, str.length() - 1);
                    yield 4;
                }
                case '6' -> {
                    str = str.substring(0, str.length() - 1);
                    yield 5;
                }
                default -> 0;
            };

            Card c = Card.toCard(str);

            // get player
            Iterator<Player> piter = players.iterator();
            Player p;
            try {
                for (int i = 0; i < idx; i++) piter.next();
                p = piter.next();
            } catch (NoSuchElementException nsee) {
                // corrupted scorecard, fail?
                return;
            }

            Knowledge k = card.get(p,c);

            // throw out uninteresting cases
            if (k.t != Knowledge.T.MIGHT_HAVE) return;
            if (k == Knowledge.MIGHT_HAVE_DEFAULT) return;

            Set<Card[]> groups = k.getGroups();
            if (groups == null) return;

            LinearLayout ll = new LinearLayout(requireContext());
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setPadding(30, 0, 30, 30);

            {
                TextView tv = new TextView(requireContext());
                tv.setText(p.name() + getString(R.string.dialog_prompt));
                tv.setPadding(20, 20, 20, 20);
                ll.addView(tv);
            }

            for (Card[] grp : groups) {
                Button tv = new Button(requireContext());
                StringBuilder txt = new StringBuilder(grp[0].toString());
                for (int i = 1; i < grp.length; i++) txt.append(getString(R.string.list_delimiter)).append(grp[i].toString());
                tv.setText(txt.toString());
                tv.setClickable(false);
                tv.setPadding(20,20,20,20);
                ll.addView(tv);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

            builder.setView(ll);
            builder.setCancelable(true);
            builder.setTitle(getString(R.string.dialog_title));
            builder.setIcon(R.drawable.scoreboard_might_have);
            AlertDialog dial = builder.create();
            dial.show();
        }
    }

    private class UndoOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            card.restore();
            buildScorecardView();
            undobutton.setEnabled(false);
        }
    }
}