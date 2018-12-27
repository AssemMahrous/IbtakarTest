
package tes.sa.net.ibtakar.ibtakartest.data.network.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileImage implements Parcelable
{

    @SerializedName("profiles")
    @Expose
    private List<Profile> profiles = null;
    public final static Creator<ProfileImage> CREATOR = new Creator<ProfileImage>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ProfileImage createFromParcel(Parcel in) {
            return new ProfileImage(in);
        }

        public ProfileImage[] newArray(int size) {
            return (new ProfileImage[size]);
        }

    }
    ;

    protected ProfileImage(Parcel in) {
        in.readList(this.profiles, (Profile.class.getClassLoader()));
    }

    public ProfileImage() {
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(profiles);
    }

    public int describeContents() {
        return  0;
    }

}
