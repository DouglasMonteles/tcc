package org.fga.tcc.enums;

public enum OpenDataEndpoints {

    API_DEPUTES_URL("https://dadosabertos.camara.leg.br/api/v2/deputados");

    private final StringBuilder path;

    OpenDataEndpoints(String path) {
        this.path = new StringBuilder(path);
    }

    public String getPath() {
        return this.path.toString();
    }

    public OpenDataEndpoints setPage(Integer page) {
        this.addSpecialCharacters();

        this.path.append("pagina=");
        this.path.append(page);
        return this;
    }

    public OpenDataEndpoints setOrder(String order) {
        this.addSpecialCharacters();

        this.path.append("ordem=");
        this.path.append(order);
        return this;
    }

    public OpenDataEndpoints setOrderBy(String orderBy) {
        this.addSpecialCharacters();

        this.path.append("ordenarPor=");
        this.path.append(orderBy);
        return this;
    }

    /*
    * @param initialDate AAAA-MM-DD
    * @param finalDate AAAA-MM-DD
    * */
    public OpenDataEndpoints setInterval(String initialDate, String finalDate) {
        this.addSpecialCharacters();

        this.path.append("dataInicio=");
        this.path.append(initialDate);

        this.path.append("&");

        this.path.append("dataFim=");
        this.path.append(finalDate);

        return this;
    }

    private void addSpecialCharacters() {
        // In case of this param to be the first
        if (this.path.indexOf("?") == -1) {
            this.path.append("?");
        }

        // In case of this query param not to be the first
        if (this.path.indexOf("=") != -1) {
            this.path.append("&");
        }
    }

}
