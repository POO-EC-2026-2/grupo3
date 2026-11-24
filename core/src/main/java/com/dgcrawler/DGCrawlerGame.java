package com.dgcrawler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.dgcrawler.view.screens.MainMenuScreen;
import com.dgcrawler.backend.database.DatabaseManager;
import com.dgcrawler.backend.database.GameDataInitializer;
import java.sql.SQLException;

public class DGCrawlerGame extends Game {

    private DatabaseManager databaseManager;

    @Override
    public void create() {

        databaseManager = new DatabaseManager(
            Gdx.files.local("data/rpg.db").file().getAbsolutePath()
        );

        try {
            databaseManager.initialize();

            System.out.println("Banco de dados inicializado com sucesso!");

            GameDataInitializer dataInitializer =
                new GameDataInitializer(databaseManager);

            dataInitializer.inicializarHabilidadesIniciais();
            dataInitializer.inicializarJogadorInicial();
            dataInitializer.inicializarMonstrosIniciais();

        } catch (SQLException e) {

            System.err.println("Erro ao inicializar o banco de dados:");
            e.printStackTrace();

            Gdx.app.exit();
            return;
        }

        setScreen(new MainMenuScreen(this));
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void dispose() {

        super.dispose();

        if (databaseManager != null) {
            databaseManager.close();
        }
    }
}
