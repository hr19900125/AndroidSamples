package com.hr.toy.router;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Subject implements Parcelable {

    public static final String SUBJECT = "extra_subject";

    @SerializedName("name")
    private String mName;

    private int mId;

    @SerializedName("fragmentClass")
    private String mFragmentClass;

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

    public String getFragmentClass() {
        return mFragmentClass;
    }

    public void setFragmentClass(String mFragmentClass) {
        this.mFragmentClass = mFragmentClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeInt(this.mId);
        dest.writeString(this.mFragmentClass);
        dest.writeList(this.mPages);
    }

    public Subject() {
    }

    protected Subject(Parcel in) {
        this.mName = in.readString();
        this.mId = in.readInt();
        this.mFragmentClass = in.readString();
        this.mPages = new ArrayList<Page>();
        in.readList(this.mPages, Page.class.getClassLoader());
    }

    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel source) {
            return new Subject(source);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
}
