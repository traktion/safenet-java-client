package org.traktion0.safenet.client.beans;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;

/**
 * Created by paul on 01/09/16.
 */
@XmlRootElement
public class SafenetFile {

    private InputStream inputStream;
    private FileInputStream fileInputStream;
    private String contentRange;
    private String acceptRanges;
    private long contentLength;
    private OffsetDateTime createdOn;
    private OffsetDateTime lastModified;
    private String contentType;
    private String metadata;

    public SafenetFile() {
    }

    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
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

        SafenetFile that = (SafenetFile) o;

        if (getContentLength() != that.getContentLength()) return false;
        if (getInputStream() != null ? !getInputStream().equals(that.getInputStream()) : that.getInputStream() != null)
            return false;
        if (getFileInputStream() != null ? !getFileInputStream().equals(that.getFileInputStream()) : that.getFileInputStream() != null)
            return false;
        if (getContentRange() != null ? !getContentRange().equals(that.getContentRange()) : that.getContentRange() != null)
            return false;
        if (getAcceptRanges() != null ? !getAcceptRanges().equals(that.getAcceptRanges()) : that.getAcceptRanges() != null)
            return false;
        if (getCreatedOn() != null ? !getCreatedOn().equals(that.getCreatedOn()) : that.getCreatedOn() != null)
            return false;
        if (getLastModified() != null ? !getLastModified().equals(that.getLastModified()) : that.getLastModified() != null)
            return false;
        if (getContentType() != null ? !getContentType().equals(that.getContentType()) : that.getContentType() != null)
            return false;
        return getMetadata() != null ? getMetadata().equals(that.getMetadata()) : that.getMetadata() == null;

    }

    @Override
    public int hashCode() {
        int result = getInputStream() != null ? getInputStream().hashCode() : 0;
        result = 31 * result + (getFileInputStream() != null ? getFileInputStream().hashCode() : 0);
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
                ", fileInputStream=" + fileInputStream +
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
