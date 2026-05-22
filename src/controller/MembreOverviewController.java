package controller;

import service.ActiviteService;
import service.InscriptionService;
import util.Lang;
import util.UIConstants;
import view.components.OverviewPanel;
import view.components.OverviewPanel.BreakdownItem;

import java.util.ArrayList;
import java.util.List;

import model.Utilisateur;

public class MembreOverviewController {

    private final OverviewPanel view;
    private final ActiviteService activiteService = new ActiviteService();
    private final InscriptionService inscriptionService = new InscriptionService();

    public MembreOverviewController(Utilisateur membre) {
        view = new OverviewPanel();
        refreshUI(membre);
    }

    public void refreshUI(Utilisateur membre) {

        view.getCardsPanel().removeAll();

        view.getCardsPanel().add(view.createStatCard(
            Lang.get("activities.available"),
            String.valueOf(activiteService.getNombreActivitesDisponibles())
        ));

        view.getCardsPanel().add(view.createCompoundCard(
            Lang.get("registrations"),
            String.valueOf(inscriptionService.getNombreMesInscriptions(membre.getId())),
            List.of(
                new BreakdownItem(
                    Lang.get("ACCEPTEE"),
                    String.valueOf(inscriptionService.getNombreInscriptionsAccepteePourMembre(membre.getId())),
                    UIConstants.emeraldGreen
                ),
                new BreakdownItem(
                    Lang.get("EN_ATTENTE"),
                    String.valueOf(inscriptionService.getNombreInscriptionsEnAttentePourMembre(membre.getId())),
                    UIConstants.sunflowerYellow
                ),
                new BreakdownItem(
                    Lang.get("REFUSEE"),
                    String.valueOf(inscriptionService.getNombreInscriptionsRefuseePourMembre(membre.getId())),
                    UIConstants.alizarinRed
                )
            )
        ));

        view.getCardsPanel().revalidate();
        view.getCardsPanel().repaint();
    }

    public OverviewPanel getView() {
        return view;
    }
}