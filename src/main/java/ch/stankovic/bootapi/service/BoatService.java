package ch.stankovic.bootapi.service;

import ch.stankovic.bootapi.exception.BoatNotFoundException;
import ch.stankovic.bootapi.model.Boat;
import ch.stankovic.bootapi.repository.BoatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoatService {
    private final BoatRepository repository;

    @Autowired
    public BoatService(BoatRepository repository){
        this.repository = repository;
    }

    public List<Boat> getAllBoat(){
        return repository.findAll();
    }

    public Optional<Boat> getBoatById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Boat createBoat(Boat boat){
        return repository.save(boat);

    }

    @Transactional
    public Boat updateBoat(Long id, Boat newBoatData) throws BoatNotFoundException {

        return repository.findById(id)
                .map(boat -> {
                    boat.setName(newBoatData.getName());
                    boat.setDescription(newBoatData.getDescription());
                    boat.setCapacity(newBoatData.getCapacity());
                    boat.setAvailable(newBoatData.isAvailable());
                    return repository.save(boat);
                })
                .orElseThrow( () -> new BoatNotFoundException("Boat not found with id " + id));
    }


    @Transactional
    public void deleteBoat(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new BoatNotFoundException("Boat not found with id " + id);
        }
    }
}
