package collection;
import java.io.Serializable;
import java.util.Date;

public class City implements Comparable<City>, Serializable {
    private static final long serialVersionUID = 1L;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer area; //Значение поля должно быть больше 0
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null
    private Float metersAboveSeaLevel;
    private Integer timezone; //Значение поля должно быть больше -13, Максимальное значение поля: 15
    private Long agglomeration;
    private Climate climate; //Поле может быть null
    private Human governor; //Поле может быть null
    private String owner;

    public City (String name, Coordinates coordinates, Date creationDate, Integer area, Long population, Float metersAboveSeaLevel,
                 Integer timezone, Long agglomeration, Climate climate,Human governor, String owner) {
        this.id = 0;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.timezone = timezone;
        this.agglomeration = agglomeration;
        this.climate = climate;
        this.governor = governor;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Integer getTimezone() {
        return timezone;
    }

    @Override
    public int compareTo(City o){
        if (o==null)
        {
            return 1;
        }
        int r = this.name.compareTo(o.name);
        if (r==0)
           r = this.coordinates.compareTo(o.coordinates);
        if (r==0)
            r = this.area-o.area;
        if (r==0)
            r = this.population.compareTo(o.population);
        if (r==0)
            r = this.metersAboveSeaLevel.compareTo(o.metersAboveSeaLevel);
        if (r==0)
            r = this.timezone-o.timezone;
        if (r==0)
            r = this.agglomeration.compareTo(o.agglomeration);
        if (r==0) {
            if (climate != null)
                r = this.climate.compareTo(o.climate);
            else if (o.climate != null)
                r = -o.climate.compareTo(this.climate);
        }
        if (r==0) {
            if (governor!=null)
                r = this.governor.compareTo(o.governor);
            else if (o.governor!=null)
                r = -o.governor.compareTo(this.governor);
        }
        return r;
    }

    @Override
    public String toString() {
        String s = "";
        s = "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate.toString() +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", timezone=" + timezone +
                ", agglomeration=" + agglomeration +
                ", climate=" + climate +
                ", governor=";
        if (governor==null)
            s += "null" + '}';
        else s+= governor.toString() +
                '}';
        return s;
    }

    public String getName() {
        return name;
    }

    public Climate getClimate() {
        return climate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Float getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public Human getGovernor() {
        return governor;
    }

    public Integer getArea() {
        return area;
    }

    public Long getAgglomeration() {
        return agglomeration;
    }

    public Long getPopulation() {
        return population;
    }

    public String getOwner() {
        return owner;
    }
}
