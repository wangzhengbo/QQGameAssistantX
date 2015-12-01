package cn.com.qqgame.assistant.duiduipeng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Match;

import cn.com.qqgame.assistant.AbstractAssistant;
import cn.com.qqgame.assistant.AssistantUtil;
import cn.com.qqgame.assistant.RegionUtil;
import cn.com.qqgame.assistant.Settings;

public class DuiDuiPengAssistant extends AbstractAssistant {
    @Override
    public void start() {
        super.start();

        for (String image : new String[] { "cat", "chicken", "cow", "dog", "frog", "monkey", "panda" }) {
            new AssistantThread(image).start();
        }
    }

    private static class AssistantThread extends Thread {
        // 图片文件
        private final String image;

        public AssistantThread(final String image) {
            setDaemon(true);
            this.image = image;
        }

        @Override
        public void run() {
            while (true) {
                if (Settings.INSTANCE.getCurrentAssistant() == null) {
                    break;
                }

                Iterator<Match> regions = null;
                synchronized (AssistantUtil.SCREEN) {
                    try {
                        regions = AssistantUtil.SCREEN.findAll(image);
                    } catch (FindFailed e) {
                    }
                }
                List<Location> locations = new ArrayList<Location>();
                if (regions != null) {
                    while (regions.hasNext()) {
                        locations.add(regions.next().getCenter());
                    }
                }
                final int size = locations.size();
                if (size <= 2) {
                    continue;
                }

                OuterForLoop: for (int i = 0; i < size; i++) {
                    for (int j = i + 1; j < size; j++) {
                        final Location location1 = locations.get(i);
                        final Location location2 = locations.get(j);

                        if (RegionUtil.isInSameRowAndAdjacent(location1, location2)) { // 在同一行且相邻
                            for (int k = 0; k < size; k++) {
                                if ((k == i) || (k == j)) {
                                    continue;
                                }

                                final Location location3 = locations.get(k);
                                Location separatedLocation = null;
                                Location diagonalLocation = null;
                                if (RegionUtil.isInSameRowAndSeparated(location3, location1)) {
                                    // region3与region1相隔
                                    separatedLocation = location1;
                                } else if (RegionUtil.isInSameRowAndSeparated(location3, location2)) {
                                    // region3与region2相隔
                                    separatedLocation = location2;
                                } else if (RegionUtil.isDiagonal(location3, location1)
                                        && !RegionUtil.isSameColumn(location3, location2)) {
                                    // region3与region1对角
                                    diagonalLocation = location1;
                                } else if (RegionUtil.isDiagonal(location3, location2)
                                        && !RegionUtil.isSameColumn(location3, location1)) {
                                    // region3与region2对角
                                    diagonalLocation = location2;
                                }
                                if (separatedLocation != null) {
                                    RegionUtil.swapImage(location3,
                                            new Location(
                                                    location3.getX() + RegionUtil.BOX_WIDTH
                                                            * ((location3.getX() < separatedLocation.getX()) ? 1 : -1),
                                            separatedLocation.getY()));
                                    break OuterForLoop;
                                } else if (diagonalLocation != null) {
                                    RegionUtil.swapImage(location3,
                                            new Location(location3.getX(), diagonalLocation.getY()));
                                    break OuterForLoop;
                                }
                            }
                        } else if (RegionUtil.isInSameRowAndSeparated(location1, location2)) { // 在同一行且相隔
                            for (int k = 0; k < size; k++) {
                                if ((k == i) || (k == j)) {
                                    continue;
                                }

                                final Location location3 = locations.get(k);
                                if (RegionUtil.isDiagonal(location3, location1)
                                        && RegionUtil.isDiagonal(location3, location2)) {
                                    // region3与region1对角且region3与region2对角
                                    RegionUtil.swapImage(location3,
                                            new Location(location3.getX(), location3.getY() + RegionUtil.BOX_HEIGHT
                                                    * ((location3.getY() < location1.getY()) ? 1 : -1)));
                                    break OuterForLoop;
                                }
                            }
                        } else if (RegionUtil.isInSameColumnAndAdjacent(location1, location2)) { // 在同一列且相邻
                            for (int k = 0; k < size; k++) {
                                if ((k == i) || (k == j)) {
                                    continue;
                                }

                                final Location location3 = locations.get(k);
                                Location separatedLocation = null;
                                Location diagonalLocation = null;
                                if (RegionUtil.isInSameColumnAndSeparated(location3, location1)) {
                                    // region3与region1相隔
                                    separatedLocation = location1;
                                } else if (RegionUtil.isInSameColumnAndSeparated(location3, location2)) {
                                    // region3与region2相隔
                                    separatedLocation = location2;
                                } else if (RegionUtil.isDiagonal(location3, location1)
                                        && !RegionUtil.isSameRow(location3, location2)) {
                                    // region3与region1对角
                                    diagonalLocation = location1;
                                } else if (RegionUtil.isDiagonal(location3, location2)
                                        && !RegionUtil.isSameRow(location3, location1)) {
                                    // region3与region2对角
                                    diagonalLocation = location2;
                                }
                                if (separatedLocation != null) {
                                    RegionUtil.swapImage(location3, new Location(separatedLocation.getX(),
                                            location3.getY() + RegionUtil.BOX_HEIGHT
                                                    * ((location3.getY() < separatedLocation.getY()) ? 1 : -1)));
                                    break OuterForLoop;
                                } else if (diagonalLocation != null) {
                                    RegionUtil.swapImage(location3,
                                            new Location(diagonalLocation.getX(), location3.getY()));
                                    break OuterForLoop;
                                }
                            }
                        } else if (RegionUtil.isInSameColumnAndSeparated(location1, location2)) { // 在同一列且相隔
                            for (int k = 0; k < size; k++) {
                                if ((k == i) || (k == j)) {
                                    continue;
                                }

                                final Location location3 = locations.get(k);
                                if (RegionUtil.isDiagonal(location3, location1)
                                        && RegionUtil.isDiagonal(location3, location2)) {
                                    // region3与region1对角且region3与region2对角
                                    RegionUtil.swapImage(location3,
                                            new Location(
                                                    location3.getX() + RegionUtil.BOX_WIDTH
                                                            * ((location3.getX() < location1.getX()) ? 1 : -1),
                                            location3.getY()));
                                    break OuterForLoop;
                                }
                            }
                        }
                    } // end for (int j = i + 1; j < size; j++) {
                } // end for (int i = 0; i < size; i++) {
            } // end while (true) {
        }
    }
}
