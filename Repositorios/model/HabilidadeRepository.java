
package model;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class HabilidadeRepository {

    private Database database;
    private Dao<Habilidade, Integer> dao;

    private List<Habilidade> loadedHabilidades;
    private Habilidade loadedHabilidade;

    public HabilidadeRepository(Database database) {
        this.database = database;

        this.loadedHabilidades = new ArrayList<>();
        this.loadedHabilidade = null;

        try {
            dao = DaoManager.createDao(
                database.getConnection(),
                Habilidade.class
            );

            TableUtils.createTableIfNotExists(
                database.getConnection(),
                Habilidade.class
            );

        } catch (SQLException e) {
            System.out.println("Erro ao iniciar HabilidadeRepository:");
            e.printStackTrace();
        }
    }

    public Habilidade create(Habilidade habilidade) {
        try {
            int nrows = dao.create(habilidade);

            if (nrows == 0) {
                throw new SQLException("Objeto não foi salvo!");
            }

            this.loadedHabilidade = habilidade;
            this.loadedHabilidades.add(habilidade);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return habilidade;
    }

    public void update(Habilidade habilidade) {
        try {
            dao.update(habilidade);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Habilidade habilidade) {
        try {
            dao.delete(habilidade);

            this.loadedHabilidades.remove(habilidade);

            if (this.loadedHabilidade == habilidade) {
                this.loadedHabilidade = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Habilidade loadFromId(int id) {
        try {
            this.loadedHabilidade = dao.queryForId(id);

            if (this.loadedHabilidade != null &&
                !this.loadedHabilidades.contains(this.loadedHabilidade)) {

                this.loadedHabilidades.add(this.loadedHabilidade);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.loadedHabilidade;
    }

    public List<Habilidade> loadAll() {
        try {
            this.loadedHabilidades = dao.queryForAll();

            if (!this.loadedHabilidades.isEmpty()) {
                this.loadedHabilidade = this.loadedHabilidades.get(0);
            } else {
                this.loadedHabilidade = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.loadedHabilidades;
    }

    public List<Habilidade> getLoadedHabilidades() {
        return loadedHabilidades;
    }

    public Habilidade getLoadedHabilidade() {
        return loadedHabilidade;
    }
}
