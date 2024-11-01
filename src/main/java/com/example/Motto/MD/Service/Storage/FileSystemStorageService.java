package com.example.Motto.MD.Service.Storage;

import com.example.Motto.MD.Exceptions.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path IMAGE_DIR = Paths.get("Uploads");

    FileSystemStorageService(){
        init();
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(IMAGE_DIR);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file, String fileName) {
        if(file.isEmpty()){
            throw new StorageException("File is empty");
        }
        try(InputStream inputStream = file.getInputStream()){
            Path filePath = Paths.get(IMAGE_DIR.toString(), fileName + dumbFileFormatCheck(file.getOriginalFilename()));
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new StorageException("Error while storing file", e);
        }
    }

    @Override
    public Stream<Path> getAllFiles() {
        return Stream.empty();
    }

    @Override
    public Path loadFile(String fileName) {
        return IMAGE_DIR.resolve(fileName);
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(IMAGE_DIR.resolve(fileName));
        } catch (IOException e) {
            throw new StorageException("Error while deleting file", e);
        }
    }

    public String dumbFileFormatCheck(String fileName){
        String[] supportedFormats = {".jpg", ".bmp"};
        for(String format : supportedFormats){
            if(fileName.endsWith(format)){
                return format;
            }
        }
        throw new StorageException("Unsupported file format");
    }
}
