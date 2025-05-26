package com.example.learningapp;

import java.util.List;

public class HistoryItem {
    public String question, selected, correct;
    public List<String> options;

    public HistoryItem(String question, List<String> options, String selected, String correct) {
        this.question = question;
        this.options = options;
        this.selected = selected;
        this.correct = correct;
    }
}
