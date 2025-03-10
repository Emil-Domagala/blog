package backend.blog.domain.dto;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTagsRequest {

    @NotEmpty(message = "At least one tag is required")
    @Size(max = 10, message = "Maximum {max} tags allowed")
    private Set<@Size(min = 2, max = 30, message = "Tag name must be beetwen {min} and {max} characters") @Pattern(regexp = "^[\\w\\s-]+$", message = "Tag can only contain letters, numbers, spaces and \"-\"") String> names;

}
