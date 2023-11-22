package jaymon.blog.model;

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
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    @ColumnDefault("0")
    private int count; //조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    // 게시판을 열었을 때 댓글도 같이 보이도록 처음부터 데이터를 가져온다 = EAGER
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // mappedBy = 난 FK가 아니에요. DB에 컬럼 만들지 마세요.
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;

}



