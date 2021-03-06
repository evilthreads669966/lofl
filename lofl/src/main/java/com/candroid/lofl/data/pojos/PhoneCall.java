package com.candroid.lofl.data.pojos;

public class PhoneCall {
    public String mType;
    public String mAddress;
    public String mDate;
    public String mDuration;

    public PhoneCall(String type, String address, String date, String duration){
        mType = type;
        mAddress = address;
        mDate = date;
        mDuration = duration;
    }

    @Override
    public String toString() {
        return String.format("PhoneCall[type=%s, address=%s, date=%s, duration=%s]",mType, mAddress, mDate, mDuration);
    }
}
