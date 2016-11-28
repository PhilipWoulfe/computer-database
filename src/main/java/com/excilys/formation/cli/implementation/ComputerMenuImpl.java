package com.excilys.formation.cli.implementation;

import java.util.Scanner;
import com.excilys.formation.cli.ComputerMenu;
import com.excilys.formation.cli.Controller;
import com.excilys.formation.cli.MainMenu;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.util.MenuUtil;

/**
 * Manage the menus and operations for the computers.
 * @author kfuster
 *
 */
public class ComputerMenuImpl implements ComputerMenu {
    private static ComputerMenu computerMenu;
    private Page<ComputerDto> pageComputer;
    private Scanner scanner = MainMenu.scanner;
    private PageFilter pageFilter;
    private Controller controller = new Controller();
    /**
     * ComputerMenuImpl constructor. Initialize ComputerService.
     */
    private ComputerMenuImpl() {
        pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
    }
    /**
     * Getter for the ComputerMenuImpl instance. Initializes it if null.
     * @return the instance of ComputerMenuImpl
     */
    public static ComputerMenu getInstance() {
        if (computerMenu == null) {
            computerMenu = new ComputerMenuImpl();
        }
        return computerMenu;
    }
    /**
     * Shows the main operations available.
     * <ul>
     * <li>1 : Computer list</li>
     * <li>2 : Computer informations</li>
     * <li>3 : Create</li>
     * <li>4 : Update</li>
     * <li>5 : Delete</li>
     * <li>6 : Back</li>
     * </ul>
     */
    @Override
    public void startMenu() {
        System.out.println(
                "Voici les opérations disponibles : \n1 : Voir la liste des ordinateurs\n2 : Voir les informations d'un ordinateur\n3 : Créer un ordinateur\n4 : Mettre à jour un ordinateur\n5 : Supprimer un ordinateur\n6 : Retour");
        int choice = MenuUtil.waitForInt();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        boolean quit = false;
        switch (choice) {
        case 1:
            list();
            quit = false;
            break;
        case 2:
            info();
            quit = false;
            break;
        case 3:
            create();
            quit = false;
            break;
        case 4:
            update();
            quit = false;
            break;
        case 5:
            delete();
            quit = false;
            break;
        case 6:
            quit = true;
            break;
        default:
            System.out.println("Opération non disponible");
            quit = false;
            break;
        }
        if (!quit) {
            startMenu();
        }
    }
    @Override
    public void list() {
        pageFilter.setPageNum(1);
        pageComputer = new Page<>(10);
        // While the user doesn't quit the list, continue.
        do {
            pageComputer = controller.getPageComputer(pageFilter);
            showPage();
        } while (MenuUtil.manageNavigation(pageFilter));
    }
    /**
     * Asks the service to populate the list of elements and show them.
     */
    private void showPage() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ComputerDto computer : pageComputer.elements) {
            stringBuilder.append(computer.toString()).append("\n");
        }
        stringBuilder.append("Page : ").append(pageComputer.page).append(" / ").append(pageComputer.totalPages)
                .append("\nOptions :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter");
        System.out.println(stringBuilder.toString());
    }
    @Override
    public void info() {
        System.out.println("Entrez l'id de l'ordinateur dont vous souhaitez voir les infos (ou entrée pour annuler) :");
        String infoId = MenuUtil.waitForLine();
        long idToShow = -1;
        try {
            idToShow = Long.parseLong(infoId);
        } catch (NumberFormatException e) {
            System.out.println("Vous devez entrer un nombre");
            return;
        }
        ComputerDto computerToShow = controller.getComputerById(idToShow);
        if (computerToShow != null) {
            System.out.println(new StringBuilder().append("Nom : ").append(computerToShow.getName())
                    .append("\nDate de début de production : ").append(computerToShow.getIntroduced())
                    .append("\nDate de fin de production : ").append(computerToShow.getDiscontinued())
                    .append("\nId de la compagnie : ").append(computerToShow.getCompanyId()).toString());
        } else {
            System.out.println("Aucun ordinateur trouvé");
        }
    }
    @Override
    public void create() {
        ComputerDto computerDto = new ComputerDto();
        System.out.println("Veuillez entrez un nom pour l'ordinateur (ou entrée pour annuler) :");
        String name = "";
        name = MenuUtil.waitForLine();
        if (name.isEmpty()) {
            return;
        }
        computerDto.setName(name);
        System.out.println(
                "Vous pouvez entrez une date de début de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
        computerDto.setIntroduced(MenuUtil.inputDate());
        System.out.println(
                "Vous pouvez entrez une date d'arrêt de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
        computerDto.setDiscontinued(MenuUtil.inputDate());
        System.out.println("Entrez l'id de la compagnie fabricant l'ordinateur");
        computerDto.setCompanyId(MenuUtil.waitForInt());
        controller.createComputer(computerDto);
    }
    @Override
    public void update() {
        System.out.println("Entrez l'id de l'ordinateur à mettre à jour (ou entrée pour annuler) :");
        String input = MenuUtil.waitForLine();
        long idToUpdate = -1;
        try {
            idToUpdate = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Vous devez entrer un nombre");
            return;
        }
        ComputerDto computerDto = controller.getComputerById(idToUpdate);
        if (computerDto != null) {
            // Asking for new name
            System.out.println(new StringBuilder().append("Entrez un nouveau nom si vous souhaitez le changer (")
                    .append(computerDto.getName()).append(") :").toString());
            String newName = MenuUtil.waitForLine();
            if (!newName.isEmpty()) {
                computerDto.setName(newName);
            }
            // Asking for new introduced date
            System.out.println(new StringBuilder().append("Entrez une nouvelle date de début de production (")
                    .append(computerDto.getIntroduced())
                    .append(") au format aaaa-mm-jj (\"null\" pour retirer la date):").toString());
            computerDto.setIntroduced(MenuUtil.inputNewDate(computerDto.getIntroduced()));
            // Asking for new discontinued date
            System.out.println(new StringBuilder().append("Entrez une nouvelle date de fin de production (")
                    .append(computerDto.getDiscontinued())
                    .append(") au format aaaa-mm-jj (\"null\" pour retirer la date):").toString());
            computerDto.setDiscontinued(MenuUtil.inputNewDate(computerDto.getDiscontinued()));
            // Asking for new company id
            System.out.println("Vous pouvez entrer un nouvel id de compagnie (" + computerDto.getCompanyId() + ") :");
            String newCompanyId = scanner.nextLine();
            if (!newCompanyId.isEmpty()) {
                try {
                    long companyId = Long.parseLong(newCompanyId);
                    computerDto.setCompanyId(companyId);
                } catch (NumberFormatException e) {
                    System.out.println("Vous devez entrer un nombre");
                    return;
                }
            }
            controller.updateComputer(computerDto);
        } else {
            System.out.println("Aucun ordinateur trouvé");
        }
    }
    @Override
    public void delete() {
        System.out.println("Entrez l'id de l'ordinateur à supprimer (ou entrée pour annuler) : ");
        String input = MenuUtil.waitForLine();
        long idToDelete = -1;
        try {
            idToDelete = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Vous devez entrer un nombre");
            return;
        }
        controller.deleteComputer(idToDelete);
        System.out.println("Ordinateur supprimé");
    }
}