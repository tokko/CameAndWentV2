package com.tokko.cameandwentv2.project;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Project {
    @Id
    public int id;
    public String title;
    public long xcoord;
    public long ycoord;

    @Keep
    public Project(String title, long xcoord, long ycoord) {
        this.title = title;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    @Generated(hash = 1122814381)
    public Project(int id, String title, long xcoord, long ycoord) {
        this.id = id;
        this.title = title;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    @Generated(hash = 1767516619)
    public Project() {
    }

    @Override
    public String toString() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getXcoord() {
        return xcoord;
    }

    public long getYcoord() {
        return ycoord;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setXcoord(long xcoord) {
        this.xcoord = xcoord;
    }

    public void setYcoord(long ycoord) {
        this.ycoord = ycoord;
    }
}
