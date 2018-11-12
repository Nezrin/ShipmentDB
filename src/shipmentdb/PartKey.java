package shipmentdb;

/**
 *
 * @author nazrin
 */
import java.io.Serializable;

public class PartKey implements Serializable
{
    private String number;


    public PartKey(String number) {
        this.number = number;
    }


    public final String getNumber() {
        return number;
    }


    public String toString() {
        return "[PartKey: number=" + number + ']';
    }
}