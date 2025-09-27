package com.cluemodeler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.cluemodeler.databinding.ActivityLauncherBinding;

import java.util.List;
import java.util.ArrayList;

import com.cluemodeler.model.Card;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LauncherActivity extends AppCompatActivity {

    private ActivityLauncherBinding binding;

    private int nplayers = 3;
    private int ncommon = (Card.NUM_CARDS - 3) % nplayers;
    private int nowned = (Card.NUM_CARDS - 3) / nplayers;

    private TextView[] tvs = new TextView[6];
    private Button[] psbtns = new Button[6];
    private TableRow[] tbrs = new TableRow[6];

    private Spinner[] known = new Spinner[6];
    private Spinner[] owned = new Spinner[3];

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

        known[0] = findViewById(R.id.card_selector);
        known[1] = findViewById(R.id.card_selector2);
        known[2] = findViewById(R.id.card_selector3);
        known[3] = findViewById(R.id.card_selector4);
        known[4] = findViewById(R.id.card_selector5);
        known[5] = findViewById(R.id.card_selector6);

        owned[0] = findViewById(R.id.card_selector7);
        owned[1] = findViewById(R.id.card_selector8);
        owned[2] = findViewById(R.id.card_selector9);

        addPlayer = findViewById(R.id.add_player);
        commonLabel = findViewById(R.id.known_cards);

        for (int i = 0; i < 6; i++) {
            psbtns[i].setOnClickListener(new SubtractPlayerListener(i));
        }
        findViewById(R.id.launch_button).setOnClickListener(new StartGameListener());
        findViewById(R.id.add_player).setOnClickListener(new AddPlayerListener());

        // todo set spinner contents

        // todo modify spinner adapter onclick to not have any cards already selected in it

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

        // todo ensure that no card is used in common and/or known twice (uniqueness)
    }

    class StartGameListener implements View.OnClickListener {
        public void onClick(View view) {
            List<Card> knownCards = new ArrayList<>(ncommon);
            List<Card> ownedCards = new ArrayList<>(nowned);
            List<String> playerNames = new ArrayList<>(nplayers);

            // todo read fields to populate lists

            // todo call check function to ensure owned cards and players is reasonable

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
            // todo move all text below this one up one
            // todo reset the text contents of the last edittext
            nplayers--;
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
}
