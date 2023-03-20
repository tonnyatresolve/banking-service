node {
    def server
    def rtMaven
    def buildInfo

    stage ('Clone') {
        git url: 'https://github.com/tonnyatresolve/test-frog-notification.git'
    }

    stage ('Artifactory configuration') {
        env.JAVA_HOME = '/opt/jdk-15.0.2'
        // Obtain an Artifactory server instance, defined in Jenkins --> Manage Jenkins --> Configure System:
        server = Artifactory.server 'docker2'

        rtMaven = Artifactory.newMavenBuild()
        // Tool name from Jenkins configuration
        rtMaven.tool = 'Maven'
        rtMaven.deployer releaseRepo: 'test-maven', snapshotRepo: 'test-maven', server: server
        //rtMaven.deployer releaseRepo: 'test-maven-virtual', snapshotRepo: 'test-maven-virtual', server: server
        rtMaven.resolver releaseRepo: 'test-maven-virtual', snapshotRepo: 'test-maven-virtual', server: server
        buildInfo = Artifactory.newBuildInfo()
    }

    stage ('Exec Maven') {
        //rtMaven.opts = '--debug'
        rtMaven.run pom: 'pom.xml', goals: 'clean install', buildInfo: buildInfo
        //rtMaven.run pom: 'pom.xml', goals: 'deploy', buildInfo: buildInfo

        /*
        withMaven (
                    maven: 'Maven'
                ) {
                    sh "/opt/apache-maven-3.9.1/bin/mvn clean install"
                }
        */
        echo "exec maven"
    }

    stage ('Publish build info') {
        //server.publishBuildInfo buildInfo
    }

    stage ('Xray scan') {
        def scanConfig = [
                'buildName'      : buildInfo.name,
                'buildNumber'    : buildInfo.number,
                'failBuild'      : true
        ]
        def scanResult = server.xrayScan scanConfig
        echo scanResult as String
    }
}