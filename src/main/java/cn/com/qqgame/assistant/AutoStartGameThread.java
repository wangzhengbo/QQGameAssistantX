package cn.com.qqgame.assistant;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;

import cn.com.qqgame.assistant.gui.AssistantGui;
import cn.com.qqgame.assistant.helper.LogHelper;

/**
 * 自动开始游戏的线程。
 *
 * @author zhengbo.wang
 */
public class AutoStartGameThread extends Thread {
    /* 检测开始按钮是否显示的时间间隔 */
    private static final int CHECK_INTEVAL = 2000;

    /* 开始按钮目标 */
    private final String startImage;

    public AutoStartGameThread(final String startImage) {
        super(AutoStartGameThread.class.getName());
        this.setDaemon(true);

        if ((startImage == null) || startImage.isEmpty()) {
            throw new NullPointerException("Parameter startImage is required.");
        }

        this.startImage = startImage;
        LogHelper.debug("startImage is " + startImage);
    }

    @Override
    public void run() {
        while (true) {
            if ((Settings.INSTANCE.getCurrentAssistant() != null)
                    && AssistantGui.getInstance().getAutoStartGameCheckBox().isSelected()) {
                // 如果设置为自动开始游戏，那么查找开始按钮，并点击
                try {
                    Match match = AssistantUtil.SCREEN.find(startImage);
                    if (match != null) {
                        RegionUtil.mouseClick(match.getCenter());
                    }
                } catch (FindFailed e) {
                }
            }

            AssistantUtil.sleep(CHECK_INTEVAL);
        }
    }
}
