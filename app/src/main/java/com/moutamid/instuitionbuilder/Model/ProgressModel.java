package com.moutamid.instuitionbuilder.Model;

import java.util.List;

public class ProgressModel {

    String progress;

    String key;
    private List<Integer> numbers;

    public String getProgress() {
        return progress;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
