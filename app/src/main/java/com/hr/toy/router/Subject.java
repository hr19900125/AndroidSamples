package com.hr.toy.router;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Subject {

    @SerializedName("name")
    private String mName;

    private int mId;

    @SerializedName("pages")
    private List<Page> mPages;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public List<Page> getPages() {
        return mPages;
    }

    public void setPages(List<Page> mPages) {
        this.mPages = mPages;
    }
}
