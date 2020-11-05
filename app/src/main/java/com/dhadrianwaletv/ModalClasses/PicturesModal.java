package com.dhadrianwaletv.ModalClasses;

import java.io.Serializable;

/**
 * Created by khushdeep-android on 15/2/18.
 */


public class PicturesModal implements Serializable {
    public String img_url;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }


    public PicturesModal(String img_url)
    {
        this.img_url = img_url;

    }
}
