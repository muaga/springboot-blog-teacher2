package shop.mtcoding.blogv2.reply;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.board.BoardRepository;
import shop.mtcoding.blogv2.reply.ReplyRequest.SaveDTO;
import shop.mtcoding.blogv2.user.User;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 댓글쓰기(SaveDTO saveDTO, Integer sessionId) {
        // insert into reply_tb(comment, board_id, user_id) values(?, ?, ?)

        // 1. board_id가 존재하는 지 유무
        // - 1) 제약조건이 없다면, DB에서 안전하게 조회(Optional)
        // - 2) 제약조건이 있다면, 없는 board_id로 insert -> * insert시도 할 때, metadata를 보고 터트린다.
        // ------> 1)은 무조건 select/insert와 2)은 insert이고, 비정상접근 시 오류가 발생
        // 쿼리를 2번 날리는 것은, 통신이 더 걸린다.
        // 이 선택은 상황을 보고 선택해야 한다. 만들고 나서 계속 공격받을 시 1), 정상접근이 많을 때 2)
        // 트랜잭션의 코드가 길면 1)으로 빨리 거르는 것이 좋다.
        // 비영속 객체
        Board board = Board.builder().id(saveDTO.getBoardId()).build();
        User user = User.builder().id(sessionId).build();

        // 비영속 객체
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(board)
                .user(user)
                .build();
        replyRepository.save(reply);
        // 영속 객체
    }

    // @Transactional
    // public void 댓글작성하기(ReplyRequest.SaveDTO saveDTO, Integer sessionUserId) {
    // Reply reply = Reply.builder()
    // .board(Board.builder().id(saveDTO.getBoardId()).build())
    // .user(User.builder().id(sessionUserId).build())
    // .comment(saveDTO.getComment())
    // .build();
    // replyRepository.save(reply);
    // }

    @Transactional
    public void 댓글삭제(Integer id, Integer sessionUserId) {
        // 아래코드 리팩토링 -> 아닌 것들을 필터링하기
        Optional<Reply> replyOP = replyRepository.findById(id);

        if (replyOP.isEmpty()) {
            throw new MyApiException("삭제할 댓글이 없습니다.");
        }

        Reply reply = replyOP.get();
        if (reply.getUser().getId() != sessionUserId) {
            throw new MyApiException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        replyRepository.deleteById(id);

        // 권한체크
        // Optional<Reply> replyOP = replyRepository.findById(id);
        // if (replyOP.isPresent()) {
        // Reply reply = replyOP.get();
        // if (reply.getUser().getId() != sessionUserId) {
        // throw new MyApiException("해당 댓글을 삭제할 권한이 없습니다.");
        // }
        // } else {
        // throw new MyApiException("삭제할 댓글이 없습니다.");
        // }
        // replyRepository.deleteById(id);
    }

    // @Transactional
    // public void 댓글삭제하기(Integer id) {
    // try {
    // replyRepository.deleteById(id);
    // } catch (Exception e) {
    // throw new RuntimeException("삭제할 댓글이 없습니다.");
    // }
    // }
}
