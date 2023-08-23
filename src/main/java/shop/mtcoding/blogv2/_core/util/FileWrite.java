package shop.mtcoding.blogv2._core.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.vo.MyPath;

public class FileWrite {

    public static String save(MultipartFile pic) {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + pic;

        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        try {
            Files.write(filePath, pic.getBytes());
            // 바이트 정보를 가져온다. 파일 자체를 만든다. - 경로에 파일 저장
            // 해당 경로에 파일을 생성
            // 이미지는 바이트 배열로 바꿔서 해야하는데, 바꿔주는게 getByte이다.
            // 즉, 데이터를 직렬화해주는 것이다.
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
        return fileName;
    }
}