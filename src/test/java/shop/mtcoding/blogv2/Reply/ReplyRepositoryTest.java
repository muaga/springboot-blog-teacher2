package shop.mtcoding.blogv2.Reply;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.reply.ReplyRepository;

@DataJpaTest
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void save_test() {
        Reply reply = Reply.builder()
                .board(Board.builder().id(5).build())
                .comment("좋아요")
                .build();
        Reply saveReply = replyRepository.save(reply);
        System.out.println("코멘트가 등록된 게시글 번호 : " + saveReply.getBoard().getId());
        System.out.println("등록된 코멘트 : " + saveReply.getComment());
    }

    @Test
    public void deleteById_test() {
        replyRepository.deleteById(null);
    }

}
