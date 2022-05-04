package com.swf.electionresult.error;

public class RedundantPartyCodeForConstituencyException extends Exception {
    public RedundantPartyCodeForConstituencyException(String party) {
        super(party);
    }
}
