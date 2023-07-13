package cn.pigeon.cross_chat;

import static cn.pigeon.cross_chat.Static.Command.BaseCommand.*;
import static cn.pigeon.cross_chat.Static.Command.FullCommand.*;

public class Static {
    public static final String configVersion = "0.1.1";
    public static final String configFileName = "cross_chat.cfg";

    public static class DataType {
        public static final String LOGIN = "login";
        public static final String LOGOUT = "logout";
        public static final String MESSAGE = "message";
    }

    public static class Command {

        public static class BaseCommand {
            public static final String rootCommand = "crosschat";
            public static final String reloadCommand = "reload";
            public static final String setCommand = "set";
            public static final String setNameCommand = "name";
            public static final String setHostCommand = "host";
            public static final String setOutputCommand = "output";
            public static final String setLoginMessageCommand = "login";
            public static final String setLogoutMessageCommand = "logout";
        }

        public static class FullCommand {
            public static final String rootCommandFull = "/" + rootCommand;
            public static final String reloadCommandFull = rootCommandFull + " " + reloadCommand;
            public static final String setCommandFull = rootCommandFull + " " + setCommand;
            public static final String setNameCommandFull = setCommandFull + " " + setNameCommand;
            public static final String setHostCommandFull = setCommandFull + " " + setHostCommand;
            public static final String setOutputCommandFull = setCommandFull + " " + setOutputCommand;
            public static final String setLoginMessageCommandFull = setCommandFull + " " + setLoginMessageCommand;
            public static final String setLogoutMessageCommandFull = setCommand + " " + setLogoutMessageCommand;
        }

        public static class CommandUsage {
            public static final String rootCommandUsage = rootCommandFull + " [" + reloadCommand + "|" + setCommand + "]";
            public static final String reloadCommandUsage = reloadCommandFull;
            public static final String setCommandUsage = setCommandFull + " [" + setNameCommand + "|" + setHostCommand + "|" + setOutputCommand + "|" + setLoginMessageCommand + "|" + setLogoutMessageCommand + "]";
            public static final String setNameCommandUsage = setNameCommandFull + " [ServerName]";
            public static final String setHostCommandUsage = setHostCommandFull + " [Host]";
            public static final String setOutputCommandUsage = setOutputCommandFull + " [OutputFormat]";
            public static final String setLoginMessageCommandUsage = setLoginMessageCommandFull + " [OutputFormat]";
            public static final String setLogoutMessageCommandUsage = setLogoutMessageCommandFull + " [OutputFormat]";
        }
    }
}
