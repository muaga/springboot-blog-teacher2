package shop.mtcoding.blogv2.reply;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blogv2.user.User;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private HttpSession session;

    // 댓글 등록
    @PostMapping("/reply/save")
    public String save(ReplyRequest.SaveDTO saveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // user를 찾지 못한다는 것은, 로그인 된 사람의 id를 찾아오지 못하는 것
        // System.out.println("board id : " + saveDTO.getBoardId());
        // board를 못 받아온다는 것은, mustache에서 받아와야 하는 것
        replyService.댓글작성하기(saveDTO, sessionUser.getId());
        return "redirect:/board/" + saveDTO.getBoardId();
    }

    // 댓글 삭제
    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable Integer id, Integer boardId) {
        replyService.댓글삭제하기(id);
        System.out.println("댓글의 id : " + id);
        return "redirect:/board/" + boardId;
    }
}
