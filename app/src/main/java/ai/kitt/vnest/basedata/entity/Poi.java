package ai.kitt.vnest.basedata.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Poi implements Parcelable {
    // private String is_promoted;
    private String img;
    // private String img_big;
    private String address;
    // private String count_reviews;
    // private String facebook;
    // private String rating;
    // private String description;
    private Gps gps;
    private String title;
    // private String[] photos;
    private String url;
    // private String no_image;
    private String phone;
    private String category;
    private String brand;
    private String hash;
    private String email;
    private double distance;

    protected Poi(Parcel in) {
        img = in.readString();
        address = in.readString();
        title = in.readString();
        url = in.readString();
        phone = in.readString();
        category = in.readString();
        brand = in.readString();
        hash = in.readString();
        email = in.readString();
        distance = in.readDouble();
    }

    public static final Creator<Poi> CREATOR = new Creator<Poi>() {
        @Override
        public Poi createFromParcel(Parcel in) {
            return new Poi(in);
        }

        @Override
        public Poi[] newArray(int size) {
            return new Poi[size];
        }
    };

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(address);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(phone);
        dest.writeString(category);
        dest.writeString(brand);
        dest.writeString(hash);
        dest.writeString(email);
        dest.writeDouble(distance);
    }
}
