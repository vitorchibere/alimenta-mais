package com.alimentamais;

import com.alimentamais.model.*;
import java.util.List;
import com.alimentamais.repository.OfertaRepository;
import com.alimentamais.repository.SolicitacaoRepository;
import com.alimentamais.service.SolicitacaoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SolicitacaoServiceTest {

    @Test
    void deveCriarSolicitacao() {

        SolicitacaoRepository solicitacaoRepository =
                mock(SolicitacaoRepository.class);

        OfertaRepository ofertaRepository =
                mock(OfertaRepository.class);

        SolicitacaoService service =
                new SolicitacaoService(
                        solicitacaoRepository,
                        ofertaRepository
                );

        Usuario beneficiario = new Usuario();
        beneficiario.setNome("Beneficiário de teste");

        Oferta oferta = new Oferta();
        oferta.setAlimento("Banana");
        oferta.setStatus(StatusOferta.DISPONIVEL);

        Solicitacao solicitacao =
                new Solicitacao(beneficiario, oferta);

        when(solicitacaoRepository.save(any(Solicitacao.class)))
                .thenReturn(solicitacao);

        Solicitacao resultado =
                service.criar(beneficiario, oferta);

        assertEquals(
                "Beneficiário de teste",
                resultado.getBeneficiario().getNome()
        );

        assertEquals(
                "Banana",
                resultado.getOferta().getAlimento()
        );

        assertEquals(
                StatusOferta.DISPONIVEL,
                resultado.getOferta().getStatus()
        );

        verify(solicitacaoRepository).save(any(Solicitacao.class));
    }

    @Test
    void naoDeveCriarSolicitacaoParaOfertaIndisponivel() {

        SolicitacaoRepository solicitacaoRepository =
                mock(SolicitacaoRepository.class);

        OfertaRepository ofertaRepository =
                mock(OfertaRepository.class);

        SolicitacaoService service =
                new SolicitacaoService(
                        solicitacaoRepository,
                        ofertaRepository
                );

        Usuario beneficiario = new Usuario();
        beneficiario.setNome("Beneficiário de teste");

        Oferta oferta = new Oferta();
        oferta.setAlimento("Banana");
        oferta.setStatus(StatusOferta.RESERVADA);

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.criar(beneficiario, oferta)
        );

        verify(solicitacaoRepository, never())
                .save(any(Solicitacao.class));
    }

    @Test
    void deveListarSolicitacoesSolicitadas() {

        SolicitacaoRepository solicitacaoRepository =
                mock(SolicitacaoRepository.class);

        OfertaRepository ofertaRepository =
                mock(OfertaRepository.class);

        SolicitacaoService service =
                new SolicitacaoService(
                        solicitacaoRepository,
                        ofertaRepository
                );

        Solicitacao solicitacao = new Solicitacao();

        when(solicitacaoRepository.findByStatus(StatusSolicitacao.SOLICITADA))
                .thenReturn(List.of(solicitacao));

        List<Solicitacao> resultado =
                service.listarSolicitadas();

        assertEquals(1, resultado.size());

        verify(solicitacaoRepository)
                .findByStatus(StatusSolicitacao.SOLICITADA);
    }

    @Test
    void deveListarTodasAsSolicitacoes() {

        SolicitacaoRepository solicitacaoRepository =
                mock(SolicitacaoRepository.class);

        OfertaRepository ofertaRepository =
                mock(OfertaRepository.class);

        SolicitacaoService service =
                new SolicitacaoService(
                        solicitacaoRepository,
                        ofertaRepository
                );

        Solicitacao solicitacao1 = new Solicitacao();
        Solicitacao solicitacao2 = new Solicitacao();

        when(solicitacaoRepository.findAll())
                .thenReturn(List.of(solicitacao1, solicitacao2));

        List<Solicitacao> resultado =
                service.listarTodas();

        assertEquals(2, resultado.size());

        verify(solicitacaoRepository).findAll();
    }

    @Test
    void deveAceitarSolicitacaoEReservarOferta() {

        SolicitacaoRepository solicitacaoRepository =
                mock(SolicitacaoRepository.class);

        OfertaRepository ofertaRepository =
                mock(OfertaRepository.class);

        SolicitacaoService service =
                new SolicitacaoService(
                        solicitacaoRepository,
                        ofertaRepository
                );

        Oferta oferta = new Oferta();
        oferta.setAlimento("Banana");
        oferta.setStatus(StatusOferta.DISPONIVEL);

        Solicitacao solicitacao =
                new Solicitacao();

        solicitacao.setOferta(oferta);
        solicitacao.setStatus(StatusSolicitacao.SOLICITADA);

        when(solicitacaoRepository.findById("123"))
                .thenReturn(java.util.Optional.of(solicitacao));

        when(solicitacaoRepository.save(solicitacao))
                .thenReturn(solicitacao);

        when(ofertaRepository.save(oferta))
                .thenReturn(oferta);

        Solicitacao resultado =
                service.atualizarStatus(
                        "123",
                        StatusSolicitacao.ACEITA
                );

        assertEquals(
                StatusSolicitacao.ACEITA,
                resultado.getStatus()
        );

        assertEquals(
                StatusOferta.RESERVADA,
                oferta.getStatus()
        );

        verify(ofertaRepository).save(oferta);
        verify(solicitacaoRepository).save(solicitacao);
    }

    @Test
    void deveRecusarSolicitacao() {

        SolicitacaoRepository solicitacaoRepository =
                mock(SolicitacaoRepository.class);

        OfertaRepository ofertaRepository =
                mock(OfertaRepository.class);

        SolicitacaoService service =
                new SolicitacaoService(
                        solicitacaoRepository,
                        ofertaRepository
                );

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setStatus(StatusSolicitacao.SOLICITADA);

        when(solicitacaoRepository.findById("123"))
                .thenReturn(java.util.Optional.of(solicitacao));

        when(solicitacaoRepository.save(solicitacao))
                .thenReturn(solicitacao);

        Solicitacao resultado =
                service.atualizarStatus(
                        "123",
                        StatusSolicitacao.RECUSADA
                );

        assertEquals(
                StatusSolicitacao.RECUSADA,
                resultado.getStatus()
        );

        verify(solicitacaoRepository).findById("123");
        verify(solicitacaoRepository).save(solicitacao);

        verifyNoInteractions(ofertaRepository);
    }

    @Test
    void deveConcluirSolicitacaoAceita() {

        SolicitacaoRepository solicitacaoRepository =
                mock(SolicitacaoRepository.class);

        OfertaRepository ofertaRepository =
                mock(OfertaRepository.class);

        SolicitacaoService service =
                new SolicitacaoService(
                        solicitacaoRepository,
                        ofertaRepository
                );

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setStatus(StatusSolicitacao.ACEITA);

        when(solicitacaoRepository.findById("123"))
                .thenReturn(java.util.Optional.of(solicitacao));

        when(solicitacaoRepository.save(solicitacao))
                .thenReturn(solicitacao);

        Solicitacao resultado =
                service.atualizarStatus(
                        "123",
                        StatusSolicitacao.CONCLUIDA
                );

        assertEquals(
                StatusSolicitacao.CONCLUIDA,
                resultado.getStatus()
        );

        verify(solicitacaoRepository).findById("123");
        verify(solicitacaoRepository).save(solicitacao);

        verifyNoInteractions(ofertaRepository);
    }

    @Test
    void naoDevePermitirTransicaoDeStatusInvalida() {

        SolicitacaoRepository solicitacaoRepository =
                mock(SolicitacaoRepository.class);

        OfertaRepository ofertaRepository =
                mock(OfertaRepository.class);

        SolicitacaoService service =
                new SolicitacaoService(
                        solicitacaoRepository,
                        ofertaRepository
                );

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setStatus(StatusSolicitacao.SOLICITADA);

        when(solicitacaoRepository.findById("123"))
                .thenReturn(java.util.Optional.of(solicitacao));

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizarStatus(
                        "123",
                        StatusSolicitacao.CONCLUIDA
                )
        );

        verify(solicitacaoRepository).findById("123");
        verify(solicitacaoRepository, never())
                .save(solicitacao);

        verifyNoInteractions(ofertaRepository);
    }

    @Test
    void deveRetornarErroQuandoSolicitacaoNaoExistir() {

        SolicitacaoRepository solicitacaoRepository =
                mock(SolicitacaoRepository.class);

        OfertaRepository ofertaRepository =
                mock(OfertaRepository.class);

        SolicitacaoService service =
                new SolicitacaoService(
                        solicitacaoRepository,
                        ofertaRepository
                );

        when(solicitacaoRepository.findById("999"))
                .thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizarStatus(
                        "999",
                        StatusSolicitacao.ACEITA
                )
        );

        verify(solicitacaoRepository).findById("999");

        verify(solicitacaoRepository, never())
                .save(any(Solicitacao.class));

        verifyNoInteractions(ofertaRepository);
    }

    @Test
    void naoDeveRecusarSolicitacaoJaAceita() {

        SolicitacaoRepository solicitacaoRepository =
                mock(SolicitacaoRepository.class);

        OfertaRepository ofertaRepository =
                mock(OfertaRepository.class);

        SolicitacaoService service =
                new SolicitacaoService(
                        solicitacaoRepository,
                        ofertaRepository
                );

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setStatus(StatusSolicitacao.ACEITA);

        when(solicitacaoRepository.findById("123"))
                .thenReturn(java.util.Optional.of(solicitacao));

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizarStatus(
                        "123",
                        StatusSolicitacao.RECUSADA
                )
        );

        verify(solicitacaoRepository).findById("123");

        verify(solicitacaoRepository, never())
                .save(solicitacao);

        verifyNoInteractions(ofertaRepository);
    }
}
