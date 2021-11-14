package com.example.demo.repository;

import com.example.demo.entity.file;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface fileRepo extends CrudRepository<file, Long> {
    file findByName(String name);

    file findFileById(Long id);

}
