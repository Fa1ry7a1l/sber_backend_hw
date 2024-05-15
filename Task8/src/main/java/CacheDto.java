import java.io.Serializable;
import java.util.ArrayList;

public class CacheDto implements Serializable {
    public ArrayList<Object> key;
    public Object value;

    public CacheDto()
    {}

    public CacheDto(ArrayList<Object> key, Object value) {
        this.key = key;
        this.value = value;
    }

    public ArrayList<Object> getKey() {
        return key;
    }

    public void setKey(ArrayList<Object> key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
