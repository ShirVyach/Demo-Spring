package com.example.demo.model;

import com.example.demo.entitiy.FileEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
@Getter
@Setter
public class FileModel {
    private Long id;
    private String name;
    private String format;
    private Long size;
    private Date dateload;
    private String url;

    final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

    public static FileModel toModel (FileEntity entity) {
        FileModel model = new FileModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setFormat(entity.getFormat());
        model.setSize(entity.getSize());
        model.setDateload(entity.getDateload());
        model.setUrl(model.baseUrl+"/files/downloadfile?id="+entity.getId());
        return model;
    }

    public FileModel() {}
}
