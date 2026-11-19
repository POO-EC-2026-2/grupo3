
package model;

import java.util.Scanner;

public class Teste {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Database database = new Database("rpg.db");

        HabilidadeRepository habilidadeRepo =
            new HabilidadeRepository(database);

        Player player = new Player();
        Monstro monstro = new Monstro("Impacto");

        player.setName("Jogador");

        player.setHpMax(20);
        player.setHp(20);
        player.setStmMax(10);
        player.setStm(20);
        player.setAgility(10);

        monstro.setHpMax(10);
        monstro.setHp(10);
        monstro.setStmMax(50);
        monstro.setStm(50);
        monstro.setAgility(10);

        System.out.println("===== CONFIGURAÇÃO DO MONSTRO =====");

        System.out.println("Digite os IDs das 4 habilidades do monstro:");

        monstro.setAtaque1(scanner.nextInt());
        monstro.setAtaque2(scanner.nextInt());
        monstro.setAtaque3(scanner.nextInt());
        monstro.setAtaque4(scanner.nextInt());

        int turno = 1;

        try {
            while (player.getHp() > 0 && monstro.getHp() > 0) {

                System.out.println("\n========== TURNO " + turno + " ==========");

                System.out.println("\n===== TURNO DO JOGADOR =====");

                player.processarEfeitosTurno();

                if (player.getHp() <= 0) {
                    break;
                }

                if (player.estaAtordoado()) {
                    System.out.println("O jogador está atordoado e perdeu o turno!");
                } else {
                    System.out.println("HP: " + player.getHp() + "/" + player.getHpMax());
                    System.out.println("STM: " + player.getStm() + "/" + player.getStmMax());

                    System.out.println("Digite o ID da habilidade que deseja usar:");
                    int id = scanner.nextInt();

                    int dano = player.atacar(id, monstro);

                    if (dano > 0) {
                        System.out.println("O jogador causou " + dano + " de dano!");
                    }
                }

                if (monstro.getHp() <= 0) {
                    break;
                }

                System.out.println("\n===== TURNO DO MONSTRO =====");

                monstro.processarEfeitosTurno();

                if (monstro.getHp() <= 0) {
                    break;
                }

                if (monstro.estaAtordoado()) {
                    System.out.println("O monstro está atordoado e perdeu o turno!");
                } else {
                    int dano = monstro.atacar(player);

                    if (dano > 0) {
                        System.out.println("O monstro causou " + dano + " de dano!");
                    }
                }

                System.out.println("\n===== STATUS DO COMBATE =====");

                System.out.println("Jogador: " + player.getHp() + "/" + player.getHpMax() + " HP");
                System.out.println("Jogador STM: " + player.getStm() + "/" + player.getStmMax());

                System.out.println("Monstro: " + monstro.getHp() + "/" + monstro.getHpMax() + " HP");
                System.out.println("Monstro STM: " + monstro.getStm() + "/" + monstro.getStmMax());

                turno++;
            }

            System.out.println("\n========== FIM DO COMBATE ==========");

            if (player.getHp() <= 0 && monstro.getHp() <= 0) {
                System.out.println("Empate! Os dois morreram!");
            } else if (player.getHp() <= 0) {
                System.out.println("O monstro venceu!");
            } else {
                System.out.println("O jogador venceu!");
            }

            System.out.println("Total de turnos: " + turno);

        } finally {
            database.close();
            scanner.close();
        }
    }
}
