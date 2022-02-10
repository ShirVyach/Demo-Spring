package com.example.demo.service;

import com.example.demo.entitiy.FileEntity;
import com.example.demo.exception.FileException;
import com.example.demo.model.FileModel;
import com.example.demo.repository.FileRepository;
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
    private FileRepository fileRepository;

    public FileEntity Upload(MultipartFile multipartFile) throws FileException, IOException {
        String filename = multipartFile.getOriginalFilename();
        String filetype = filename.substring(multipartFile.getOriginalFilename().lastIndexOf('.') + 1);
        if (fileRepository.findByName(filename) != null) {
            throw new FileException("Файл с таким именем уже существует");
        }
        if (
                filetype.equals("doc") ||
                        filetype.equals("docx") ||
                        filetype.equals("txt") ||
                        filetype.equals("xls") ||
                        filetype.equals("csv") ||
                        filetype.equals("pdf")) {
            throw new FileException("Неверный формат файла");
        }
        FileEntity FileEntity = new FileEntity();
        FileEntity.setName(multipartFile.getOriginalFilename());
        FileEntity.setData(multipartFile.getBytes());
        FileEntity.setFormat(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.') + 1));
        FileEntity.setSize(multipartFile.getSize());
        FileEntity.setDateload(new Date());

        return fileRepository.save(FileEntity);
    }

    public FileEntity getFile(Long id) {
        return fileRepository.findFileById(id);
    }

    public Iterable<FileModel> getAll() {
        List<FileModel> files = new ArrayList<>();
        for (FileEntity FileEntity :
                fileRepository.findAll()) {
            files.add(FileModel.toModel(FileEntity));
        }
        return files;
    }

    public Long delete(Long id) {
        fileRepository.deleteById(id);
        return id;
    }

    public void downloadfile(List<Long> id, HttpServletResponse response) throws IOException {
        if (id.size() > 1) {
            ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

            for (Long Id : id) {
                FileEntity FileEntity = getFile(Id);
                ZipEntry zipEntry = new ZipEntry(FileEntity.getName());
                zipEntry.setSize(FileEntity.getData().length);
                zipOut.putNextEntry(zipEntry);
                StreamUtils.copy(FileEntity.getData(), zipOut);
                zipOut.closeEntry();
            }
            zipOut.finish();
            zipOut.close();
            response.setStatus(HttpServletResponse.SC_OK);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "zipFileName" + "\"");
        }
        else {
            FileEntity FileEntity = getFile(id.get(0));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment ;filename=\"" + FileEntity.getName() + "\"");
            FileCopyUtils.copy(FileEntity.getData(), response.getOutputStream());
        }
    }

}
