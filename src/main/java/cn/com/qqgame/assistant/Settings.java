package cn.com.qqgame.assistant;

public class Settings {
    public static final Settings INSTANCE = new Settings();
    private Assistant currentAssistant = null;

    private Settings() {
    }

    public Assistant getCurrentAssistant() {
        return currentAssistant;
    }

    public void setCurrentAssistant(Assistant currentAssistant) {
        this.currentAssistant = currentAssistant;
    }
}
