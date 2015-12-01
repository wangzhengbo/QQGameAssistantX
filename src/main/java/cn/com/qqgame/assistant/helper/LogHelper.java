package cn.com.qqgame.assistant.helper;

public final class LogHelper {
    private LogHelper() {

    }

    public static void debug(Object o) {
        System.out.println(o);
    }

    public static void debug(String message, Object... args) {
        debug(String.format(message, args));
    }

    public static void error(Object o) {
        System.err.println(o);
    }

    public static void error(Throwable e) {
        e.printStackTrace(System.err);
    }
}
