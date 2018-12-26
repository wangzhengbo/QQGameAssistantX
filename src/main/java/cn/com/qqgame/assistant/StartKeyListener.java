package cn.com.qqgame.assistant;

import org.sikuli.basics.HotkeyEvent;
import org.sikuli.basics.HotkeyListener;

import cn.com.qqgame.assistant.helper.LogHelper;

/**
 * 监听Esc按键，用于停止辅助工具。
 *
 * @author zhengbo.wang
 *
 */
public class StartKeyListener extends HotkeyListener {
    @Override
    public void hotkeyPressed(HotkeyEvent event) {
        try {
            AssistantUtil.startAssistant();
        } catch (Exception e) {
            LogHelper.error(e);
        }
    }
}
