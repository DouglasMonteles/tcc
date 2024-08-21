package org.fga.tcc.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Depute {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("siglaPartido")
    private String partyAcronym;

    @JsonProperty("idLegislatura")
    private Integer legislatureId;

    @JsonProperty("urlFoto")
    private String pictureUrl;

}
