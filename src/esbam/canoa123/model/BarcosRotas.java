/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package esbam.canoa123.model;

/**
 *
 * @author ACER
 */
public class BarcosRotas {
    private int id;
    private Barcos barco;
    private Rotas rota;

    public BarcosRotas(int id, Barcos barco, Rotas rota) {
        this.id = id;
        this.barco = barco;
        this.rota = rota;
    }

    public int getId() {
        return id;
    }

    public Barcos getBarco() {
        return barco;
    }

    public Rotas getRota() {
        return rota;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBarco(Barcos barco) {
        this.barco = barco;
    }

    public void setRota(Rotas rota) {
        this.rota = rota;
    }
    
    
}
