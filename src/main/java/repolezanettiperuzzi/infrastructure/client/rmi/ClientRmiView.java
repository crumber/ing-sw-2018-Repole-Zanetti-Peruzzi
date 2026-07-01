package repolezanettiperuzzi.infrastructure.client.rmi;

import repolezanettiperuzzi.common.ClientStubRMI;

/**
 * RMI client callback exported by the client view.
 */
public interface ClientRmiView extends ClientStubRMI {

    void setRMIActive();
}
