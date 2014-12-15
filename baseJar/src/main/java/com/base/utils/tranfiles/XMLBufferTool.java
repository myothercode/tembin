package com.base.utils.tranfiles;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2014/12/10.
 */
public class XMLBufferTool {
    private static final int defaultLineCount = 10;
    private static final int defaultMaxOutputSize = 50;

    private static final Pattern elementPattern = Pattern.compile("<[a-zA-Z]+>");
    private static final Pattern charSetPattern = Pattern.compile("<[?][[0-9a-zA-Z]|[\\s]|[=]|[\"]|[.]|[-]]+[?]>");

    private StringBuffer xmlContentBuffer;
    /* just used to store and output the data divided */
    XMLOutputBuffer xmlOutput;

    private String charSetTitle = "";

    private String rootElemetMark = "";

    private String childElementMark = "";


    InputStreamReader bufferedReader;
    InputStream fileInputStream;

public XMLBufferTool(){}
    public XMLBufferTool(String xmlFilePath) {

        this.xmlContentBuffer = new StringBuffer();

        try {

            this.fileInputStream = new FileInputStream(xmlFilePath);
//             bufferedReader = new InputStreamReader(fileInputStream, "UTF-8");
            String charSet = getCharSet(xmlFilePath);
            if (charSet != null)
                bufferedReader = new InputStreamReader(fileInputStream, charSet);
            else
                bufferedReader = new InputStreamReader(fileInputStream);
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        try {
            preparePaser();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    public String getCharSetTitle() {
        return charSetTitle;
    }

    public String getRootElemetMark() {
        return rootElemetMark;
    }

    private String getCharSet(String filePath) throws IOException {
        char temp[] = new char[512];
        FileInputStream tempInput = new FileInputStream(filePath);
        InputStreamReader tempReader = new InputStreamReader(tempInput);

        int i = tempReader.read(temp);

        tempReader.close();
        tempInput.close();
        if (i < 0)
            return null;

        String tempStr = new String(temp);
        Matcher m = charSetPattern.matcher(tempStr);
        if (m.find()) {
            String charSetStr = tempStr.substring(m.start(), m.end());
            Pattern tempP = Pattern.compile("[\"][[0-9a-zA-Z]|[-]]+[\"]");
            Matcher tempM = tempP.matcher(charSetStr);
            if (tempM.find()) {
                String charSet = charSetStr.substring(tempM.start(), tempM.end());
                return charSet.substring(1, charSet.length() - 1);
            }
        }

        return null;
    }


    private void preparePaser() throws IOException {
        readSomeLine(defaultLineCount);
        Matcher m = charSetPattern.matcher(xmlContentBuffer);
        if (m.find()) {
            this.charSetTitle = this.xmlContentBuffer.substring(m.start(), m.end());
            this.xmlContentBuffer.delete(0, m.end());
        }

        m = elementPattern.matcher(xmlContentBuffer);
        if (m.find()) {
            this.rootElemetMark = this.xmlContentBuffer.substring(m.start(), m.end());
            this.xmlContentBuffer.delete(0, m.end());
        }

        m = elementPattern.matcher(xmlContentBuffer);
        if (m.find()) {
            this.childElementMark = this.xmlContentBuffer.substring(m.start(), m.end());
        }
        this.xmlOutput = new XMLOutputBuffer(this.childElementMark);

        parserBuffer();
    }


    private int readSomeLine(int lineCount) throws IOException {

        char buffer[] = new char[1024];
        int i = 0;
        int index = 0;
        /* be careful of the sequence of the boolean caculation */
        while (i++ < lineCount && (index = this.bufferedReader.read(buffer)) > 0) {
            xmlContentBuffer.append(buffer, 0, index);
        }

        return index;

    }


    private void parserBuffer() {

        int lastIndex = this.xmlContentBuffer.lastIndexOf(this.childElementMark);

        if (lastIndex > 0) {
            this.xmlOutput.append(this.xmlContentBuffer.substring(0, lastIndex));
            this.xmlContentBuffer.delete(0, lastIndex);
        }
    }

    public StringBuffer popDividedDataAfterParser() throws IOException {

        while (this.xmlOutput.getItemCount() < defaultMaxOutputSize) {
            int i = readSomeLine(defaultLineCount);
            parserBuffer();
            if (i < 0)
                break;
        }

        if (this.xmlOutput.getItemCount() == 0)
            return null;

        StringBuffer returnSB = this.xmlOutput.getXmlOutput();
        this.xmlOutput.clearBuffer();
        return returnSB.insert(0, this.rootElemetMark).append(this.rootElemetMark.replaceFirst("<", "</"));

    }
}