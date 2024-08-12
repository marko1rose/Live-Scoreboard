package coding.exercise;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Scoreboard {
    private List<Match> matches;

    private static final String MATCH_NOT_FOUND = "Match not found.";

    public Scoreboard() {
        this.matches = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized void startMatch(String homeTeam, String awayTeam) {
        if (StringUtils.isAnyBlank(homeTeam, awayTeam)) {
            throw new IllegalArgumentException("Team name cannot be null or blank.");
        }

        if (matches.stream().anyMatch(match -> matchExists(homeTeam, awayTeam))) {
            throw new IllegalArgumentException("One or both teams are already playing in another match.");
        }
        matches.add(new Match(homeTeam, awayTeam));
    }

    public synchronized void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative.");
        }

        Match match = getMatch(homeTeam, awayTeam).orElseThrow(() -> new IllegalArgumentException(MATCH_NOT_FOUND));
        match.updateScore(homeScore, awayScore);
    }

    public synchronized void finishMatch(String homeTeam, String awayTeam) {
        Match match = getMatch(homeTeam, awayTeam).orElseThrow(() -> new IllegalArgumentException(MATCH_NOT_FOUND));
        matches.remove(match);
    }

    public synchronized List<Match> getSummary() {
        return matches.stream()
                .sorted(Comparator.comparing(Match::getTotalScore)
                        .reversed()
                        .thenComparing(Match::getStartTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private boolean matchExists(String homeTeam, String awayTeam) {
        return matches.stream().anyMatch(match ->
                match.getHomeTeam().equals(homeTeam) ||
                        match.getAwayTeam().equals(homeTeam) ||
                        match.getHomeTeam().equals(awayTeam) ||
                        match.getAwayTeam().equals(awayTeam)
        );
    }

    private Optional<Match> getMatch(String homeTeam, String awayTeam) {
        return matches.stream().filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)).findFirst();
    }
}
