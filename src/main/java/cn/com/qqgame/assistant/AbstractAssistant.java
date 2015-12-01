package cn.com.qqgame.assistant;

import org.sikuli.script.ImagePath;

public abstract class AbstractAssistant implements Assistant {
    private static AutoStartGameThread autoStartGameThread;

    static {
        ImagePath.add(AbstractAssistant.class.getCanonicalName() + "/duiduipeng");
    }

    @Override
    public void start() {
        Settings.INSTANCE.setCurrentAssistant(this);
        if (autoStartGameThread == null) {
            autoStartGameThread = new AutoStartGameThread("actions/start");
            autoStartGameThread.start();
        }
    }

    @Override
    public void stop() {
        Settings.INSTANCE.setCurrentAssistant(null);
    }
}
