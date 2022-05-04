package com.swf.electionresult.error;

public class ConstituencyNotFoundException extends Exception {
    public ConstituencyNotFoundException(String constituency) {
        super(constituency);
    }
}
