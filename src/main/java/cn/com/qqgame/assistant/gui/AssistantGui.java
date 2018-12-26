package cn.com.qqgame.assistant.gui;

import java.awt.BorderLayout;
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
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Screen;

import cn.com.qqgame.assistant.AssistantUtil;
import cn.com.qqgame.assistant.SpringUtilities;
import cn.com.qqgame.assistant.StartKeyListener;
import cn.com.qqgame.assistant.StopKeyListener;
import cn.com.qqgame.assistant.helper.LogHelper;

public class AssistantGui extends JFrame {
    private static final long serialVersionUID = 5057456474131539463L;
    private static final String TITLE = "腾讯QQ游戏辅助工具";
    public static final String START = "开始";
    public static final String STOP = "停止";
    private static final String GAME = "游戏";
    private static final String AUTO_START_GAME = "自动开始游戏";
    private static final String DELAY_TIME = "消除后的暂停时间( %3d毫秒 )";
    private static final int DEFAULT_DELAY_TIME = 20; // 20毫秒
    private static final int MIN_DELAY_TIME = 0; // 0毫秒
    private static final int MAX_DELAY_TIME = 200; // 200毫秒

    public static final Map<String, String> GAME_MAP = new LinkedHashMap<String, String>();

    private static AssistantGui INSTANCE = null;

    public final JComboBox<String> gameComboBox;
    public final JCheckBox autoStartGameCheckBox;
    public final JSlider delayTimeSlider;
    public final JButton startButton;

    static {
        GAME_MAP.put(cn.com.qqgame.assistant.duiduipeng.DuiDuiPengAssistant.class.getName(), "对对碰");
    }

    private AssistantGui() {
        setTitle(TITLE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new SpringLayout());

        // Game combo box
        JLabel gameLabel = new JLabel(GAME + "：");
        panel.add(gameLabel);
        gameComboBox = new JComboBox<String>(new Vector<String>(GAME_MAP.values()));
        gameLabel.setLabelFor(gameComboBox);
        gameComboBox.setEditable(false);
        panel.add(gameComboBox);

        // Auto start checkbox
        JLabel autoStartGameLabel = new JLabel(AUTO_START_GAME + "：");
        panel.add(autoStartGameLabel);
        autoStartGameCheckBox = new JCheckBox();
        autoStartGameLabel.setLabelFor(autoStartGameCheckBox);
        autoStartGameCheckBox.setSelected(true);
        panel.add(autoStartGameCheckBox);

        // Delay time slider
        final JLabel delayTimeLabel = new JLabel(String.format(DELAY_TIME, DEFAULT_DELAY_TIME) + "：");
        panel.add(delayTimeLabel);
        delayTimeSlider = new JSlider(MIN_DELAY_TIME, MAX_DELAY_TIME);
        delayTimeLabel.setLabelFor(delayTimeSlider);
        delayTimeSlider.setValue(DEFAULT_DELAY_TIME);
        delayTimeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                delayTimeLabel.setText(String.format(DELAY_TIME, delayTimeSlider.getValue()) + "：");
            }
        });

        // 设置绘制刻度
        delayTimeSlider.setPaintTicks(true);

        // 设置大刻度与小刻度之间的距离
        delayTimeSlider.setMajorTickSpacing(MAX_DELAY_TIME / 10);
        delayTimeSlider.setMinorTickSpacing(2);

        // 设置是否标记数字
        delayTimeSlider.setPaintLabels(true);

        panel.add(delayTimeSlider);

        // Button
        panel.add(new JLabel());
        startButton = new JButton(START);
        startButton.addActionListener(new StartActionListener());
        startButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        panel.add(startButton);

        // Lay out the panel.
        SpringUtilities.makeCompactGrid(panel, 4, 2, // rows, cols
                6, 6, // initX, initY
                6, 6); // xPad, yPad

        JPanel p = new JPanel(new BorderLayout());
        p.add(panel, BorderLayout.CENTER);
        setContentPane(p);

        // Display the window.
        pack();
        setSize(600, (int) getSize().getHeight());
        setVisible(true);
    }

    private static class StartActionListener implements ActionListener {
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

    public JComboBox<String> getGameComboBox() {
        return gameComboBox;
    }

    public JCheckBox getAutoStartGameCheckBox() {
        return autoStartGameCheckBox;
    }

    public JSlider getDelayTimeSlider() {
        return delayTimeSlider;
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
        Key.addHotkey(Key.ESC, 0, new StopKeyListener());
        Key.addHotkey('S', KeyModifier.CTRL + KeyModifier.SHIFT, new StartKeyListener());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AssistantGui.getInstance();
            }
        });
    }
}
