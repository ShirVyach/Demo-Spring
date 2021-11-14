package com.example.demo.service;

import com.example.demo.entity.file;
import com.example.demo.exception.fileException;
import com.example.demo.model.fileModel;
import com.example.demo.repository.fileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class fileService {

    @Autowired
    private fileRepo FileRepos;

    public file Upload(MultipartFile multipartFile) throws fileException, IOException {
        String filename = multipartFile.getOriginalFilename();
        String filetype = filename.substring(multipartFile.getOriginalFilename().lastIndexOf('.')+1);
        if(FileRepos.findByName(filename) != null) {
            throw new fileException("Файл с таким именем уже существует");
        }
        if(
                filetype.equals("doc") ||
                filetype.equals("docx") ||
                filetype.equals("txt") ||
                filetype.equals("xls") ||
                filetype.equals("csv") ||
                filetype.equals("pdf"))
            {
                throw new fileException("Неверный формат файла");
            }
        file File = new file();
        File.setName(multipartFile.getOriginalFilename());
        File.setData(multipartFile.getBytes());
        File.setFormat(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.')+1));
        File.setSize(multipartFile.getSize());
        File.setDateload(new Date());

        return FileRepos.save(File);
    }

    public file getFile(Long id) {
        return FileRepos.findFileById(id);
    }

    public Iterable<fileModel> getAll(){
        List<fileModel> files = new ArrayList<>();
        for (file File:
                FileRepos.findAll() ) {
            files.add(fileModel.toModel(File));
        }
        return files;
    }

    public Long delete(Long id){
        FileRepos.deleteById(id);
        return id;
    }

}
