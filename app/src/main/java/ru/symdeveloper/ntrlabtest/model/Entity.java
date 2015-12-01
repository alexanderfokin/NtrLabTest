package ru.symdeveloper.ntrlabtest.model;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ru.symdeveloper.ntrlabtest.R;

/**
 * Created by Alexander Fokin on 01.12.2015.
 */
public class Entity {
    //поля, с которыми будем работать
    private String name;
    private String type;
    private String date_opened;
    private Address address;
    private String phone;

    //поля, с которыми сейчас не будем работать
    private String id;
    private String date_closed;
    private String status;
    private ArrayList<String> products;

    public String getName() {
        return name;
    }
    public @Nullable String getType() { return type; }

    public @Nullable String getDateOpened() {
        if (!TextUtils.isEmpty(date_opened)) {
            Date parsedDate = null;
            try {
                parsedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date_opened);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (parsedDate != null) {
                return new SimpleDateFormat("HH:MM | d MMMM yyyy").format(parsedDate);
            }
            return "";
        }
        return date_opened; }
    public @Nullable String getPhone() { return phone; }
    public @Nullable String getAddress() {
        if (address != null) {
            return address.toString();
        } else {
            return "";
        }
    }

    public static class Address {
        private String additional_information;
        private String zip;
        private String country;
        private String region;
        private String area;
        private String city;
        private String town;
        private String street;
        private String house;

        @SerializedName("case")
        private String case_number;

        private String apartment;
        private double gps_lat;
        private double gps_lng;

        public String toString() {
            return "";
        }
    }

    public ArrayList<SimpleListItem> fillListData(Resources resources) {
        ArrayList<SimpleListItem> items = new ArrayList<>();
        if (!TextUtils.isEmpty(name)) {
            items.add(new SimpleListItem(resources.getString(R.string.fieldName), name));
        }
        if (!TextUtils.isEmpty(phone)) {
            items.add(new SimpleListItem(resources.getString(R.string.fieldPhone), phone));
        }
        String addressValue = getAddress();
        if (!TextUtils.isEmpty(addressValue)) {
            items.add(new SimpleListItem(resources.getString(R.string.fieldAddress), addressValue));
        }
        String dateOpenedValue = getDateOpened();
        if (!TextUtils.isEmpty(dateOpenedValue)) {
            items.add(new SimpleListItem(resources.getString(R.string.fieldDateOpened), dateOpenedValue));
        }
        if (!TextUtils.isEmpty(type)) {
            items.add(new SimpleListItem(resources.getString(R.string.fieldType), type));
        }
        return items;
    }
}
