package com.javarush.island.pukhov.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString
@Getter
public class ConfigurationObjectIsland {
    @JsonProperty("weight")
    int maxWeight;
    int maxCount;
    int maxSpeed;
    double needCountFood;
    Map<String,Integer> mapFood;
}
