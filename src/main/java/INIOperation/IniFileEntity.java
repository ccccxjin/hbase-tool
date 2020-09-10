package INIOperation;


public class IniFileEntity {
    private String section;
    private String key;
    private String value;

    public String getSection() {
        return section;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public IniFileEntity(String section, String key, String value) {
        this.section = section;
        this.key = key;
        this.value = value;
    }

    public IniFileEntity() {

    }
}
