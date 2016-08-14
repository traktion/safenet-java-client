package org.traktion0.safenet.client.beans;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by paul on 24/07/16.
 */
@XmlRootElement
public class Token implements Serializable {

    private String token = "";

    public Token() {
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
        if (!(o instanceof Token)) return false;

        Token that = (Token) o;

        return getToken() != null ? getToken().equals(that.getToken()) : that.getToken() == null;

    }

    @Override
    public int hashCode() {
        return getToken() != null ? getToken().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                '}';
    }
}
