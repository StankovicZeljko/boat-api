package ch.stankovic.bootapi.mapper;

import ch.stankovic.bootapi.dto.request.BoatRequestDto;
import ch.stankovic.bootapi.dto.response.BoatResponseDto;
import ch.stankovic.bootapi.model.Boat;
import org.springframework.stereotype.Component;

@Component
public class BoatMapper {

    public Boat toEntity(BoatRequestDto requestDto){
        return Boat.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .capacity(requestDto.getCapacity())
                .available(requestDto.isAvailable()).build();
    }

    public BoatResponseDto toResponse(Boat boat){
        return BoatResponseDto.builder()
                .id(boat.getId())
                .name(boat.getName())
                .description(boat.getDescription())
                .capacity(boat.getCapacity())
                .available(boat.isAvailable()).build();
    }

}
