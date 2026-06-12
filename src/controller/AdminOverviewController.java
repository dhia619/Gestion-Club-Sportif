package controller;

import service.ActiviteService;
import service.InscriptionService;
import service.UtilisateurService;
import util.Lang;
import util.UIConstants;
import view.components.OverviewPanel;
import view.components.OverviewPanel.BreakdownItem;

import java.util.ArrayList;
import java.util.List;

import model.ActiviteRow;
import model.MembreActifRow;

public class AdminOverviewController {

    private final OverviewPanel view;
    private final ActiviteService activiteService = new ActiviteService();
    private final UtilisateurService utilisateurService = new UtilisateurService();
    private final InscriptionService inscriptionService = new InscriptionService();

    public AdminOverviewController() {
        view = new OverviewPanel();
        refreshUI();
    }

    public void refreshUI() {

        view.getCardsPanel().removeAll();

        view.getCardsPanel().add(view.createStatCard(
            Lang.get("members"),
            String.valueOf(utilisateurService.getNombreMembres())
        ));

        view.getCardsPanel().add(view.createCompoundCard(
            Lang.get("activities"),
            String.valueOf(activiteService.getNombreActivites()),
            List.of(
                new BreakdownItem(
                    Lang.get("DISPONIBLE"),
                    String.valueOf(activiteService.getNombreActivitesDisponibles()),
                    UIConstants.emeraldGreen
                ),
                new BreakdownItem(
                    Lang.get("COMPLET"),
                    String.valueOf(activiteService.getNombreActivitesCompletes()),
                    UIConstants.alizarinRed
                )
            )
        ));

        view.getCardsPanel().add(view.createCompoundCard(
            Lang.get("registrations"),
            String.valueOf(inscriptionService.getNombreInscriptions()),
            List.of(
                new BreakdownItem(
                    Lang.get("EN_ATTENTE"),
                    String.valueOf(inscriptionService.getNombreInscriptionsEnAttente()),
                    UIConstants.sunflowerYellow
                )
            )
        ));

        ArrayList<MembreActifRow> rows = inscriptionService.getMembresPlusActifs(3);
        ArrayList<BreakdownItem> items = new ArrayList<>();

        int i = 0;
        for (MembreActifRow row : rows) {
            items.add(new BreakdownItem(
                row.getMembre().getLogin(),
                String.valueOf(row.getNbInscriptionsAccepte()),
                UIConstants.belizeBlue
            ));

            i++;
            if (i >= 2) break;
        }

        view.getCardsPanel().add(view.createCompoundCard(
            Lang.get("members.most.active"),
            "",
            items
        ));

        ArrayList<ActiviteRow> activitesPopulaires = activiteService.getActivitesPopulaires(3);
        ArrayList<BreakdownItem> activitesPopulairesItems = new ArrayList<BreakdownItem>();

        for (ActiviteRow a : activitesPopulaires) {
            if (a.getActivite() != null) {
                activitesPopulairesItems.add(
                    new BreakdownItem(a.getActivite().getNom(), String.valueOf(a.getPlacesRestantes()), UIConstants.belizeBlue)
                );
            }
        }

        view.getCardsPanel().add(view.createCompoundCard(
            Lang.get("activities.popular"), 
            "", 
            activitesPopulairesItems
        ));

        view.getCardsPanel().revalidate();
        view.getCardsPanel().repaint();
    }

    public OverviewPanel getView() {
        return view;
    }
}