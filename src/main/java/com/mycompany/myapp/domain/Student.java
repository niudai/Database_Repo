package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<JException> exceptions = new HashSet<>();

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Record> records = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private Major major;

    @OneToOne(mappedBy = "student")
    @JsonIgnore
    private People people;

    @ManyToOne
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private SchoolClass schoolClass;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public Student studentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
        return this;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public Student startDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEmail() {
        return email;
    }

    public Student email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<JException> getExceptions() {
        return exceptions;
    }

    public Student exceptions(Set<JException> jExceptions) {
        this.exceptions = jExceptions;
        return this;
    }

    public Student addExceptions(JException jException) {
        this.exceptions.add(jException);
        jException.setStudent(this);
        return this;
    }

    public Student removeExceptions(JException jException) {
        this.exceptions.remove(jException);
        jException.setStudent(null);
        return this;
    }

    public void setExceptions(Set<JException> jExceptions) {
        this.exceptions = jExceptions;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public Student records(Set<Record> records) {
        this.records = records;
        return this;
    }

    public Student addRecord(Record record) {
        this.records.add(record);
        record.setStudent(this);
        return this;
    }

    public Student removeRecord(Record record) {
        this.records.remove(record);
        record.setStudent(null);
        return this;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    public Major getMajor() {
        return major;
    }

    public Student major(Major major) {
        this.major = major;
        return this;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public People getPeople() {
        return people;
    }

    public Student people(People people) {
        this.people = people;
        return this;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public Student schoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
        return this;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", studentNumber='" + getStudentNumber() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
