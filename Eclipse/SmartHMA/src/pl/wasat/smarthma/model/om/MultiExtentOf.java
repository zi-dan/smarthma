package pl.wasat.smarthma.model.om;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import pl.wasat.smarthma.utils.text.SmartHMAStringStyle;

public class MultiExtentOf implements Serializable {

    private static final long serialVersionUID = 1L;

    private String _prefix;
    private MultiSurface multiSurface;


    public String get_prefix() {
        return _prefix;
    }

    public void set_prefix(String _prefix) {
        this._prefix = _prefix;
    }

    public MultiExtentOf with_prefix(String _prefix) {
        this._prefix = _prefix;
        return this;
    }

    public MultiSurface getMultiSurface() {
        return multiSurface;
    }

    public void setMultiSurface(MultiSurface multiSurface) {
        this.multiSurface = multiSurface;
    }

    public MultiExtentOf withMultiSurface(MultiSurface multiSurface) {
        this.multiSurface = multiSurface;
        return this;
    }

    @Override
    public String toString() {
        ToStringStyle style = new SmartHMAStringStyle();
        ToStringBuilder.setDefaultStyle(style);
        return ToStringBuilder.reflectionToString(this, style);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }


}