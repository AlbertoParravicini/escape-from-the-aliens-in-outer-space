**Gruppo 8**

*Ingegneria del Software, a.a. 2014/2015*


Alberto Parravicini

Simone Ripamonti

#Features#
* RMI
* Socket
* CLI
* GUI
* XML maps
* Map vote

#How to run the project#

1. Start server
2. Connect at least 2 clients
3. Wait the timeout or connect 6 more clients

###Server launcher:
**Server.java** in it.polimi.ingsw.cg_8.server package.

Server automatically starts both RMI and Socket connection handler.

###GUI launcher:
**MainGUI.java** in it.polimi.ingsw.cg_8.client.gui package.

To start a game press 'play' button

###CLI launcher:
**MainCLI.java** in it.polimi.ingsw.cg_8.client.cli package.

To start a game enter your name, the connection method and the map vote.
When the match has started write 'actions' to see the available commands.

