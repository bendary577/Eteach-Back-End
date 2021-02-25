package com.eteach.eteach.model.file;

import com.eteach.eteach.model.course.Lesson;

import javax.persistence.*;

@Entity
@Table(name="materials")
public class Material extends File {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private Lesson lesson;
}
