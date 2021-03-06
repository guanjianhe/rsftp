package org.tuzhao.ftp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * zhaotu
 * 17-8-7
 */
public final class ServerEntity implements Serializable, Parcelable {

    private static final long serialVersionUID = -7060210544600464481L;

    private int display;
    private String encoding;
    private String savePath;
    private String address;
    private String port;
    private String account;
    private String pwd;
    private int id;

    public ServerEntity() {
        display = 0;
        encoding = "";
        address = "";
        port = "";
        account = "";
        pwd = "";
        id = 0;
        savePath = "";
    }

    /**
     * whether to display hidden files<br>
     * 0 -> don't display<br>
     * 1 -> display
     */
    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void update(ServerEntity entity) {
        setDisplay(entity.getDisplay());
        setAddress(entity.getAddress());
        setPort(entity.getPort());
        setAccount(entity.getAccount());
        setPwd(entity.getPwd());
        setSavePath(entity.getSavePath());
        setEncoding(entity.getEncoding());
    }

    @Override
    public String toString() {
        return "ServerEntity{" +
                   "display='" + display + '\'' +
                   ", encoding='" + encoding + '\'' +
                   ", savePath='" + savePath + '\'' +
                   ", address='" + address + '\'' +
                   ", port='" + port + '\'' +
                   ", account='" + account + '\'' +
                   ", pwd='" + pwd + '\'' +
                   ", id=" + id +
                   "} hash: " + super.toString();
    }

    public ServerEntity(Parcel in) {
        display = in.readInt();
        encoding = in.readString();
        savePath = in.readString();
        address = in.readString();
        port = in.readString();
        account = in.readString();
        pwd = in.readString();
        id = in.readInt();
    }

    public static final Creator<ServerEntity> CREATOR = new Creator<ServerEntity>() {
        @Override
        public ServerEntity createFromParcel(Parcel in) {
            return new ServerEntity(in);
        }

        @Override
        public ServerEntity[] newArray(int size) {
            return new ServerEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(display);
        parcel.writeString(encoding);
        parcel.writeString(savePath);
        parcel.writeString(address);
        parcel.writeString(port);
        parcel.writeString(account);
        parcel.writeString(pwd);
        parcel.writeInt(id);
    }
}
