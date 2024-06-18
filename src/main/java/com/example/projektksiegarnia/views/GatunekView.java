package com.example.projektksiegarnia.views;

import com.example.projektksiegarnia.DataBaseManager;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;
/**
 * Klasa reprezentująca widok encji Gatunek.
 * Reprezentuje tabelę gatunki w bazie danych.
 */
@Entity
@Table(name="gatunki")
public class GatunekView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nazwa;
    /**
     *  metoda ta dodaje nowy gatunek do bazy
     *       @param nazwa gatunek dodawanej ksiazki
     */
    public static void AddNew(String nazwa){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        GatunekView gatunek = new GatunekView();
        gatunek.setNazwa(nazwa);

        s.merge(gatunek);
        t.commit();
        s.close();
    }
    /**
     *  metoda ta usuwa bierzacy gatunek z bazy
     */
    public void RemoveThis(){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.remove(this);
        t.commit();
        s.close();
    }
    /**
     *  metoda ta aktualziuje bierzacy gatunek w bazie
     *      @param newValues nowe wartosci w tablicy (id,nazwa)
     *
     */
    public void UpdateThis(List<String> newValues){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        GatunekView g = s.get(GatunekView.class,getId());
        g.setId(Long.parseLong(newValues.get(0)));
        g.setNazwa(newValues.get(1));

        s.merge(g);
        t.commit();
        s.close();
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

    public String GetNormalizedInfo(){
        return getId() + "\t\t\t" + getNazwa();
    }

    public String GetFullInfo(){
        return getId() + "\t\t\t" + getNazwa();
    }

    public List<String> CurrentValuesAsString() {
        return Arrays.asList(getId().toString(),getNazwa());
    }


}
