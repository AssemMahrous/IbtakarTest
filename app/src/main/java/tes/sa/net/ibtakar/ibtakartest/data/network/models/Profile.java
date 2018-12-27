
package tes.sa.net.ibtakar.ibtakartest.data.network.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile implements Parcelable
{

    @SerializedName("iso_639_1")
    @Expose
    private Object iso6391;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("aspect_ratio")
    @Expose
    private Double aspectRatio;
    public final static Creator<Profile> CREATOR = new Creator<Profile>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        public Profile[] newArray(int size) {
            return (new Profile[size]);
        }

    }
    ;

    protected Profile(Parcel in) {
        this.iso6391 = ((Object) in.readValue((Object.class.getClassLoader())));
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.filePath = ((String) in.readValue((String.class.getClassLoader())));
        this.aspectRatio = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public Profile() {
    }

    public Object getIso6391() {
        return iso6391;
    }

    public void setIso6391(Object iso6391) {
        this.iso6391 = iso6391;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(iso6391);
        dest.writeValue(width);
        dest.writeValue(height);
        dest.writeValue(voteCount);
        dest.writeValue(voteAverage);
        dest.writeValue(filePath);
        dest.writeValue(aspectRatio);
    }

    public int describeContents() {
        return  0;
    }

}
