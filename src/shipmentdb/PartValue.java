package shipmentdb;

/**
 *
 * @author nazrin
 */
import java.io.Serializable;

public class PartValue implements Serializable
{
    private String name;
    private String color;
    private Weight weight;
    private String city;


    public PartValue(String name, String color, Weight weight, String city)
    {
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.city = city;
    }


    public final String getName()
    {
        return name;
    }


    public final String getColor()
    {
        return color;
    }


    public final Weight getWeight()
    {
        return weight;
    }


    public final String getCity()
    {
        return city;
    }


    public String toString()
    {
        return "[PartValue: name=" + name +
               " color=" + color +
               " weight=" + weight +
               " city=" + city + ']';
    }
}