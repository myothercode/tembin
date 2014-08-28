package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 在线商品活动圈
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("TicketListingDetails")
public class TicketListingDetails {
    /**
     * 活动标题
     */
    private String EventTitle;
    /**
     * 活动日期
     */
    private String PrintedDate;
    /**
     * 活动时间
     */
    private String PrintedTime;
    /**
     * 活动地点
     */
    private String Venue;

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }

    public String getPrintedDate() {
        return PrintedDate;
    }

    public void setPrintedDate(String printedDate) {
        PrintedDate = printedDate;
    }

    public String getPrintedTime() {
        return PrintedTime;
    }

    public void setPrintedTime(String printedTime) {
        PrintedTime = printedTime;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }
}
