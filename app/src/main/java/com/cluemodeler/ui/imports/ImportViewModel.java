package com.cluemodeler.ui.imports;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cluemodeler.model.Model;

public class ImportViewModel extends ViewModel {
    private final MutableLiveData<Model> modelLiveData = new MutableLiveData<>();

    public void setModel(Model card) {
        modelLiveData.setValue(card);
    }

    public Model getModel() {
        return modelLiveData.getValue();
    }
    public ImportViewModel() {

    }
}
