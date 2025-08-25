package com.cluemodeler.ui.scoreboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cluemodeler.ModelActivity;
import com.cluemodeler.R;
import com.cluemodeler.databinding.FragmentScoreboardBinding;
import com.cluemodeler.model.ImmutableScorecard;
import com.cluemodeler.model.Knowledge;
import com.cluemodeler.model.PlayerList;
import com.cluemodeler.model.Player;
import com.cluemodeler.model.Card;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ScoreboardFragment extends Fragment {

    private FragmentScoreboardBinding binding;
    private ImmutableScorecard card;
    private PlayerList players;

    private final TileOnClickListener tocl = new TileOnClickListener();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        card = ((ModelActivity) requireActivity()).getScorecard();
        players = ((ModelActivity) requireActivity()).getPlist();

        binding = FragmentScoreboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // make all unused tiles GONE
        if (players.getPlayerCount() < 6) {
            binding.name6.setVisibility(View.GONE);
            binding.vbar6.setVisibility(View.GONE);
            binding.rope6.setVisibility(View.GONE);
            binding.candle6.setVisibility(View.GONE);
            binding.pipe6.setVisibility(View.GONE);
            binding.pistol6.setVisibility(View.GONE);
            binding.dagger6.setVisibility(View.GONE);
            binding.wrench6.setVisibility(View.GONE);
            binding.green6.setVisibility(View.GONE);
            binding.mustard6.setVisibility(View.GONE);
            binding.peacock6.setVisibility(View.GONE);
            binding.white6.setVisibility(View.GONE);
            binding.plum6.setVisibility(View.GONE);
            binding.scarlet6.setVisibility(View.GONE);
            binding.courtyard6.setVisibility(View.GONE);
            binding.garage6.setVisibility(View.GONE);
            binding.game6.setVisibility(View.GONE);
            binding.bedroom6.setVisibility(View.GONE);
            binding.bathroom6.setVisibility(View.GONE);
            binding.office6.setVisibility(View.GONE);
            binding.kitchen6.setVisibility(View.GONE);
            binding.dining6.setVisibility(View.GONE);
            binding.living6.setVisibility(View.GONE);
            if (players.getPlayerCount() < 5) {
                binding.name5.setVisibility(View.GONE);
                binding.vbar5.setVisibility(View.GONE);
                binding.rope5.setVisibility(View.GONE);
                binding.candle5.setVisibility(View.GONE);
                binding.pipe5.setVisibility(View.GONE);
                binding.pistol5.setVisibility(View.GONE);
                binding.dagger5.setVisibility(View.GONE);
                binding.wrench5.setVisibility(View.GONE);
                binding.green5.setVisibility(View.GONE);
                binding.mustard5.setVisibility(View.GONE);
                binding.peacock5.setVisibility(View.GONE);
                binding.white5.setVisibility(View.GONE);
                binding.plum5.setVisibility(View.GONE);
                binding.scarlet5.setVisibility(View.GONE);
                binding.courtyard5.setVisibility(View.GONE);
                binding.garage5.setVisibility(View.GONE);
                binding.game5.setVisibility(View.GONE);
                binding.bedroom5.setVisibility(View.GONE);
                binding.bathroom5.setVisibility(View.GONE);
                binding.office5.setVisibility(View.GONE);
                binding.kitchen5.setVisibility(View.GONE);
                binding.dining5.setVisibility(View.GONE);
                binding.living5.setVisibility(View.GONE);
                if (players.getPlayerCount() < 4) {
                    binding.name4.setVisibility(View.GONE);
                    binding.vbar4.setVisibility(View.GONE);
                    binding.rope4.setVisibility(View.GONE);
                    binding.candle4.setVisibility(View.GONE);
                    binding.pipe4.setVisibility(View.GONE);
                    binding.pistol4.setVisibility(View.GONE);
                    binding.dagger4.setVisibility(View.GONE);
                    binding.wrench4.setVisibility(View.GONE);
                    binding.green4.setVisibility(View.GONE);
                    binding.mustard4.setVisibility(View.GONE);
                    binding.peacock4.setVisibility(View.GONE);
                    binding.white4.setVisibility(View.GONE);
                    binding.plum4.setVisibility(View.GONE);
                    binding.scarlet4.setVisibility(View.GONE);
                    binding.courtyard4.setVisibility(View.GONE);
                    binding.garage4.setVisibility(View.GONE);
                    binding.game4.setVisibility(View.GONE);
                    binding.bedroom4.setVisibility(View.GONE);
                    binding.bathroom4.setVisibility(View.GONE);
                    binding.office4.setVisibility(View.GONE);
                    binding.kitchen4.setVisibility(View.GONE);
                    binding.dining4.setVisibility(View.GONE);
                    binding.living4.setVisibility(View.GONE);
                }
            }
        }

        // set names
        // set values
        Iterator<Player> piter = players.iterator();
        Player p = piter.next();
        ImmutableScorecard.ImmutablePlayerScorecard pcard = card.get(p);

        binding.name.setText(p.name());
        setupButton(pcard.get(Card.ROPE), binding.rope);
        setupButton(pcard.get(Card.CANDLESTICK), binding.candle);
        setupButton(pcard.get(Card.LEAD_PIPE), binding.pipe);
        setupButton(pcard.get(Card.PISTOL), binding.pistol);
        setupButton(pcard.get(Card.DAGGER), binding.dagger);
        setupButton(pcard.get(Card.WRENCH), binding.wrench);
        setupButton(pcard.get(Card.GREEN), binding.green);
        setupButton(pcard.get(Card.MUSTARD), binding.mustard);
        setupButton(pcard.get(Card.PEACOCK), binding.peacock);
        setupButton(pcard.get(Card.WHITE), binding.white);
        setupButton(pcard.get(Card.PLUM), binding.plum);
        setupButton(pcard.get(Card.SCARLET), binding.scarlet);
        setupButton(pcard.get(Card.COURTYARD), binding.courtyard);
        setupButton(pcard.get(Card.GARAGE), binding.garage);
        setupButton(pcard.get(Card.GAME_ROOM), binding.game);
        setupButton(pcard.get(Card.BEDROOM), binding.bedroom);
        setupButton(pcard.get(Card.BATHROOM), binding.bathroom);
        setupButton(pcard.get(Card.OFFICE), binding.office);
        setupButton(pcard.get(Card.KITCHEN), binding.kitchen);
        setupButton(pcard.get(Card.DINING_ROOM), binding.dining);
        setupButton(pcard.get(Card.LIVING_ROOM), binding.living);
        
        if (piter.hasNext()) {
            p = piter.next();
            pcard = card.get(p);

            binding.name2.setText(p.name());
            setupButton(pcard.get(Card.ROPE), binding.rope2);
            setupButton(pcard.get(Card.CANDLESTICK), binding.candle2);
            setupButton(pcard.get(Card.LEAD_PIPE), binding.pipe2);
            setupButton(pcard.get(Card.PISTOL), binding.pistol2);
            setupButton(pcard.get(Card.DAGGER), binding.dagger2);
            setupButton(pcard.get(Card.WRENCH), binding.wrench2);
            setupButton(pcard.get(Card.GREEN), binding.green2);
            setupButton(pcard.get(Card.MUSTARD), binding.mustard2);
            setupButton(pcard.get(Card.PEACOCK), binding.peacock2);
            setupButton(pcard.get(Card.WHITE), binding.white2);
            setupButton(pcard.get(Card.PLUM), binding.plum2);
            setupButton(pcard.get(Card.SCARLET), binding.scarlet2);
            setupButton(pcard.get(Card.COURTYARD), binding.courtyard2);
            setupButton(pcard.get(Card.GARAGE), binding.garage2);
            setupButton(pcard.get(Card.GAME_ROOM), binding.game2);
            setupButton(pcard.get(Card.BEDROOM), binding.bedroom2);
            setupButton(pcard.get(Card.BATHROOM), binding.bathroom2);
            setupButton(pcard.get(Card.OFFICE), binding.office2);
            setupButton(pcard.get(Card.KITCHEN), binding.kitchen2);
            setupButton(pcard.get(Card.DINING_ROOM), binding.dining2);
            setupButton(pcard.get(Card.LIVING_ROOM), binding.living2);
            
            if (piter.hasNext()) {
                p = piter.next();
                pcard = card.get(p);

                binding.name3.setText(p.name());
                setupButton(pcard.get(Card.ROPE), binding.rope3);
                setupButton(pcard.get(Card.CANDLESTICK), binding.candle3);
                setupButton(pcard.get(Card.LEAD_PIPE), binding.pipe3);
                setupButton(pcard.get(Card.PISTOL), binding.pistol3);
                setupButton(pcard.get(Card.DAGGER), binding.dagger3);
                setupButton(pcard.get(Card.WRENCH), binding.wrench3);
                setupButton(pcard.get(Card.GREEN), binding.green3);
                setupButton(pcard.get(Card.MUSTARD), binding.mustard3);
                setupButton(pcard.get(Card.PEACOCK), binding.peacock3);
                setupButton(pcard.get(Card.WHITE), binding.white3);
                setupButton(pcard.get(Card.PLUM), binding.plum3);
                setupButton(pcard.get(Card.SCARLET), binding.scarlet3);
                setupButton(pcard.get(Card.COURTYARD), binding.courtyard3);
                setupButton(pcard.get(Card.GARAGE), binding.garage3);
                setupButton(pcard.get(Card.GAME_ROOM), binding.game3);
                setupButton(pcard.get(Card.BEDROOM), binding.bedroom3);
                setupButton(pcard.get(Card.BATHROOM), binding.bathroom3);
                setupButton(pcard.get(Card.OFFICE), binding.office3);
                setupButton(pcard.get(Card.KITCHEN), binding.kitchen3);
                setupButton(pcard.get(Card.DINING_ROOM), binding.dining3);
                setupButton(pcard.get(Card.LIVING_ROOM), binding.living3);

                if (piter.hasNext()) {
                    p = piter.next();
                    pcard = card.get(p);

                    binding.name4.setText(p.name());
                    setupButton(pcard.get(Card.ROPE), binding.rope4);
                    setupButton(pcard.get(Card.CANDLESTICK), binding.candle4);
                    setupButton(pcard.get(Card.LEAD_PIPE), binding.pipe4);
                    setupButton(pcard.get(Card.PISTOL), binding.pistol4);
                    setupButton(pcard.get(Card.DAGGER), binding.dagger4);
                    setupButton(pcard.get(Card.WRENCH), binding.wrench4);
                    setupButton(pcard.get(Card.GREEN), binding.green4);
                    setupButton(pcard.get(Card.MUSTARD), binding.mustard4);
                    setupButton(pcard.get(Card.PEACOCK), binding.peacock4);
                    setupButton(pcard.get(Card.WHITE), binding.white4);
                    setupButton(pcard.get(Card.PLUM), binding.plum4);
                    setupButton(pcard.get(Card.SCARLET), binding.scarlet4);
                    setupButton(pcard.get(Card.COURTYARD), binding.courtyard4);
                    setupButton(pcard.get(Card.GARAGE), binding.garage4);
                    setupButton(pcard.get(Card.GAME_ROOM), binding.game4);
                    setupButton(pcard.get(Card.BEDROOM), binding.bedroom4);
                    setupButton(pcard.get(Card.BATHROOM), binding.bathroom4);
                    setupButton(pcard.get(Card.OFFICE), binding.office4);
                    setupButton(pcard.get(Card.KITCHEN), binding.kitchen4);
                    setupButton(pcard.get(Card.DINING_ROOM), binding.dining4);
                    setupButton(pcard.get(Card.LIVING_ROOM), binding.living4);

                    if (piter.hasNext()) {
                        p = piter.next();
                        pcard = card.get(p);

                        binding.name5.setText(p.name());
                        setupButton(pcard.get(Card.ROPE), binding.rope5);
                        setupButton(pcard.get(Card.CANDLESTICK), binding.candle5);
                        setupButton(pcard.get(Card.LEAD_PIPE), binding.pipe5);
                        setupButton(pcard.get(Card.PISTOL), binding.pistol5);
                        setupButton(pcard.get(Card.DAGGER), binding.dagger5);
                        setupButton(pcard.get(Card.WRENCH), binding.wrench5);
                        setupButton(pcard.get(Card.GREEN), binding.green5);
                        setupButton(pcard.get(Card.MUSTARD), binding.mustard5);
                        setupButton(pcard.get(Card.PEACOCK), binding.peacock5);
                        setupButton(pcard.get(Card.WHITE), binding.white5);
                        setupButton(pcard.get(Card.PLUM), binding.plum5);
                        setupButton(pcard.get(Card.SCARLET), binding.scarlet5);
                        setupButton(pcard.get(Card.COURTYARD), binding.courtyard5);
                        setupButton(pcard.get(Card.GARAGE), binding.garage5);
                        setupButton(pcard.get(Card.GAME_ROOM), binding.game5);
                        setupButton(pcard.get(Card.BEDROOM), binding.bedroom5);
                        setupButton(pcard.get(Card.BATHROOM), binding.bathroom5);
                        setupButton(pcard.get(Card.OFFICE), binding.office5);
                        setupButton(pcard.get(Card.KITCHEN), binding.kitchen5);
                        setupButton(pcard.get(Card.DINING_ROOM), binding.dining5);
                        setupButton(pcard.get(Card.LIVING_ROOM), binding.living5);

                        if (piter.hasNext()) {
                            p = piter.next();
                            pcard = card.get(p);

                            binding.name6.setText(p.name());
                            setupButton(pcard.get(Card.ROPE), binding.rope6);
                            setupButton(pcard.get(Card.CANDLESTICK), binding.candle6);
                            setupButton(pcard.get(Card.LEAD_PIPE), binding.pipe6);
                            setupButton(pcard.get(Card.PISTOL), binding.pistol6);
                            setupButton(pcard.get(Card.DAGGER), binding.dagger6);
                            setupButton(pcard.get(Card.WRENCH), binding.wrench6);
                            setupButton(pcard.get(Card.GREEN), binding.green6);
                            setupButton(pcard.get(Card.MUSTARD), binding.mustard6);
                            setupButton(pcard.get(Card.PEACOCK), binding.peacock6);
                            setupButton(pcard.get(Card.WHITE), binding.white6);
                            setupButton(pcard.get(Card.PLUM), binding.plum6);
                            setupButton(pcard.get(Card.SCARLET), binding.scarlet6);
                            setupButton(pcard.get(Card.COURTYARD), binding.courtyard6);
                            setupButton(pcard.get(Card.GARAGE), binding.garage6);
                            setupButton(pcard.get(Card.GAME_ROOM), binding.game6);
                            setupButton(pcard.get(Card.BEDROOM), binding.bedroom6);
                            setupButton(pcard.get(Card.BATHROOM), binding.bathroom6);
                            setupButton(pcard.get(Card.OFFICE), binding.office6);
                            setupButton(pcard.get(Card.KITCHEN), binding.kitchen6);
                            setupButton(pcard.get(Card.DINING_ROOM), binding.dining6);
                            setupButton(pcard.get(Card.LIVING_ROOM), binding.living6);
                        }
                    }
                }
            }
        }

        return root;
    }

    private void setupButton(Knowledge k, ImageButton img) {
        int id = switch (k.t) {
            case HAS -> R.drawable.ic_scoreboard_has;
            case NO_HAS -> R.drawable.ic_scoreboard_no_has;
            case MIGHT_HAVE -> k == Knowledge.MIGHT_HAVE_DEFAULT ? R.drawable.ic_scoreboard_unknown : R.drawable.ic_scoreboard_might_have;
            case KNOWN -> R.drawable.ic_scoreboard_known;
        };
        
        img.setImageResource(id);
        if (id == R.drawable.ic_scoreboard_might_have) img.setOnClickListener(tocl);
        else img.setClickable(false);
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
                for (int i = 1; i < grp.length; i++) txt.append(getString(R.string.list_delim)).append(grp[i].toString());
                tv.setText(txt.toString());
                tv.setClickable(false);
                tv.setPadding(20,20,20,20);
                ll.addView(tv);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

            builder.setView(ll);
            builder.setCancelable(true);
            builder.setTitle(getString(R.string.dialog_title));
            builder.setIcon(R.drawable.ic_scoreboard_might_have);
            AlertDialog dial = builder.create();
            dial.show();
        }
    }
}