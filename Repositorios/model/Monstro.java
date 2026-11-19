
package model;

import java.util.Random;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "monstro")
public class Monstro extends Entidade {

    @DatabaseField
    protected String fraqueza = "Impacto";

    public Monstro() {
        super();
    }

    public Monstro(String fraqueza) {
        super();
        this.fraqueza = fraqueza;
    }

    public String getFraqueza() {
        return this.fraqueza;
    }

    public void setFraqueza(String fraqueza) {
        this.fraqueza = fraqueza;
    }

    public int atacar(Player player) {
        Database database = new Database("rpg.db");
        HabilidadeRepository habilidadeRepo =
            new HabilidadeRepository(database);

        try {
            Random random = new Random();

            int[] ataques = {
                getAtaque1(),
                getAtaque2(),
                getAtaque3(),
                getAtaque4()
            };

            int idHabilidade = ataques[random.nextInt(ataques.length)];

            if (idHabilidade <= 0) {
                System.out.println("O monstro não possui uma habilidade nesse espaço!");
                return 0;
            }

            Habilidade habilidade = habilidadeRepo.loadFromId(idHabilidade);

            if (habilidade == null) {
                System.out.println("Habilidade não encontrada!");
                return 0;
            }

            int dano = habilidade.getDano();

            int dado = random.nextInt(20) + 1;

            if (dado <= player.getAgility()) {
                System.out.println("O jogador desviou do ataque!");
                return 0;
            }

            System.out.println("O ataque do monstro acertou!");

            player.setHp(player.getHp() - dano);

            if (habilidade.isBurn()) {
                player.setTurnosBurn(2);
            }

            if (habilidade.isPosion()) {
                player.setTurnosPoison(3);
            }

            if (habilidade.isStun()) {
                player.setTurnosStun(1);
            }

            if (habilidade.isLifesteal()) {
                setHp(getHp() + 2);
            }

            if (habilidade.isStmSteal()) {
                if (player.getStm() > 0) {
                    player.setStm(player.getStm() - 1);
                    setStm(getStm() + 1);
                }
            }

            return dano;

        } finally {
            database.close();
        }
    }
}
