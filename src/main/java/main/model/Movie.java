package main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Integer year;

    @Column(name = "rank")
    private Float rank;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="movies_directors",
            joinColumns=@JoinColumn(name="movie_id"),
            inverseJoinColumns=@JoinColumn(name="director_id"))
    private Set<Director> directors = new HashSet<>();

    public Movie(String name, Integer year, Float rank, Set<Director> directors) {
        this.name = name;
        this.year = year;
        this.rank = rank;
        this.directors = directors;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getRank() {
        return rank;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", rank=" + rank +
                ", directors=" + directors +
                '}';
    }
}
