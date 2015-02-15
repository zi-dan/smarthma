package pl.wasat.smarthma.model.iso;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import pl.wasat.smarthma.utils.text.SmartHMAStringStyle;

public class IdentificationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private MDDataIdentification MDDataIdentification;
    private String Prefix;


    /**
     * @return The MDDataIdentification
     */
    public MDDataIdentification getMDDataIdentification() {
        return MDDataIdentification;
    }

    /**
     * @param MDDataIdentification The MD_DataIdentification
     */
    public void setMDDataIdentification(
            MDDataIdentification MDDataIdentification) {
        this.MDDataIdentification = MDDataIdentification;
    }

    /**
     * @return The Prefix
     */
    public String getPrefix() {
        return Prefix;
    }

    /**
     * @param Prefix The _prefix
     */
    public void setPrefix(String Prefix) {
        this.Prefix = Prefix;
    }

    @Override
    public String toString() {
        ToStringStyle style = new SmartHMAStringStyle();
        ToStringBuilder.setDefaultStyle(style);
        return ToStringBuilder.reflectionToString(this, style);
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(MDDataIdentification)
                .append(Prefix).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof IdentificationInfo)) {
            return false;
        }
        IdentificationInfo rhs = ((IdentificationInfo) other);
        return new EqualsBuilder()
                .append(MDDataIdentification, rhs.MDDataIdentification)
                .append(Prefix, rhs.Prefix)

                .isEquals();
    }

}
