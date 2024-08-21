package org.fga.tcc.services.impl;

import org.fga.tcc.entities.Depute;
import org.fga.tcc.entities.OpenDataBaseResponse;
import org.fga.tcc.enums.OpenDataEndpoints;
import org.fga.tcc.json.FetchJson;
import org.fga.tcc.services.DeputeService;

import java.util.List;

public class DeputeServiceImpl implements DeputeService {

    public static void main(String[] args) {
        DeputeService deputeService = new DeputeServiceImpl();
        System.out.println(deputeService.getDeputes());
    }

    @Override
    public List<Depute> getDeputes() {
        FetchJson<Depute> fetchJson = new FetchJson<>();
        OpenDataBaseResponse<Depute> deputeOpenDataBaseResponse = fetchJson.get(
                OpenDataEndpoints.API_DEPUTES_URL
                        .setPage(1)
                        .setOrderBy("nome")
                        .getPath()
        );

        return deputeOpenDataBaseResponse.getData();
    }
}
