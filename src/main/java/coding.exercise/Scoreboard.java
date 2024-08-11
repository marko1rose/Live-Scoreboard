package coding.exercise;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Scoreboard {
    private List<Match> matches;

    public Scoreboard() {
        this.matches = Collections.synchronizedList(new ArrayList<>());
    }

    public void startMatch(String homeTeam, String awayTeam) {
        if (matches.stream().anyMatch(match -> matchExists(match, homeTeam, awayTeam))) {
            throw new IllegalArgumentException("Match between " + homeTeam + " and " + awayTeam + " is already in progress.");
        }

        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        for (Match match : matches) {
            if (matchExists(match, homeTeam, awayTeam)) {
                match.updateScore(homeScore, awayScore);
                return;
            }
        }
        throw new IllegalArgumentException("Match not found!");
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        matches.removeIf(match -> matchExists(match, homeTeam, awayTeam));
    }

    public List<Match> getSummary() {
        return matches.stream()
                .sorted(Comparator.comparing(Match::getTotalScore)
                        .reversed()
                        .thenComparing(Match::getStartTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private boolean matchExists(Match match, String homeTeam, String awayTeam) {
        return match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam);
    }

    private boolean listContainsMatch(String homeTeam, String awayTeam) {

    }
}
