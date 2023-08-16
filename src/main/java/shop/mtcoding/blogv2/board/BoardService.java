package shop.mtcoding.blogv2.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blogv2.user.User;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 회원가입(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
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
}
