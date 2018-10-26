package pojos.createFixture;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class Goal {
    private String period;
    private Boolean penalty;
    private String teamId;
    private String id;
    private Boolean confirmed;
    private Boolean ownGoal;
    private Integer clockTime;
    private String playerId;
}