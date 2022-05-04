package com.swf.electionresult.factory;

import com.swf.electionresult.domain.valueobject.PartyCode;
import com.swf.electionresult.error.InvalidPartyCodeException;

public class PartyCodeFactory {

    public PartyCode createFrom(String rawPartyCode) throws InvalidPartyCodeException {
        try {
            return PartyCode.valueOf(rawPartyCode);
        } catch (Exception e) {
            throw new InvalidPartyCodeException(rawPartyCode);
        }
    }
}
