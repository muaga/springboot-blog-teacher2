package shop.mtcoding.blogv2.reply;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2.user.User;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private HttpSession session;

    // 댓글삭제
    @DeleteMapping("/api/reply/{id}/delete")
    public @ResponseBody ApiUtil<String> delete(@PathVariable Integer id) {
        // 1. 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new MyApiException("인증되지 않았습니다.");
        }

        // 2. 핵심로직
        replyService.댓글삭제(id, sessionUser.getId());

        // 3. 응답
        return new ApiUtil<String>(true, "댓글삭제 성공");
    }

    // 자바스크립트로 댓글등록
    @PostMapping("/api/reply/save")
    public @ResponseBody ApiUtil<String> save(@RequestBody ReplyRequest.SaveDTO saveDTO) {
        // System.out.println("boardId : " + saveDTO.getBoardId());
        // System.out.println("comment : " + saveDTO.getComment());
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            // return new ApiUtil<String>(false, "인증이 되지 않았습니다.");
            // 이렇게 하면, 모든 exception을 DS에게 보내려고 했지만 거기는 JS로 처리하는 곳이라서 기능이 틀리다.
            throw new MyApiException("인증되지 않았습니다.");
        }
        replyService.댓글쓰기(saveDTO, sessionUser.getId());
        return new ApiUtil<String>(true, "댓글쓰기 성공");
    }

    // 댓글 등록
    // @PostMapping("/reply/save")
    // public String save(ReplyRequest.SaveDTO saveDTO) {
    // User sessionUser = (User) session.getAttribute("sessionUser");
    // // user를 찾지 못한다는 것은, 로그인 된 사람의 id를 찾아오지 못하는 것
    // // System.out.println("board id : " + saveDTO.getBoardId());
    // // board를 못 받아온다는 것은, mustache에서 받아와야 하는 것
    // replyService.댓글작성하기(saveDTO, sessionUser.getId());
    // return "redirect:/board/" + saveDTO.getBoardId();
    // }

    // 댓글 삭제
    // @PostMapping("/reply/{id}/delete")
    // public String delete(@PathVariable Integer id, Integer boardId) {
    // replyService.댓글삭제하기(id);
    // System.out.println("댓글의 id : " + id);
    // return "redirect:/board/" + boardId;
    // }
}
