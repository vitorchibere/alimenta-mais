package com.alimentamais.service;

import com.alimentamais.model.Oferta;
import com.alimentamais.model.StatusOferta;
import com.alimentamais.repository.OfertaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    public OfertaService(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    public Oferta criar(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public List<Oferta> listarDisponiveis() {
        return ofertaRepository.findByStatus(StatusOferta.DISPONIVEL);
    }

    public Oferta atualizarStatus(String id, StatusOferta status) {
        Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Oferta não encontrada"));

        oferta.setStatus(status);

        return ofertaRepository.save(oferta);
    }

    public Oferta buscarPorId(String id) {
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Oferta não encontrada"));
    }
}