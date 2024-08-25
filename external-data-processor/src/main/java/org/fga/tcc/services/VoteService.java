package org.fga.tcc.services;

import org.fga.tcc.entities.Voting;

import java.util.List;

public interface VoteService {

    List<Voting> getVotingByYear(int page);

    void savePureData();
}
