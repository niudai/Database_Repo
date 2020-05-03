package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Major.
 */
@Entity
@Table(name = "major")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private People manager;

    @OneToMany(mappedBy = "major")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchoolClass> schoolClasses = new HashSet<>();

    @OneToMany(mappedBy = "major")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "originalMajor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exception> exceptions = new HashSet<>();

    @OneToMany(mappedBy = "newMajor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exception> exceptions = new HashSet<>();

    @OneToMany(mappedBy = "major")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Course> courses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("majors")
    private Campus campus;

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

    public Major name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public People getManager() {
        return manager;
    }

    public Major manager(People people) {
        this.manager = people;
        return this;
    }

    public void setManager(People people) {
        this.manager = people;
    }

    public Set<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public Major schoolClasses(Set<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
        return this;
    }

    public Major addSchoolClass(SchoolClass schoolClass) {
        this.schoolClasses.add(schoolClass);
        schoolClass.setMajor(this);
        return this;
    }

    public Major removeSchoolClass(SchoolClass schoolClass) {
        this.schoolClasses.remove(schoolClass);
        schoolClass.setMajor(null);
        return this;
    }

    public void setSchoolClasses(Set<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public Major teachers(Set<Teacher> teachers) {
        this.teachers = teachers;
        return this;
    }

    public Major addTeachers(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.setMajor(this);
        return this;
    }

    public Major removeTeachers(Teacher teacher) {
        this.teachers.remove(teacher);
        teacher.setMajor(null);
        return this;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<Exception> getExceptions() {
        return exceptions;
    }

    public Major exceptions(Set<Exception> exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    public Major addException(Exception exception) {
        this.exceptions.add(exception);
        exception.setOriginalMajor(this);
        return this;
    }

    public Major removeException(Exception exception) {
        this.exceptions.remove(exception);
        exception.setOriginalMajor(null);
        return this;
    }

    public void setExceptions(Set<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public Set<Exception> getExceptions() {
        return exceptions;
    }

    public Major exceptions(Set<Exception> exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    public Major addException(Exception exception) {
        this.exceptions.add(exception);
        exception.setNewMajor(this);
        return this;
    }

    public Major removeException(Exception exception) {
        this.exceptions.remove(exception);
        exception.setNewMajor(null);
        return this;
    }

    public void setExceptions(Set<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Major courses(Set<Course> courses) {
        this.courses = courses;
        return this;
    }

    public Major addCourse(Course course) {
        this.courses.add(course);
        course.setMajor(this);
        return this;
    }

    public Major removeCourse(Course course) {
        this.courses.remove(course);
        course.setMajor(null);
        return this;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Campus getCampus() {
        return campus;
    }

    public Major campus(Campus campus) {
        this.campus = campus;
        return this;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Major)) {
            return false;
        }
        return id != null && id.equals(((Major) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Major{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
