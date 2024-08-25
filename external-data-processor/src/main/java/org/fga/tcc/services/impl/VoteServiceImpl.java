package org.fga.tcc.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.fga.tcc.entities.DeputySpeech;
import org.fga.tcc.entities.OpenDataBaseResponse;
import org.fga.tcc.entities.Vote;
import org.fga.tcc.enums.OpenDataEndpoints;
import org.fga.tcc.json.FetchJson;
import org.fga.tcc.json.RouterManager;
import org.fga.tcc.services.VoteService;
import org.fga.tcc.utils.ResourceUtils;

import java.util.List;

public class VoteServiceImpl implements VoteService {

    public static void main(String[] args) {
        VoteService voteService = new VoteServiceImpl();
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

        voteService.getVotes(2020);
    }

    @Override
    public List<Vote> getVotes(int year) {
        RouterManager routerManager = new RouterManager();
        FetchJson<Vote> fetchJson = new FetchJson<>();
        OpenDataBaseResponse<Vote> voteOpenDataBaseResponse = fetchJson.get(
                routerManager
                        .setUrl(OpenDataEndpoints.API_VOTES_URL.getPath())
                        .setJsonName(String.valueOf(year))
                        .getUrl(),
                new TypeReference<>() {}
        );

        return voteOpenDataBaseResponse.getData();
    }

    @Override
    public void savePureData() {
//        for (Vote deputy : getVotes()) {
//            int voteCont = 0;
//            List<DeputySpeech> deputySpeeches = getDeputySpeech(deputy.getId());
//            String path = ResourceUtils.RESOURCE_TRAINING_PURE_DATA_PATH + "/" + "deputies/speeches/" + deputy.getId() + "/" + speechCont + ".txt";
//            File file = FileUtils.createFile(path);
//
//            FileUtils.saveTxtFile(file.getAbsolutePath(), deputySpeech.getSummary());
//            voteCont++;
//        }
    }

    private String getNormalizedSpeechType(DeputySpeech deputySpeech) {
        /*
        * If we use a cont variable, it's possible that the two or more
        * speeches (different deputies) has the same cont. Then, in this case,
        * the timestamp will be used instead.
        * */
        long timestamp = System.currentTimeMillis();

        String normalizedSpeechType = deputySpeech.getSpeechType()
                .replaceAll(" ", "_")
                .replaceAll("Í", "I")
                .replaceAll("Ã", "A")
                .replaceAll("Ç", "C")
                .replaceAll("Õ", "O")
                .replaceAll("Ê", "E")
                .trim();

        return ResourceUtils.RESOURCE_TRAINING_PURE_DATA_PATH + "/" + "deputies/speechesTypes/" + normalizedSpeechType + "/" + timestamp + ".txt";
    }

}
