package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.Season;

/**
 * A Semaster.
 */
@Entity
@Table(name = "semaster")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Semaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;

    @OneToMany(mappedBy = "semaster")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "semaster")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Record> records = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public Semaster year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Season getSeason() {
        return season;
    }

    public Semaster season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Semaster courses(Set<Course> courses) {
        this.courses = courses;
        return this;
    }

    public Semaster addCourse(Course course) {
        this.courses.add(course);
        course.setSemaster(this);
        return this;
    }

    public Semaster removeCourse(Course course) {
        this.courses.remove(course);
        course.setSemaster(null);
        return this;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public Semaster records(Set<Record> records) {
        this.records = records;
        return this;
    }

    public Semaster addRecord(Record record) {
        this.records.add(record);
        record.setSemaster(this);
        return this;
    }

    public Semaster removeRecord(Record record) {
        this.records.remove(record);
        record.setSemaster(null);
        return this;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Semaster)) {
            return false;
        }
        return id != null && id.equals(((Semaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Semaster{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", season='" + getSeason() + "'" +
            "}";
    }
}
