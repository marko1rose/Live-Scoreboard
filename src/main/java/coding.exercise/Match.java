package coding.exercise;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class Match {

    private static final AtomicLong startTimeCounter = new AtomicLong();
    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;
    private long startTime;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = startTimeCounter.incrementAndGet();
    }

    public void updateScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }
}
