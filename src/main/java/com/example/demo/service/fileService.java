package com.example.demo.service;

import com.example.demo.entity.file;
import com.example.demo.exception.fileException;
import com.example.demo.model.fileModel;
import com.example.demo.repository.fileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    public void downloadfile (List<Long> id, HttpServletResponse response) throws IOException {
        if(id.size()>1) {
            ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

            for (Long Id : id) {
                file file = getFile(Id);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipEntry.setSize(file.getData().length);
                zipOut.putNextEntry(zipEntry);
                StreamUtils.copy(file.getData(), zipOut);
                zipOut.closeEntry();
            }
            zipOut.finish();
            zipOut.close();
            response.setStatus(HttpServletResponse.SC_OK);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "zipFileName" + "\"");
        }
        else {
            file file = getFile(id.get(0));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment ;filename=\"" + file.getName() + "\"");
            FileCopyUtils.copy(file.getData(), response.getOutputStream());
        }
    }

}
