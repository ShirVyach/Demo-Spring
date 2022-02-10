package com.example.demo.controller;

import com.example.demo.exception.FileException;
import com.example.demo.service.fileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private fileService FileService;

    @PostMapping
    public ResponseEntity Upload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            FileService.Upload(multipartFile);
            return ResponseEntity.ok("Файл добавлен");
        } catch (FileException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }


    @GetMapping
    public ResponseEntity getfiles() {
        try {
            return ResponseEntity.ok(FileService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @GetMapping("/delete")
    public ResponseEntity deleteFile(@Param("id") Long id) {
        try {
            FileService.delete(id);
            return ResponseEntity.ok("Файл удалён");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

    @GetMapping("/downloadfile")
    public ResponseEntity downfile(@RequestParam ("id") List<Long> id, HttpServletResponse response) throws IOException {

        FileService.downloadfile(id, response);
        try {
            return ResponseEntity.ok("Скачивание завершено");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }
}
