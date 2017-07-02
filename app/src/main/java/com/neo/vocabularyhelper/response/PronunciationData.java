package com.neo.vocabularyhelper.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/7/1.
 */
public class PronunciationData {
    public final static String TAG_MACMILLAN = "macmillan";

    @SerializedName("commentCount")
    private String commentCount;
    @SerializedName("createdBy")
    private String createdBy;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private String id;
    @SerializedName("word")
    private String word;
    @SerializedName("duration")
    private String duration;
    @SerializedName("fileUrl")
    private String fileUrl;
    @SerializedName("audioType")
    private String audioType;
    @SerializedName("attributionText")
    private String attributionText;
    @SerializedName("attributionUrl")
    private String attributionUrl;

    public static List<PronunciationData> getGson(String response) {
        Gson gson = new Gson();
        List<PronunciationData> list = (List<PronunciationData>) gson.fromJson(response, PronunciationData.class);
        return list;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getAudioType() {
        return audioType;
    }

    public void setAudioType(String audioType) {
        this.audioType = audioType;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getAttributionUrl() {
        return attributionUrl;
    }

    public void setAttributionUrl(String attributionUrl) {
        this.attributionUrl = attributionUrl;
    }
}
