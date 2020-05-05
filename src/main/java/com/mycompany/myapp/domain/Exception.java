package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A Exception.
 */
@Entity
@Table(name = "exception")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "is_youth_league")
    private Boolean isYouthLeague;

    @Column(name = "cause")
    private String cause;

    @ManyToOne
    @JsonIgnoreProperties("exceptions")
    private Major originalMajor;

    @ManyToOne
    @JsonIgnoreProperties("exceptions")
    private Major newMajor;

    @ManyToOne
    @JsonIgnoreProperties("exceptions")
    private SchoolClass originalSchoolClass;

    @ManyToOne
    @JsonIgnoreProperties("exceptions")
    private SchoolClass newSchoolClass;

    @ManyToOne
    @JsonIgnoreProperties("exceptions")
    private Grade originalGrade;

    @ManyToOne
    @JsonIgnoreProperties("exceptions")
    private Grade newGrade;

    @ManyToOne
    @JsonIgnoreProperties("exceptions")
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public Exception date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean isIsYouthLeague() {
        return isYouthLeague;
    }

    public Exception isYouthLeague(Boolean isYouthLeague) {
        this.isYouthLeague = isYouthLeague;
        return this;
    }

    public void setIsYouthLeague(Boolean isYouthLeague) {
        this.isYouthLeague = isYouthLeague;
    }

    public String getCause() {
        return cause;
    }

    public Exception cause(String cause) {
        this.cause = cause;
        return this;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Major getOriginalMajor() {
        return originalMajor;
    }

    public Exception originalMajor(Major major) {
        this.originalMajor = major;
        return this;
    }

    public void setOriginalMajor(Major major) {
        this.originalMajor = major;
    }

    public Major getNewMajor() {
        return newMajor;
    }

    public Exception newMajor(Major major) {
        this.newMajor = major;
        return this;
    }

    public void setNewMajor(Major major) {
        this.newMajor = major;
    }

    public SchoolClass getOriginalSchoolClass() {
        return originalSchoolClass;
    }

    public Exception originalSchoolClass(SchoolClass schoolClass) {
        this.originalSchoolClass = schoolClass;
        return this;
    }

    public void setOriginalSchoolClass(SchoolClass schoolClass) {
        this.originalSchoolClass = schoolClass;
    }

    public SchoolClass getNewSchoolClass() {
        return newSchoolClass;
    }

    public Exception newSchoolClass(SchoolClass schoolClass) {
        this.newSchoolClass = schoolClass;
        return this;
    }

    public void setNewSchoolClass(SchoolClass schoolClass) {
        this.newSchoolClass = schoolClass;
    }

    public Grade getOriginalGrade() {
        return originalGrade;
    }

    public Exception originalGrade(Grade grade) {
        this.originalGrade = grade;
        return this;
    }

    public void setOriginalGrade(Grade grade) {
        this.originalGrade = grade;
    }

    public Grade getNewGrade() {
        return newGrade;
    }

    public Exception newGrade(Grade grade) {
        this.newGrade = grade;
        return this;
    }

    public void setNewGrade(Grade grade) {
        this.newGrade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public Exception student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exception)) {
            return false;
        }
        return id != null && id.equals(((Exception) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Exception{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isYouthLeague='" + isIsYouthLeague() + "'" +
            ", cause='" + getCause() + "'" +
            "}";
    }
}
