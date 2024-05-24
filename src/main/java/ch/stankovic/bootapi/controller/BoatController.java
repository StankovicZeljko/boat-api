package ch.stankovic.bootapi.controller;

import ch.stankovic.bootapi.dto.request.BoatRequestDto;
import ch.stankovic.bootapi.dto.response.BoatResponseDto;
import ch.stankovic.bootapi.exception.BoatNotFoundException;
import ch.stankovic.bootapi.mapper.BoatMapper;
import ch.stankovic.bootapi.service.BoatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/boat")
public class BoatController {


    private final BoatService boatService;
    private final BoatMapper boatMapper;

    @Autowired
    public BoatController(BoatService boatService, BoatMapper boatMapper) {
        this.boatService = boatService;
        this.boatMapper = boatMapper;
    }

    @GetMapping
    public ResponseEntity<List<BoatResponseDto>> getAllBoats() {
        List<BoatResponseDto> boats = boatService.getAllBoat().stream()
                .map(boatMapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(boats, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoatResponseDto> getBoatById(@PathVariable Long id) {
        return boatService.getBoatById(id)
                .map(boat -> new ResponseEntity<>(boatMapper.toResponse(boat), HttpStatus.OK))
                .orElseThrow(() -> new BoatNotFoundException("Boat not found with id " + id));
    }

    @PostMapping
    public ResponseEntity<BoatResponseDto> createBoat(@Valid @RequestBody BoatRequestDto boatRequestDto) {
        return new ResponseEntity<>(boatMapper.toResponse(boatService.createBoat(boatMapper.toEntity(boatRequestDto))), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoatResponseDto> updateBoat(@PathVariable Long id, @Valid @RequestBody BoatRequestDto newBoatData) {
        try {
            return new ResponseEntity<>(boatMapper.toResponse(boatService.updateBoat(id, boatMapper.toEntity(newBoatData))), HttpStatus.OK);
        } catch (BoatNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoat(@PathVariable Long id) {
        try {
            boatService.deleteBoat(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BoatNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
