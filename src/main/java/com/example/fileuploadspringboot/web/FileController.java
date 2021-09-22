package com.example.fileuploadspringboot.web;

import com.example.fileuploadspringboot.entites.FileDb;
import com.example.fileuploadspringboot.message.ResponseFile;
import com.example.fileuploadspringboot.message.ResponseMessage;
import com.example.fileuploadspringboot.service.FileServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/")
public class FileController {
    private FileServiceImpl fileService;

    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file){
        String message = "";
        try {
            fileService.store(file);
            message = "Upload the file successfully " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not Upload the file " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = fileService.getAllFiles().map(fileDb -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files")
                    .path(fileDb.getId())
                    .toUriString();
            return new ResponseFile(
                    fileDb.getName(),
                    fileDownloadUri,
                    fileDb.getType(),
                    fileDb.getData().length,
                    fileDb.getId());
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id){
        FileDb fileDb = fileService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +  fileDb.getName() + "\"")
                .body(fileDb.getData());
    }
}
