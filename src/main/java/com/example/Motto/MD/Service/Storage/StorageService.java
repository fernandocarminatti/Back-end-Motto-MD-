package com.example.Motto.MD.Service.Storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String storeFile(MultipartFile file, String fileName);

    Stream<Path> getAllFiles();

    Path loadFile(String fileName);

    void deleteFile(String fileName);

}
