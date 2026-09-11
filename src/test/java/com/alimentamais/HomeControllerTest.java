package com.alimentamais;

import com.alimentamais.controller.HomeController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    @Test
    void deveRetornarPaginaInicial() {

        HomeController controller = new HomeController();

        String resultado = controller.home();

        assertEquals("index", resultado);
    }
}
