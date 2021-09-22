package com.example.fileuploadspringboot.service;


import com.example.fileuploadspringboot.entites.FileDb;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface FileService {
    FileDb store(MultipartFile file) throws IOException;
    FileDb getFile(String id);
    Stream<FileDb> getAllFiles();
}
