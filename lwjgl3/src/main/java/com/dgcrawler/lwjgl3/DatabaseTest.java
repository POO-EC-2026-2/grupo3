
package com.dgcrawler.lwjgl3;

import com.badlogic.gdx.files.FileHandle;

import com.dgcrawler.backend.database.DatabaseManager;
import com.dgcrawler.backend.model.Habilidade;

public class DatabaseTest {

    public static void main(String[] args) {

        FileHandle databaseFile =
            new FileHandle("data/test-rpg.db");

        DatabaseManager manager =
            new DatabaseManager(
                databaseFile.file().getAbsolutePath()
            );

        try {
            manager.initialize();

            System.out.println("===== TESTE DE CREATE =====");

            Habilidade habilidade = new Habilidade(
                35,          // dano
                true,        // stun
                true,        // burn
                false,       // posion
                false,       // lifesteal
                true,        // stmSteal
                "Impacto",   // tipo
                "GolpePesado", // nome
                15           // custo
            );

            manager.getHabilidadeRepository().create(habilidade);

            int idHabilidade = habilidade.getIdSkill();

            System.out.println("Habilidade cadastrada!");
            System.out.println("ID: " + idHabilidade);
            System.out.println("Nome: " + habilidade.getNomeSkill());

            System.out.println("\n===== TESTE DE READ =====");

            Habilidade habilidadeLida =
                manager.getHabilidadeRepository()
                    .loadFromId(idHabilidade);

            if (habilidadeLida == null) {
                System.out.println("Habilidade não encontrada!");
                return;
            }

            System.out.println("Nome: " + habilidadeLida.getNomeSkill());
            System.out.println("Dano: " + habilidadeLida.getDano());
            System.out.println("Custo: " + habilidadeLida.getCusto());
            System.out.println("Tipo: " + habilidadeLida.getTipo());
            System.out.println("Stun: " + habilidadeLida.isStun());
            System.out.println("Burn: " + habilidadeLida.isBurn());
            System.out.println("Poison: " + habilidadeLida.isPosion());
            System.out.println("Lifesteal: " + habilidadeLida.isLifesteal());
            System.out.println("StmSteal: " + habilidadeLida.isStmSteal());

            System.out.println("\n===== TESTE DE UPDATE =====");

            habilidadeLida.setDano(50);
            habilidadeLida.setCusto(20);
            habilidadeLida.setBurn(false);

            manager.getHabilidadeRepository().update(habilidadeLida);

            Habilidade habilidadeAtualizada =
                manager.getHabilidadeRepository()
                    .loadFromId(idHabilidade);

            System.out.println("Dano atualizado: "
                + habilidadeAtualizada.getDano());

            System.out.println("Custo atualizado: "
                + habilidadeAtualizada.getCusto());

            System.out.println("Burn atualizado: "
                + habilidadeAtualizada.isBurn());

            manager.close();

            System.out.println("\n===== TESTE DE DELETE =====");

            manager = new DatabaseManager(
                databaseFile.file().getAbsolutePath()
            );

            manager.initialize();

            Habilidade habilidadeParaExcluir =
                manager.getHabilidadeRepository()
                    .loadFromId(idHabilidade);

            manager.getHabilidadeRepository()
                .delete(habilidadeParaExcluir);

            Habilidade habilidadeExcluida =
                manager.getHabilidadeRepository()
                    .loadFromId(idHabilidade);

            if (habilidadeExcluida == null) {
                System.out.println("Habilidade excluída com sucesso!");
            } else {
                System.out.println("Falha ao excluir habilidade!");
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            manager.close();
        }
    }
}
