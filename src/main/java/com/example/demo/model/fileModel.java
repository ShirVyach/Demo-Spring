package com.example.demo.model;

import com.example.demo.entity.file;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

public class fileModel {
    private Long id;
    private String name;
    private String format;
    private Long size;
    private Date dateload;
    private String url;

    final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

    public static fileModel toModel (file entity) {
        fileModel model = new fileModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setFormat(entity.getFormat());
        model.setSize(entity.getSize());
        model.setDateload(entity.getDateload());
        model.setUrl(model.baseUrl+"/files/downloadfile?id="+entity.getId());
        return model;
    }

    public fileModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getDateload() {
        return dateload;
    }

    public void setDateload(Date dateload) {
        this.dateload = dateload;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
