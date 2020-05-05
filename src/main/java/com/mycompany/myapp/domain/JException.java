package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A JException.
 */
@Entity
@Table(name = "j_exception")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JException implements Serializable {

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
    @JsonIgnoreProperties("jExceptions")
    private Major originalMajor;

    @ManyToOne
    @JsonIgnoreProperties("jExceptions")
    private Major newMajor;

    @ManyToOne
    @JsonIgnoreProperties("jExceptions")
    private SchoolClass originalSchoolClass;

    @ManyToOne
    @JsonIgnoreProperties("jExceptions")
    private SchoolClass newSchoolClass;

    @ManyToOne
    @JsonIgnoreProperties("jExceptions")
    private Grade originalGrade;

    @ManyToOne
    @JsonIgnoreProperties("jExceptions")
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

    public JException date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean isIsYouthLeague() {
        return isYouthLeague;
    }

    public JException isYouthLeague(Boolean isYouthLeague) {
        this.isYouthLeague = isYouthLeague;
        return this;
    }

    public void setIsYouthLeague(Boolean isYouthLeague) {
        this.isYouthLeague = isYouthLeague;
    }

    public String getCause() {
        return cause;
    }

    public JException cause(String cause) {
        this.cause = cause;
        return this;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Major getOriginalMajor() {
        return originalMajor;
    }

    public JException originalMajor(Major major) {
        this.originalMajor = major;
        return this;
    }

    public void setOriginalMajor(Major major) {
        this.originalMajor = major;
    }

    public Major getNewMajor() {
        return newMajor;
    }

    public JException newMajor(Major major) {
        this.newMajor = major;
        return this;
    }

    public void setNewMajor(Major major) {
        this.newMajor = major;
    }

    public SchoolClass getOriginalSchoolClass() {
        return originalSchoolClass;
    }

    public JException originalSchoolClass(SchoolClass schoolClass) {
        this.originalSchoolClass = schoolClass;
        return this;
    }

    public void setOriginalSchoolClass(SchoolClass schoolClass) {
        this.originalSchoolClass = schoolClass;
    }

    public SchoolClass getNewSchoolClass() {
        return newSchoolClass;
    }

    public JException newSchoolClass(SchoolClass schoolClass) {
        this.newSchoolClass = schoolClass;
        return this;
    }

    public void setNewSchoolClass(SchoolClass schoolClass) {
        this.newSchoolClass = schoolClass;
    }

    public Grade getOriginalGrade() {
        return originalGrade;
    }

    public JException originalGrade(Grade grade) {
        this.originalGrade = grade;
        return this;
    }

    public void setOriginalGrade(Grade grade) {
        this.originalGrade = grade;
    }

    public Grade getNewGrade() {
        return newGrade;
    }

    public JException newGrade(Grade grade) {
        this.newGrade = grade;
        return this;
    }

    public void setNewGrade(Grade grade) {
        this.newGrade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public JException student(Student student) {
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
        if (!(o instanceof JException)) {
            return false;
        }
        return id != null && id.equals(((JException) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "JException{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isYouthLeague='" + isIsYouthLeague() + "'" +
            ", cause='" + getCause() + "'" +
            "}";
    }
}
