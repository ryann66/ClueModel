package com.cluemodeler.ui.questions;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cluemodeler.model.Model;

public class QuestionViewModel extends ViewModel {
    private final MutableLiveData<Model> modelLiveData = new MutableLiveData<>();

    public void setModel(Model card) {
        modelLiveData.setValue(card);
    }

    public Model getModel() {
        return modelLiveData.getValue();
    }
    public QuestionViewModel() {

    }
}
