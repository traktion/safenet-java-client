package org.traktion0.safenet.client.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by paul on 16/08/16.
 */
@XmlRootElement
public class Dns {

    private String longName = "";
    private String serviceName = "'";
    private String rootPath = "";
    private String serviceHomeDirPath = "";

    public Dns() {
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getServiceHomeDirPath() {
        return serviceHomeDirPath;
    }

    public void setServiceHomeDirPath(String serviceHomeDirPath) {
        this.serviceHomeDirPath = serviceHomeDirPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dns)) return false;

        Dns dns = (Dns) o;

        if (getLongName() != null ? !getLongName().equals(dns.getLongName()) : dns.getLongName() != null) return false;
        if (getServiceName() != null ? !getServiceName().equals(dns.getServiceName()) : dns.getServiceName() != null)
            return false;
        if (getRootPath() != null ? !getRootPath().equals(dns.getRootPath()) : dns.getRootPath() != null) return false;
        return getServiceHomeDirPath() != null ? getServiceHomeDirPath().equals(dns.getServiceHomeDirPath()) : dns.getServiceHomeDirPath() == null;

    }

    @Override
    public int hashCode() {
        int result = getLongName() != null ? getLongName().hashCode() : 0;
        result = 31 * result + (getServiceName() != null ? getServiceName().hashCode() : 0);
        result = 31 * result + (getRootPath() != null ? getRootPath().hashCode() : 0);
        result = 31 * result + (getServiceHomeDirPath() != null ? getServiceHomeDirPath().hashCode() : 0);
        return result;
    }


}
