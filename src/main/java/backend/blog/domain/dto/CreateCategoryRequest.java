package backend.blog.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
    
    @Size(min=2,max=50,message = "Category name must be beetween {min} and {max} characters")
    @NotBlank(message = "Category name is required")
    @Pattern(regexp = "^[\\w\\s-]+$",message = "Category name can only contain letters, numbers, spaces and hyphens")
    private String name;
}
