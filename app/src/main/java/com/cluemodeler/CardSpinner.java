package com.cluemodeler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import com.cluemodeler.model.Card;

public class CardSpinner extends Spinner {
    private Card value;
    private OnItemSelectedListener oisl;

    public CardSpinner(Context context) {
        super(context);
        ctor(context);
    }

    public CardSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctor(context);
    }

    public CardSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctor(context);
    }

    private void ctor(Context ctx) {
        ArrayAdapter<String> adp = new ArrayAdapter<>(ctx, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        for (Card c : Card.values()) adp.add(c.toString());
        this.setAdapter(adp);

        super.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                value = Card.toCard(adapterView.getSelectedItem().toString());
                if (oisl != null) oisl.onItemSelected(adapterView, view, i, l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (oisl != null) oisl.onNothingSelected(adapterView);
            }
        });
    }

    @Override
    public void setOnItemSelectedListener(@Nullable OnItemSelectedListener listener) {
        oisl = listener;
    }

    public Card getValue() {
        return value;
    }
}
