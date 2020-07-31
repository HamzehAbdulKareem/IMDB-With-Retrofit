package com.example.retrofitwithimdb;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductionCompaniesClass implements Parcelable {
    int id;
    Object logo_path;
    String name;
    String origin_country;

    protected ProductionCompaniesClass(Parcel in) {
        id = in.readInt();
        name = in.readString();
        origin_country = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(origin_country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductionCompaniesClass> CREATOR = new Creator<ProductionCompaniesClass>() {
        @Override
        public ProductionCompaniesClass createFromParcel(Parcel in) {
            return new ProductionCompaniesClass(in);
        }

        @Override
        public ProductionCompaniesClass[] newArray(int size) {
            return new ProductionCompaniesClass[size];
        }
    };
}
