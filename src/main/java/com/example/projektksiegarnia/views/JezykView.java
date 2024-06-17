package com.example.projektksiegarnia.views;

import com.example.projektksiegarnia.DataBaseManager;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 * Klasa reprezentująca widok encji Jezyk.
 * Reprezentuje tabelę jezyki w bazie danych.
 */
@Entity
@Table(name="jezyki")
public class JezykView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nazwa;
    /**
     *  metoda ta dodaje nowy jezyk do bazy
     *       @param nazwa jezyk dodawanej ksiazki
     *
     */
    public static void AddNew(String nazwa){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        JezykView jezyk = new JezykView();
        jezyk.setNazwa(nazwa);

        s.merge(jezyk);
        t.commit();
        s.close();
    }
    /**
     *  metoda ta usuwa bierzacy jezyk z bazy
     */
    public void RemoveThis(){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.remove(this);
        t.commit();
        s.close();
    }
    /**
     * Metoda ta zwraca znormalizowane informacje na temat jezyka
     * @return String zawierajacy informacje o jezyku (id,nazwa)
     */
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
}
