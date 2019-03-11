package com.example.mymemolist;

public class Memo {
    private int memoID;
    private String memoData;
    private String memoPriority;

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
