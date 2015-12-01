package cn.com.qqgame.assistant;

import static org.hamcrest.Matchers.is;

import org.junit.Assert;
import org.junit.Test;
import org.sikuli.script.Location;

public class RegionUtilTest {
    @Test
    public void isInSameRow() {
        Assert.assertThat(RegionUtil.isSameRow(getLocation(0, 50), getLocation(100, 47)), is(false));
        Assert.assertThat(RegionUtil.isSameRow(getLocation(0, 50), getLocation(100, 48)), is(true));
        Assert.assertThat(RegionUtil.isSameRow(getLocation(0, 50), getLocation(100, 49)), is(true));

        Assert.assertThat(RegionUtil.isSameRow(getLocation(0, 50), getLocation(100, 50)), is(true));

        Assert.assertThat(RegionUtil.isSameRow(getLocation(0, 50), getLocation(100, 51)), is(true));
        Assert.assertThat(RegionUtil.isSameRow(getLocation(0, 50), getLocation(100, 52)), is(true));
        Assert.assertThat(RegionUtil.isSameRow(getLocation(0, 50), getLocation(100, 53)), is(false));
    }

    @Test
    public void isInSameRowAndAdjacent() {
        Assert.assertThat(RegionUtil.isInSameRowAndAdjacent(getLocation(100, 50),
                getLocation(100 - RegionUtil.BOX_WIDTH - 3, 50)), is(false));
        Assert.assertThat(RegionUtil.isInSameRowAndAdjacent(getLocation(100, 50),
                getLocation(100 - RegionUtil.BOX_WIDTH - 2, 50)), is(true));
        Assert.assertThat(RegionUtil.isInSameRowAndAdjacent(getLocation(100, 50),
                getLocation(100 - RegionUtil.BOX_WIDTH - 1, 50)), is(true));
        Assert.assertThat(
                RegionUtil.isInSameRowAndAdjacent(getLocation(100, 50), getLocation(100 - RegionUtil.BOX_WIDTH, 50)),
                is(true));

        Assert.assertThat(
                RegionUtil.isInSameRowAndAdjacent(getLocation(100, 50), getLocation(100 + RegionUtil.BOX_WIDTH, 50)),
                is(true));
        Assert.assertThat(RegionUtil.isInSameRowAndAdjacent(getLocation(100, 50),
                getLocation(100 + RegionUtil.BOX_WIDTH + 1, 50)), is(true));
        Assert.assertThat(RegionUtil.isInSameRowAndAdjacent(getLocation(100, 50),
                getLocation(100 + RegionUtil.BOX_WIDTH + 2, 50)), is(true));
        Assert.assertThat(RegionUtil.isInSameRowAndAdjacent(getLocation(100, 50),
                getLocation(100 + RegionUtil.BOX_WIDTH + 3, 50)), is(false));

    }

    private Location getLocation(int x, int y) {
        return new Location(x, y);
    }
}
