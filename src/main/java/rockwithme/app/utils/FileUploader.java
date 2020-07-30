package rockwithme.app.utils;

import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileUploader {
    public static final String UPLOAD_DIR = "uploads";
    public static void handleMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        long size = file.getSize();
//        log.info("File: " + name + ", Size: " + size);
        try {
            File currentDir = new File(UPLOAD_DIR);
            if (!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String path = currentDir.getAbsolutePath() + "/" + file.getOriginalFilename();
            path = new File(path).getAbsolutePath();
//            log.info(path);
            File f = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String checkFile(MultipartFile file) {
        if (file != null && !file.isEmpty() && file.getOriginalFilename().length() > 0) {
            if (!Pattern.matches(".+\\.(jpg|png)", file.getOriginalFilename())) {
                return "The photo must be in 'jpg' or 'png' format!";
            }
        }
        return null;
    }

    public static boolean deleteFile(String imgUrl) {
        File file = new File(UPLOAD_DIR);
        String img = imgUrl.replace("/uploads", "");
        String path = file.getAbsolutePath() + img;
        file = new File(path);
        return file.delete();
    }
}
