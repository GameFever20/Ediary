package utils;

/**
 * Created by gamef on 02-04-2017.
 */

public class NoticeDetail {

    String noticeMessage;
    String Date;

    public NoticeDetail() {
    }

    public String getNoticeMessage() {

        return noticeMessage;
    }

    public void setNoticeMessage(String noticeMessage) {
        this.noticeMessage = noticeMessage;
    }

    @Override
    public String toString() {
        return "NoticeDetail{" +
                "noticeMessage='" + noticeMessage + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
