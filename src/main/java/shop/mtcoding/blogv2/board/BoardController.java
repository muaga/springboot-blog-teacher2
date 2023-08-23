package shop.mtcoding.blogv2.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    // 글 삭제 기능
    @PostMapping("/board/{id}/delete")
    public @ResponseBody String delete(@PathVariable Integer id) {
        try {
            boardService.글삭제하기(id);
            return Script.href("/");
        } catch (Exception e) {
            return Script.back("삭제 실패");
        }
        // 이렇게 처리하는 것은 힘들다.

    }

    // 글 수정 기능
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO updateDTO) {
        // where 데이터, body, session 값
        boardService.글수정하기(updateDTO, id);
        return "redirect:/board/" + id;
    }

    // 글 수정 페이지
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        Board board = boardService.글원본보기(id);
        request.setAttribute("board", board);
        return "board/updateForm";
    }

    // 글 상세보기 페이지
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Board board = boardService.상세보기(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    // 글 목록 페이지
    // localhost:8080?page=1&keyword=바나나
    // @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        Page<Board> boardPG = boardService.게시글목록보기(page);
        request.setAttribute("boardPG", boardPG);
        request.setAttribute("prevPage", boardPG.getNumber() - 1);
        request.setAttribute("nextPage", boardPG.getNumber() + 1);
        return "index";
    }

    // 글 검색 결과 페이지
    @GetMapping("/")
    public String index(String keyword, @RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        Page<Board> boardPG = null;
        if (keyword == null || keyword.trim().isEmpty()) {
            boardPG = boardService.게시글목록보기(page);
        } else {
            boardPG = boardService.검색후게시글목록보기(page, keyword);
        }
        request.setAttribute("boardPG", boardPG);
        request.setAttribute("prevPage", boardPG.getNumber() - 1);
        request.setAttribute("nextPage", boardPG.getNumber() + 1);
        return "index";
    }

    // 글쓰기 페이지
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
        // 게시글 먼저 만들때 메인화면이 없어서 localhost:8080을 해도 404가 나온다.
        // 위의 URL을 직접 쳐서 뜨면 실행성공이다.
    }

    // 글쓰기 기능
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

        boardService.글쓰기(saveDTO, 1);

        return "redirect:/";
        // ★★ 다른 팀원이 만드는 영역을 "/"를 위해 굳이 만들 필요가 없다. -> 해놓기만 하자.
        // 나도 동일한 코드를 만들면 merge시 충돌이 일어난다.
    }

}
