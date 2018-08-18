package com.thoughtworks.gauge.maven.Response.POJO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class Fixture {
    private FootballFullState footballFullState;
    private String fixtureId;
    private FixtureStatus fixtureStatus;
}