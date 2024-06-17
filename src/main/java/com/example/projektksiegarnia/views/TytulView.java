package com.example.projektksiegarnia.views;

import com.example.projektksiegarnia.DataBaseManager;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "tytuly")
public class TytulView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nazwa;

    public static void AddNew(String nazwa){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        TytulView tytul = new TytulView();
        tytul.setNazwa(nazwa);

        s.merge(tytul);
        t.commit();
        s.close();
    }
    public void RemoveThis(){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.remove(this);
        t.commit();
        s.close();
    }

    public void UpdateThis(List<String> newValues){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        TytulView g = s.get(TytulView.class,getId());
        g.setId(Long.parseLong(newValues.get(0)));
        g.setNazwa(newValues.get(1));

        s.merge(g);
        t.commit();
        s.close();
    }

    public String GetNormalizedInfo(){
        return getId() + "\t\t\t" + getNazwa();
    }
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    public String GetFullInfo(){
        return getId() + "\t\t\t" + getNazwa();
    }
    public List<String> CurrentValuesAsString() {
        return Arrays.asList(getId().toString(),getNazwa());
    }
}
