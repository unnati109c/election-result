package com.swf.electionresult.error;

public class InvalidPartyCodeException extends Exception {
    public InvalidPartyCodeException(String rawPartyCode) {
        super(rawPartyCode);
    }
}
