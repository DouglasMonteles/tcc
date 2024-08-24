package org.fga.tcc.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.fga.tcc.entities.Deputy;
import org.fga.tcc.entities.DeputySpeech;
import org.fga.tcc.entities.OpenDataBaseResponse;
import org.fga.tcc.enums.OpenDataEndpoints;
import org.fga.tcc.json.FetchJson;
import org.fga.tcc.json.RouterManager;
import org.fga.tcc.services.DeputyService;

import java.util.List;

public class DeputyServiceImpl implements DeputyService {

    public static void main(String[] args) {
//        DeputyService deputeService = new DeputyServiceImpl();
//        System.out.println(deputeService.getDeputes());
//
//        int x = 0;
//
//        for (Deputy d : deputeService.getDeputes()) {
//            deputeService.getDeputySpeech(d.getId());
//            x++;
//
//
//        }
//        System.out.println("x="+x);
    }

    @Override
    public List<Deputy> getDeputes() {
        RouterManager routerManager = new RouterManager();
        FetchJson<Deputy> fetchJson = new FetchJson<>();
        OpenDataBaseResponse<Deputy> deputeOpenDataBaseResponse = fetchJson.get(
                routerManager
                        .setUrl(OpenDataEndpoints.API_DEPUTES_URL.getPath())
                        .setPage(1)
                        .setOrderBy("nome")
                        .getUrl(),
                new TypeReference<>() {}
        );

        return deputeOpenDataBaseResponse.getData();
    }

    @Override
    public List<DeputySpeech> getDeputySpeech(Integer deputeId) {
        RouterManager routerManager = new RouterManager();
        FetchJson<DeputySpeech> fetchJson = new FetchJson<>();
        OpenDataBaseResponse<DeputySpeech> deputySpeechOpenDataBaseResponse = fetchJson.get(
                routerManager
                        .setUrl(OpenDataEndpoints.API_DEPUTES_URL.getPath())
                        .setRequestParamId(deputeId)
                        .setRequestUri("discursos")
                        .setItems(1000)
                        .getUrl(),
                new TypeReference<>() {}
        );

        return deputySpeechOpenDataBaseResponse.getData();
    }
}
