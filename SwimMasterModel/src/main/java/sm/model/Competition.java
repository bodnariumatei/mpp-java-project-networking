package sm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Competition implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("distance")
    private int distance;

    @JsonProperty("style")
    private CompetitionStyle style;

    public Competition(){}

    public Competition(int distance, CompetitionStyle style) {
        this.distance = distance;
        this.style = style;
    }

    public Competition(int id, int distance, CompetitionStyle style){
        this.id = id;
        this.distance = distance;
        this.style = style;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public CompetitionStyle getStyle() {
        return style;
    }

    public void setStyle(CompetitionStyle style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return this.style + " - " + this.distance +"m";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
