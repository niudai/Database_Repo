package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.ExamType;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "exam_type")
    private ExamType examType;

    @OneToMany(mappedBy = "course")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Interval> times = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("courses")
    private Major major;

    @ManyToOne
    @JsonIgnoreProperties("courses")
    private Teacher teacher;

    @ManyToOne
    @JsonIgnoreProperties("courses")
    private Semaster semaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Course name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExamType getExamType() {
        return examType;
    }

    public Course examType(ExamType examType) {
        this.examType = examType;
        return this;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public Set<Interval> getTimes() {
        return times;
    }

    public Course times(Set<Interval> intervals) {
        this.times = intervals;
        return this;
    }

    public Course addTime(Interval interval) {
        this.times.add(interval);
        interval.setCourse(this);
        return this;
    }

    public Course removeTime(Interval interval) {
        this.times.remove(interval);
        interval.setCourse(null);
        return this;
    }

    public void setTimes(Set<Interval> intervals) {
        this.times = intervals;
    }

    public Major getMajor() {
        return major;
    }

    public Course major(Major major) {
        this.major = major;
        return this;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Course teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Semaster getSemaster() {
        return semaster;
    }

    public Course semaster(Semaster semaster) {
        this.semaster = semaster;
        return this;
    }

    public void setSemaster(Semaster semaster) {
        this.semaster = semaster;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", examType='" + getExamType() + "'" +
            "}";
    }
}
