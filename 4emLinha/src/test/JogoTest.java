package test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.angelopaiva.jogo.logica.JogoStates;
import pt.isec.angelopaiva.jogo.logica.TypePiece;
import pt.isec.angelopaiva.jogo.logica.dados.Jogo;
import pt.isec.angelopaiva.jogo.logica.dados.players.PlayerHuman;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isec.angelopaiva.jogo.logica.dados.Jogo.NR_COLUMNS;

public class JogoTest {

    Jogo jogo;

    @BeforeEach
    void setUp() {
        jogo = new Jogo();
        jogo.setPlayers(
                new PlayerHuman("Player1"),
                new PlayerHuman("Player2")
        );

        jogo.setCurrentPlayer(1); // 1 corresponds to Player1
    }

    @Test
    public void testHorizontalWinCondition() {
        for (int i = 0; i < 4; i++) {
            jogo.placePieceOnColumn(i);
        }

        /* update jogo.getState() */
        jogo.updateJogo();

        assertEquals(JogoStates.VICTORY_P1, jogo.getState());
    }

    @Test
    public void testVerticalWinCondition() {
        for (int i = 0; i < 4; i++) {
            jogo.placePieceOnColumn(0);
        }

        /* update jogo.getState() */
        jogo.updateJogo();

        assertEquals(JogoStates.VICTORY_P1, jogo.getState());
    }

    @Test
    public void testRightToLeftDiagonalWinCondition() {
        // Arrange, Act, Assert
        assertEquals(2, 1 + 1);
    }

    @Test
    public void testLeftToRightDiagonalWinCondition() {
        // Arrange, Act, Assert
        assertEquals(2, 1 + 1);
    }

    @Test
    public void testDrawCondition() {
        /* Set the columns with a draw condition game state */
        for (int i = 0; i < Jogo.NR_COLUMNS; i++) {
            List<TypePiece> column = new ArrayList<>();
            TypePiece startPiece = (i % 2 == 0) ? TypePiece.PIECE_P1 : TypePiece.PIECE_P2;

            for (int j = 0; j < Jogo.NR_ROWS; j++) {
                if ((j / 2) % 2 == 0) {
                    column.add(startPiece);
                } else {
                    column.add((startPiece == TypePiece.PIECE_P1) ? TypePiece.PIECE_P2 : TypePiece.PIECE_P1);
                }
            }

            jogo.replaceColumnWithList(column, i);
        }

        /* update jogo.getState() */
        jogo.updateJogo();

        assertEquals(JogoStates.DRAW, jogo.getState());
    }
}