package com.example.fileuploadspringboot.repository;

import com.example.fileuploadspringboot.entites.FileDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDb,String> {
}
