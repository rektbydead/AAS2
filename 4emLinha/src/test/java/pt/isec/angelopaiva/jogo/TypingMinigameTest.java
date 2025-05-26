package pt.isec.angelopaiva.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.angelopaiva.jogo.logica.dados.minigames.MathMinigame;

import java.text.DecimalFormat;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TypingMinigameTest {

    /*
    * Data flow testing on math minigame setAnswer
    */
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private MathMinigame minigame;

    @BeforeEach
    void setUp() {
        minigame = new MathMinigame();
    }

    private double getLastSolution(MathMinigame minigame) {
        try {
            var field = MathMinigame.class.getDeclaredField("lastSolution");
            field.setAccessible(true);
            return (double) field.get(minigame);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getMaxSecond(MathMinigame minigame) {
        try {
            var field = MathMinigame.class.getDeclaredField("MAX_SECONDS");
            field.setAccessible(true);
            return (int) field.get(minigame);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getNrRightAnswers(MathMinigame minigame) {
        try {
            var field = MathMinigame.class.getDeclaredField("nrRightAnswers");
            field.setAccessible(true);
            return (int) field.get(minigame);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getRequiredNrRightAnswer(MathMinigame minigame) {
        try {
            var field = MathMinigame.class.getDeclaredField("REQUIRED_NR_RIGHT_ANSWERS");
            field.setAccessible(true);
            return (int) field.get(minigame);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testMathMinigameCorrectAnswer() {
        String question = minigame.getQuestion();
        double solution = getLastSolution(minigame);

        String solutionAsString = decimalFormat.format(solution);
        minigame.setAnswer(solutionAsString);

        int nrRightAnswers = getNrRightAnswers(minigame);
        assertEquals(1, nrRightAnswers);
    }

    @Test
    public void testMathMinigameWrongAnswer() {
        String question = minigame.getQuestion();
        double wrongSolution = getLastSolution(minigame) + 10; // guarantee wrong answer

        String wrongSolutionAsString = decimalFormat.format(wrongSolution);
        minigame.setAnswer(wrongSolutionAsString);

        int nrRightAnswers = getNrRightAnswers(minigame);
        assertEquals(0, nrRightAnswers);
    }

    @Test
    public void testMathMinigameInvalidAnswer() {
        String question = minigame.getQuestion();
        minigame.setAnswer("abc");
        int nrRightAnswers = getNrRightAnswers(minigame);
        assertEquals(0, nrRightAnswers);
    }

    @Test
    public void testMathMinigameEmptyAnswer() {
        String question = minigame.getQuestion();
        minigame.setAnswer("");
        int nrRightAnswers = getNrRightAnswers(minigame);
        assertEquals(0, nrRightAnswers);
    }

    @Test
    public void testMathMinigameNullAnswer() {
        String question = minigame.getQuestion();
        minigame.setAnswer(null);
        int nrRightAnswers = getNrRightAnswers(minigame);
        assertEquals(0, nrRightAnswers);
    }

    @Test
    public void testMathMinigameFinishedRequiredRightAnswerAchieved() {
        for (int i = 0; i < getRequiredNrRightAnswer(minigame); i++) {
            String question = minigame.getQuestion();
            double solution = getLastSolution(minigame);
            String solutionAsString = decimalFormat.format(solution);
            minigame.setAnswer(solutionAsString);
        }

        assertTrue(minigame.isFinished());
    }

    @Test
    public void testMathMinigameFinishedOnlyOneAnswer() {
        String question = minigame.getQuestion();
        double solution = getLastSolution(minigame);
        String solutionAsString = decimalFormat.format(solution);
        minigame.setAnswer(solutionAsString);

        assertFalse(minigame.isFinished());
    }

    @Test
    public void testMathMinigameFinishedBecauseTimeout() throws InterruptedException {
        int secondsTimeOut = getMaxSecond(minigame) + 2;
        Thread.sleep(Duration.ofSeconds(secondsTimeOut));

        String question = minigame.getQuestion();
        double solution = getLastSolution(minigame);
        String solutionAsString = decimalFormat.format(solution);
        minigame.setAnswer(solutionAsString);

        assertTrue(minigame.isFinished());
    }

    @Test
    public void testMathMinigameHasWon() {
        for (int i = 0; i < getRequiredNrRightAnswer(minigame); i++) {
            String question = minigame.getQuestion();
            double solution = getLastSolution(minigame);
            String solutionAsString = decimalFormat.format(solution);
            minigame.setAnswer(solutionAsString);
        }

        assertTrue(minigame.hasWon());
    }

    @Test
    public void testMathMinigameHasWonFailedBecauseTimeout() throws InterruptedException {
        for (int i = 0; i < getRequiredNrRightAnswer(minigame); i++) {
            if (i == getRequiredNrRightAnswer(minigame) - 1) {
                int secondsTimeOut = getMaxSecond(minigame) + 2;
                Thread.sleep(Duration.ofSeconds(secondsTimeOut));
            }

            String question = minigame.getQuestion();
            double solution = getLastSolution(minigame);
            String solutionAsString = decimalFormat.format(solution);
            minigame.setAnswer(solutionAsString);
        }

        assertFalse(minigame.hasWon());
    }
}