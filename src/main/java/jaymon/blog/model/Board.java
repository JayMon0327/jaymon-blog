package jaymon.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    private int count; //조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    // 게시판을 열었을 때 댓글도 같이 보이도록 처음부터 데이터를 가져온다 = EAGER
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // mappedBy = 난 FK가 아니에요. DB에 컬럼 만들지 마세요.
    @JsonIgnoreProperties({"board"}) //무한 참조 방지 - 다이렉트론 괜찮지만, Board를 호출했을 때 이 필드를 참조한다면 가져오지 않을 필드 설정
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;

}



