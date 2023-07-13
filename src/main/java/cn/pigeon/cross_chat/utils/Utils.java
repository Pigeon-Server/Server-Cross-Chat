package cn.pigeon.cross_chat.utils;

import java.util.regex.Pattern;

public class Utils {
    public static final String patternString = "^ws://[\\w.-]+(:\\d+)?([/\\w-]+)?";
    public static final Pattern pattern = Pattern.compile(patternString);

    public static boolean judgeWebsocketAddress(String address) {
        return pattern.matcher(address).matches();
    }
}
