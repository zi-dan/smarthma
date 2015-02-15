package pl.wasat.smarthma.model.iso;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import pl.wasat.smarthma.utils.text.SmartHMAStringStyle;

public class CITelephone implements Serializable {

    private static final long serialVersionUID = 1L;

    private Voice voice;
    private Facsimile facsimile;
    private String Prefix;


    /**
     * @return The voice
     */
    public Voice getVoice() {
        return voice;
    }

    /**
     * @param voice The voice
     */
    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    /**
     * @return The facsimile
     */
    public Facsimile getFacsimile() {
        return facsimile;
    }

    /**
     * @param facsimile The facsimile
     */
    public void setFacsimile(Facsimile facsimile) {
        this.facsimile = facsimile;
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
        return new HashCodeBuilder().append(voice).append(facsimile)
                .append(Prefix).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CITelephone)) {
            return false;
        }
        CITelephone rhs = ((CITelephone) other);
        return new EqualsBuilder().append(voice, rhs.voice)
                .append(facsimile, rhs.facsimile).append(Prefix, rhs.Prefix)

                .isEquals();
    }

}
