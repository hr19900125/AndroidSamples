package com.hr.toy.router;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Page implements Parcelable {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("pageClass")
    private String mPageClass;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getPageClass() {
        return mPageClass;
    }

    public void setPageClass(String pageClass) {
        this.mPageClass = pageClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mPageClass);
    }

    public Page() {
    }

    protected Page(Parcel in) {
        this.mTitle = in.readString();
        this.mPageClass = in.readString();
    }

    public static final Parcelable.Creator<Page> CREATOR = new Parcelable.Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel source) {
            return new Page(source);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };
}
