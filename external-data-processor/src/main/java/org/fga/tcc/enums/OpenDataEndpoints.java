package org.fga.tcc.enums;

public enum OpenDataEndpoints {

    API_DEPUTES_URL("https://dadosabertos.camara.leg.br/api/v2/deputados");

    private final String path;

    OpenDataEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

}
