
package model;

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

    public void potionHp() {

        if (potionsHp <= 0) {
            System.out.println("Você não possui poções de HP!");
            return;
        }

        if (getHp() >= getHpMax()) {
            System.out.println("Seu HP já está cheio!");
            return;
        }

        int hpAnterior = getHp();

        setHp(getHp() + 5);

        int recuperado = getHp() - hpAnterior;

        potionsHp--;

        System.out.println("Você recuperou " + recuperado + " HP!");
        System.out.println("Poções de HP restantes: " + potionsHp);
    }

    public void potionStm() {

        if (potionsStm <= 0) {
            System.out.println("Você não possui poções de STM!");
            return;
        }

        if (getStm() >= getStmMax()) {
            System.out.println("Sua STM já está cheia!");
            return;
        }

        int stmAnterior = getStm();

        setStm(getStm() + 3);

        int recuperado = getStm() - stmAnterior;

        potionsStm--;

        System.out.println("Você recuperou " + recuperado + " STM!");
        System.out.println("Poções de STM restantes: " + potionsStm);
    }

    public void trocarArma() {
        if (arma == 1) {
            this.arma = 0;
            this.setAtaque1(1);
            this.setAtaque2(2);
            this.setAtaque3(3);
            this.setAtaque4(4);
        } else {
            this.arma = 1;
            this.setAtaque1(5);
            this.setAtaque2(6);
            this.setAtaque3(7);
            this.setAtaque4(8);
        }
    }

    public int atacar(int id, Monstro monstro) {

        Database database = new Database("rpg.db");
        HabilidadeRepository habilidadeRepo =
            new HabilidadeRepository(database);

        try {
            Habilidade habilidade = habilidadeRepo.loadFromId(id);

            if (habilidade == null) {
                System.out.println("Habilidade não encontrada!");
                return 0;
            }

            int custo = habilidade.getCusto();
            int dano = habilidade.getDano();
            String tipo = habilidade.getTipo();

            if (this.getStm() < custo) {
                System.out.println("STM insuficiente!");
                return 0;
            }

            setStm(this.getStm() - custo);

            Random random = new Random();
            int dado = random.nextInt(20) + 1;

            if (dado <= monstro.getAgility()) {
                System.out.println("O monstro desviou do ataque!");
                return 0;
            }

            System.out.println("Ataque acertou!");

            int danoFinal = dano;

            if (tipo != null && tipo.equals(monstro.getFraqueza())) {
                danoFinal *= 2;
                System.out.println("Efetivo!");
            }

            monstro.setHp(monstro.getHp() - danoFinal);

            System.out.println("Dano causado: " + danoFinal);

            if (habilidade.isBurn()) {
                monstro.setTurnosBurn(2);
                System.out.println("O monstro foi queimado!");
            }

            if (habilidade.isPosion()) {
                monstro.setTurnosPoison(3);
                System.out.println("O monstro foi envenenado!");
            }

            if (habilidade.isStun()) {
                monstro.setTurnosStun(1);
                System.out.println("O monstro ficou atordoado!");
            }

            if (habilidade.isLifesteal()) {
                setHp(getHp() + 2);
                System.out.println("Você recuperou 2 HP!");
            }

            if (habilidade.isStmSteal()) {
                if (monstro.getStm() > 0) {
                    monstro.setStm(monstro.getStm() - 1);
                    setStm(getStm() + 1);
                    System.out.println("Você roubou 1 STM do monstro!");
                } else {
                    System.out.println("O monstro não possui STM para roubar!");
                }
            }

            return danoFinal;

        } finally {
            database.close();
        }
    }
}
