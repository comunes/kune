package org.ourproject.massmob.client.ui;

public class AssistanceTitleSumData {
    public int yes;
    public int no;
    public int maybe;
    public int noyet;
    public int total;

    public AssistanceTitleSumData() {
        resetImpl();
    }

    public void reset() {
        resetImpl();
    }

    private void resetImpl() {
        yes = 0;
        no = 0;
        maybe = 0;
        noyet = 0;
        total = 0;
    }
}
