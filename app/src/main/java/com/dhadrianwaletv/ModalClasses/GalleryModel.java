package com.dhadrianwaletv.ModalClasses;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public class GalleryModel {

    String title;
    String id;


    public GalleryModel(String name, String phone) {
        this.title = name;
        this.id = phone;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}



