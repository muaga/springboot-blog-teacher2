package shop.mtcoding.blogv2.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
        // 게시글 먼저 만들때 메인화면이 없어서 localhost:8080을 해도 404가 나온다.
        // 위의 URL을 직접 쳐서 뜨면 실행성공이다.
    }

    // 1. 데이터 받기(DS가 준다.)
    // 2. 로그인 인증검사 - TODO
    // 3. 유효성 검사 - TODO
    // 4. 핵심로직 호출 = service에서 Transaction 관리
    // 5. 데이터 응답(view or data)
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        // 1 - test(Form(DS)에서 데이터가 오는 지)
        // System.out.println("title : " + saveDTO.getTitle());
        // System.out.println("title : " + saveDTO.getContent());

        return "redirect:/";
        // ★★ 다른 팀원이 만드는 영역을 "/"를 위해 굳이 만들 필요가 없다. -> 해놓기만 하자.
        // 나도 동일한 코드를 만들면 merge시 충돌이 일어난다.
    }

}
