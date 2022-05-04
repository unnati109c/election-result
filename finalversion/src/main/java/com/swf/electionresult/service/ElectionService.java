package com.swf.electionresult.service;

import com.swf.electionresult.domain.service.WinningPartyProcessor;
import com.swf.electionresult.domain.valueobject.VoteShare;
import com.swf.electionresult.dto.ElectionDataDto;
import com.swf.electionresult.dto.VotesShareDto;
import com.swf.electionresult.dto.WinningParty;
import com.swf.electionresult.error.ConstituencyNotFoundException;
import com.swf.electionresult.error.InvalidPartyCodeException;
import com.swf.electionresult.error.RedundantElectionResultsForConstituencyException;
import com.swf.electionresult.error.RedundantPartyCodeForConstituencyException;
import com.swf.electionresult.factory.PartyCodeFactory;
import com.swf.electionresult.repository.ElectionResultRepository;

import java.util.ArrayList;
import java.util.List;

public class ElectionService {
    private final ElectionResultRepository electionResultRepository;
    private final WinningPartyProcessor winningPartyProcessor;
    private final PartyCodeFactory partyCodeFactory;

    public ElectionService(ElectionResultRepository electionResultRepository,
                           WinningPartyProcessor winningPartyProcessor, PartyCodeFactory partyCodeFactory) {
        this.electionResultRepository = electionResultRepository;
        this.winningPartyProcessor = winningPartyProcessor;
        this.partyCodeFactory = partyCodeFactory;
    }

    public void saveElectionResults(List<ElectionDataDto> electionDataTokens)
            throws InvalidPartyCodeException, RedundantElectionResultsForConstituencyException,
            RedundantPartyCodeForConstituencyException {

        for (ElectionDataDto tokens : electionDataTokens) {
            List<VoteShare> voteShares = new ArrayList<>();
            for (VotesShareDto c : tokens.getVoteShares()) {
                VoteShare voteShare = new VoteShare(partyCodeFactory.createFrom(c.getPartyCode()), c.getVotes());
                for (VoteShare v : voteShares)
                    if (voteShare.getPartyCode().equals(v.getPartyCode()))
                        throw new RedundantPartyCodeForConstituencyException(
                                tokens.getConstituency() + "-" + voteShare.getPartyCode());
                voteShares.add(voteShare);
            }
            List<String> constituencies = electionResultRepository.getConstituencies();
            if (constituencies.contains(tokens.getConstituency()))
                throw new RedundantElectionResultsForConstituencyException(tokens.getConstituency());
            else
                electionResultRepository.add(tokens.getConstituency(), voteShares);
        }
    }

    public List<WinningParty> getWinners() throws ConstituencyNotFoundException {
        // get list of constituencies from db
        // iterate over the list
        // get maxPartyVotes from db
        // getTotal votes per constituency from db
        // processor

        List<WinningParty> winningParties = new ArrayList<>();
        List<String> constituencies = electionResultRepository.getConstituencies();
        for (String constituency : constituencies) {
            VoteShare maxVotedParty = electionResultRepository.maxVotedParty(constituency);
            int totalVotes = electionResultRepository.totalVotes(constituency);
            winningParties.add(winningPartyProcessor.getWinner(constituency, maxVotedParty, totalVotes));
        }
        return winningParties;
    }
}
