package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blogv2.user.User;

// 1. 비즈니스로직 처리(핵심로직)
// 2. 트랜잭션관리
// 3. 예외처리
// 4. DTO 변환
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
        Board board = Board.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .user(User.builder().id(sessionUserId).build())
                .build();
        boardRepository.save(board);
    }

    public Page<Board> 게시글목록보기(Integer page) {
        // 'Page<Board>'는 Board의 페이지를 나눠진 결과를 담은 객체이다.
        // 매개변수 'page'는 가져올 페이지 번호를 나타낸다.
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        // Pageable 객체를 생성하고, 페이지 정보를 설정한다.
        return boardRepository.findAll(pageable);
        // findAll 메소드 실행시, pageable을 기준으로
        // 지정된 페이지 번호와 페이지 당 개수, 정렬 정보에 따라 데이터를 가져온다.
    }

    public Board 상세보기(Integer id) {
        return boardRepository.findById(id).get();
    }

    @Transactional
    public void 글수정하기(BoardRequest.UpdateDTO updateDTO, Integer id) {
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            Board board = boardOP.get();
            board.setTitle(updateDTO.getTitle());
            board.setContent(updateDTO.getContent());
        }
    } // flush (더티체킹)

    public Board 글원본보기(Integer id) {

        return boardRepository.findById(id).get();
    }

    @Transactional
    public void 글삭제하기(Integer id) {
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("100번은 없어요");
            // 가장 좋은 것은 Contorller에게까지 오류를 전가
        }
        // reposioty에서는 throws를 사용하지 말고, try-catch를 사용한다.
        // 왜냐하면, 코드가 엄청 많을 때 특정한 코드에 대한 오류처리가 힘들기 때문이다.
    }
}
