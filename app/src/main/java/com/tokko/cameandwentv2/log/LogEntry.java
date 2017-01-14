package com.tokko.cameandwentv2.log;

import com.tokko.cameandwentv2.project.Project;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import com.tokko.cameandwentv2.project.ProjectDao;

@Entity
public class LogEntry {
    @Id
    public long time;
    public boolean entered;
    private Long projectId;
    public String comment;

    @ToOne(joinProperty = "projectId")
    public Project project;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1375140308)
    private transient LogEntryDao myDao;
    @Generated(hash = 1005767482)
    private transient Long project__resolvedKey;

    @Keep
    public LogEntry(long time, boolean entered, Long projectId) {
        this.time = time;
        this.entered = entered;
        this.projectId = projectId;
    }

    @Generated(hash = 1393228716)
    public LogEntry() {
    }

    @Generated(hash = 761683243)
    public LogEntry(long time, boolean entered, Long projectId, String comment) {
        this.time = time;
        this.entered = entered;
        this.projectId = projectId;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1654636707)
    public Project getProject() {
        Long __key = this.projectId;
        if (project__resolvedKey == null || !project__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProjectDao targetDao = daoSession.getProjectDao();
            Project projectNew = targetDao.load(__key);
            synchronized (this) {
                project = projectNew;
                project__resolvedKey = __key;
            }
        }
        return project;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1434093065)
    public void setProject(Project project) {
        synchronized (this) {
            this.project = project;
            projectId = project == null ? null : project.getId();
            project__resolvedKey = projectId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1097455826)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLogEntryDao() : null;
    }
}
