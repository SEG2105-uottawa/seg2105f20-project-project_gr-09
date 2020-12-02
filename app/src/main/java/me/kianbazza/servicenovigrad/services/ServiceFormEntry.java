package me.kianbazza.servicenovigrad.services;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class ServiceFormEntry<K, V> implements Map.Entry<K, V>, Parcelable {

    private K key;
    private V value;

    // Empty constructor
    public ServiceFormEntry() {

    }

    public ServiceFormEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    protected ServiceFormEntry(Parcel in) {
        key = (K) in.readString();
        value = (V) in.readString();


    }

    public static final Creator<ServiceFormEntry<String, String>> CREATOR = new Creator<ServiceFormEntry<String, String>>() {
        @Override
        public ServiceFormEntry<String, String> createFromParcel(Parcel in) {
            return new ServiceFormEntry<>(in);
        }

        @Override
        public ServiceFormEntry<String, String>[] newArray(int size) {
            return new ServiceFormEntry[size];
        }
    };

    @Override
    public K getKey() {
        return key;
    }

    public K setKey(K key) {
        K old = this.key;
        this.key = key;
        return old;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString((String) key);
        dest.writeString((String) value);
    }
}
