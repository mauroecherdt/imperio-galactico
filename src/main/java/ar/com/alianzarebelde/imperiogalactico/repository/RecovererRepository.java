package ar.com.alianzarebelde.imperiogalactico.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.alianzarebelde.imperiogalactico.enums.SatelliteType;
import ar.com.alianzarebelde.imperiogalactico.models.Satellite;

@Repository
public interface RecovererRepository extends MongoRepository<Satellite, String>{
	
	public Optional<Satellite> findByName(SatelliteType name);

}
