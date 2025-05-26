package test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.angelopaiva.jogo.logica.JogoStates;
import pt.isec.angelopaiva.jogo.logica.TypePiece;
import pt.isec.angelopaiva.jogo.logica.dados.Jogo;
import pt.isec.angelopaiva.jogo.logica.dados.players.PlayerHuman;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MinigameTest {

    /*
    * Data flow testing minigame eligibility
    */
    private Jogo jogo;

    @BeforeEach
    void setUp() {
        jogo = new Jogo();
        jogo.setPlayers(
                new PlayerHuman("Player1"),
                new PlayerHuman("Player2")
        );

        jogo.setCurrentPlayer(1);
    }

    @Test
    public void testPlayerEligibleToMinigame() {
        for (int i = 0; i < 4; i++) {
            jogo.updateJogo();
            jogo.updateCurrentPlayer(); // simulate round change
        }

        assertTrue(jogo.currentPlayerCanPlayMinigames());
    }

    @Test
    public void testPlayerNotEligibleToMinigame() {
        /* Player no rounds */
        assertFalse(jogo.currentPlayerCanPlayMinigames());
    }
}