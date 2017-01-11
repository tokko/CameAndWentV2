package com.tokko.cameandwentv2.project;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Project {
    @Id(autoincrement = true)
    public Long id;
    public String title;
    public long xcoord;
    public long ycoord;

    @Keep
    public Project(String title, long xcoord, long ycoord) {
        this.title = title;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    @Generated(hash = 1767516619)
    public Project() {
    }

    @Generated(hash = 1088829048)
    public Project(Long id, String title, long xcoord, long ycoord) {
        this.id = id;
        this.title = title;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getXcoord() {
        return this.xcoord;
    }

    public void setXcoord(long xcoord) {
        this.xcoord = xcoord;
    }

    public long getYcoord() {
        return this.ycoord;
    }

    public void setYcoord(long ycoord) {
        this.ycoord = ycoord;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


}
