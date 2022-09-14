package ashmar32.venusrestblog.data;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

//Lombok to do the getters, setters, and constructors for you!
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 1024)
    private String content;

    @Transient
    private User author;

    @Transient
    private Collection<Category> categories;
}
