package pt.isec.angelopaiva.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.angelopaiva.jogo.logica.dados.minigames.MathMinigame;
import pt.isec.angelopaiva.jogo.logica.dados.minigames.TypingMinigame;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TypingMinigameTest {

    /*
    * Data flow testing on typing minigame setAnswer
    */

    private TypingMinigame minigame;

    @BeforeEach
    void setUp() {
        minigame = new TypingMinigame();
    }

    private String[] getLastSolution(TypingMinigame minigame) {
        try {
            var field = TypingMinigame.class.getDeclaredField("solutions");
            field.setAccessible(true);
            return (String[]) field.get(minigame);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String buildCorrectAnswer(String[] solutions) {
        return String.join(" ", solutions);
    }

    @Test
    public void testTypingMinigameHasWon() {
        String question = minigame.getQuestion();
        String correctAnswer = buildCorrectAnswer(getLastSolution(minigame));
        minigame.setAnswer(correctAnswer);

        assertTrue(minigame.hasWon());
    }

    @Test
    public void testTypingMinigameWrongAnswer() {
        String question = minigame.getQuestion();
        minigame.setAnswer("WRONG WORDS TESTING");

        assertFalse(minigame.hasWon());
    }

    @Test
    public void testTypingMinigameBlankAnswer() {
        minigame.getQuestion();
        minigame.setAnswer("");

        assertFalse(minigame.hasWon());
    }

    @Test
    public void testTypingMinigameNullAnswer() {
        minigame.getQuestion();
        minigame.setAnswer(null);

        assertFalse(minigame.hasWon());
    }

    @Test
    public void testTypingMinigameTokenMismatchOrder() {
        minigame.getQuestion();
        String[] correctAnswer = getLastSolution(minigame);

        String input = "";
        for (int i = correctAnswer.length - 1; i >= 0; i--) {
            input += correctAnswer[i] + " ";
        }

        minigame.setAnswer(input);
        assertFalse(minigame.hasWon());
    }

    /*
     * This test fails because the program is not checking the size of the answer.
     * If I only write 4 of the 5 words, it ignores the last one.
    */
    @Test
    public void testTypingMinigameIncompleteAnswer() {
        minigame.getQuestion();
        String[] solutions = getLastSolution(minigame);

        String partialAnswer = "";
        for (int i = 0; i < solutions.length - 1; i++) {
            partialAnswer += solutions[i] + " ";
        }

        minigame.setAnswer(partialAnswer);
        assertFalse(minigame.hasWon());
    }
}