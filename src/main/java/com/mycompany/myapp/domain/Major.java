package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Major.
 */
@Entity
@Table(name = "major")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "major")
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "major")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SchoolClass> schoolClasses = new HashSet<>();

    @OneToMany(mappedBy = "major")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "major")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Course> courses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "majors", allowSetters = true)
    private Campus campus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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

    // prettier-ignore
    @Override
    public String toString() {
        return "Major{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
