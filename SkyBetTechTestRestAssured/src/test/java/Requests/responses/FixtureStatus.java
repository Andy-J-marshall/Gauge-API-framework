package com.thoughtworks.gauge.maven.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class FixtureStatus {
    private Boolean displayed;
    private Boolean suspended;
}