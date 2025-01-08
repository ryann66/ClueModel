package com.cluemodeler.ui.guess;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cluemodeler.model.Strategy;

public class GuessViewModel extends ViewModel {
    private final MutableLiveData<Strategy> strategyLiveData = new MutableLiveData<>();

    public void setStrategy(Strategy strategy) {
        strategyLiveData.setValue(strategy);
    }

    public Strategy getStrategy() {
        return strategyLiveData.getValue();
    }

    public GuessViewModel() {

    }
}
