package com.logicea.card;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table( //gives us full control
        name = "card"
)
public class Card {

    @Id
    @SequenceGenerator(
            name = "card_id_seq",
            sequenceName = "card_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "card_id_seq"
    )
    private Integer id;

    @Column(
            nullable = false
    )
    private String name;

    @Column(
    )
    private String description;

    @Column(
    )
    private String color;

    @Column(
            nullable = false,
            columnDefinition="VARCHAR(20) default 'To Do'"
    )
    private String status;

    @Column(
            nullable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Date createdAt;

    @Column(
            nullable = false
    )
    private String createdBy;


    public Card(){
    }
    public Card(Integer id, String name, String description, String color, String status, Date createdAt, String createdBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;

    }

    public Card(String name, String description, String color, String status, Date createdAt, String createdBy) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) &&
                Objects.equals(name, card.name) &&
                Objects.equals(description, card.description) &&
                Objects.equals(color, card.color) &&
                Objects.equals(status, card.status) &&
                Objects.equals(createdAt, card.createdAt) &&
                Objects.equals(createdBy, card.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, color, status, createdAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description=" + description +
                ", color='" + color  + '\'' +
                ", status='" + status  + '\'' +
                ", createdAt='" + createdAt  + '\'' +
                ", createdBy='" + createdBy  + '\'' +
                '}';
    }

}

