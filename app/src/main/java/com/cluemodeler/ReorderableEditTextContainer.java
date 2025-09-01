package com.cluemodeler;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ReorderableEditTextContainer extends ConstraintLayout {
    public ReorderableEditTextContainer(Context context) {
        super(context);
    }

    public ReorderableEditTextContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReorderableEditTextContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ReorderableEditTextContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean ch, int l, int t, int r, int b) {
        // args: box to layout inside of

        // TODO: call layout() on children with specified bounds



        // maybe use a constraint layout with unsatisfiable constraints?
        // might allow for smoother animation
        // onDragEvent()
    }
}
