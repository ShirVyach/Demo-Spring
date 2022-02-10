package com.example.demo.repository;

import com.example.demo.entitiy.FileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<FileEntity, Long> {
    FileEntity findByName(String name);

    FileEntity findFileById(Long id);

}
