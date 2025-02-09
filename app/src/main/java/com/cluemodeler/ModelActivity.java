package com.cluemodeler;

import android.os.Bundle;

import com.cluemodeler.model.BasicModel;
import com.cluemodeler.model.Card;
import com.cluemodeler.model.ImmutableScorecard;
import com.cluemodeler.model.Model;

import com.cluemodeler.model.Player;
import com.cluemodeler.model.PlayerList;
import com.cluemodeler.model.Strategy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cluemodeler.databinding.ActivityModelBinding;

public class ModelActivity extends AppCompatActivity {

    public final static String ARG_TOKEN_PLAYER_NAMES_ARR = "players",
            ARG_TOKEN_CARD_INDICES_OWNED = "owned",
            ARG_TOKEN_CARD_INDICES_KNOWN = "known",
            ARG_TOKEN_STRATEGY_INDEX = "strategy";

    private ActivityModelBinding binding;

    public Model getModel() {
        return model;
    }

    public PlayerList getPlist() {
        return plist;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public ImmutableScorecard getScorecard() {
        return scorecard;
    }

    public Player getSelf() {
        return self;
    }

    public Player getLasttoplay() {
        return lasttoplay;
    }

    private Model model;
    private PlayerList plist;
    private Strategy strategy;
    private ImmutableScorecard scorecard;
    private Player self;
    private Player lasttoplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fetch args
        Bundle args = getIntent().getExtras();
        String[] playerNames = args.getStringArray(ARG_TOKEN_PLAYER_NAMES_ARR);
        int[] ownedCards = args.getIntArray(ARG_TOKEN_CARD_INDICES_OWNED);
        int[] knownCards = args.getIntArray(ARG_TOKEN_CARD_INDICES_KNOWN);
        int strategyIndex = args.getInt(ARG_TOKEN_STRATEGY_INDEX);

        // validate args
        if (playerNames == null || ownedCards == null || knownCards == null)
            throw new IllegalArgumentException("Missing argument");
        if (playerNames.length < 3 || playerNames.length > 6)
            throw new IllegalArgumentException("Wrong player count");
        if ((Card.NUM_CARDS - 3) % playerNames.length != knownCards.length ||
                (Card.NUM_CARDS - 3) / playerNames.length != ownedCards.length) {
            throw new IllegalArgumentException("Invalid number of cards");
        }

        // convert args from int[] to enum[]
        Player[] parr = new Player[playerNames.length];
        for (int i = 0; i < parr.length; i++) parr[i] = new Player(playerNames[i], ownedCards.length);
        PlayerList plist = new PlayerList(parr);
        Card[] cards = Card.values();
        Card[] known = new Card[knownCards.length];
        for (int i = 0; i < known.length; i++) {
            known[i] = cards[knownCards[i]];
        }
        Card[] owned = new Card[ownedCards.length];
        for (int i = 0; i < owned.length; i++) {
            owned[i] = cards[ownedCards[i]];
        }

        // build model
        Model model = new BasicModel(plist, parr[0], known, owned);
        ImmutableScorecard card = model.getFullScorecard();
        Strategy strategy = Strategy.buildStrategy(Strategy.T.values()[strategyIndex], card, plist, parr[0]);

        this.scorecard = card;
        this.model = model;
        this.strategy = strategy;
        this.plist = plist;

        binding = ActivityModelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_scoreboard, R.id.navigation_import, R.id.navigation_questions, R.id.navigation_guess)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}