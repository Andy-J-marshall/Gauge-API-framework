package com.thoughtworks.gauge.maven.responses;

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