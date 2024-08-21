package org.fga.tcc.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenDataBaseResponse<T> {

    @JsonProperty("dados")
    private final List<T> data = new ArrayList<>();

    @JsonProperty("links")
    private final List<Link> links = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Link {
        @JsonProperty("rel")
        private String rel;

        @JsonProperty("href")
        private String href;
    }

}
