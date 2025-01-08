package com.cluemodeler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cluemodeler.databinding.ActivityLauncherBinding;

import java.util.List;
import java.util.ArrayList;

import com.cluemodeler.model.Card;
import com.cluemodeler.model.Strategy;

public class LauncherActivity extends AppCompatActivity {

    private ActivityLauncherBinding binding;

    private List<String> playerNames = new ArrayList<>(6);
    private List<Card> knownCards = new ArrayList<>(3);
    private List<Card> ownedCards = new ArrayList<>(6);
    private Strategy.T strategy = Strategy.T.BALANCED;

    public LauncherActivity() {
        // todo: remove preloader
        playerNames.add("Self");
        playerNames.add("Alice");
        playerNames.add("Bob");
        playerNames.add("Charles");
        playerNames.add("Dom");
        playerNames.add("Eric");

        ownedCards.add(Card.WEAPONS.get(0));
        ownedCards.add(Card.PEOPLE.get(0));
        ownedCards.add(Card.LOCATIONS.get(0));

        // todo: add check function to ensure that owned cards and players is reasonable
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        findViewById(R.id.launch_button).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // todo call check function to ensure owned cards and players is reasonable

                Intent intent = new Intent(LauncherActivity.this, ModelActivity.class);
                Bundle bundle = new Bundle(4);

                bundle.putStringArray(ModelActivity.ARG_TOKEN_PLAYER_NAMES_ARR, playerNames.toArray(new String[0]));
                bundle.putInt(ModelActivity.ARG_TOKEN_STRATEGY_INDEX, strategy.ordinal());
                int[] known = new int[knownCards.size()];
                for (int i = 0; i < known.length; i++) {
                    known[i] = knownCards.get(i).ordinal();
                }
                bundle.putIntArray(ModelActivity.ARG_TOKEN_CARD_INDICES_KNOWN, known);
                int[] owned = new int[ownedCards.size()];
                for (int i = 0; i < owned.length; i++) {
                    owned[i] = ownedCards.get(i).ordinal();
                }
                bundle.putIntArray(ModelActivity.ARG_TOKEN_CARD_INDICES_OWNED, owned);

                intent.putExtras(bundle);
                try {
                    startActivity(intent);
                } catch (Exception ignored) { }
            }
        });
    }
}
