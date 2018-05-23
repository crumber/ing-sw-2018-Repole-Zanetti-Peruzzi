mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=false
/etc/sonarqube/bin/macosx-universal-64/sonar.sh console