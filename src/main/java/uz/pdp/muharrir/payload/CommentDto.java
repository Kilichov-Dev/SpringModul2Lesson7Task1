package uz.pdp.muharrir.payload;

import lombok.Data;
import uz.pdp.muharrir.entity.Post;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
public class CommentDto {

    private String text;

    private Long postId;
}
