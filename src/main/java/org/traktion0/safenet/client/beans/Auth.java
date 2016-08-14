package org.traktion0.safenet.client.beans;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

/**
 * Created by paul on 24/07/16.
 */
@XmlRootElement
public class Auth {

    private App app;
    private String[] permissions;

    public Auth() {
    }

    public Auth(App app, String[] permissions) {
        this.app = app;
        this.permissions = permissions;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auth)) return false;

        Auth authBean = (Auth) o;

        if (getApp() != null ? !getApp().equals(authBean.getApp()) : authBean.getApp() != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(getPermissions(), authBean.getPermissions());

    }

    @Override
    public int hashCode() {
        int result = getApp() != null ? getApp().hashCode() : 0;
        result = 31 * result + Arrays.hashCode(getPermissions());
        return result;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "app=" + app +
                ", permissions=" + Arrays.toString(permissions) +
                '}';
    }
}
