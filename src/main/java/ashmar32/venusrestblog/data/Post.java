package ashmar32.venusrestblog.data;

import lombok.*;
//Lombok to do the getters, setters, and constructors for you!
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Post {
    private Long id;
    private String title;
    private String content;
}
