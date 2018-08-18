package com.thoughtworks.gauge.maven.Response.POJO;

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
    private Integer id;
    private Boolean confirmed;
    private Boolean ownGoal;
    private Integer clockTime;
    private Integer playerId;
}