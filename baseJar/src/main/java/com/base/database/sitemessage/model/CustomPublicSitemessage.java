package com.base.database.sitemessage.model;

import java.util.Date;

public class CustomPublicSitemessage extends PublicSitemessage {
    public String fromName;
    public String toName;

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }
}