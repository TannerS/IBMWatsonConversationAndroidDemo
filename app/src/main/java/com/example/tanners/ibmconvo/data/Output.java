package com.example.tanners.ibmconvo.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Output {
    private ArrayList<String> text;
    private ArrayList<String> nodes_visited;
    private ArrayList<String> log_messages;

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }

    public ArrayList<String> getNodes_visited() {
        return nodes_visited;
    }

    public void setNodes_visited(ArrayList<String> nodes_visited) {
        this.nodes_visited = nodes_visited;
    }

    public ArrayList<String> getLog_messages() {
        return log_messages;
    }

    public void setLog_messages(ArrayList<String> log_messages) {
        this.log_messages = log_messages;
    }
}
