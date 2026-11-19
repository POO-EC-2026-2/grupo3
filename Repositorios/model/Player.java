
package model;

import java.util.Random;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "player")
public class Player extends Entidade {

    @DatabaseField
    private String name;

    @DatabaseField
    private int arma;

    public Player() {
        super();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 0 para espada, 1 para martelo de guerra
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
