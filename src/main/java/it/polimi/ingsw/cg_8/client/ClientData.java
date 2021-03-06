package it.polimi.ingsw.cg_8.client;

import it.polimi.ingsw.cg_8.view.server.ResponseCard;
import it.polimi.ingsw.cg_8.view.server.ResponseChat;
import it.polimi.ingsw.cg_8.view.server.ResponseMap;
import it.polimi.ingsw.cg_8.view.server.ResponseNoise;
import it.polimi.ingsw.cg_8.view.server.ResponsePrivate;
import it.polimi.ingsw.cg_8.view.server.ResponseState;
import it.polimi.ingsw.cg_8.view.server.ServerResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Class used to store the messages sent by the server to the client; when a new
 * message is added, the GUI is notified so that the message can be displayed on
 * screen. Three different storages are used, depending on the type of message
 * received.
 * 
 * @author Alberto Parravicini
 * @version 1.0
 */
public class ClientData extends Observable {
    /**
     * List used to store {@link ResponseChat chat messages}.
     */
    private List<ResponseChat> chat;
    /**
     * List used to store {@link ResponseNoise noises}.
     */
    private List<ResponseNoise> noise;
    /**
     * List used to store {@link ResponsePrivate private messages}.
     */
    private List<ResponsePrivate> privateMessages;
    /**
     * List used to store {@link ResponseCard card responses};
     */
    private ResponseCard cards;
    /**
     * State of the player, during a turn of the match;
     */
    private ResponseState state;
    /**
     * Map
     */
    private ResponseMap map;
    /**
     * Server last command ack or nack
     */
    private boolean lastAck;

    /**
     * Constructor, creates an empty collection of datas
     */
    public ClientData() {
        chat = new ArrayList<ResponseChat>();
        noise = new ArrayList<ResponseNoise>();
        privateMessages = new ArrayList<ResponsePrivate>();
        cards = null;
        state = null;
        map = null;
        lastAck = false;
    }

    /**
     * Stores the response in the appropriate section.
     * 
     * @param response
     *            The message sent by the server.
     */
    public void storeResponse(ServerResponse response) {
        if (response instanceof ResponseChat) {
            chat.add((ResponseChat) response);
            setChanged();
            notifyObservers("Chat");
            return;
        }
        if (response instanceof ResponseNoise) {
            noise.add((ResponseNoise) response);
            setChanged();
            notifyObservers("Noise");
            return;
        }
        if (response instanceof ResponsePrivate) {
            privateMessages.add((ResponsePrivate) response);
            setChanged();
            notifyObservers("Private");
            return;
        }
        if (response instanceof ResponseCard) {
            cards = (ResponseCard) response;
            setChanged();
            notifyObservers("Cards");
            return;
        }
        if (response instanceof ResponseState) {
            state = (ResponseState) response;
            setChanged();
            notifyObservers("State");
            return;
        }
        if (response instanceof ResponseMap) {
            map = (ResponseMap) response;
            setChanged();
            notifyObservers("Map");
        }
        return;
    }

    /**
     * Used to get the list of chat message.
     * 
     * @return list of ResponseChat received from the server
     */
    public List<ResponseChat> getChat() {
        return chat;
    }

    /**
     * Used to get the list of noise message.
     * 
     * @return list of ResponseNoise received from the server
     */
    public List<ResponseNoise> getNoise() {
        return noise;
    }

    /**
     * Used to get the list of private message.
     * 
     * @return list of ResponsePrivate received from the server
     */
    public List<ResponsePrivate> getPrivateMessages() {
        return privateMessages;
    }

    /**
     * Used to get the last ResponseMap received
     * 
     * @return ResponseMap received from the server
     */
    public ResponseMap getMap() {
        return map;
    }

    /**
     * Used to get the last message sent.
     * 
     * @return cards the cards held by the player, stored in
     *         {@link ResponseCard card response}
     */
    public ResponseCard getCards() {
        return cards;
    }

    /**
     * Used to get the last state added to the list.
     * 
     * @return state the state of the player.
     */
    public ResponseState getState() {
        return state;
    }

    /**
     * Used to get the last message added it the list.
     * 
     * @return the last {@link ResponseChat chat message}
     */
    public ResponseChat getLastChat() {
        return chat.get(chat.size() - 1);
    }

    /**
     * Used to get the last message added it the list.
     * 
     * @return the last {@link ResponseNoise noise}
     */
    public ResponseNoise getLastNoise() {
        return noise.get(noise.size() - 1);

    }

    /**
     * Used to get the last message added it the list.
     * 
     * @return the last {@link ResponsePrivate private message}
     */
    public ResponsePrivate getLastPrivate() {
        return privateMessages.get(privateMessages.size() - 1);

    }

    /**
     * Used to store the last ack received from the server
     * 
     * @param serverResponse
     *            ack received
     */
    public void storeAck(boolean serverResponse) {
        lastAck = serverResponse;
        setChanged();
        notifyObservers("Ack");
    }

    /**
     * Used to get the last ack received from the server
     * 
     * @return a string that tells if the ack was true or false
     */
    public String getAck() {
        if (lastAck) {
            return "Your action has been accepted by the server";
        } else {
            return "Your action has been refused by the server";
        }
    }

}
