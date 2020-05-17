package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.WeekDay;

/**
 * A Interval.
 */
@Entity
@Table(name = "jhi_interval")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "interval")
public class Interval implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private WeekDay day;

    @Column(name = "start")
    private Integer start;

    @Column(name = "end")
    private Integer end;

    @ManyToOne
    @JsonIgnoreProperties("times")
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeekDay getDay() {
        return day;
    }

    public Interval day(WeekDay day) {
        this.day = day;
        return this;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public Integer getStart() {
        return start;
    }

    public Interval start(Integer start) {
        this.start = start;
        return this;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public Interval end(Integer end) {
        this.end = end;
        return this;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Course getCourse() {
        return course;
    }

    public Interval course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Interval)) {
            return false;
        }
        return id != null && id.equals(((Interval) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Interval{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", start=" + getStart() +
            ", end=" + getEnd() +
            "}";
    }
}
