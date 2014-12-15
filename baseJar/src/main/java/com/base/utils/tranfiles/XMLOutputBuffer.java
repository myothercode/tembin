package com.base.utils.tranfiles;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2014/12/10.
 */
public class XMLOutputBuffer {
    private StringBuffer xmlOutput;
    private int itemCount;

    private Pattern markPattern;

    XMLOutputBuffer(String markStr) {
        this.markPattern = Pattern.compile(markStr);
        xmlOutput = new StringBuffer();
        itemCount = 0;
    }

    public void append(String str) {
        if (str == null || "".equals(str))
            return;
        this.xmlOutput.append(str);
        Matcher m = this.markPattern.matcher(str);
        while (m.find())
            this.itemCount++;
    }

    public void clearBuffer() {
        xmlOutput = new StringBuffer();
        this.itemCount = 0;
    }

    public StringBuffer getXmlOutput() {
        return xmlOutput;
    }

    public int getItemCount() {
        return itemCount;
    }
}


