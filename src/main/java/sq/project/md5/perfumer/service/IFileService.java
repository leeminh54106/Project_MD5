package sq.project.md5.perfumer.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService{
    String uploadLocal(MultipartFile fileUpload);
    String uploadFirebase(String localPath);
}
