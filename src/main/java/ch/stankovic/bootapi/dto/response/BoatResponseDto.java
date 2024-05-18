package ch.stankovic.bootapi.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoatResponseDto {

    private Long id;
    String name;
    int capacity;
    String description;
    boolean available;
}
