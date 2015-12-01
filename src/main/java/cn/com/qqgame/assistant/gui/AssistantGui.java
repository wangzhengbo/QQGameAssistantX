package cn.com.qqgame.assistant.gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;

import cn.com.qqgame.assistant.AssistantUtil;
import cn.com.qqgame.assistant.GlobalEscKeyListener;
import cn.com.qqgame.assistant.helper.LogHelper;
import jp.co.tlt.Selfie3.guitools.TableLayout.TableLayout;

public class AssistantGui extends JFrame {
    private static final long serialVersionUID = 5057456474131539463L;
    private static final String TITLE = "腾讯QQ游戏辅助工具";
    public static final String START = "开始";
    public static final String STOP = "停止";
    private static final String GAME = "游戏";
    private static final String AUTO_START_GAME = "自动开始游戏";
    private static final int TABLE_LAYOUT_ROW_HEIGHT = 34;
    private static final int TABLE_LAYOUT_LEFT_COLUMN_WIDTH = 90;
    private static final int TABLE_LAYOUT_RIGHT_COLUMN_WIDTH = 240;

    public static final Map<String, String> GAME_MAP = new LinkedHashMap<String, String>();

    private static AssistantGui INSTANCE = null;

    public final JComboBox gameComboBox;
    public final JCheckBox autoStartGameCheckBox;
    public final JButton startButton;

    static {
        GAME_MAP.put(cn.com.qqgame.assistant.duiduipeng.DuiDuiPengAssistant.class.getName(), "对对碰");
    }

    private AssistantGui() {
        setTitle(TITLE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        final TableLayout tableLayout = new TableLayout(new Insets(5, 5, 5, 5));
        tableLayout.addTR(TABLE_LAYOUT_ROW_HEIGHT).addTD(TABLE_LAYOUT_LEFT_COLUMN_WIDTH)
                .addTD(TABLE_LAYOUT_RIGHT_COLUMN_WIDTH).repeat(3);
        panel.setLayout(tableLayout);
        getContentPane().add(panel);

        // Game combo box
        JLabel gameLabel = new JLabel(GAME + "：");
        panel.add(gameLabel);
        gameComboBox = new JComboBox(new Vector<String>(GAME_MAP.values()));
        gameComboBox.setEditable(false);
        panel.add(gameComboBox);

        // Auto start checkbox
        JLabel autoStartGameLabel = new JLabel(AUTO_START_GAME + "：");
        panel.add(autoStartGameLabel);
        autoStartGameCheckBox = new JCheckBox();
        autoStartGameCheckBox.setSelected(true);
        panel.add(autoStartGameCheckBox);

        // Button
        panel.add(new JLabel());
        startButton = new JButton(START);
        startButton.addActionListener(new StartActionListener());
        startButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        panel.add(startButton);

        // 设置窗体的大小
        setSize(new Dimension(400, 200));
    }

    private static class StartActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JButton button = (JButton) event.getSource();

            try {
                if (START.equals(button.getText())) {
                    // 开始辅助
                    AssistantUtil.startAssistant();
                } else {
                    // 停止辅助
                    AssistantUtil.stopAssistant();
                }
            } catch (Exception e) {
                LogHelper.error(e);
            }
        }
    }

    public static AssistantGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AssistantGui();
        }
        return INSTANCE;
    }

    public JComboBox getGameComboBox() {
        return gameComboBox;
    }

    public JCheckBox getAutoStartGameCheckBox() {
        return autoStartGameCheckBox;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public static void main(String[] args) {
        AssistantUtil.SCREEN = new Screen();

        // 使用beautyeye外观
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);
        } catch (Exception e) {
            LogHelper.error(e);
        }

        // 监听Esc按键，用于停止辅助工具
        Key.addHotkey(Key.ESC, 0, new GlobalEscKeyListener());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AssistantGui.getInstance().setVisible(true);
            }
        });
    }
}
