package pl.wasat.smarthma.model.iso;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import pl.wasat.smarthma.utils.text.SmartHMAStringStyle;

public class MDRestrictionCode implements Serializable {

    private static final long serialVersionUID = 1L;

    private String CodeList;
    private String CodeListValue;
    private String Prefix;
    private String Text;
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The CodeList
     */
    public String getCodeList() {
        return CodeList;
    }

    /**
     * @param CodeList The _codeList
     */
    public void setCodeList(String CodeList) {
        this.CodeList = CodeList;
    }

    /**
     * @return The CodeListValue
     */
    public String getCodeListValue() {
        return CodeListValue;
    }

    /**
     * @param CodeListValue The _codeListValue
     */
    public void setCodeListValue(String CodeListValue) {
        this.CodeListValue = CodeListValue;
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

    /**
     * @return The Text
     */
    public String getText() {
        return Text;
    }

    /**
     * @param Text The _text
     */
    public void setText(String Text) {
        this.Text = Text;
    }

    @Override
    public String toString() {
        ToStringStyle style = new SmartHMAStringStyle();
        ToStringBuilder.setDefaultStyle(style);
        return ToStringBuilder.reflectionToString(this, style);
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(CodeList).append(CodeListValue)
                .append(Prefix).append(Text).append(additionalProperties)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MDRestrictionCode)) {
            return false;
        }
        MDRestrictionCode rhs = ((MDRestrictionCode) other);
        return new EqualsBuilder().append(CodeList, rhs.CodeList)
                .append(CodeListValue, rhs.CodeListValue)
                .append(Prefix, rhs.Prefix).append(Text, rhs.Text)
                .append(additionalProperties, rhs.additionalProperties)
                .isEquals();
    }

}
