package util;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CollectionTools {

    // 数据库 @ 表名
    public static String structTitle(String db, String table) {
        return table + "@" + db;
    }

    // 时间 -> 时间戳
    public static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(s);
            return date.getTime();
        } catch (Exception e) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(s);
            return date.getTime();
        }
    }

    // 时间戳 -> 时间
    public static String stampToDate(long s){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = new Date(s);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(s);
            return simpleDateFormat.format(date);
        }
    }

    // 修改容器下, 所有组件的状态
    public static void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
    }
}
