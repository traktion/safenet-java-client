package org.traktion0.safenet.client.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by paul on 24/07/16.
 */
@XmlRootElement
public class App {

    private String name;
    private String id;
    private String version;
    private String vendor;

    public App() {
    }

    public App(String name, String id, String version, String vendor) {
        this.name = name;
        this.id = id;
        this.version = version;
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof App)) return false;

        App appBean = (App) o;

        if (getName() != null ? !getName().equals(appBean.getName()) : appBean.getName() != null) return false;
        if (getId() != null ? !getId().equals(appBean.getId()) : appBean.getId() != null) return false;
        if (getVersion() != null ? !getVersion().equals(appBean.getVersion()) : appBean.getVersion() != null)
            return false;
        return getVendor() != null ? getVendor().equals(appBean.getVendor()) : appBean.getVendor() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getVersion() != null ? getVersion().hashCode() : 0);
        result = 31 * result + (getVendor() != null ? getVendor().hashCode() : 0);
        return result;
    }
}
