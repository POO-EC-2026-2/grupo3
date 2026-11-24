
package com.dgcrawler.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "monstro")
public class Monstro extends Entidade {

    @DatabaseField
    protected String nomeMonstro;

    @DatabaseField
    protected String fraqueza = "Impacto";

    public Monstro() {
        super();
    }

    public Monstro(
        String nomeMonstro,
        String fraqueza,
        int hpMax,
        int agility,
        int stmMax,
        int ataque1,
        int ataque2,
        int ataque3,
        int ataque4
    ) {
        super();

        this.nomeMonstro = nomeMonstro;
        this.fraqueza = fraqueza;

        this.hpMax = hpMax;
        this.hp = hpMax;

        this.agility = agility;

        this.stmMax = stmMax;
        this.stm = stmMax;

        this.ataque1 = ataque1;
        this.ataque2 = ataque2;
        this.ataque3 = ataque3;
        this.ataque4 = ataque4;

        this.turnosBurn = 0;
        this.turnosPoison = 0;
        this.turnosStun = 0;
    }

    public String getNomeMonstro() {
        return nomeMonstro;
    }

    public void setNomeMonstro(String nomeMonstro) {
        this.nomeMonstro = nomeMonstro;
    }

    public String getFraqueza() {
        return fraqueza;
    }

    public void setFraqueza(String fraqueza) {
        this.fraqueza = fraqueza;
    }

    public int atacar(Player player, List<Habilidade> habilidadesDisponiveis) {

        if (player == null || player.getHp() <= 0 ||
            habilidadesDisponiveis == null ||
            habilidadesDisponiveis.isEmpty()) {
            return 0;
        }

        List<Habilidade> ataquesValidos = new ArrayList<>();

        for (Habilidade habilidade : habilidadesDisponiveis) {
            if (habilidade != null) {
                ataquesValidos.add(habilidade);
            }
        }

        if (ataquesValidos.isEmpty()) {
            return 0;
        }

        Random random = new Random();

        Habilidade habilidade = ataquesValidos.get(
            random.nextInt(ataquesValidos.size())
        );

        int dano = habilidade.getDano();

        int dado = random.nextInt(20) + 1;

        if (dado <= player.getAgility()) {
            return 0;
        }

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
            this.setHp(this.getHp() + 2);
        }

        if (habilidade.isStmSteal() && player.getStm() > 0) {
            player.setStm(player.getStm() - 1);
            this.setStm(this.getStm() + 1);
        }

        return dano;
    }
}
