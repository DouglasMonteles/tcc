package org.fga.tcc.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.fga.tcc.entities.Deputy;
import org.fga.tcc.entities.DeputySpeech;
import org.fga.tcc.entities.OpenDataBaseResponse;
import org.fga.tcc.enums.OpenDataEndpoints;
import org.fga.tcc.json.FetchJson;
import org.fga.tcc.json.RouterManager;
import org.fga.tcc.services.DeputyService;
import org.fga.tcc.utils.ResourceInfo;
import org.fga.tcc.utils.FileUtils;

import java.io.File;
import java.util.List;

public class DeputyServiceImpl implements DeputyService {

    public static void main(String[] args) {
        DeputyService deputeService = new DeputyServiceImpl();
//        System.out.println(deputeService.getDeputes());

//        int x = 0;
//
//        for (Deputy d : deputeService.getDeputes()) {
//            deputeService.getDeputySpeech(d.getId());
//            x++;
//
//
//        }
//        System.out.println("x="+x);

        deputeService.savePureData();
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
                        .setInterval("2008-01-01", "2023-12-31")
                        .setItems(1_000_000)
                        .getUrl(),
                new TypeReference<>() {}
        );

        return deputySpeechOpenDataBaseResponse.getData();
    }

    @Override
    public void savePureData() {
        for (Deputy deputy : getDeputes()) {
            int speechCont = 0;
            List<DeputySpeech> deputySpeeches = getDeputySpeech(deputy.getId());

            for (DeputySpeech deputySpeech : deputySpeeches) {
                String path = ResourceInfo.RESOURCE_DEPUTY_SPEECH_DATA_PATH + "/" + deputy.getId() + "/" + speechCont + ".txt";
                File file = FileUtils.createFile(path);

                FileUtils.saveTxtFile(file.getAbsolutePath(), deputySpeech.getSummary());
                speechCont++;
            }
        }
    }
}
