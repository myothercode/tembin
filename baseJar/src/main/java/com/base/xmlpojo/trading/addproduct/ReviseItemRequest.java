package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by Administrtor on 2014/8/21.
 */
@XStreamAlias("ReviseItemRequest")
public class ReviseItemRequest {
    @XStreamAsAttribute
    private String xmlns;

    private RequesterCredentials RequesterCredentials;

    private String ErrorLanguage;

    private String MessageID;

    private String Version;

    private String WarningLevel;

    private Item Item;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public RequesterCredentials getRequesterCredentials() {
        return RequesterCredentials;
    }

    public void setRequesterCredentials(RequesterCredentials requesterCredentials) {
        RequesterCredentials = requesterCredentials;
    }

    public String getErrorLanguage() {
        return ErrorLanguage;
    }

    public void setErrorLanguage(String errorLanguage) {
        ErrorLanguage = errorLanguage;
    }

    public String getMessageID() {
        return MessageID;
    }

    public void setMessageID(String messageID) {
        MessageID = messageID;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getWarningLevel() {
        return WarningLevel;
    }

    public void setWarningLevel(String warningLevel) {
        WarningLevel = warningLevel;
    }

    public Item getItem() {
        return Item;
    }

    public void setItem(Item item) {
        Item = item;
    }
}
