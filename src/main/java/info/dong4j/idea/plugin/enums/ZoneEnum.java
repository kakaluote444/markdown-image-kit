/*
 * MIT License
 *
 * Copyright (c) 2019 dong4j <dong4j@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package info.dong4j.idea.plugin.enums;

import com.qiniu.common.Zone;

import org.jetbrains.annotations.Contract;

/**
 * <p>Company: 科大讯飞股份有限公司-四川分公司</p>
 * <p>Description: ${description}</p>
 *
 * @author dong4j
 * @date 2019-03-19 20:26
 * @email sjdong3@iflytek.com
 */
public enum ZoneEnum {
    // 区域名称：z0 华东  z1 华北  z2 华南  na0 北美  as0 东南亚
    EAST_CHINA(0, "华东", Zone.zone0()),
    NORT_CHINA(1, "华北", Zone.zone1()),
    SOUTH_CHINA(2, "华南", Zone.zone2()),
    NORTH_AMERIA(3, "北美", Zone.zoneNa0()),
    SOUTHEAST_ASIA(4, "东南亚", Zone.zoneAs0());

    public int index;
    public String name;
    public Zone zone;

    ZoneEnum(int index, String name, Zone zone) {
        this.index = index;
        this.name = name;
        this.zone = zone;
    }

    @Contract(pure = true)
    public int getIndex() {
        return index;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }

    @Contract(pure = true)
    public Zone getZone() {
        return zone;
    }
}
