package util;

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
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        return date.getTime();
    }

    // 时间戳 -> 时间
    public static String stampToDate(long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }
}
