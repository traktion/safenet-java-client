package org.traktion0.safenet.client.beans;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by paul on 06/08/16.
 */
@XmlRootElement
public class Directory {

    private Info info;
    private List<Info> files;
    private List<Info> subDirectories;

    public Directory() {
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
        if (!(o instanceof Directory)) return false;

        Directory directory = (Directory) o;

        if (getInfo() != null ? !getInfo().equals(directory.getInfo()) : directory.getInfo() != null) return false;
        if (getFiles() != null ? !getFiles().equals(directory.getFiles()) : directory.getFiles() != null) return false;
        return getSubDirectories() != null ? getSubDirectories().equals(directory.getSubDirectories()) : directory.getSubDirectories() == null;

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
        return "Directory{" +
                "info=" + info +
                ", files=" + files +
                ", subDirectories=" + subDirectories +
                '}';
    }
}
