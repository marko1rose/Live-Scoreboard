# Live-Scoreboard
This Java library allows you to manage live football World Cup matches, tracking their scores and providing a summary of ongoing matches.

## Features
- Start a new match.
- Update the score of an ongoing match.
- Finish a match.
- Get a summary of matches sorted by total score and start time.

## How to Use

### Start a match
```
Scoreboard scoreboard = new Scoreboard();
scoreboard.startMatch("Team A", "Team B");
```

### Update the score
```
scoreboard.updateScore("Team A", "Team B", 1, 2);
```

### Finish a match
```
scoreboard.finishMatch("Team A", "Team B");
```

### Get Summary of matches
```
List<Match> summary = scoreboard.getSummary();
for (Match match : summary) {
    System.out.println(match);
}
```

### Design Decisions
- Match start time: The start time of a match is managed using an AtomicLong to ensure uniqueness and order even when matches are started in quick succession.
- Synchronized Methods: All operations are synchronized, ensuring thread safety when multiple threads interact with the Scoreboard.
- Validation:
  - Team names are neither null nor blank.
  - Teams cannot be involved in more than one match simultaneously.
  - Scores must be non-negative.
  - IllegalArgumentException is used when validation fails
