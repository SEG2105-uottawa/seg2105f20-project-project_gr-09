package me.kianbazza.servicenovigrad.services;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceDocument implements Parcelable {

    private String name;
    private String linkToUpload;

    public ServiceDocument() {

    }

    public ServiceDocument(String name, String linkToDocument) {
        this.name = name;
        this.linkToUpload = linkToDocument;
    }

    protected ServiceDocument(Parcel in) {
        name = in.readString();
        linkToUpload = in.readString();
    }

    public static final Creator<ServiceDocument> CREATOR = new Creator<ServiceDocument>() {
        @Override
        public ServiceDocument createFromParcel(Parcel in) {
            return new ServiceDocument(in);
        }

        @Override
        public ServiceDocument[] newArray(int size) {
            return new ServiceDocument[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getLinkToUpload() {
        return linkToUpload;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(linkToUpload);
    }
}
