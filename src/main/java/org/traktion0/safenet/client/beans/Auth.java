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
    private String token = "";

    public Auth() {
        this(new App(), new String[]{}, "");
    }

    public Auth(App app) {
        this(app, new String[]{}, "");
    }

    public Auth(App app, String[] permissions) {
        this(app, permissions, "");
    }

    public Auth(App app, String[] permissions, String token) {
        this.app = app;
        this.permissions = permissions;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auth)) return false;

        Auth auth = (Auth) o;

        if (getApp() != null ? !getApp().equals(auth.getApp()) : auth.getApp() != null) return false;
        if (getToken() != null ? !getToken().equals(auth.getToken()) : auth.getToken() != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(getPermissions(), auth.getPermissions());

    }

    @Override
    public int hashCode() {
        int result = getApp() != null ? getApp().hashCode() : 0;
        result = 31 * result + (getToken() != null ? getToken().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getPermissions());
        return result;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "app=" + app +
                ", token='" + token + '\'' +
                ", permissions=" + Arrays.toString(permissions) +
                '}';
    }
}
