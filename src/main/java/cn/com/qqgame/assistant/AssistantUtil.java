package cn.com.qqgame.assistant;

import java.util.Map;

import javax.swing.JButton;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.sikuli.script.Screen;

import cn.com.qqgame.assistant.gui.AssistantGui;
import cn.com.qqgame.assistant.helper.LogHelper;

public class AssistantUtil {
    // Create a screen region object that corresponds to the default
    // monitor in full screen
    public static Screen SCREEN = null;

    private AssistantUtil() {
    }

    public static void sleep(final long millis) {
        if(millis > 0) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }

    /**
     * 运行辅助工具。
     *
     * @throws Exception
     */
    public static void startAssistant() throws Exception {
        Assistant assistant = getAssistant();
        assistant.start();

        AssistantGui.getInstance().getGameComboBox().setEnabled(false);
        AssistantGui.getInstance().getAutoStartGameCheckBox().setEnabled(false);
        AssistantGui.getInstance().getDelayTimeSlider().setEnabled(false);

        JButton button = AssistantGui.getInstance().getStartButton();
        button.setText(AssistantGui.STOP);
        button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));

        LogHelper.debug("Start running assistant: %s.", assistant.getClass().getName());
    }

    /**
     * 停止辅助工具。
     */
    public static void stopAssistant() {
        Assistant assistant = Settings.INSTANCE.getCurrentAssistant();
        if (assistant != null) {
            assistant.stop();

            AssistantGui.getInstance().getGameComboBox().setEnabled(true);
            AssistantGui.getInstance().getAutoStartGameCheckBox().setEnabled(true);
            AssistantGui.getInstance().getDelayTimeSlider().setEnabled(true);

            JButton button = AssistantGui.getInstance().getStartButton();
            button.setText(AssistantGui.START);
            button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));

            LogHelper.debug("Stop running assistant: %s.", assistant.getClass().getName());
        }
    }

    private static Assistant getAssistant() throws Exception {
        for (Map.Entry<String, String> entry : AssistantGui.GAME_MAP.entrySet()) {
            if (entry.getValue().equals(AssistantGui.getInstance().getGameComboBox().getSelectedItem())) {
                return (Assistant) Class.forName(entry.getKey()).newInstance();
            }
        }

        return null;
    }
}
