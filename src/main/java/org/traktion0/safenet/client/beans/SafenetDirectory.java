package org.traktion0.safenet.client.beans;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by paul on 06/08/16.
 */
@XmlRootElement
public class SafenetDirectory {

    private Info info;
    private List<Info> files;
    private List<Info> subDirectories;

    public SafenetDirectory() {
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Info> getFiles() {
        return files;
    }

    public void setFiles(List<Info> files) {
        this.files = files;
    }

    public List<Info> getSubDirectories() {
        return subDirectories;
    }

    public void setSubDirectories(List<Info> subDirectories) {
        this.subDirectories = subDirectories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SafenetDirectory)) return false;

        SafenetDirectory safenetDirectory = (SafenetDirectory) o;

        if (getInfo() != null ? !getInfo().equals(safenetDirectory.getInfo()) : safenetDirectory.getInfo() != null) return false;
        if (getFiles() != null ? !getFiles().equals(safenetDirectory.getFiles()) : safenetDirectory.getFiles() != null) return false;
        return getSubDirectories() != null ? getSubDirectories().equals(safenetDirectory.getSubDirectories()) : safenetDirectory.getSubDirectories() == null;

    }

    @Override
    public int hashCode() {
        int result = getInfo() != null ? getInfo().hashCode() : 0;
        result = 31 * result + (getFiles() != null ? getFiles().hashCode() : 0);
        result = 31 * result + (getSubDirectories() != null ? getSubDirectories().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SafenetDirectory{" +
                "info=" + info +
                ", files=" + files +
                ", subDirectories=" + subDirectories +
                '}';
    }
}
