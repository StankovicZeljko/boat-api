package ch.stankovic.bootapi.controller;

import ch.stankovic.bootapi.dto.request.BoatRequestDto;
import ch.stankovic.bootapi.dto.response.BoatResponseDto;
import ch.stankovic.bootapi.exception.BoatNotFoundException;
import ch.stankovic.bootapi.mapper.BoatMapper;
import ch.stankovic.bootapi.model.Boat;
import ch.stankovic.bootapi.service.BoatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoatControllerTest {

    @InjectMocks
    private BoatController controller;

    @Mock
    private BoatService service;

    @Mock
    private BoatMapper mapper;

    @Test
    public void when_getAllBoats_ListOfBoat(){
        Boat boat = new Boat();
        BoatResponseDto boatResponseDto = new BoatResponseDto();
        when(service.getAllBoat()).thenReturn(List.of(boat));
        when(mapper.toResponse(boat)).thenReturn(boatResponseDto);

        ResponseEntity<List<BoatResponseDto>> result = controller.getAllBoats();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(boatResponseDto, Objects.requireNonNull(result.getBody()).get(0));
        verify(service, times(1)).getAllBoat();
        verify(mapper, times(1)).toResponse(boat);
    }

    @Test
    public void when_getBoatById_Boat(){
        Long id = 1L;
        Boat boat = new Boat();
        BoatResponseDto boatResponseDto = new BoatResponseDto();
        when(service.getBoatById(id)).thenReturn(Optional.of(boat));
        when(mapper.toResponse(boat)).thenReturn(boatResponseDto);

        ResponseEntity<BoatResponseDto> result = controller.getBoatById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(boatResponseDto, result.getBody());
        verify(service, times(1)).getBoatById(id);
        verify(mapper, times(1)).toResponse(boat);
    }

    @Test
    public void when_getBoatById_NotFound(){
        Long id = 1L;
        when(service.getBoatById(id)).thenReturn(Optional.empty());

        try {
            controller.getBoatById(id);
        } catch (BoatNotFoundException exception) {
            assertEquals("Boat not found with id 1", exception.getMessage());
        }
    }

    @Test
    public void when_createBoat_ResponseOk(){
        BoatRequestDto requestDto = getTestDataBoatRequestDto();
        Boat boat = getTestDataBoat();
        BoatResponseDto responseDto = getTestDataBoatResponseDto();
        when(mapper.toEntity(requestDto)).thenReturn(boat);
        when(service.createBoat(boat)).thenReturn(boat);
        when(mapper.toResponse(boat)).thenReturn(responseDto);

        ResponseEntity<BoatResponseDto> result = controller.createBoat(requestDto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(mapper, times(1)).toEntity(requestDto);
        verify(service, times(1)).createBoat(boat);
        verify(mapper, times(1)).toResponse(boat);

    }

    @Test
    public void when_updateBoat_ResponseOk(){
        Long id = 1L;
        BoatRequestDto boatRequestDto = getTestDataBoatRequestDto();
        Boat boat = getTestDataBoat();
        BoatResponseDto boatResponseDto = getTestDataBoatResponseDto();
        when(mapper.toEntity(boatRequestDto)).thenReturn(boat);
        when(service.updateBoat(id, boat)).thenReturn(boat);
        when(mapper.toResponse(boat)).thenReturn(boatResponseDto);

        ResponseEntity<BoatResponseDto> result = controller.updateBoat(id, boatRequestDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(boatResponseDto, result.getBody());
        verify(mapper, times(1)).toEntity(boatRequestDto);
        verify(service, times(1)).updateBoat(id, boat);
        verify(mapper, times(1)).toResponse(boat);
    }

    @Test
    public void when_updateBoat_NotFound(){
        Long id = 1L;
        BoatRequestDto requestDto = getTestDataBoatRequestDto();
        Boat boat = getTestDataBoat();
        when(mapper.toEntity(requestDto)).thenReturn(boat);
        when(service.updateBoat(id, boat)).thenThrow(new BoatNotFoundException("Boat not found with id " + id));

        try {
            controller.updateBoat(id, requestDto);
        } catch (BoatNotFoundException exception) {
            assertEquals("Boat not found with id " + id, exception.getMessage());
        }
    }

    @Test
    public void when_deleteBoat_ResponseNoContent(){
        Long id = 1L;
        doNothing().when(service).deleteBoat(id);

        ResponseEntity<Void> result = controller.deleteBoat(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(service, times(1)).deleteBoat(id);
    }

    @Test
    public void when_deleteBoat_NotFound(){
        Long id = 1L;
        doThrow(new BoatNotFoundException("Boat not found with id " + id)).when(service).deleteBoat(id);

        try {
            controller.deleteBoat(id);
        } catch (BoatNotFoundException exception) {
            assertEquals("Boat not found with id " + id, exception.getMessage());
        }
    }

    private BoatRequestDto getTestDataBoatRequestDto(){
        return BoatRequestDto.builder()
                .name("Name")
                .description("Boat")
                .capacity(4)
                .available(true).build();
    }

    private Boat getTestDataBoat(){
        return Boat.builder().name("Name")
                .description("Boat")
                .capacity(4)
                .available(true).build();
    }

    private BoatResponseDto getTestDataBoatResponseDto() {
        return BoatResponseDto.builder().name("Name")
                .description("Boat")
                .capacity(4)
                .available(true).build();
    }
}
