package org.traktion0.safenet.client.beans;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStream;
import java.time.OffsetDateTime;

/**
 * Created by paul on 01/09/16.
 */
@XmlRootElement
public class SafenetFile {

    private InputStream inputStream;
    private String contentRange;
    private String acceptRanges;
    private long contentLength;
    private OffsetDateTime createdOn;
    private OffsetDateTime lastModified;
    private String contentType;
    private String metadata;

    public SafenetFile() {
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getContentRange() {
        return contentRange;
    }

    public void setContentRange(String contentRange) {
        this.contentRange = contentRange;
    }

    public String getAcceptRanges() {
        return acceptRanges;
    }

    public void setAcceptRanges(String acceptRanges) {
        this.acceptRanges = acceptRanges;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public OffsetDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(OffsetDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SafenetFile)) return false;

        SafenetFile safenetFile = (SafenetFile) o;

        if (getContentLength() != safenetFile.getContentLength()) return false;
        if (getInputStream() != null ? !getInputStream().equals(safenetFile.getInputStream()) : safenetFile.getInputStream() != null)
            return false;
        if (getContentRange() != null ? !getContentRange().equals(safenetFile.getContentRange()) : safenetFile.getContentRange() != null)
            return false;
        if (getAcceptRanges() != null ? !getAcceptRanges().equals(safenetFile.getAcceptRanges()) : safenetFile.getAcceptRanges() != null)
            return false;
        if (getCreatedOn() != null ? !getCreatedOn().equals(safenetFile.getCreatedOn()) : safenetFile.getCreatedOn() != null)
            return false;
        if (getLastModified() != null ? !getLastModified().equals(safenetFile.getLastModified()) : safenetFile.getLastModified() != null)
            return false;
        if (getContentType() != null ? !getContentType().equals(safenetFile.getContentType()) : safenetFile.getContentType() != null)
            return false;
        return getMetadata() != null ? getMetadata().equals(safenetFile.getMetadata()) : safenetFile.getMetadata() == null;

    }

    @Override
    public int hashCode() {
        int result = getInputStream() != null ? getInputStream().hashCode() : 0;
        result = 31 * result + (getContentRange() != null ? getContentRange().hashCode() : 0);
        result = 31 * result + (getAcceptRanges() != null ? getAcceptRanges().hashCode() : 0);
        result = 31 * result + (int) (getContentLength() ^ (getContentLength() >>> 32));
        result = 31 * result + (getCreatedOn() != null ? getCreatedOn().hashCode() : 0);
        result = 31 * result + (getLastModified() != null ? getLastModified().hashCode() : 0);
        result = 31 * result + (getContentType() != null ? getContentType().hashCode() : 0);
        result = 31 * result + (getMetadata() != null ? getMetadata().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SafenetFile{" +
                "inputStream=" + inputStream +
                ", contentRange='" + contentRange + '\'' +
                ", acceptRanges='" + acceptRanges + '\'' +
                ", contentLength=" + contentLength +
                ", createdOn=" + createdOn +
                ", lastModified=" + lastModified +
                ", contentType='" + contentType + '\'' +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
