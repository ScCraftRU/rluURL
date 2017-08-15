package ru.sccraft.urlshortner;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by alexandr on 21.01.17.
 */

public class Link implements Parcelable {
    public String longU; //Длинная
    public String shortU; //Короткая

    public Link(String ДЛИННАЯ_ССЫЛКА, String КОРОТКАЯ_ССЫЛКА){
        this.longU = ДЛИННАЯ_ССЫЛКА;
        this.shortU = КОРОТКАЯ_ССЫЛКА;
    }

    protected Link(Parcel in) {
        longU = in.readString();
        shortU = in.readString();
    }

    public static final Creator<Link> CREATOR = new Creator<Link>() {
        @Override
        public Link createFromParcel(Parcel in) {
            return new Link(in);
        }

        @Override
        public Link[] newArray(int size) {
            return new Link[size];
        }
    };

    public static Link fromJSON(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Link link = gson.fromJson(jsonString, Link.class);
        return link;
    }

    public String toJSON() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(longU);
        dest.writeString(shortU);
    }
}
