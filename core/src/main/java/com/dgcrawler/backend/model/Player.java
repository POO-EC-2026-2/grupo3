
package com.dgcrawler.backend.model;

import java.util.Random;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "player")
public class Player extends Entidade implements Inventario {

    @DatabaseField
    private String name;

    @DatabaseField
    private int arma;

    @DatabaseField
    private int potionsHp;

    @DatabaseField
    private int potionsStm;

    public Player() {
        super();

        setAtaque1(1);
        setAtaque2(2);
        setAtaque3(3);
        setAtaque4(4);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPotionsHp() {
        return potionsHp;
    }

    public void setPotionsHp(int potionsHp) {
        this.potionsHp = Math.max(0, potionsHp);
    }

    public int getPotionsStm() {
        return potionsStm;
    }

    public void setPotionsStm(int potionsStm) {
        this.potionsStm = Math.max(0, potionsStm);
    }

    public int getArma() {
        return arma;
    }

    public void potionHp() {

        if (potionsHp <= 0 || getHp() >= getHpMax()) {
            return;
        }

        setHp(getHp() + 5);
        potionsHp--;
    }

    public void potionStm() {

        if (potionsStm <= 0 || getStm() >= getStmMax()) {
            return;
        }

        setStm(getStm() + 3);
        potionsStm--;
    }

    public void trocarArma() {

        if (arma == 1) {
            this.arma = 0;

            setAtaque1(1);
            setAtaque2(2);
            setAtaque3(3);
            setAtaque4(4);

        } else {
            this.arma = 1;

            setAtaque1(5);
            setAtaque2(6);
            setAtaque3(7);
            setAtaque4(8);
        }
    }

    public int atacar(Monstro monstro) {

        if (monstro == null || monstro.getHp() <= 0) {
            return 0;
        }

        Random random = new Random();
        int dado = random.nextInt(20) + 1;

        setStm(getStm() + 2);

        if (dado <= monstro.getAgility()) {
            return 0;
        }

        int dano = 2;

        String tipo = (arma == 0) ? "Slash" : "Blunt";

        if (tipo.equals(monstro.getFraqueza())) {
            dano *= 2;
        }

        monstro.setHp(monstro.getHp() - dano);

        return dano;
    }

    public int usarHabilidade(Habilidade habilidade, Monstro monstro) {

        if (habilidade == null || monstro == null ||
            monstro.getHp() <= 0) {
            return 0;
        }

        int custo = habilidade.getCusto();

        if (getStm() < custo) {
            return 0;
        }

        setStm(getStm() - custo);

        Random random = new Random();
        int dado = random.nextInt(20) + 1;

        if (dado <= monstro.getAgility()) {
            return 0;
        }

        int danoFinal = habilidade.getDano();

        String tipo = habilidade.getTipo();

        if (tipo != null && tipo.equals(monstro.getFraqueza())) {
            danoFinal *= 2;
        }

        monstro.setHp(monstro.getHp() - danoFinal);

        if (habilidade.isBurn()) {
            monstro.setTurnosBurn(2);
        }

        if (habilidade.isPosion()) {
            monstro.setTurnosPoison(3);
        }

        if (habilidade.isStun()) {
            monstro.setTurnosStun(1);
        }

        if (habilidade.isLifesteal()) {
            setHp(getHp() + 2);
        }

        if (habilidade.isStmSteal() && monstro.getStm() > 0) {
            monstro.setStm(monstro.getStm() - 1);
            setStm(getStm() + 1);
        }

        return danoFinal;
    }
}
