package cn.com.qqgame.assistant;

import org.sikuli.script.Location;

import cn.com.jautoitx.Mouse;
import cn.com.jautoitx.Mouse.MouseButton;
import cn.com.qqgame.assistant.gui.AssistantGui;
import cn.com.jautoitx.Pixel;

public final class RegionUtil {
    // 对对碰格子的宽度
    public static final int BOX_WIDTH = 48; // 48px

    // 对对碰格子的高度
    public static final int BOX_HEIGHT = BOX_WIDTH; // 48px

    // 在判断格子位置时允许的误差
    private static final int DEVIATION = 2; // 2px

    private static final Object MOUSE_LOCK_OBJECT = new Object();

    private RegionUtil() {
    }

    /**
     * 是否在同一行中。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isSameRow(final Location location1, Location location2) {
        return Math.abs(location1.getY() - location2.getY()) <= DEVIATION;
    }

    /**
     * 是否行相邻。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isRowAdjacent(final Location location1, Location location2) {
        return between(Math.abs(location1.getY() - location2.getY()), BOX_HEIGHT - DEVIATION, BOX_HEIGHT + DEVIATION);
    }

    /**
     * 是否间隔一行。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isRowSeparated(final Location location1, Location location2) {
        return between(Math.abs(location1.getY() - location2.getY()), 2 * BOX_HEIGHT - DEVIATION,
                2 * BOX_HEIGHT + DEVIATION);
    }

    /**
     * 是否在同一行中且相邻。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isInSameRowAndAdjacent(final Location location1, Location location2) {
        return isSameRow(location1, location2) && isColumnAdjacent(location1, location2);
    }

    /**
     * 是否在同一行中且间隔一列。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isInSameRowAndSeparated(final Location location1, Location location2) {
        return isSameRow(location1, location2) && isColumnSeparated(location1, location2);
    }

    /**
     * 是否在同一列中。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isSameColumn(final Location location1, Location location2) {
        return Math.abs(location1.getX() - location2.getX()) <= DEVIATION;
    }

    /**
     * 是否列相邻。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isColumnAdjacent(final Location location1, Location location2) {
        return between(Math.abs(location1.getX() - location2.getX()), BOX_WIDTH - DEVIATION, BOX_WIDTH + DEVIATION);
    }

    /**
     * 是否间隔一列。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isColumnSeparated(final Location location1, Location location2) {
        return between(Math.abs(location1.getX() - location2.getX()), 2 * BOX_WIDTH - DEVIATION,
                2 * BOX_WIDTH + DEVIATION);
    }

    /**
     * 是否在同一列中且相邻。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isInSameColumnAndAdjacent(final Location location1, Location location2) {
        return isSameColumn(location1, location2) && isRowAdjacent(location1, location2);
    }

    /**
     * 是否在同一列中且相隔一个方块。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isInSameColumnAndSeparated(final Location location1, Location location2) {
        return isSameColumn(location1, location2) && isRowSeparated(location1, location2);
    }

    /**
     * 是否在对角。
     *
     * @param location1
     * @param location2
     * @return
     */
    public static boolean isDiagonal(final Location location1, Location location2) {
        return isRowAdjacent(location1, location2) && isColumnAdjacent(location1, location2);
    }

    /**
     * 通过鼠标点击交换2个图片。
     *
     * @param location1
     * @param location2
     */
    public static void swapImage(final Location location1, Location location2) {
        if (Settings.INSTANCE.getCurrentAssistant() == null) {
            return;
        }

        synchronized (MOUSE_LOCK_OBJECT) {
            int checksum = Pixel.checksum(location1.getX() - BOX_WIDTH, location1.getY() - BOX_HEIGHT,
                    location1.getX() + BOX_WIDTH, location1.getY() + BOX_HEIGHT);
            mouseClick(location1);
            AssistantUtil.sleep(5);
            if (checksum != (Pixel.checksum(location1.getX() - BOX_WIDTH, location1.getY() - BOX_HEIGHT,
                    location1.getX() + BOX_WIDTH, location1.getY() + BOX_HEIGHT))) {
                // 第一次点击有效才点击第二次
                mouseClick(location2);
                AssistantUtil.sleep(AssistantGui.getInstance().getDelayTimeSlider().getValue());
            }
        }
    }

    public static void mouseClick(Location location) {
        Mouse.click(MouseButton.LEFT, location.getX(), location.getY());
    }

    private static boolean between(int value, int minValue, int maxValue) {
        return (value >= minValue) && (value <= maxValue);
    }
}
