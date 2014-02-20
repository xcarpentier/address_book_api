package com.contact;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

    private String text;
    private String more;

    /**
     * @return the more
     */
    public String getMore() {
        return more;
    }

    /**
     * @param more the more to set
     */
    public void setMore(String more) {
        this.more = more;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}
