package ch.stankovic.bootapi.mapper;

import ch.stankovic.bootapi.dto.request.BoatRequestDto;
import ch.stankovic.bootapi.dto.response.BoatResponseDto;
import ch.stankovic.bootapi.model.Boat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BoatMapperTest {

    @InjectMocks
    private BoatMapper mapper;

    @Test
    public void when_toEntity_Boat(){
        BoatRequestDto requestDto = getBoatRequestTestData();

        Boat result = mapper.toEntity(requestDto);

        assertEquals(result.getName(),requestDto.getName());
        assertEquals(result.getCapacity(), requestDto.getCapacity());
        assertEquals(result.getDescription(), requestDto.getDescription());
        assertEquals(result.isAvailable(), requestDto.isAvailable());
    }

    @Test
    public void when_toResponse_BoatResponseDto(){
        Boat boat = getBoatTestData();

        BoatResponseDto result = mapper.toResponse(boat);

        assertEquals(result.getName(),boat.getName());
        assertEquals(result.getCapacity(), boat.getCapacity());
        assertEquals(result.getDescription(), boat.getDescription());
        assertEquals(result.isAvailable(), boat.isAvailable());
    }


    private BoatRequestDto getBoatRequestTestData(){
        return BoatRequestDto
                .builder()
                .name("Boat")
                .description("A Boat")
                .capacity(4)
                .available(true).build();
    }

    private Boat getBoatTestData(){
        return Boat
                .builder()
                .name("Boat")
                .description("A Boat")
                .capacity(4)
                .available(true).build();
    }

}
