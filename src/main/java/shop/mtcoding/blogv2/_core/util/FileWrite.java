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
        // 1. 파일명에 랜덤한 해시값을 만들어준다. = 충돌방지
        // uuid : 전세계에서 유일한 식별자를 만들어 주는 표준 규약
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + pic;
        // pic = joinDTO.getPic().getOriginalFilename()
        // getOriginalFilename은 확장자를 가지고 있어 제일 뒤에 위치해야 한다.

        // 2. 경로지정(./ = 상대경로)
        // 상대경로가 좋은 점. OS가 다르면 절대경로 사용시 경로가 오류날 수 있다. 그래서 상대경로가 가장 좋다.
        // 프로젝트 실행 파일변경 -> blogv2-1.0.jar(build-gradel에서 version을 변경했다.)
        // 해당 실행파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        
        try {
            Files.write(filePath, pic.getBytes());
            // 바이트 정보를 가져온다. 파일 자체를 만든다. - 경로에 파일 저장
            // 해당 경로에 파일을 생성
            // 이미지는 바이트 배열로 바꿔서 해야하는데, 바꿔주는게 getByte이다.
            // 즉, 데이터를 직렬화해주는 것이다.

        } catch (Exception e) {

            throw new MyException(e.getMessage());
            // 폴더, 경로, 파일 등의 오류 처리
        }
        
        return fileName;
    }
}