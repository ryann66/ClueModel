package com.cluemodeler.ui.model;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.cluemodeler.R;
import com.cluemodeler.databinding.ActivityModelBinding;
import com.cluemodeler.model.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ModelActivity extends AppCompatActivity {

    public final static String ARG_TOKEN_PLAYER_NAMES_ARR = "players",
            ARG_TOKEN_CARD_INDICES_OWNED = "owned",
            ARG_TOKEN_CARD_INDICES_KNOWN = "known";

    public Model getModel() {
        return model;
    }

    public PlayerList getPlist() {
        return plist;
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

    public void setLasttoplay(Player p) {
        lasttoplay = p;
    }

    private Model model;
    private PlayerList plist;
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

        try {

            // validate args
            if (playerNames == null || ownedCards == null || knownCards == null)
                throw new IllegalArgumentException("Missing argument");
            if (playerNames.length < 3 || playerNames.length > 6)
                throw new IllegalArgumentException("Wrong player count");
            if ((Card.NUM_CARDS - 3) % playerNames.length != knownCards.length ||
                    (Card.NUM_CARDS - 3) / playerNames.length != ownedCards.length) {
                throw new IllegalArgumentException("Invalid number of cards");
            }

            // create player list
            Player[] parr = new Player[playerNames.length];
            for (int i = 0; i < parr.length; i++) parr[i] = new Player(playerNames[i], ownedCards.length);
            plist = new PlayerList(parr);
            lasttoplay = self = parr[0];

            // convert args from int[] to enum[]
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
            model = new BasicModel(plist, parr[0], known, owned);
        } catch (RuntimeException re) {
            AlertDialog.Builder dial = new AlertDialog.Builder(this);
            dial.setCancelable(true);
            dial.setTitle(getString(R.string.model_error));
            dial.setMessage(re.getMessage());
            dial.setIcon(R.drawable.dialog_error);
            dial.show();

            finish();
        }

        this.scorecard = model.getFullScorecard();

        ActivityModelBinding binding = ActivityModelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_scoreboard, R.id.navigation_import, R.id.navigation_questions)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // set back button callback
        this.getOnBackPressedDispatcher().addCallback(this, new BackCallback(binding.navView));
    }

    private class BackCallback extends OnBackPressedCallback {
        private final BottomNavigationView bar;

        final Toast toast = Toast.makeText(ModelActivity.this, R.string.toast_text, Toast.LENGTH_SHORT);
        final ToastCallBack tcb = new ToastCallBack();

        public BackCallback(BottomNavigationView bar) {
            super(true);
            this.bar = bar;
            toast.addCallback(tcb);
        }

        @Override
        public void handleOnBackPressed() {
            // if toodling about, go back to scoreboard
            if (bar.getSelectedItemId() != R.id.navigation_scoreboard) {
                bar.setSelectedItemId(R.id.navigation_scoreboard);
                return;
            }

            // check for double back tap
            if (!tcb.isShown()) {
                // show toast and return
                toast.show();
                return;
            }

            toast.cancel();

            finish();
        }
    }

    static class ToastCallBack extends Toast.Callback {
        private int shown = 0;

        public void onToastHidden() {
            shown--;
        }

        public void onToastShown() {
            shown++;
        }

        public boolean isShown() {
            return shown > 0;
        }
    }

}