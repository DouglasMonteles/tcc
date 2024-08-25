package org.fga.tcc.services;

import org.fga.tcc.entities.Deputy;
import org.fga.tcc.entities.DeputySpeech;
import org.fga.tcc.entities.Vote;

import java.util.List;

public interface VoteService {

    List<Vote> getVotes(int page);

    void savePureData();
}
