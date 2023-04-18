package com.example.testjavafx;

import com.example.testjavafx.RegistrationForm;

import java.io.Serializable;

public class InscriptionStreamObject implements Serializable {

    private String cmd;
    private RegistrationForm arg;

    public InscriptionStreamObject(String cmd, RegistrationForm arg) {
        this.cmd = cmd;
        this.arg = arg;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public RegistrationForm getArg() {
        return arg;
    }

    public void setArg(RegistrationForm arg) {
        this.arg = arg;
    }
}

