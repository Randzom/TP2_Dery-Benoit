package com.example.testjavafx;

import java.io.Serializable;

/**
 * Un cours ayant un nom, un code et une session à laquelle il est donné.
 */
public class Course implements Serializable {

    private String name;
    private String code;
    private String session;

    /**
     * l'unique constructeur de la classe Course
     *
     * @param name    la <code>String</code> du nom du cours
     * @param code    la <code>String</code> du code du cours
     * @param session la <code>String</code> de la session à laquelle le cours est donné
     */
    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    /**
     * Retourne le nom du cours.
     *
     * @return la <code>String</code> du nom du cours
     */
    public String getName() {
        return name;
    }

    /**
     * Remplace le nom du cours par celui entré en paramètre.
     *
     * @param name la <code>String</code> du nouveau nom du cours
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le code du cours.
     *
     * @return la <code>String</code> du code du cours
     */
    public String getCode() {
        return code;
    }

    /**
     * Remplace le code du cours par celui entré en paramètre.
     *
     * @param code la <code>String</code> du nouveau code du cours
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Retourne la session à laquelle le cours est donné.
     *
     * @return la <code>String</code> de la session à laquelle le cours est donné
     */
    public String getSession() {
        return session;
    }

    /**
     * Remplace la session à laquelle le cours est donné par celle en paramètre.
     *
     * @param session la <code>String</code> de la nouvelle session à laquelle le cours est donné
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * Retourne une <code>String</code> avec le nom de la classe et le contenu de chacun des champs.
     *
     * @return une <code>String</code> avec le nom de la classe et le contenu de chacun des champs
     */
    @Override
    public String toString() {
        return "Course{" +
                "name=" + name +
                ", code=" + code +
                ", session=" + session +
                '}';
    }
}
