package com.example.demo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "demo",name = "files")
public class file {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String format;
    private Long size;
    private byte[] data;
    private Date dateload;

    public file() {
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getDateload() {
        return dateload;
    }

    public void setDateload(Date dateload) {
        this.dateload = dateload;
    }
}