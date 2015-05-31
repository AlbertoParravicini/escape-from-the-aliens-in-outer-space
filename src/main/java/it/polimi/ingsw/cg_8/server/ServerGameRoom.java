package it.polimi.ingsw.cg_8.server;

import it.polimi.ingsw.cg_8.client.SubscriberInterface;
import it.polimi.ingsw.cg_8.controller.Controller;
import it.polimi.ingsw.cg_8.controller.StateMachine;
import it.polimi.ingsw.cg_8.view.client.actions.ClientAction;
import it.polimi.ingsw.cg_8.view.server.ServerResponse;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerGameRoom extends ServerPublisher implements
		ServerGameRoomInterface {

	private SubscriberInterface clientRMI;

	protected ServerGameRoom(SubscriberInterface client) throws RemoteException {
		UnicastRemoteObject.exportObject(this, 7777);
		this.clientRMI = client;
	}

	@Override
	public void makeAction(int clientId, ClientAction action) throws RemoteException {

		Controller controller = Server.getId2Controller().get(
				clientId);
		boolean result = StateMachine.evaluateAction(controller,
				action, controller.getPlayerById(clientId));
		System.out.println("[DEBUG]"+result);
	}

	// ERRORE: clientRMI non è il riferimento al vero client remoto, ma un client che il server si crea!
	@Override
	public void dispatchMessage(ServerResponse message) {
		
		try {
			clientRMI.publishMessage(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
