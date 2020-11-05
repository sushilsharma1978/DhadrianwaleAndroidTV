package com.dhadrianwaletv.ModalClasses;

/**
 * Created by khushdeep-android on 15/2/18.
 */

public class YoutubeModal {
    public String img_url,title,video_id;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public YoutubeModal(String title, String img_url, String video_id)
    {
        this.img_url = img_url;
        this.title = title;
        this.video_id = video_id;
    }
}
