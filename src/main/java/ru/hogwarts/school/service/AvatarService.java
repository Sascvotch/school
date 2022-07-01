package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("$(avatar.dir.path)")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.debug("metod uploadAvatar started");
        Student student = studentService.getStudentById(studentId).orElseThrow(() -> {
            logger.error("There is not student with id = " + studentId);
            return new StudentNotFoundException();
        });
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setPrewiew(avatarFile.getBytes());
        avatar.setPrewiew(generatePreview(filePath));
        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long studentId) {
        logger.debug("metod findAvatar started");
        return avatarRepository.findByStudentId(studentId).orElseThrow(() -> {
            logger.error("There is not avatar for student with id = " + studentId);
           return new AvatarNotFoundException();
        });
    }

    public List<Avatar> findAll(Integer pageNumber, Integer pageSize) {
        logger.debug("metod findAll started");
        List<Avatar> avatars = new ArrayList<Avatar>();
        if (pageNumber > 0) {
            PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
            avatars = avatarRepository.findAll(pageRequest).getContent();
        }
        return avatars;
    }

    private byte[] generatePreview(Path filePath) throws IOException {
        logger.debug("metod generatePreview started");
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage prewiew = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = prewiew.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();
            ImageIO.write(prewiew, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtensions(String fileName) {
        logger.debug("metod getExtensions started");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
