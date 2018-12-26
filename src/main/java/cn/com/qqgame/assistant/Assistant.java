package cn.com.qqgame.assistant;

/**
 *
 * @author zhengbo.wang
 *
 */
public interface Assistant {
    // 开始辅助游戏
    void start();

    boolean started();

    // 停止辅助游戏
    void stop();
}
