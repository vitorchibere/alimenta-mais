package com.alimentamais.repository;

import com.alimentamais.model.Oferta;
import com.alimentamais.model.StatusOferta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OfertaRepository extends MongoRepository<Oferta, String> {

    List<Oferta> findByStatus(StatusOferta status);
}
