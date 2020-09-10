package INIOperation;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ini4jUtils {
    public static boolean creatIniFile(String filePath, List<IniFileEntity> filecontent) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            return false;
        }
        file.createNewFile();
        Ini ini = new Ini();
        ini.load(file);

        //将文件内容保存到ini对象中
        filecontent.stream().forEach((entity) -> {
            ini.add(entity.getSection(), entity.getKey(), entity.getValue() == null ? "" : entity.getValue());
        });

        //将文件内容保存到文件中
        ini.store(file);
        return true;
    }

    public static void readIniFile(File iniFile, Map<String, List<String>> fileContent) throws IOException,
            NoSuchFieldException, IllegalAccessException {
        Ini4jFileVo fileVo = new Ini4jFileVo();
        Ini ini = new Ini();
        ini.load(iniFile);
        Profile.Section section = null;
        Field field = null;
        for (String key : ini.keySet()) {
            section = ini.get(key);
            System.out.println(
                    "zookeeper: " + section.get("zookeeper") + " master: " + section.get("master")
            );

        }
    }


    public static void updateIniFile(File iniFile, Map<String, Map<String, String>> updateData) throws IOException {
        Ini ini = new Ini();
        ini.load(iniFile);
        Profile.Section section = null;
        Map<String, String> dataMap = null;
        for (String sect : updateData.keySet()) {
            section = ini.get(sect);
            dataMap = updateData.get(sect);
            for (String key : dataMap.keySet()) {
                section.put(key, dataMap.get(key) == null ? "" : dataMap.get(key));
            }
        }
        ini.store(iniFile);
    }


    /**
     * 修改
     */
    public static void main(String[] args) {
        File file = new File("D:\\WorkSpace\\Java\\JavaLearn\\src\\main\\java\\INIOperation\\test.ini");
        Map<String, Map<String, String>> updateData = new HashMap<>();
        Map<String, String> ldap = new HashMap<>();
        ldap.put("zookeeper", "8.8.8.8");
        updateData.put("hbase1", ldap);
        try {
            Ini4jUtils.updateIniFile(file, updateData);
            Map<String, List<String>> fileContent = new HashMap<>();
            fileContent.put("ldap", Arrays.asList("ip", "ipPort"));
            fileContent.put("test", Arrays.asList("isUsed"));
            Ini4jUtils.readIniFile(file, fileContent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 读
     */
    public static void main2(String[] args) {
        File file = new File("D:\\WorkSpace\\Java\\JavaLearn\\src\\main\\java\\INIOperation\\test.ini");
        Map<String, List<String>> fileContent = new HashMap<>();
        try {
            Ini4jUtils.readIniFile(file, fileContent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 写
     */
    public static void main1(String[] args) {
        List<IniFileEntity> list = Arrays.asList(new IniFileEntity("ldap", "ip", "1.1.1.1"),
                new IniFileEntity("ldap", "ipPort", "8567"),
                new IniFileEntity("test", "isUsed", "true"));
        try {
            System.out.println(Ini4jUtils.creatIniFile("D:\\WorkSpace\\Java\\JavaLearn\\src\\main\\java\\INIOperation\\test.ini", list));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
