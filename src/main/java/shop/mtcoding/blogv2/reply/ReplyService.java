package shop.mtcoding.blogv2.reply;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.user.User;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 댓글작성하기(ReplyRequest.SaveDTO saveDTO, Integer sessionUserId) {
        Reply reply = Reply.builder()
                .board(Board.builder().id(saveDTO.getBoardId()).build())
                .user(User.builder().id(sessionUserId).build())
                .comment(saveDTO.getComment())
                .build();
        replyRepository.save(reply);
    }

    @Transactional
    public void 댓글삭제하기(Integer id) {
        try {
            replyRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("삭제할 댓글이 없습니다.");
        }
    }

}
