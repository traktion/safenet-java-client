package org.traktion0.safenet.client.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by paul on 06/08/16.
 */
@XmlRootElement
public class Info {

    private String name = "";
    private boolean isPrivate = false;
    private boolean isVersioned = false;
    private long createdOn = 0;
    private long modifiedOn = 0;
    private String metadata = "";
    private String userMetadata = "";

    public Info() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isVersioned() {
        return isVersioned;
    }

    public void setVersioned(boolean versioned) {
        isVersioned = versioned;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(long modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(String userMetadata) {
        this.userMetadata = userMetadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Info)) return false;

        Info info = (Info) o;

        if (isPrivate() != info.isPrivate()) return false;
        if (isVersioned() != info.isVersioned()) return false;
        if (getCreatedOn() != info.getCreatedOn()) return false;
        if (getModifiedOn() != info.getModifiedOn()) return false;
        if (getName() != null ? !getName().equals(info.getName()) : info.getName() != null) return false;
        if (getMetadata() != null ? !getMetadata().equals(info.getMetadata()) : info.getMetadata() != null)
            return false;
        return getUserMetadata() != null ? getUserMetadata().equals(info.getUserMetadata()) : info.getUserMetadata() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (isPrivate() ? 1 : 0);
        result = 31 * result + (isVersioned() ? 1 : 0);
        result = 31 * result + (int) (getCreatedOn() ^ (getCreatedOn() >>> 32));
        result = 31 * result + (int) (getModifiedOn() ^ (getModifiedOn() >>> 32));
        result = 31 * result + (getMetadata() != null ? getMetadata().hashCode() : 0);
        result = 31 * result + (getUserMetadata() != null ? getUserMetadata().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Info{" +
                "name='" + name + '\'' +
                ", isPrivate=" + isPrivate +
                ", isVersioned=" + isVersioned +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", metadata='" + metadata + '\'' +
                ", userMetadata='" + userMetadata + '\'' +
                '}';
    }
}
