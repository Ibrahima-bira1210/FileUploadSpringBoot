package com.example.fileuploadspringboot.service;

import com.example.fileuploadspringboot.entites.FileDb;
import com.example.fileuploadspringboot.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {
    private FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileDb store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDb FileDb = new FileDb(fileName,file.getContentType(),file.getBytes());
        return fileRepository.save(FileDb);
    }

    @Override
    public FileDb getFile(String id) {
        return fileRepository.findById(id).get();
    }

    @Override
    public Stream<FileDb> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}
