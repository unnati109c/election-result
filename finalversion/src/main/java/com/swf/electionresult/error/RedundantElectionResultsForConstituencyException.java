package com.swf.electionresult.error;

public class RedundantElectionResultsForConstituencyException extends Exception {
    public RedundantElectionResultsForConstituencyException(String constituency) {
        super(constituency);
    }
}
