package repolezanettiperuzzi.client;

import repolezanettiperuzzi.presentation.gui.FXMLController;

/**
 * JavaFX-specific client coordinator actions.
 */
public interface ClientGuiActions extends ClientViewActions {

    void setFXMLController(FXMLController fxmlController);
}
