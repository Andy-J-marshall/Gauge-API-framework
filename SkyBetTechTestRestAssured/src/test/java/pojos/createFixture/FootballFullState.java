package pojos.createFixture;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class FootballFullState {
    private String period;
    private List<Team> teams = null;
    private String awayTeam;
    private Boolean finished;
    private Boolean started;
    private List<Object> redCards = null;
    private List<Object> possibles = null;
    private Integer gameTimeInSeconds;
    private List<Object> corners = null;
    private String startDateTime;
    private String homeTeam;
    private List<Object> yellowCards = null;
    private List<Goal> goals = null;
}