package com.cluemodeler.ui.scoreboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cluemodeler.model.ImmutableScorecard;

public class ScoreboardViewModel extends ViewModel {
    private final MutableLiveData<ImmutableScorecard> scorecardLiveData = new MutableLiveData<>();

    public void setScorecard(ImmutableScorecard card) {
        scorecardLiveData.setValue(card);
    }

    public ImmutableScorecard getScorecard() {
        return scorecardLiveData.getValue();
    }

    public ScoreboardViewModel() {

    }
}