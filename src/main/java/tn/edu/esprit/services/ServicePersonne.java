package tn.edu.esprit.services;

import tn.edu.esprit.entites.Personne;
import tn.edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePersonne implements IService<Personne> {

    private Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Personne p) {
        String req = "INSERT INTO personne (nom, prenom) VALUES (?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.executeUpdate();
            System.out.println("Personne ajoutée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Personne p) {
        String req = "UPDATE personne SET nom = ?, prenom = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setInt(3, p.getId());
            ps.executeUpdate();
            System.out.println("Personne mise à jour !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM personne WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Personne supprimée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    public Personne getOne(int id) {
        String req = "SELECT * FROM personne WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Personne> getAll() {
        List<Personne> list = new ArrayList<>();
        String req = "SELECT * FROM personne";
        try (Statement st = cnx.createStatement()) {
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return list;
    }
}
