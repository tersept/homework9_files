package qa.guru.domain;

public class Cat {
    private String name;
    private Boolean hasKittens;
    private String color;
    private Integer year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasKittens() {
        return hasKittens;
    }

    public void setHasKittens(Boolean hasKittens) {
        this.hasKittens = hasKittens;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
