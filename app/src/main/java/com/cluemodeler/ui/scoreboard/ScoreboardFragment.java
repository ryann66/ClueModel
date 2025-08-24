package com.cluemodeler.ui.scoreboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ScoreboardFragment extends Fragment {

    private FragmentScoreboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ImmutableScorecard card = ((ModelActivity) requireActivity()).getScorecard();
        PlayerList players = ((ModelActivity) requireActivity()).getPlist();

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
        Iterator<Player> piter = players.iterator();
        Player p = piter.next();
        ImmutableScorecard.ImmutablePlayerScorecard pcard = card.get(p);

        binding.name.setText(p.name());
        binding.rope.setImageResource(imageResource(pcard.get(Card.ROPE)));
        binding.candle.setImageResource(imageResource(pcard.get(Card.CANDLESTICK)));
        binding.pipe.setImageResource(imageResource(pcard.get(Card.LEAD_PIPE)));
        binding.pistol.setImageResource(imageResource(pcard.get(Card.PISTOL)));
        binding.dagger.setImageResource(imageResource(pcard.get(Card.DAGGER)));
        binding.wrench.setImageResource(imageResource(pcard.get(Card.WRENCH)));
        binding.green.setImageResource(imageResource(pcard.get(Card.GREEN)));
        binding.mustard.setImageResource(imageResource(pcard.get(Card.MUSTARD)));
        binding.peacock.setImageResource(imageResource(pcard.get(Card.PEACOCK)));
        binding.white.setImageResource(imageResource(pcard.get(Card.WHITE)));
        binding.plum.setImageResource(imageResource(pcard.get(Card.PLUM)));
        binding.scarlet.setImageResource(imageResource(pcard.get(Card.SCARLET)));
        binding.courtyard.setImageResource(imageResource(pcard.get(Card.COURTYARD)));
        binding.garage.setImageResource(imageResource(pcard.get(Card.GARAGE)));
        binding.game.setImageResource(imageResource(pcard.get(Card.GAME_ROOM)));
        binding.bedroom.setImageResource(imageResource(pcard.get(Card.BEDROOM)));
        binding.bathroom.setImageResource(imageResource(pcard.get(Card.BATHROOM)));
        binding.office.setImageResource(imageResource(pcard.get(Card.OFFICE)));
        binding.kitchen.setImageResource(imageResource(pcard.get(Card.KITCHEN)));
        binding.dining.setImageResource(imageResource(pcard.get(Card.DINING_ROOM)));
        binding.living.setImageResource(imageResource(pcard.get(Card.LIVING_ROOM)));
        
        if (piter.hasNext()) {
            p = piter.next();
            pcard = card.get(p);

            binding.name2.setText(p.name());
            binding.rope2.setImageResource(imageResource(pcard.get(Card.ROPE)));
            binding.candle2.setImageResource(imageResource(pcard.get(Card.CANDLESTICK)));
            binding.pipe2.setImageResource(imageResource(pcard.get(Card.LEAD_PIPE)));
            binding.pistol2.setImageResource(imageResource(pcard.get(Card.PISTOL)));
            binding.dagger2.setImageResource(imageResource(pcard.get(Card.DAGGER)));
            binding.wrench2.setImageResource(imageResource(pcard.get(Card.WRENCH)));
            binding.green2.setImageResource(imageResource(pcard.get(Card.GREEN)));
            binding.mustard2.setImageResource(imageResource(pcard.get(Card.MUSTARD)));
            binding.peacock2.setImageResource(imageResource(pcard.get(Card.PEACOCK)));
            binding.white2.setImageResource(imageResource(pcard.get(Card.WHITE)));
            binding.plum2.setImageResource(imageResource(pcard.get(Card.PLUM)));
            binding.scarlet2.setImageResource(imageResource(pcard.get(Card.SCARLET)));
            binding.courtyard2.setImageResource(imageResource(pcard.get(Card.COURTYARD)));
            binding.garage2.setImageResource(imageResource(pcard.get(Card.GARAGE)));
            binding.game2.setImageResource(imageResource(pcard.get(Card.GAME_ROOM)));
            binding.bedroom2.setImageResource(imageResource(pcard.get(Card.BEDROOM)));
            binding.bathroom2.setImageResource(imageResource(pcard.get(Card.BATHROOM)));
            binding.office2.setImageResource(imageResource(pcard.get(Card.OFFICE)));
            binding.kitchen2.setImageResource(imageResource(pcard.get(Card.KITCHEN)));
            binding.dining2.setImageResource(imageResource(pcard.get(Card.DINING_ROOM)));
            binding.living2.setImageResource(imageResource(pcard.get(Card.LIVING_ROOM)));
            
            if (piter.hasNext()) {
                p = piter.next();
                pcard = card.get(p);

                binding.name3.setText(p.name());
                binding.rope3.setImageResource(imageResource(pcard.get(Card.ROPE)));
                binding.candle3.setImageResource(imageResource(pcard.get(Card.CANDLESTICK)));
                binding.pipe3.setImageResource(imageResource(pcard.get(Card.LEAD_PIPE)));
                binding.pistol3.setImageResource(imageResource(pcard.get(Card.PISTOL)));
                binding.dagger3.setImageResource(imageResource(pcard.get(Card.DAGGER)));
                binding.wrench3.setImageResource(imageResource(pcard.get(Card.WRENCH)));
                binding.green3.setImageResource(imageResource(pcard.get(Card.GREEN)));
                binding.mustard3.setImageResource(imageResource(pcard.get(Card.MUSTARD)));
                binding.peacock3.setImageResource(imageResource(pcard.get(Card.PEACOCK)));
                binding.white3.setImageResource(imageResource(pcard.get(Card.WHITE)));
                binding.plum3.setImageResource(imageResource(pcard.get(Card.PLUM)));
                binding.scarlet3.setImageResource(imageResource(pcard.get(Card.SCARLET)));
                binding.courtyard3.setImageResource(imageResource(pcard.get(Card.COURTYARD)));
                binding.garage3.setImageResource(imageResource(pcard.get(Card.GARAGE)));
                binding.game3.setImageResource(imageResource(pcard.get(Card.GAME_ROOM)));
                binding.bedroom3.setImageResource(imageResource(pcard.get(Card.BEDROOM)));
                binding.bathroom3.setImageResource(imageResource(pcard.get(Card.BATHROOM)));
                binding.office3.setImageResource(imageResource(pcard.get(Card.OFFICE)));
                binding.kitchen3.setImageResource(imageResource(pcard.get(Card.KITCHEN)));
                binding.dining3.setImageResource(imageResource(pcard.get(Card.DINING_ROOM)));
                binding.living3.setImageResource(imageResource(pcard.get(Card.LIVING_ROOM)));

                if (piter.hasNext()) {
                    p = piter.next();
                    pcard = card.get(p);

                    binding.name4.setText(p.name());
                    binding.rope4.setImageResource(imageResource(pcard.get(Card.ROPE)));
                    binding.candle4.setImageResource(imageResource(pcard.get(Card.CANDLESTICK)));
                    binding.pipe4.setImageResource(imageResource(pcard.get(Card.LEAD_PIPE)));
                    binding.pistol4.setImageResource(imageResource(pcard.get(Card.PISTOL)));
                    binding.dagger4.setImageResource(imageResource(pcard.get(Card.DAGGER)));
                    binding.wrench4.setImageResource(imageResource(pcard.get(Card.WRENCH)));
                    binding.green4.setImageResource(imageResource(pcard.get(Card.GREEN)));
                    binding.mustard4.setImageResource(imageResource(pcard.get(Card.MUSTARD)));
                    binding.peacock4.setImageResource(imageResource(pcard.get(Card.PEACOCK)));
                    binding.white4.setImageResource(imageResource(pcard.get(Card.WHITE)));
                    binding.plum4.setImageResource(imageResource(pcard.get(Card.PLUM)));
                    binding.scarlet4.setImageResource(imageResource(pcard.get(Card.SCARLET)));
                    binding.courtyard4.setImageResource(imageResource(pcard.get(Card.COURTYARD)));
                    binding.garage4.setImageResource(imageResource(pcard.get(Card.GARAGE)));
                    binding.game4.setImageResource(imageResource(pcard.get(Card.GAME_ROOM)));
                    binding.bedroom4.setImageResource(imageResource(pcard.get(Card.BEDROOM)));
                    binding.bathroom4.setImageResource(imageResource(pcard.get(Card.BATHROOM)));
                    binding.office4.setImageResource(imageResource(pcard.get(Card.OFFICE)));
                    binding.kitchen4.setImageResource(imageResource(pcard.get(Card.KITCHEN)));
                    binding.dining4.setImageResource(imageResource(pcard.get(Card.DINING_ROOM)));
                    binding.living4.setImageResource(imageResource(pcard.get(Card.LIVING_ROOM)));

                    if (piter.hasNext()) {
                        p = piter.next();
                        pcard = card.get(p);

                        binding.name5.setText(p.name());
                        binding.rope5.setImageResource(imageResource(pcard.get(Card.ROPE)));
                        binding.candle5.setImageResource(imageResource(pcard.get(Card.CANDLESTICK)));
                        binding.pipe5.setImageResource(imageResource(pcard.get(Card.LEAD_PIPE)));
                        binding.pistol5.setImageResource(imageResource(pcard.get(Card.PISTOL)));
                        binding.dagger5.setImageResource(imageResource(pcard.get(Card.DAGGER)));
                        binding.wrench5.setImageResource(imageResource(pcard.get(Card.WRENCH)));
                        binding.green5.setImageResource(imageResource(pcard.get(Card.GREEN)));
                        binding.mustard5.setImageResource(imageResource(pcard.get(Card.MUSTARD)));
                        binding.peacock5.setImageResource(imageResource(pcard.get(Card.PEACOCK)));
                        binding.white5.setImageResource(imageResource(pcard.get(Card.WHITE)));
                        binding.plum5.setImageResource(imageResource(pcard.get(Card.PLUM)));
                        binding.scarlet5.setImageResource(imageResource(pcard.get(Card.SCARLET)));
                        binding.courtyard5.setImageResource(imageResource(pcard.get(Card.COURTYARD)));
                        binding.garage5.setImageResource(imageResource(pcard.get(Card.GARAGE)));
                        binding.game5.setImageResource(imageResource(pcard.get(Card.GAME_ROOM)));
                        binding.bedroom5.setImageResource(imageResource(pcard.get(Card.BEDROOM)));
                        binding.bathroom5.setImageResource(imageResource(pcard.get(Card.BATHROOM)));
                        binding.office5.setImageResource(imageResource(pcard.get(Card.OFFICE)));
                        binding.kitchen5.setImageResource(imageResource(pcard.get(Card.KITCHEN)));
                        binding.dining5.setImageResource(imageResource(pcard.get(Card.DINING_ROOM)));
                        binding.living5.setImageResource(imageResource(pcard.get(Card.LIVING_ROOM)));

                        if (piter.hasNext()) {
                            p = piter.next();
                            pcard = card.get(p);

                            binding.name6.setText(p.name());
                            binding.rope6.setImageResource(imageResource(pcard.get(Card.ROPE)));
                            binding.candle6.setImageResource(imageResource(pcard.get(Card.CANDLESTICK)));
                            binding.pipe6.setImageResource(imageResource(pcard.get(Card.LEAD_PIPE)));
                            binding.pistol6.setImageResource(imageResource(pcard.get(Card.PISTOL)));
                            binding.dagger6.setImageResource(imageResource(pcard.get(Card.DAGGER)));
                            binding.wrench6.setImageResource(imageResource(pcard.get(Card.WRENCH)));
                            binding.green6.setImageResource(imageResource(pcard.get(Card.GREEN)));
                            binding.mustard6.setImageResource(imageResource(pcard.get(Card.MUSTARD)));
                            binding.peacock6.setImageResource(imageResource(pcard.get(Card.PEACOCK)));
                            binding.white6.setImageResource(imageResource(pcard.get(Card.WHITE)));
                            binding.plum6.setImageResource(imageResource(pcard.get(Card.PLUM)));
                            binding.scarlet6.setImageResource(imageResource(pcard.get(Card.SCARLET)));
                            binding.courtyard6.setImageResource(imageResource(pcard.get(Card.COURTYARD)));
                            binding.garage6.setImageResource(imageResource(pcard.get(Card.GARAGE)));
                            binding.game6.setImageResource(imageResource(pcard.get(Card.GAME_ROOM)));
                            binding.bedroom6.setImageResource(imageResource(pcard.get(Card.BEDROOM)));
                            binding.bathroom6.setImageResource(imageResource(pcard.get(Card.BATHROOM)));
                            binding.office6.setImageResource(imageResource(pcard.get(Card.OFFICE)));
                            binding.kitchen6.setImageResource(imageResource(pcard.get(Card.KITCHEN)));
                            binding.dining6.setImageResource(imageResource(pcard.get(Card.DINING_ROOM)));
                            binding.living6.setImageResource(imageResource(pcard.get(Card.LIVING_ROOM)));
                        }
                    }
                }
            }
        }

        return root;
    }

    private int imageResource(Knowledge k) {
        return switch (k.t) {
            case HAS -> R.drawable.ic_scoreboard_has;
            case NO_HAS -> R.drawable.ic_scoreboard_no_has;
            case MIGHT_HAVE -> k == Knowledge.MIGHT_HAVE_DEFAULT ? R.drawable.ic_scoreboard_unknown : R.drawable.ic_scoreboard_might_have;
            case KNOWN -> R.drawable.ic_scoreboard_known;
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}