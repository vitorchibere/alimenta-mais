package com.alimentamais;

import com.alimentamais.controller.SolicitacaoController;
import com.alimentamais.model.Oferta;
import com.alimentamais.model.Solicitacao;
import com.alimentamais.model.StatusSolicitacao;
import com.alimentamais.model.Usuario;
import com.alimentamais.service.OfertaService;
import com.alimentamais.service.SolicitacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SolicitacaoControllerTest {

    @Test
    void deveCriarSolicitacaoERedirecionar() {

        SolicitacaoService solicitacaoService = mock(SolicitacaoService.class);
        OfertaService ofertaService = mock(OfertaService.class);

        SolicitacaoController controller =
                new SolicitacaoController(solicitacaoService, ofertaService);

        Oferta oferta = new Oferta();
        oferta.setId("123");
        oferta.setAlimento("Banana");

        when(ofertaService.buscarPorId("123")).thenReturn(oferta);

        String resultado = controller.criarSolicitacao("123");

        assertEquals("redirect:/ofertas", resultado);

        verify(ofertaService).buscarPorId("123");
        verify(solicitacaoService).criar(
                any(Usuario.class),
                eq(oferta)
        );
    }

    @Test
    void deveListarSolicitacoes() {

        SolicitacaoService solicitacaoService = mock(SolicitacaoService.class);
        OfertaService ofertaService = mock(OfertaService.class);

        SolicitacaoController controller =
                new SolicitacaoController(solicitacaoService, ofertaService);

        Model model = mock(Model.class);

        List<Solicitacao> solicitacoes = List.of();

        when(solicitacaoService.listarTodas()).thenReturn(solicitacoes);

        String resultado = controller.listarSolicitacoes(model);

        assertEquals("solicitacoes", resultado);

        verify(solicitacaoService).listarTodas();
        verify(model).addAttribute("solicitacoes", solicitacoes);
    }

    @Test
    void deveAtualizarStatusDaSolicitacaoERedirecionar() {

        SolicitacaoService solicitacaoService = mock(SolicitacaoService.class);
        OfertaService ofertaService = mock(OfertaService.class);

        SolicitacaoController controller =
                new SolicitacaoController(solicitacaoService, ofertaService);

        String resultado = controller.atualizarStatus(
                "123",
                StatusSolicitacao.ACEITA
        );

        assertEquals("redirect:/solicitacoes", resultado);

        verify(solicitacaoService).atualizarStatus(
                "123",
                StatusSolicitacao.ACEITA
        );
    }
}