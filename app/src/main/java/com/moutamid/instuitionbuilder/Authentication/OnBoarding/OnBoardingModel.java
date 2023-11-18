package com.moutamid.instuitionbuilder.Authentication.OnBoarding;

public class OnBoardingModel {
    String text, textMain;
    int vector, page_no;

    public OnBoardingModel(String text, String textMain, int vector, int page_no) {
        this.text = text;
        this.textMain = textMain;
        this.vector = vector;
        this.page_no = page_no;
    }

    public OnBoardingModel(String text, String textMain, int vector) {
        this.text = text;
        this.textMain = textMain;
        this.vector = vector;
    }


    public String getText() {
        return text;
    }

    public String getTextMain() {
        return textMain;
    }

    public int getVector() {
        return vector;
    }

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }
}
