package ch.stankovic.bootapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoatRequestDto {

    @NotBlank(message = "Name must be set")
    String name;

    @Positive(message = "Capacity must be positve number")
    int capacity;

    @NotBlank(message = "Description must be set")
    @Size(max = 100, message = "The Description is to long")
    String description;

    boolean available;
}
