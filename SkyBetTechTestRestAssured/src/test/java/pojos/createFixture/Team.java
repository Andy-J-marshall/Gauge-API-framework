package pojos.createFixture;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class Team {
    private String teamId;
    private String name;
    private String association;
}