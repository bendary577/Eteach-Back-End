package com.eteach.eteach.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private String id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String type;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String path;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String extension;

    @JsonIgnore
    @Column
    private boolean is_write;

    @JsonIgnore
    @Column(name = "thumbnail_path")
    @Type(type = "text")
    private String thumbnail_path;

    @JsonIgnore
    @Column
    private boolean has_thumbnail;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updated_at;

    @OneToOne(mappedBy = "file")
    private Course course;

    public File() {
    }

    public File(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean isIs_write() {
        return is_write;
    }

    public void setIs_write(boolean is_write) {
        this.is_write = is_write;
    }

    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    public boolean isHas_thumbnail() {
        return has_thumbnail;
    }

    public void setHas_thumbnail(boolean has_thumbnail) {
        this.has_thumbnail = has_thumbnail;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}