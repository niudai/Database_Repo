package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Grade.
 */
@Entity
@Table(name = "grade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade")
    private Integer grade;

    @OneToMany(mappedBy = "grade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchoolClass> schoolClasses = new HashSet<>();

    @OneToMany(mappedBy = "originalGrade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exception> exceptions = new HashSet<>();

    @OneToMany(mappedBy = "newGrade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exception> exceptions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public Grade grade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Set<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public Grade schoolClasses(Set<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
        return this;
    }

    public Grade addSchoolClass(SchoolClass schoolClass) {
        this.schoolClasses.add(schoolClass);
        schoolClass.setGrade(this);
        return this;
    }

    public Grade removeSchoolClass(SchoolClass schoolClass) {
        this.schoolClasses.remove(schoolClass);
        schoolClass.setGrade(null);
        return this;
    }

    public void setSchoolClasses(Set<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }

    public Set<Exception> getExceptions() {
        return exceptions;
    }

    public Grade exceptions(Set<Exception> exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    public Grade addException(Exception exception) {
        this.exceptions.add(exception);
        exception.setOriginalGrade(this);
        return this;
    }

    public Grade removeException(Exception exception) {
        this.exceptions.remove(exception);
        exception.setOriginalGrade(null);
        return this;
    }

    public void setExceptions(Set<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public Set<Exception> getExceptions() {
        return exceptions;
    }

    public Grade exceptions(Set<Exception> exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    public Grade addException(Exception exception) {
        this.exceptions.add(exception);
        exception.setNewGrade(this);
        return this;
    }

    public Grade removeException(Exception exception) {
        this.exceptions.remove(exception);
        exception.setNewGrade(null);
        return this;
    }

    public void setExceptions(Set<Exception> exceptions) {
        this.exceptions = exceptions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grade)) {
            return false;
        }
        return id != null && id.equals(((Grade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Grade{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            "}";
    }
}
