package com.alimentamais;

import com.alimentamais.model.Oferta;
import com.alimentamais.model.StatusOferta;
import com.alimentamais.model.Usuario;
import com.alimentamais.repository.OfertaRepository;
import com.alimentamais.service.OfertaService;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OfertaServiceTest {

    @Test
    void deveCriarOferta() {

        OfertaRepository repository = mock(OfertaRepository.class);
        OfertaService service = new OfertaService(repository);

        Usuario doador = new Usuario();
        doador.setNome("Doador de teste");

        Oferta oferta = new Oferta(
                "Banana",
                10,
                "KG",
                "Bananas maduras próprias para consumo",
                "Maringá",
                doador
        );

        when(repository.save(oferta)).thenReturn(oferta);

        Oferta resultado = service.criar(oferta);

        assertEquals("Banana", resultado.getAlimento());
        assertEquals(10, resultado.getQuantidade());
        assertEquals("KG", resultado.getUnidade());
        assertEquals("Maringá", resultado.getCidade());

        verify(repository).save(oferta);
    }

    @Test
    void deveListarOfertasDisponiveis() {

        OfertaRepository repository = mock(OfertaRepository.class);
        OfertaService service = new OfertaService(repository);

        Oferta oferta = new Oferta();
        oferta.setAlimento("Banana");
        oferta.setStatus(StatusOferta.DISPONIVEL);

        when(repository.findByStatus(StatusOferta.DISPONIVEL))
                .thenReturn(List.of(oferta));

        List<Oferta> resultado = service.listarDisponiveis();

        assertEquals(1, resultado.size());
        assertEquals("Banana", resultado.get(0).getAlimento());
        assertEquals(StatusOferta.DISPONIVEL, resultado.get(0).getStatus());

        verify(repository).findByStatus(StatusOferta.DISPONIVEL);
    }

    @Test
    void deveReservarOfertaDisponivel() {

        OfertaRepository repository = mock(OfertaRepository.class);
        OfertaService service = new OfertaService(repository);

        Oferta oferta = new Oferta();
        oferta.setId("123");
        oferta.setAlimento("Banana");
        oferta.setStatus(StatusOferta.DISPONIVEL);

        when(repository.findById("123"))
                .thenReturn(java.util.Optional.of(oferta));

        when(repository.save(oferta))
                .thenReturn(oferta);

        Oferta resultado = service.atualizarStatus(
                "123",
                StatusOferta.RESERVADA
        );

        assertEquals(StatusOferta.RESERVADA, resultado.getStatus());

        verify(repository).findById("123");
        verify(repository).save(oferta);
    }

    @Test
    void naoDevePermitirTransicaoDeStatusInvalida() {

        OfertaRepository repository = mock(OfertaRepository.class);
        OfertaService service = new OfertaService(repository);

        Oferta oferta = new Oferta();
        oferta.setId("123");
        oferta.setAlimento("Banana");
        oferta.setStatus(StatusOferta.DISPONIVEL);

        when(repository.findById("123"))
                .thenReturn(java.util.Optional.of(oferta));

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizarStatus(
                        "123",
                        StatusOferta.ENTREGUE
                )
        );

        verify(repository).findById("123");
        verify(repository, never()).save(oferta);
    }

    @Test
    void deveMarcarOfertaComoEntregue() {

        OfertaRepository repository = mock(OfertaRepository.class);
        OfertaService service = new OfertaService(repository);

        Oferta oferta = new Oferta();
        oferta.setId("123");
        oferta.setAlimento("Banana");
        oferta.setStatus(StatusOferta.RESERVADA);

        when(repository.findById("123"))
                .thenReturn(java.util.Optional.of(oferta));

        when(repository.save(oferta))
                .thenReturn(oferta);

        Oferta resultado = service.atualizarStatus(
                "123",
                StatusOferta.ENTREGUE
        );

        assertEquals(StatusOferta.ENTREGUE, resultado.getStatus());

        verify(repository).findById("123");
        verify(repository).save(oferta);
    }

    @Test
    void deveRetornarErroQuandoOfertaNaoExistir() {

        OfertaRepository repository = mock(OfertaRepository.class);
        OfertaService service = new OfertaService(repository);

        when(repository.findById("999"))
                .thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizarStatus(
                        "999",
                        StatusOferta.RESERVADA
                )
        );

        verify(repository).findById("999");
        verify(repository, never()).save(any(Oferta.class));
    }

    @Test
    void deveEncerrarOfertaDisponivel() {

        OfertaRepository repository = mock(OfertaRepository.class);
        OfertaService service = new OfertaService(repository);

        Oferta oferta = new Oferta();
        oferta.setId("123");
        oferta.setAlimento("Banana");
        oferta.setStatus(StatusOferta.DISPONIVEL);

        when(repository.findById("123"))
                .thenReturn(java.util.Optional.of(oferta));

        when(repository.save(oferta))
                .thenReturn(oferta);

        Oferta resultado = service.atualizarStatus(
                "123",
                StatusOferta.ENCERRADA
        );

        assertEquals(StatusOferta.ENCERRADA, resultado.getStatus());

        verify(repository).findById("123");
        verify(repository).save(oferta);
    }

    @Test
    void deveListarTodasAsOfertas() {

        OfertaRepository repository = mock(OfertaRepository.class);
        OfertaService service = new OfertaService(repository);

        Oferta oferta1 = new Oferta();
        oferta1.setAlimento("Banana");

        Oferta oferta2 = new Oferta();
        oferta2.setAlimento("Morango");

        when(repository.findAll())
                .thenReturn(List.of(oferta1, oferta2));

        List<Oferta> resultado = service.listarTodas();

        assertEquals(2, resultado.size());
        assertEquals("Banana", resultado.get(0).getAlimento());
        assertEquals("Morango", resultado.get(1).getAlimento());

        verify(repository).findAll();
    }

    @Test
    void deveBuscarOfertaPorId() {

        OfertaRepository repository = mock(OfertaRepository.class);
        OfertaService service = new OfertaService(repository);

        Oferta oferta = new Oferta();
        oferta.setId("123");
        oferta.setAlimento("Morango");

        when(repository.findById("123"))
                .thenReturn(java.util.Optional.of(oferta));

        Oferta resultado = service.buscarPorId("123");

        assertEquals("123", resultado.getId());
        assertEquals("Morango", resultado.getAlimento());

        verify(repository).findById("123");
    }

    @Test
    void deveRetornarErroQuandoBuscarOfertaInexistente() {

        OfertaRepository repository = mock(OfertaRepository.class);
        OfertaService service = new OfertaService(repository);

        when(repository.findById("999"))
                .thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.buscarPorId("999")
        );

        verify(repository).findById("999");
    }
}
