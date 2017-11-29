package com.example.tanners.ibmconvo.data;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class WatsonResponse
{
    private ArrayList<Intent> intents;
    private ArrayList<Entity> entities;
    private Input input;
    private Output output;
    private Context context;

    public ArrayList<Intent> getIntents() {
        return intents;
    }

    public void setIntents(ArrayList<Intent> intents) {
        this.intents = intents;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
