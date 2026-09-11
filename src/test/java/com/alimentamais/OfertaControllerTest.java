package com.alimentamais;

import com.alimentamais.controller.OfertaController;
import com.alimentamais.model.Oferta;
import com.alimentamais.model.StatusOferta;
import com.alimentamais.service.OfertaService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OfertaControllerTest {

    @Test
    void deveRetornarFormularioDeOferta() {

        OfertaService service = mock(OfertaService.class);
        OfertaController controller = new OfertaController(service);

        String resultado = controller.novaOferta();

        assertEquals("oferta-form", resultado);
    }

    @Test
    void deveListarOfertasDisponiveis() {

        OfertaService service = mock(OfertaService.class);
        OfertaController controller = new OfertaController(service);
        Model model = mock(Model.class);

        List<Oferta> ofertas = List.of();
        when(service.listarDisponiveis()).thenReturn(ofertas);

        String resultado = controller.listarOfertas(model);

        assertEquals("ofertas", resultado);
        verify(service).listarDisponiveis();
        verify(model).addAttribute("ofertas", ofertas);
    }

    @Test
    void deveCriarOfertaERedirecionar() {

        OfertaService service = mock(OfertaService.class);
        OfertaController controller = new OfertaController(service);

        String resultado = controller.criarOferta(
                "Banana",
                10.0,
                "KG",
                "Bananas maduras",
                "Maringá"
        );

        assertEquals("redirect:/", resultado);
        verify(service).criar(any(Oferta.class));
    }

    @Test
    void deveAtualizarStatusDaOfertaERedirecionar() {

        OfertaService service = mock(OfertaService.class);
        OfertaController controller = new OfertaController(service);

        String resultado = controller.atualizarStatus(
                "123",
                StatusOferta.RESERVADA
        );

        assertEquals("redirect:/ofertas", resultado);
        verify(service).atualizarStatus("123", StatusOferta.RESERVADA);
    }

    @Test
    void deveListarTodasAsOfertas() {

        OfertaService service = mock(OfertaService.class);
        OfertaController controller = new OfertaController(service);
        Model model = mock(Model.class);

        List<Oferta> ofertas = List.of();
        when(service.listarTodas()).thenReturn(ofertas);

        String resultado = controller.listarTodas(model);

        assertEquals("ofertas-todas", resultado);
        verify(service).listarTodas();
        verify(model).addAttribute("ofertas", ofertas);
    }
}