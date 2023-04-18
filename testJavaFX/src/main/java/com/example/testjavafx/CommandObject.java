package com.example.testjavafx;

import java.io.Serializable;

public class CommandObject implements Serializable {

    private String cmd;
    private String arg;

    public CommandObject(String cmd, String arg) {
        this.cmd = cmd;
        this.arg = arg;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    @Override
    public String toString() {
        return "CommandObject{" +
                "arg=" + arg +
                ", cmd=" + cmd +
                '}';
    }
}
