package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 上架一口价商品
 * Created by cz on 2014/7/18.
 */
@XStreamAlias("AddFixedPriceItemRequest")
public class AddFixedPriceItemRequest {

    @XStreamAsAttribute
    private String xmlns;

    private RequesterCredentials RequesterCredentials;

    private String ErrorLanguage;

    private String MessageID;

    private String Version;

    private String WarningLevel;

    private Item Item;

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

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }
}
