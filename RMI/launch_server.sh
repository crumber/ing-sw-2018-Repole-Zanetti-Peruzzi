LOCAL=${1:-0}

if [[ $LOCAL -eq 0 ]]; then
  echo "Using local codebase..."
  java \
    -Djava.rmi.server.useCodebaseOnly=false \
    -Djava.rmi.server.logCalls=true \
    -Djava.rmi.server.codebase="file://`pwd`/target/classes/repolezanettiperuzzi/controller/ file://`pwd`/target/classes/repolezanettiperuzzi/model/ file://`pwd`/target/classes/repolezanettiperuzzi/common/" \
    -cp target/classes/repolezanettiperuzzi/controller:target/classes/repolezanettiperuzzi/model:target/classes/repolezanettiperuzzi/common: \
    StartServers
else
  echo "Using remote codebase..."
  java \
    -Djava.rmi.server.useCodebaseOnly=false \
    -Djava.rmi.server.logCalls=true \
    -Djava.rmi.server.codebase="http://localhost:8080/controller/ http://localhost:8080/model/ http://localhost:8080/common/" \
    -cp ./target/classes/repolezanettiperuzzi/controller:./target/classes/repolezanettiperuzzi/model:./target/classes/repolezanettiperuzzi/common \
    StartServers
fi