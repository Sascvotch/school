package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping
public class AvatarController {
private final StudentService studentService;
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService, StudentService studentService) {
        this.avatarService = avatarService;
        this.studentService=studentService;
    }
    @PostMapping(value = "/{id}/avatar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatarFile) throws IOException {
        if (avatarFile.getSize()>1024*300){
            return  ResponseEntity.badRequest().body("File is to big");
        }
        avatarService.uploadAvatar(id,avatarFile);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getPrewiew().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getPrewiew());
    }
    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }
}
