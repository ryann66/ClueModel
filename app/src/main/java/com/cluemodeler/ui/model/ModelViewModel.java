package com.cluemodeler.ui.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cluemodeler.model.ImmutableScorecard;
import com.cluemodeler.model.Model;
import com.cluemodeler.model.Player;
import com.cluemodeler.model.PlayerList;

public class ModelViewModel extends ViewModel {
    MutableLiveData<Model> model = new MutableLiveData<>();

    MutableLiveData<PlayerList> plist = new MutableLiveData<>();

    MutableLiveData<ImmutableScorecard> scorecard = new MutableLiveData<>();

    MutableLiveData<Player> self = new MutableLiveData<>();

    MutableLiveData<Player> lasttoplay = new MutableLiveData<>();

    boolean isInitialized() {
        return model.isInitialized() && plist.isInitialized() && scorecard.isInitialized() && self.isInitialized() && lasttoplay.isInitialized();
    }
}