package com.example.mymemolist;

public class Memo {
    private int memoID;
    private String memoData;
    private String memoPriority;
    private String memoTitle;

    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }

    public Memo(){
        memoID = -1;
    }

    public String getMemoPriority() {
        return memoPriority;
    }

    public void setMemoPriority(String memoPriority) {
        this.memoPriority = memoPriority;
    }

    public int getMemoID() {
        return memoID;
    }

    public void setMemoID(int memoID) {
        this.memoID = memoID;
    }

    public String getMemoData() {
        return memoData;
    }

    public void setMemoData(String memoData) {
        this.memoData = memoData;
    }
}
