node {
    def server
    def rtMaven
    def buildInfo
    // def scanConfig
    // def scanResult
    
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

    // stage ('Collect env vars') {
    //     buildInfo.env.filter.addExclude("DONT_COLLECT*")

    //     // By default the filter is configured to exclude "*password*,*secret*,*key*", but since we're overriding this configuration by adding our own exclusion, let's add these excludes:
    //     buildInfo.env.filter
    //         .addExclude("*password*")        
    //         .addExclude("*secret*")        
    //         .addExclude("*key*")        

    //     withEnv(['DO_COLLECT_FOO=BAR', 'DONT_COLLECT_FOO=BAR']) {
    //         buildInfo.env.collect()
    //     }
    // }

    // stage ('Access build info env vars') {
    //     // BAR will printed
    //     echo buildInfo.env.vars['DO_COLLECT_FOO']

    //     // null will be printed, because we excluded it.
    //     echo buildInfo.env.vars['DONT_COLLECT_FOO'] 
    // }

    // stage ('Set build retention') {
    //     buildInfo.retention maxBuilds: 1, maxDays: 2, doNotDiscardBuilds: ["3"], deleteBuildArtifacts: true
    // }

    stage ('Xray artifactory scan') {
      def scanBuildInfo = buildInfo
      def buildName = buildInfo.name
      echo buildName
      def buildNumber = buildInfo.number
      echo buildNumber
      def (p1, p2, p3) = buildName.replace(" :: ", "_").split("_")
      def HIGH_WATCH_NAME = p1 + '_' + p2 + '_' + 'high'
      def LOW_WATCH_NAME = p1 + '_' + p2 + '_' + 'low'
      def REPORT_NAME = p1 + '_' + p2 + '_' + p3 + '_' + BUILD_NUMBER
      echo HIGH_WATCH_NAME
      echo LOW_WATCH_NAME
      scanConfig = []
      // scanResult = []

      // try{
        echo buildInfo.name
        echo buildInfo.number


        scanConfig = [
          'buildName'      : scanBuildInfo.name,
          'buildNumber'    : scanBuildInfo.number,
          'failBuild'      : false,
          'printTable'     : true
        ]
        def scanResult = server.xrayScan scanConfig
        echo "zzzzz"

        def result = scanResult.toString()
        def logFile = 'ScanResult'+'-'+buildNumber+'.log'
        writeFile(file: logFile, text: result, encoding: "UTF-8")

        // sh """curl --user $creds --header 'Content-Type: application/json' --request POST --data '{"builds":[{"name":"${buildName}"}]}' https://jfartifactory.resolve.local:8081/xray/api/v1/violations/ignored |jq '.data[] | select(.impacted_artifact.version == "${BUILD_NUMBER}")| {"violation_id": .violation_id, "issue_id": .issue_id, "created": .created, "watch_name": .watch_name, "description": .description, "severity": .severity, "properties": .properties, "matched_policies": .matched_policies, "ignore_rule_details": .ignore_rule_details}' >> IgnoredViolation-${BUILD_NUMBER}.log"""
        // sh """
             // curl --user $creds --header 'Content-Type: application/json' --request POST --data '{"builds":[{"name":"${buildName}"}]}' 'https://jfartifactory.resolve.local:8081/xray/api/v1/violations/ignored?limit=1000000&order_by=updated&offset=1' |jq '.data[] | select(.impacted_artifact.version == "${BUILD_NUMBER}")' >> IgnoredViolation-${BUILD_NUMBER}.log
        // """        
        // sh """curl --user $creds --header 'Content-Type: application/json' --request POST --data '{"builds":[{"name":"${buildName}","version":"${BUILD_NUMBER}"}]}' https://jfartifactory.resolve.local:8081/xray/api/v1/violations/ignored |jq '.' >> IgnoredViolation-${BUILD_NUMBER}.log"""

        // sh "curl --user $creds https://jfartifactory.resolve.local:8081/xray/api/v1/violations/ignored/${LOW_WATCH_NAME}|jq >> IgnoredViolation-${BUILD_NUMBER}.log"

        def REPORT_ID = sh(script: """curl --user $creds --header 'Content-Type: application/json' --request POST --data '{"name":"${REPORT_NAME}","resources":{"builds":{"names":["${buildName}"],"number_of_latest_versions":2}},"filters":{"violation_status":"ignored"}}' 'https://jfartifactory.resolve.local:8081/xray/api/v1/reports/violations'|jq '.report_id'""", returnStdout: true).trim()
        
        while (true) {
           sh "sleep 5"
           def REPORT_STATUS = sh(script: """curl --user admin:P@ssw0rd --header 'Content-Type: application/json' --request GET 'https://jfartifactory.resolve.local:8081/xray/api/v1/reports/${REPORT_ID}'|jq .status""", returnStdout: true).trim()
           String str = "\"completed\""
           echo REPORT_STATUS
           echo str
           if ( REPORT_STATUS.equals(str) ){
             break
           } 
        }

        sh "echo 'report generate'"

        def IMPACTED_ARTIFACT = 'build://' + buildName + ":" + BUILD_NUMBER

        sh """curl --user $creds --header 'Content-Type: application/json' --request POST 'https://jfartifactory.resolve.local:8081/xray/api/v1/reports/violations/${REPORT_ID}?direction=asc&page_num=1&num_of_rows=10000'|jq '.rows[]| select(.impacted_artifact == "${IMPACTED_ARTIFACT}")' >> IgnoredViolation-${BUILD_NUMBER}.log"""
        
        sh """curl --user $creds --header 'Content-Type: application/json' --request DELETE 'https://jfartifactory.resolve.local:8081/xray/api/v1/reports/{${REPORT_ID}}'"""

        sh """echo '' >> IgnoredViolation-${BUILD_NUMBER}.log"""
        sh """echo '=================================== Ignored Rules ===================================' >> IgnoredViolation-${BUILD_NUMBER}.log"""
        sh """curl --user $creds --header 'Content-Type: application/json' --request GET 'https://jfartifactory.resolve.local:8081/xray/api/v1/ignore_rules'|jq .data[] >> IgnoredViolation-${BUILD_NUMBER}.log"""

        // sh """
          // echo ${REPORT_ID} \
        
          // until ["$(curl --user $creds --header 'Content-Type: application/json' --request GET 'https://jfartifactory.resolve.local:8081/xray/api/v1/reports/${REPORT_ID}'|jq .status)" == "completed"] \
          // do \
            //sleep 5 \
          // done \
          
          // echo "report gen"
        // """

        sh 'ls -rlt'

        def uploadSpec = """{
          "files": [
           {
              "pattern": "*.log",
              "target": "upload-test/${JOB_NAME}/"
            }
          ]
        }"""

        def xrayBuildInfo = Artifactory.newBuildInfo()
        xrayBuildInfo.name = scanBuildInfo.name
        xrayBuildInfo.number = scanBuildInfo.number + '.xray'

        def uploadBuildInfo = server.upload spec: uploadSpec, buildInfo: xrayBuildInfo

        server.publishBuildInfo uploadBuildInfo

        sh 'echo ${WORKSPACE}'
        sh 'echo ${JOB_NAME}'
        sh 'rm -rf ${WORKSPACE}/ScanResult-*.log'
        sh 'rm -rf ${WORKSPACE}/IgnoredViolation-*.log'

        if (scanResult.isFoundVulnerable()){
          error('Stopping early… got Xray issues ')
        }

    //   String json = echo scanResult as String
      // new File("result.json").write(scanResult)

      // } catch(error) {
        // echo "xxxxx"
        // echo scanConfig as String
        // echo "yyyyy"
        // echo scanResult as String
        // echo "wwwww"
        // echo error as String
        // error | Out-File 'error.log' -Append
        // sh 'ls -rlt'

        // echo buildName as String
        // // def resultFile = new File(buildName-buildNumber.json)
        // writeFile(file: 'buildName.txt', text: error)
        // sh 'ls -rlt'        

        //sh 'echo "https://jfartifactory.resolve.local:8081/api/v2/ci/build/(buildName)/(buildNumber)[?include_vulnerabilities=true]"'
        // sh 'ls -lrt'
        // sh 'cat scan-result.txt'
        // echo scanResult as String
        //   echo buildInfo.number
        //   echo buildInfo.name

        //   def buildName=`echo buildInfo.name|sed -i 's| |%20|g'
        //   echo buildName

        //   sh "exit 1"
        // }
    }

    // stage ('Xray artifactory scan2') {
    //   echo scanResult as String
    // }

    // stage ('Xray artifactory scan') {
    //     try {
    //             def scanConfig = [
    //                     'buildName'      : buildInfo.name,
    //                     'buildNumber'    : buildInfo.number,
    //                     'failBuild'      : true
    //             ]
    //             def scanResult = server.xrayScan scanConfig
    //             echo scanResult as String
    //     } catch(error) {
    //         echo "vulnerability found, please fix and rebuild"
    //         testing from chris
    //         sh 'curl https://www.google.com'
    //         retry(2) {
    //             input "Violation found, retry the scan?"
    //             def scanConfig = [
    //                     'buildName'      : buildInfo.name,
    //                     'buildNumber'    : buildInfo.number,
    //                     'failBuild'      : true
    //             ]
    //             def scanResult = server.xrayScan scanConfig
    //             echo scanResult as String
    //         }
    //     }
    // }
    
    stage ('Deploy') {
        echo "Deploy"
    }

    interactivePromotion(server, buildInfo)

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

def interactivePromotion(def promoServer, def promoBuildInfo) {
    def promotionConfig = [
      'buildName'      : promoBuildInfo.name,
      'buildNumber'    : promoBuildInfo.number,
      'targetRepo'     : 'ga-maven',
      'sourceRepo'     : 'test-maven',
      'comment'        : 'Test Promotion',
      'status'         : 'General Availability'
    ]
    def promotionXrayConfig = [
      'buildName'      : promoBuildInfo.name,
      'buildNumber'    : promoBuildInfo.number + '.xray',
      'targetRepo'     : 'ga-maven',
      'sourceRepo'     : 'upload-test',
      'comment'        : 'Test Xray Promotion',
      'status'         : 'General Availability'
    ]
    Artifactory.addInteractivePromotion server: promoServer, promotionConfig: promotionConfig, displayName: "Promote artifacts to GA"
    Artifactory.addInteractivePromotion server: promoServer, promotionConfig: promotionXrayConfig, displayName: "Promote xray log to GA"
}