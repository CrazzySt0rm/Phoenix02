package org.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Product {


//    private long id;

//    @Column
//    private String name;
//    @Column
//    private String genre;
//    @Column
//    private int release_date;
//
//    public Phoenix() {}
//
//    public Phoenix(String name, String genre, int release_date) {
//        this.name = name;
//        this.genre = genre;
//        this.release_date = release_date;
//    }
//
//    public long getId() { return id; }
//
//    public String getName() {return name;}
//
//    public void setName(String name) {this.name = name;}
//
//    public String getGenre() {return genre;}
//
//    public void setGenre(String genre) {this.genre = genre;}
//
//    public int getRelease_date() {return release_date;}
//
//    public void setRelease_date(int release_date) {this.release_date = release_date;}
//    @Override
//    public String toString() {
//        return "Phoenix" + " " + "name =" + " " + name +
//                ", genre =" + " " + genre +
//                ", release_date =" + " " + release_date + "\n";
//    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "price")
    private int price;
    @Column(name = "city")
    private String city;





    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    mappedBy = "product")
    private List<Image> images = new ArrayList<>();
    private long previewImageId;



    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    public void addImagetoProduct(Image image) {
        image.setProduct(this);
        images.add(image);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPreviewImageId() { return previewImageId; }
    public void setPreviewImageId(long previewImageId) { this.previewImageId = previewImageId; }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
