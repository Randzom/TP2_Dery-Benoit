package server.models;

import java.io.Serializable;

/**
 * Formulaire d'inscription d'un étudiant à un cours, avec son nom complet, son adresse courriel et son matricule.
 */
public class RegistrationForm implements Serializable {
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private Course course;

    /**
     * l'unique constructeur de la classe <code>RegistrationForm</code>
     *
     * @param prenom la <code>String</code> du prénom de l'étudiant
     * @param nom la <code>String</code> du nom de famille de l'étudiant
     * @param email la <code>String</code> de l'adresse courriel de l'étudiant
     * @param matricule la <code>String</code> du matricule de l'étudiant
     * @param course le <code>Course</code> auquel l'étudiant s'inscrit
     */
    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    /**
     * Retourne le prénom de l'étudiant.
     *
     * @return la <code>String</code> du prénom de l'étudiant
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Remplace le prénom de l'étudiant par celui entré en paramètre.
     *
     * @param prenom la <code>String</code> du nouveau prénom de l'étudiant
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Retourne le nom de l'étudiant.
     *
     * @return la <code>String</code> du nom de l'étudiant
     */
    public String getNom() {
        return nom;
    }

    /**
     * Remplace le nom de l'étudiant par celui entré en paramètre.
     *
     * @param nom la <code>String</code> du nouveau nom de l'étudiant
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne l'adresse courriel de l'étudiant.
     *
     * @return la <code>String</code> de l'adresse courriel de l'étudiant
     */
    public String getEmail() {
        return email;
    }

    /**
     * Remplace l'adresse courriel de l'étudiant par celle entrée en paramètre.
     *
     * @param email la <code>String</code> de la nouvelle adresse courriel de l'étudiant
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le matricule de l'étudiant.
     *
     * @return la <code>String</code> du matricule de l'étudiant
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Remplace le matricule de l'étudiant par celui entré en paramètre.
     *
     * @param matricule la <code>String</code> du nouveau matricule de l'étudiant
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * Retourne le cours auquel l'étudiant s'inscrit.
     *
     * @return le <code>Course</code> auquel l'étudiant s'inscrit
     * @see Course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Remplace le cours auquel l'étudiant s'inscrit par celui entré en paramètre.
     *
     * @param course le nouveau <code>Course</code> auquel l'étudiant s'inscrit
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Retourne une <code>String</code> avec le nom de la classe et le contenu de chacun des champs.
     *
     * @return une <code>String</code> avec le nom de la classe et le contenu de chacun des champs
     */
    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}
