package com.tokko.cameandwentv2.log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LogEntry {
    @Id
    public long time;
    public boolean entered;

    public LogEntry() {
        super();
    }

    @Keep
    public LogEntry(long time, boolean entered) {
        this.time = time;
        this.entered = entered;
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
}
