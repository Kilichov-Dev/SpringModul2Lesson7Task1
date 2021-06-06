package uz.pdp.muharrir.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.security.DenyAll;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String resourceField;
    private Object object;
}
