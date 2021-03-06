package cn.com.qqgame.assistant;

import org.sikuli.basics.HotkeyEvent;
import org.sikuli.basics.HotkeyListener;

/**
 * 监听Esc按键，用于停止辅助工具。
 *
 * @author zhengbo.wang
 *
 */
public class StopKeyListener extends HotkeyListener {
    @Override
    public void hotkeyPressed(HotkeyEvent event) {
        AssistantUtil.stopAssistant();
    }
}
