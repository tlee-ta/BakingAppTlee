package com.example.android.bakingapptlee.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {
    @SerializedName("id")
    private int stepId;
    @SerializedName("shortDescription")
    private String stepShortDescription;
    @SerializedName("description")
    private String stepDescription;
    @SerializedName("videoURL")
    private String stepVideoURL;
    @SerializedName("thumbnailURL")
    private String stepThumbnailURL;

    public Step(int stepId, String stepShortDescription, String stepDescription, String stepVideoURL, String stepThumbnailURL) {
        this.stepId = stepId;
        this.stepShortDescription = stepShortDescription;
        this.stepDescription = stepDescription;
        this.stepVideoURL = stepVideoURL;
        this.stepThumbnailURL = stepThumbnailURL;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getStepShortDescription() {
        return stepShortDescription;
    }

    public void setStepShortDescription(String stepShortDescription) {
        this.stepShortDescription = stepShortDescription;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public String getStepVideoURL() {
        return stepVideoURL;
    }

    public void setStepVideoURL(String stepVideoURL) {
        this.stepVideoURL = stepVideoURL;
    }

    public String getStepThumbnailURL() {
        return stepThumbnailURL;
    }

    public void setStepThumbnailURL(String stepThumbnailURL) {
        this.stepThumbnailURL = stepThumbnailURL;
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel parcel) {
            return new Step(parcel);
        }

        @Override
        public Step[] newArray(int i) {
            return new Step[i];
        }
    };

    protected Step(Parcel parcel) {
        stepId = parcel.readInt();
        stepShortDescription = parcel.readString();
        stepDescription = parcel.readString();
        stepVideoURL = parcel.readString();
        stepThumbnailURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(stepId);
        parcel.writeString(stepShortDescription);
        parcel.writeString(stepDescription);
        parcel.writeString(stepVideoURL);
        parcel.writeString(stepThumbnailURL);
    }
}
