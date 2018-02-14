package com.emejia.list_topics.model;

/**
 * Created by emejia on 13/03/2017.
 */

public class ListItem {

    private String head;
    private String desc;
    private String url;
    private String image;

    public ListItem(String head, String desc,String url,String image) {
        this.head = head;
        this.desc = desc;
        this.url = url;
        this.image = image;
    }

    public ListItem() {}

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
