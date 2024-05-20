package ch.stankovic.bootapi.service;

import ch.stankovic.bootapi.exception.BoatNotFoundException;
import ch.stankovic.bootapi.model.Boat;
import ch.stankovic.bootapi.repository.BoatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoatServiceTest {

    @InjectMocks
    private BoatService service;

    @Mock
    private BoatRepository repository;


    @Test
    public void when_getAllBoat_ListOfBoat(){
        Boat boat = getTestDataBoat();
        when(repository.findAll()).thenReturn(List.of(boat));

        List<Boat> result = service.getAllBoat();

        assertEquals(1, result.size());
        assertEquals(boat, result.get(0));
        verify(repository, times(1)).findAll();
    }

    @Test
    public void when_getBoatById_Boat(){
        Boat boat = getTestDataBoat();
        when(repository.findById(1L)).thenReturn(Optional.of(boat));

        Optional<Boat> result = service.getBoatById(1L);

        assertEquals(Optional.of(boat), result);
        verify(repository, times(1)).findById(1L);
    }


    @Test
    public void when_createBoat_Boat(){
        Boat boat = getTestDataBoat();
        when(repository.save(boat)).thenReturn(boat);

        Boat result = service.createBoat(boat);

        assertEquals(boat, result);
        verify(repository, times(1)).save(boat);
    }

    @Test
    public void when_updateBoat_UpdatedBoat(){
        Long id = 1L;
        Boat oldBoat = getTestDataBoat();
        Boat newBoat = getTestDataBoat();
        newBoat.setName("NewName");
        newBoat.setDescription("NewBoat");
        newBoat.setCapacity(7);
        when(repository.findById(id)).thenReturn(Optional.of(oldBoat));
        when(repository.save(oldBoat)).thenReturn(newBoat);

        Boat result = service.updateBoat(1L, newBoat);

        assertEquals(newBoat.getName(), result.getName());
        assertEquals(newBoat.getDescription(), result.getDescription());
        assertEquals(newBoat.getCapacity(), result.getCapacity());
        assertEquals(newBoat.isAvailable(), result.isAvailable());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(oldBoat);

    }

    @Test
    public void when_updateBoat_BoatNotFoundException(){
        Long id = 1L;
        Boat boat = getTestDataBoat();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BoatNotFoundException.class, () -> service.updateBoat(id, boat));

    }

    @Test
    public void when_deleteBoat_Ok(){
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        service.deleteBoat(id);

        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);

    }

    @Test
    public void when_deleteBoat_BoatNotFoundException(){
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(BoatNotFoundException.class, () -> service.deleteBoat(id));
    }

    private Boat getTestDataBoat(){
        return Boat.builder()
                .name("Name")
                .description("Boat")
                .capacity(4)
                .available(true).build();
    }
}
