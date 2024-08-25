package org.fga.tcc.enums;

import lombok.Getter;

@Getter
public enum OpenDataEndpoints {

    API_DEPUTES_URL("https://dadosabertos.camara.leg.br/api/v2/deputados"),

    /*
    * In the case of votes, the url used returns the json data based on the year.
    * The url respects the format:
    * https://dadosabertos.camara.leg.br/arquivos/votacoes/json/votacoes-{year}.json
    * */
    API_VOTES_URL("https://dadosabertos.camara.leg.br/arquivos/votacoes/json/votacoes-");

    private final String path;

    OpenDataEndpoints(String path) {
        this.path = path;
    }

}
