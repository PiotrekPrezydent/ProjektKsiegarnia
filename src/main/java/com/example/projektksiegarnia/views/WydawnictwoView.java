package com.example.projektksiegarnia.views;

import com.example.projektksiegarnia.DataBaseManager;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;
/**
 * Klasa reprezentująca widok encji Wydawnictwo.
 * Reprezentuje tabelę wydawnictwa w bazie danych.
 */
@Entity
@Table(name="wydawnictwa")
public class WydawnictwoView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nazwa;

    /**
     * Metoda ta dodaje nowe wydwanictwo do bazy
     */
    public static void AddNew(String nazwa){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        WydawnictwoView wydawnictwo = new WydawnictwoView();
        wydawnictwo.setNazwa(nazwa);

        s.merge(wydawnictwo);
        t.commit();
        s.close();
    }

    /**
     * Metoda ta usuwa wydwanictwo z bazy
     */
    public void RemoveThis(){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.remove(this);
        t.commit();
        s.close();
    }
    /**
     *  metoda ta aktualziuje bierzacy wydawnictwo w bazie
     *      @param newValues nowe wartosci w tablicy (id,nazwa)
     *
     */

    public void UpdateThis(List<String> newValues){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        WydawnictwoView g = s.get(WydawnictwoView.class,getId());
        g.setId(Long.parseLong(newValues.get(0)));
        g.setNazwa(newValues.get(1));

        s.merge(g);
        t.commit();
        s.close();
    }
    /**
     * Metoda ta zwraca znormalizowane informacje na temat wydawnictwa
     * @return String zawierajacy informacje o wydawnictwie (id,nazwa)
     */
    public String GetNormalizedInfo(){
        return getId() + "\t\t\t" + getNazwa();
    }

    // Getters and setters
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String GetFullInfo(){
        return getId() + "\t\t\t" + getNazwa();
    }
    public List<String> CurrentValuesAsString() {
        return Arrays.asList(getId().toString(),getNazwa());
    }
}
