package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = true, length = 10000)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    // LAZY : user fetch 미실행 = ORM 불필요 = 모순의 불일치가 발생하지 않는다. ; PK값만 가지고 온다.
    // EAGER : board 조회시 반드시 fetch 실행 = ORM 필요 = 모순의 불일치를 해결해야 한다. ; 디폴트값
    private User user; // 1+N

    // 양방향(반대방향) 매핑(1:n) = table은 컬렉션을 가질 수 없기 때문에, board가 FK가 아닌 것을 설정해야 한다.
    // ""는 Reply에서 연관관계를 맺는 Board 객체의 변수명을 넣는다.
    // LAZY ; 디폴트값
    // Many 쪽이 여러 건이기 때문에, LAZY 전략이 디폴트값이다.
    @JsonIgnoreProperties("board")
    // 그 객체 안의 필드를 JSON으로 직렬화하지 않도록 한다.
    // reply 속 내부
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @CreationTimestamp // insert할 때 자동으로 시간을 넣어 준다. - Test시에도 유용
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }
}