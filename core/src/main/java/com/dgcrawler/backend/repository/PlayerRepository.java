
package com.dgcrawler.backend.repository;

import com.dgcrawler.backend.database.Database;
import com.dgcrawler.backend.model.Player;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class PlayerRepository {

    private Database database;
    private Dao<Player, Integer> dao;

    private List<Player> loadedPlayers;
    private Player loadedPlayer;

    public PlayerRepository(Database database) throws SQLException {
        this.database = database;

        this.loadedPlayers = new ArrayList<>();
        this.loadedPlayer = null;

        dao = DaoManager.createDao(
            database.getConnection(),
            Player.class
        );

        TableUtils.createTableIfNotExists(
            database.getConnection(),
            Player.class
        );
    }

    public Player create(Player player) {
        try {
            int nrows = dao.create(player);

            if (nrows == 0) {
                throw new SQLException("Objeto não foi salvo!");
            }

            this.loadedPlayer = player;
            this.loadedPlayers.add(player);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    public void update(Player player) {
        try {
            dao.update(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Player player) {
        try {
            dao.delete(player);
            this.loadedPlayers.remove(player);

            if (this.loadedPlayer == player) {
                this.loadedPlayer = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Player loadFromId(int id) {
        try {
            this.loadedPlayer = dao.queryForId(id);

            if (this.loadedPlayer != null &&
                !this.loadedPlayers.contains(this.loadedPlayer)) {

                this.loadedPlayers.add(this.loadedPlayer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.loadedPlayer;
    }

    public List<Player> loadAll() {
        try {
            this.loadedPlayers = dao.queryForAll();

            if (!this.loadedPlayers.isEmpty()) {
                this.loadedPlayer = this.loadedPlayers.get(0);
            } else {
                this.loadedPlayer = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.loadedPlayers;
    }

    public List<Player> getLoadedPlayers() {
        return loadedPlayers;
    }

    public Player getLoadedPlayer() {
        return loadedPlayer;
    }
}
