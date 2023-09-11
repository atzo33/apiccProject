package apicc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Integer id;

    private String createdAt;

    private UserDTO postedBy;

    private String text;

    private Integer postId;

    private Integer parentCommentID;

    private List<CommentDTO> commentReplies;

}
