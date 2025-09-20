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

    /**
     * Initializes the children of this
     */
    private void ctor() {

    }
}
