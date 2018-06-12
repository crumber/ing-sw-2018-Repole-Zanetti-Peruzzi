#!/bin/bash
basedir=`pwd`/target/classes

mvn compile

if [[ $? -eq 0 ]]; then
  # client
  cp $basedir/FXMLController.class ./classloading/client/
  cp $basedir/GameView.class ./classloading/client/
  cp $basedir/GameViewCLI.class ./classloading/client/
  cp $basedir/GameViewGUI.class ./classloading/client/
  cp $basedir/GameViewRMI.class ./classloading/client/
  cp $basedir/GameViewSocket.class ./classloading/client/
  cp $basedir/LoginFXMLController.class ./classloading/client/
  cp $basedir/WaitingRoomFXMLController.class ./classloading/client/

  # server
  cp $basedir/BeginRoundState.class ./classloading/server/
  cp $basedir/Controller.class ./classloading/server/
  cp $basedir/ControllerRMIServer.class ./classloading/server/
  cp $basedir/ControllerState.class ./classloading/server/
  cp $basedir/EndGameState.class ./classloading/server/
  cp $basedir/FetchState.class ./classloading/server/
  cp $basedir/HandlerControllerRMI.class ./classloading/server/
  cp $basedir/HandlerControllerSocket.class ./classloading/server/
  cp $basedir/HandlerGameViewRMI.class ./classloading/server/
  cp $basedir/HandlerGameViewSocket.class ./classloading/server/
  cp $basedir/HandlerSkeletonRMI.class ./classloading/server/
  cp $basedir/HandlerSkeletonSocket.class ./classloading/server/
  cp $basedir/HandlerStubRMI.class ./classloading/server/
  cp $basedir/HandlerStubSocket.class ./classloading/server/
  cp $basedir/HandlerSkeletonSocket.class ./classloading/server/
  cp $basedir/ResetGameState.class ./classloading/server/
  cp $basedir/SetConnectionState.class ./classloading/server/
  cp $basedir/HandlerSkeletonSocket.class ./classloading/server/
  cp $basedir/HandlerSkeletonSocket.class ./classloading/server/
  cp $basedir/HandlerSkeletonSocket.class ./classloading/server/



  # codebase
  cp $basedir/Warehouse.class ./classloading/common/
  cp $basedir/Product.class ./classloading/common/
fi