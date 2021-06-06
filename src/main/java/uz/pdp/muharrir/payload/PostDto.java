package uz.pdp.muharrir.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class PostDto {
    @NotNull(message = "Title is not be empty")
    private String title;

    @NotNull(message = "Text is not be empty")
    private String text;

    @NotNull(message = "url is not be empty")
    private String url;

}
