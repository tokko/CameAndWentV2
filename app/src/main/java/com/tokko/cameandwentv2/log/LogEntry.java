package com.tokko.cameandwentv2.log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LogEntry {
    @Id
    public long time;
    public boolean entered;
    private Long projectId;

    @Keep
    public LogEntry(long time, boolean entered, Long projectId) {
        this.time = time;
        this.entered = entered;
        this.projectId = projectId;
    }

    @Generated(hash = 1393228716)
    public LogEntry() {
    }

    public long getTime() {
        return time;
    }

    public boolean isEntered() {
        return entered;
    }

    public Long getDate(){
        return time - (time % (1000 * 60 * 60 * 24));
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean getEntered() {
        return this.entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
