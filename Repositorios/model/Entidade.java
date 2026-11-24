
package model;

import com.j256.ormlite.field.DatabaseField;

public abstract class Entidade {

    @DatabaseField(generatedId = true)
    protected int id;

    @DatabaseField
    protected int turnosBurn;

    @DatabaseField
    protected int turnosPoison;

    @DatabaseField
    protected int turnosStun;

    @DatabaseField
    protected int hp = 10;

    @DatabaseField
    protected int hpMax = 10;

    @DatabaseField
    protected int agility = 10;

    @DatabaseField
    protected int str;

    @DatabaseField
    protected int stm = 10;

    @DatabaseField
    protected int stmMax = 10;

    @DatabaseField
    protected int ataque1;

    @DatabaseField
    protected int ataque2;

    @DatabaseField
    protected int ataque3;

    @DatabaseField
    protected int ataque4;

    public Entidade() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, hpMax));
    }

    public int getHpMax() {
        return hpMax;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = Math.max(0, hpMax);

        if (this.hp > this.hpMax) {
            this.hp = this.hpMax;
        }
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = Math.max(0, agility);
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = Math.max(0, str);
    }

    public int getStm() {
        return stm;
    }

    public void setStm(int stm) {
        this.stm = Math.max(0, Math.min(stm, stmMax));
    }

    public int getStmMax() {
        return stmMax;
    }

    public void setStmMax(int stmMax) {
        this.stmMax = Math.max(0, stmMax);

        if (this.stm > this.stmMax) {
            this.stm = this.stmMax;
        }
    }

    public int getAtaque1() {
        return this.ataque1;
    }

    public void setAtaque1(int ataque1) {
        this.ataque1 = ataque1;
    }

    public int getAtaque2() {
        return this.ataque2;
    }

    public void setAtaque2(int ataque2) {
        this.ataque2 = ataque2;
    }

    public int getAtaque3() {
        return this.ataque3;
    }

    public void setAtaque3(int ataque3) {
        this.ataque3 = ataque3;
    }

    public int getAtaque4() {
        return this.ataque4;
    }

    public void setAtaque4(int ataque4) {
        this.ataque4 = ataque4;
    }

    public int getTurnosBurn() {
        return turnosBurn;
    }

    public void setTurnosBurn(int turnosBurn) {
        this.turnosBurn = Math.max(0, turnosBurn);
    }

    public int getTurnosPoison() {
        return turnosPoison;
    }

    public void setTurnosPoison(int turnosPoison) {
        this.turnosPoison = Math.max(0, turnosPoison);
    }

    public int getTurnosStun() {
        return turnosStun;
    }

    public void setTurnosStun(int turnosStun) {
        this.turnosStun = Math.max(0, turnosStun);
    }

    public int processarEfeitosTurno() {
        int danoTotal = 0;

        if (turnosBurn > 0) {
            setHp(getHp() - 1);
            turnosBurn--;
            danoTotal++;

            System.out.println("Burn causou 1 de dano!");
        }

        if (turnosPoison > 0) {
            setHp(getHp() - 1);
            turnosPoison--;
            danoTotal++;

            System.out.println("Poison causou 1 de dano!");
        }

        return danoTotal;
    }

    public boolean estaAtordoado() {
        if (turnosStun > 0) {
            turnosStun--;
            return true;
        }

        return false;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entidade other = (Entidade) o;
        return this.getId() != 0 && this.getId() == other.getId();
    }
    public int hashCode() {
        return Integer.hashCode(this.getId());
    }
}
