package com.dgcrawler.backend.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "habilidade")
public class Habilidade {

    @DatabaseField
    protected int dano;

    @DatabaseField
    protected boolean stun;

    @DatabaseField
    protected boolean burn;

    @DatabaseField
    protected boolean posion;

    @DatabaseField
    protected boolean lifesteal;

    @DatabaseField
    protected boolean stmSteal;

    @DatabaseField
    protected String tipo;

    @DatabaseField
    protected String nomeSkill;

    @DatabaseField
    protected int custo;

    @DatabaseField(generatedId = true)
    protected int idSkill;

    public Habilidade() {
    }

    public Habilidade(int dano, boolean stun, boolean burn,
                      boolean posion, boolean lifesteal,
                      boolean stmSteal, String tipo,
                      String nomeSkill, int custo) {

        this.dano = dano;
        this.stun = stun;
        this.burn = burn;
        this.posion = posion;
        this.lifesteal = lifesteal;
        this.stmSteal = stmSteal;
        this.tipo = tipo;
        this.nomeSkill = nomeSkill;
        this.custo = custo;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public boolean isStun() {
        return stun;
    }

    public void setStun(boolean stun) {
        this.stun = stun;
    }

    public boolean isBurn() {
        return burn;
    }

    public void setBurn(boolean burn) {
        this.burn = burn;
    }

    public boolean isPosion() {
        return posion;
    }

    public void setPosion(boolean posion) {
        this.posion = posion;
    }

    public boolean isLifesteal() {
        return lifesteal;
    }

    public void setLifesteal(boolean lifesteal) {
        this.lifesteal = lifesteal;
    }

    public boolean isStmSteal() {
        return stmSteal;
    }

    public void setStmSteal(boolean stmSteal) {
        this.stmSteal = stmSteal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeSkill() {
        return nomeSkill;
    }

    public void setNomeSkill(String nomeSkill) {
        this.nomeSkill = nomeSkill;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public int getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(int idSkill) {
        this.idSkill = idSkill;
    }

}
