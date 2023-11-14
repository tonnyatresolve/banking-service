node {
    def server
    def rtMaven
    def buildInfo


    stage ('Git checkout') {
        git url: 'https://github.com/tonnyatresolve/test-frog-notification.git'
    }

    stage ('Push to artifactory') {
        // env.JAVA_HOME = '/opt/jdk-15.0.2'
        // env.JAVA_HOME = '/opt/jdk-17'
        // Obtain an Artifactory server instance, defined in Jenkins --> Manage Jenkins --> Configure System:
        server = Artifactory.server 'jfartifactory'

        rtMaven = Artifactory.newMavenBuild()
        // Tool name from Jenkins configuration
        rtMaven.tool = 'Maven3.6.3'
        rtMaven.deployer releaseRepo: 'test-maven', snapshotRepo: 'test-maven', server: server
        //rtMaven.deployer releaseRepo: 'test-maven-virtual', snapshotRepo: 'test-maven-virtual', server: server
        rtMaven.resolver releaseRepo: 'test-maven-virtual', snapshotRepo: 'test-maven-virtual', server: server
        buildInfo = Artifactory.newBuildInfo()
        // buildInfo.name = "xdo:sample:banking-service2"

        // Exec Maven
        rtMaven.run pom: 'pom.xml', goals: 'clean install', buildInfo: buildInfo
        //rtMaven.run pom: 'pom.xml', goals: 'deploy', buildInfo: buildInfo
        
        // Publish build info
        server.publishBuildInfo buildInfo
    }

    stage ('Xray artifactory scan') {
      try{
        echo buildInfo.name
        echo buildInfo.number
        def scanConfig = [
            'buildName'      : buildInfo.name,
            'buildNumber'    : buildInfo.number,
            'failBuild'      : true
        ]
        def scanResult = server.xrayScan scanConfig > test.txt
        // server.xrayScan scanConfig > test.txt
        // echo scanResult as String
      } catch(error) {
        echo buildInfo.name
        echo buildInfo.number 
        // ls -rlt
        // cat buildInfo.name-buildInfo.number-result.json
        sh "exit 1"
      }

        // echo "test"
        // echo scanResult as String
        // echo scanResult > buildInfo.name-buildInfo.number-result.txt
        // ls -rlt
        // cat buildInfo.name-buildInfo.number-result.txt
    }

    // stage ('Xray artifactory scan') {
        // try {
                // def scanConfig = [
                        // 'buildName'      : buildInfo.name,
                        // 'buildNumber'    : buildInfo.number,
                        // 'failBuild'      : true
                // ]
                // def scanResult = server.xrayScan scanConfig
                // echo scanResult as String
        // } catch(error) {
            // echo "vulnerability found, please fix and rebuild"
            // testing from chris
            // sh 'curl https://www.google.com'
            // retry(2) {
                // input "Violation found, retry the scan?"
                // def scanConfig = [
                        // 'buildName'      : buildInfo.name,
                        // 'buildNumber'    : buildInfo.number,
                        // 'failBuild'      : true
                // ]
                // def scanResult = server.xrayScan scanConfig
                // echo scanResult as String
            // }
        // }
    // }
    
    stage ('Deploy') {
        echo "Deploy"
    }
    /*
    stage ('Build Docker') {
        sh 'docker pull openjdk:21-jdk'
        sh 'docker tag openjdk:21-jdk 192.168.11.60:8082/test-docker/openjdk:21-jdk'
        //def server = Artifactory.server 'docker2'
   
        // Step 2: Create an Artifactory Docker instance:
        def rtDocker = Artifactory.docker server: server
        // Or if the docker daemon is configured to use a TCP connection:
        // def rtDocker = Artifactory.docker server: server, host: "tcp://<docker daemon host>:<docker daemon port>"
        // If your agent is running on OSX:
        // def rtDocker= Artifactory.docker server: server, host: "tcp://127.0.0.1:1234"
    
        // Step 3: Push the image to Artifactory.
        // Make sure that <artifactoryDockerRegistry> is configured to reference <targetRepo> Artifactory repository. In case it references a different repository, your build will fail with "Could not find manifest.json in Artifactory..." following the push.
        def dockerBuildInfo = rtDocker.push '192.168.11.60:8082/test-docker/openjdk:21-jdk', 'openjdk:21-jdk'
        
        // Step 4: Publish the build-info to Artifactory:
        server.publishBuildInfo dockerBuildInfo
    }

    stage ('Xray docer scan') {
        try {
                def scanConfig = [
                        'buildName'      : dockerBuildInfo.name,
                        'buildNumber'    : dockerBuildInfo.number,
                        'failBuild'      : true
                ]
                def scanResult = server.xrayScan scanConfig
                echo scanResult as String
        } catch(error) {
            echo "First build failed, let's retry if accepted"
            retry(2) {
                input "Retry the job ?"
                def scanConfig = [
                        'buildName'      : buildInfo.name,
                        'buildNumber'    : buildInfo.number,
                        'failBuild'      : true
                ]
                def scanResult = server.xrayScan scanConfig
                echo scanResult as String
            }
        }
    }
    */
}
