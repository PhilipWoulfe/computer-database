package com.excilys.formation;

import org.springframework.context.ApplicationContext;

import com.excilys.formation.cli.MainMenu;

/**
 * Main class, launch the menu.
 * @author kfuster
 *
 */
public class ComputerDatabase {
    public static ApplicationContext context;

    /**
     * Launches the main menu.
     * @param args a list of arguments
     */
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.startMenu();
    }
}