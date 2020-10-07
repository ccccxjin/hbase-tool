package ToolComponent.DataTable.RowTable;

import util.CollectionTools;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class TableCardsData {

//    // 卡片页面存储
//    private static final HashMap<String, JPanel> panelHashMap = new HashMap<>();
//
//    // 卡片分页面存储
//    private static final HashMap<String, ButtonPanel> buttonPanelHashMap = new HashMap<>();
//    private static final HashMap<String, HbaseTableView> tableViewHashMap = new HashMap<>();
//    private static final HashMap<String, PageFooter> pageFooterHashMap = new HashMap<>();
//
//    // 标签存储
//    private static final HashMap<String, TitleLabel> titleLabelHashMap = new HashMap<>();
//
//    // 设置数据
//    public static void put(String dbName, String tableName, Component obj, String type) {
//        String name = CollectionTools.structTitle(dbName, tableName);
//        switch (type) {
//            case "title": titleLabelHashMap.put(name, (TitleLabel) obj); break;
//            case "panel": panelHashMap.put(name, (JPanel) obj);break;
//            case "button": buttonPanelHashMap.put(name, (ButtonPanel) obj);break;
//            case "table": tableViewHashMap.put(name, (HbaseTableView) obj);break;
//            case "footer": pageFooterHashMap.put(name, (PageFooter) obj);break;
//        }
//    }
//
//    // 获取数据
//    public static Object get(String dbName, String tableName, String type) {
//        String name = CollectionTools.structTitle(dbName, tableName);
//        switch (type) {
//            case "title": return titleLabelHashMap.get(name);
//            case "panel": return panelHashMap.get(name);
//            case "button": return buttonPanelHashMap.get(name);
//            case "table": return tableViewHashMap.get(name);
//            case "footer": return pageFooterHashMap.get(name);
//            default: return null;
//        }
//    }
//
//    // 删除数据
//    public static Object remove(String dbName, String tableName, String type) {
//        String name = CollectionTools.structTitle(dbName, tableName);
//        switch (type) {
//            case "title": return titleLabelHashMap.remove(name);
//            case "panel": return panelHashMap.remove(name);
//            case "button": return buttonPanelHashMap.remove(name);
//            case "table": return tableViewHashMap.remove(name);
//            case "footer": return pageFooterHashMap.remove(name);
//            default:return null;
//        }
//    }
}
