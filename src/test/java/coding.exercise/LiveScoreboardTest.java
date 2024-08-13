package coding.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LiveScoreboardTest {

    private Scoreboard scoreboard;

    @BeforeEach
    public void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    public void testStartMatch() {
        scoreboard.startMatch("Team A", "Team B");
        List<Match> summary = scoreboard.getSummary();
        assertEquals(1, summary.size());
    }

    @Test
    public void testUpdateScore() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.updateScore("Team A", "Team B", 1, 2);
        List<Match> summary = scoreboard.getSummary();
        Match match = summary.getFirst();
        assertEquals(1, match.getHomeScore());
        assertEquals(2, match.getAwayScore());
    }

    @Test
    public void testFinishMatch() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.finishMatch("Team A", "Team B");
        List<Match> summary = scoreboard.getSummary();
        assertEquals(0, summary.size());
    }

    @Test
    public void testGetSummary() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.startMatch("Germany", "France");
        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.startMatch("Argentina", "Australia");

        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.updateScore("Germany", "France", 2, 2);
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreboard.getSummary();

        assertEquals("Uruguay 6 - Italy 6", summary.get(0).toString());
        assertEquals("Spain 10 - Brazil 2", summary.get(1).toString());
        assertEquals("Mexico 0 - Canada 5", summary.get(2).toString());
        assertEquals("Argentina 3 - Australia 1", summary.get(3).toString());
        assertEquals("Germany 2 - France 2", summary.get(4).toString());
    }

    @Test
    public void testStartMatchWithTeamAlreadyPlaying() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Team A", "Team C"));
        assertEquals("One or both teams are already playing in another match.", exception.getMessage());
    }

    @Test
    public void testUpdateScoreForNonExistentMatch() {
        Scoreboard scoreboard = new Scoreboard();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("Team A", "Team B", 1, 2));
        assertEquals("Match not found.", exception.getMessage());
    }

    @Test
    public void testFinishNonExistentMatch() {
        Scoreboard scoreboard = new Scoreboard();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.finishMatch("Team A", "Team B"));
        assertEquals("Match not found.", exception.getMessage());
    }

    @Test
    public void testStartMatchWithInvalidTeamNames() {
        Scoreboard scoreboard = new Scoreboard();

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("", "Team B"));
        assertEquals("Team name cannot be null or blank.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Team A", null));
        assertEquals("Team name cannot be null or blank.", exception2.getMessage());
    }

    @Test
    public void testUpdateScoreWithInvalidValues() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("Team A", "Team B", -1, 2));
        assertEquals("Score cannot be negative.", exception.getMessage());
    }

    @Test
    public void testStartMatchAfterFinishingPreviousMatch() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.finishMatch("Team A", "Team B");

        scoreboard.startMatch("Team A", "Team B");
        assertEquals(1, scoreboard.getSummary().size());
    }
}
