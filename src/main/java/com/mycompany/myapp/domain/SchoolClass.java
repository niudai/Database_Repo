package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A SchoolClass.
 */
@Entity
@Table(name = "school_class")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchoolClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Teacher master;

    @OneToMany(mappedBy = "schoolClass")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("schoolClasses")
    private Grade grade;

    @ManyToOne
    @JsonIgnoreProperties("schoolClasses")
    private Major major;

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

    public SchoolClass name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public SchoolClass createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Teacher getMaster() {
        return master;
    }

    public SchoolClass master(Teacher teacher) {
        this.master = teacher;
        return this;
    }

    public void setMaster(Teacher teacher) {
        this.master = teacher;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public SchoolClass students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public SchoolClass addStudents(Student student) {
        this.students.add(student);
        student.setSchoolClass(this);
        return this;
    }

    public SchoolClass removeStudents(Student student) {
        this.students.remove(student);
        student.setSchoolClass(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Grade getGrade() {
        return grade;
    }

    public SchoolClass grade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Major getMajor() {
        return major;
    }

    public SchoolClass major(Major major) {
        this.major = major;
        return this;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolClass)) {
            return false;
        }
        return id != null && id.equals(((SchoolClass) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SchoolClass{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
