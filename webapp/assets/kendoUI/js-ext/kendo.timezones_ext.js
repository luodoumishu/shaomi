/*
* Kendo UI Web v2013.2.716 (http://kendoui.com)
* Copyright 2013 Telerik AD. All rights reserved.
*
* Kendo UI Web commercial licenses may be obtained at
* https://www.kendoui.com/purchase/license-agreement/kendo-ui-web-commercial.aspx
* If you do not own a commercial license, this file shall be governed by the
* GNU General Public License (GPL) version 3.
* For GPL requirements, please review: http://www.gnu.org/copyleft/gpl.html
*/


kendo.timezone.zones = {"Asia/Shanghai": [
    [-485.95, "-", "LMT", -1293926400000],
    [-480, "Shang", "C%sT", -631238400000],
    [-480, "PRC", "C%sT"]
]};
kendo.timezone.rules = {"Shang": [
    [1940, "only", "-", "Jun", "3", [0, 0, 0], 60, "D"],
    [1940, 1941, "-", "Oct", "1", [0, 0, 0], 0, "S"],
    [1941, "only", "-", "Mar", "16", [0, 0, 0], 60, "D"]
], "PRC": [
    [1986, "only", "-", "May", "4", [0, 0, 0], 60, "D"],
    [1986, 1991, "-", "Sep", "Sun>=11", [0, 0, 0], 0, "S"],
    [1987, 1991, "-", "Apr", "Sun>=10", [0, 0, 0], 60, "D"]
]};
kendo.timezone.zones_titles = [
    {"name": "(GMT+08:00) Beijing, Chongqing, Hong Kong, Urumqi", "other_zone": "China Standard Time"}
];
kendo.timezone.windows_zones = [
    {"other_zone": "China Standard Time", "zone": "Asia/Shanghai", "territory": "Shanghai"}
];
