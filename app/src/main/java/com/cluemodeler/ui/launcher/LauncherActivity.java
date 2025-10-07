package com.cluemodeler.ui.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cluemodeler.ui.model.CardSpinner;
import com.cluemodeler.ui.model.ModelActivity;
import com.cluemodeler.R;
import com.cluemodeler.databinding.ActivityLauncherBinding;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.cluemodeler.model.Card;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LauncherActivity extends AppCompatActivity {

    private ActivityLauncherBinding binding;

    private int nplayers = 3;
    private int ncommon = (Card.NUM_CARDS - 3) % nplayers;
    private int nowned = (Card.NUM_CARDS - 3) / nplayers;

    private EditText[] tvs = new EditText[6];
    private ImageButton[] psbtns = new ImageButton[6];
    private TableRow[] tbrs = new TableRow[6];

    private CardSpinner[] known = new CardSpinner[3];
    private CardSpinner[] owned = new CardSpinner[6];

    private Button addPlayer;
    private TextView commonLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tvs[0] = findViewById(R.id.namebox);
        tvs[1] = findViewById(R.id.namebox2);
        tvs[2] = findViewById(R.id.namebox3);
        tvs[3] = findViewById(R.id.namebox4);
        tvs[4] = findViewById(R.id.namebox5);
        tvs[5] = findViewById(R.id.namebox6);

        psbtns[0] = findViewById(R.id.minus);
        psbtns[1] = findViewById(R.id.minus2);
        psbtns[2] = findViewById(R.id.minus3);
        psbtns[3] = findViewById(R.id.minus4);
        psbtns[4] = findViewById(R.id.minus5);
        psbtns[5] = findViewById(R.id.minus6);

        tbrs[0] = findViewById(R.id.row);
        tbrs[1] = findViewById(R.id.row2);
        tbrs[2] = findViewById(R.id.row3);
        tbrs[3] = findViewById(R.id.row4);
        tbrs[4] = findViewById(R.id.row5);
        tbrs[5] = findViewById(R.id.row6);

        owned[0] = findViewById(R.id.card_selector);
        owned[1] = findViewById(R.id.card_selector2);
        owned[2] = findViewById(R.id.card_selector3);
        owned[3] = findViewById(R.id.card_selector4);
        owned[4] = findViewById(R.id.card_selector5);
        owned[5] = findViewById(R.id.card_selector6);

        known[0] = findViewById(R.id.card_selector7);
        known[1] = findViewById(R.id.card_selector8);
        known[2] = findViewById(R.id.card_selector9);

        addPlayer = findViewById(R.id.add_player);
        commonLabel = findViewById(R.id.known_cards);

        for (int i = 0; i < 6; i++) {
            psbtns[i].setOnClickListener(new SubtractPlayerListener(i));
        }
        findViewById(R.id.launch_button).setOnClickListener(new StartGameListener());
        findViewById(R.id.add_player).setOnClickListener(new AddPlayerListener());

        setComponentStates();
    }

    private void setComponentStates() {
        ncommon = (Card.NUM_CARDS - 3) % nplayers;
        nowned = (Card.NUM_CARDS - 3) / nplayers;

        for (int i = 0; i < nplayers; i++) tbrs[i].setVisibility(VISIBLE);
        for (int i = nplayers; i < 6; i++) tbrs[i].setVisibility(GONE);

        {
            boolean enable = (nplayers > 3);
            for (int i = 0; i < 6; i++) psbtns[i].setEnabled(enable);
            addPlayer.setEnabled(nplayers < 6);
        }

        for (int i = 0; i < nowned; i++) owned[i].setVisibility(VISIBLE);
        for (int i = nowned; i < 6; i++) owned[i].setVisibility(GONE);

        for (int i = 0; i < ncommon; i++) known[i].setVisibility(VISIBLE);
        for (int i = ncommon; i < 3; i++) known[i].setVisibility(GONE);

        commonLabel.setVisibility((ncommon == 0) ? GONE : VISIBLE);
    }

    class StartGameListener implements View.OnClickListener {
        public void onClick(View view) {
            List<Card> knownCards = new ArrayList<>(ncommon);
            List<Card> ownedCards = new ArrayList<>(nowned);
            List<String> playerNames = new ArrayList<>(nplayers);

            Set<String> play = new HashSet<>(nplayers);
            for (int i = 0; i < nplayers; i++) {
                String name = tvs[i].getText().toString().trim();
                if (name.isBlank()) {
                    showError(getString(R.string.empty_name));
                    return;
                }
                if (!play.add(name)) {
                    // error out: duplicate name
                    showError(getString(R.string.double_name) + name);
                    return;
                }
                if (!validName(name)) {
                    // error out: bad name
                    showError(getString(R.string.invalid_name) + name);
                    return;
                }
                playerNames.add(name);
            }

            Set<Card> inplay = new HashSet<>(ncommon + nowned);
            for (int i = 0; i < ncommon; i++) {
                Card c = known[i].getValue();
                if (!inplay.add(c)) {
                    // error out: card in play twice
                    showError(getString(R.string.double_card) + c.toString());
                    return;
                }
                knownCards.add(c);
            }
            for (int i = 0; i < nowned; i++) {
                Card c = owned[i].getValue();
                if (!inplay.add(c)) {
                    // error out: card in play twice
                    showError(getString(R.string.double_card) + c.toString());
                    return;
                }
                ownedCards.add(c);
            }

            // check for all of a category being in play at the start of the game
            int wep = 0, pep = 0, loc = 0;
            for (Card c : inplay) {
                switch (c.type) {
                    case WEAPON -> wep++;
                    case PERSON -> pep++;
                    case LOCATION -> loc++;
                }
            }
            if (wep >= Card.WEAPONS.size()) {
                showError(getString(R.string.all_weapons));
                return;
            }
            if (pep >= Card.PEOPLE.size()) {
                showError(getString(R.string.all_people));
                return;
            }
            if (loc >= Card.LOCATIONS.size()) {
                showError(getString(R.string.all_locations));
                return;
            }

            Intent intent = new Intent(LauncherActivity.this, ModelActivity.class);
            Bundle bundle = new Bundle(4);

            bundle.putStringArray(ModelActivity.ARG_TOKEN_PLAYER_NAMES_ARR, playerNames.toArray(new String[0]));
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
    }

    class SubtractPlayerListener implements View.OnClickListener {
        private final int idx;

        SubtractPlayerListener(int idx) {
            this.idx = idx;
        }

        @Override
        public void onClick(View view) {
            for (int i = idx; i < nplayers - 1; i++) {
                tvs[i].setText(tvs[i+1].getText());
            }
            nplayers--;
            tvs[nplayers].setText("");

            setComponentStates();
        }
    }

    class AddPlayerListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            nplayers++;
            setComponentStates();
        }
    }

    private static boolean validName(String name) {
        return name.length() <= 20;
    }

    private void showError(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.dialog_error);
        builder.setTitle(R.string.invalid_error);
        builder.setMessage(error);
        builder.show();
    }
}
