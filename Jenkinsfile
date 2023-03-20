pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK15'
    }

    stages {
        def server
        def buildInfo
        def rtMaven

        stage('Artifactory configuration') {
            steps {
                // Obtain an Artifactory server instance, defined in Jenkins --> Manage Jenkins --> Configure System:
                server = Artifactory.server SERVER_ID

                rtMaven = Artifactory.newMavenBuild()
                // Tool name from Jenkins configuration
                rtMaven.tool = Maven
                rtMaven.deployer releaseRepo: ARTIFACTORY_LOCAL_RELEASE_REPO, snapshotRepo: ARTIFACTORY_LOCAL_SNAPSHOT_REPO, server: server
                rtMaven.resolver releaseRepo: ARTIFACTORY_VIRTUAL_RELEASE_REPO, snapshotRepo: ARTIFACTORY_VIRTUAL_SNAPSHOT_REPO, server: server
                buildInfo = Artifactory.newBuildInfo()
            }
        }
        stage('Git clone') {
            steps {
                git url: 'https://github.com/tonnyatresolve/test-frog-notification.git'
            }
        }
        stage ('Publish build info') {
            server.publishBuildInfo buildInfo
        }
        stage('Build code') {
            steps {
                /*
                withMaven (
                    maven: 'Maven'
                ) {
                    sh "/opt/apache-maven-3.9.1/bin/mvn deploy -s settings.xml"
                }
                */
                rtMaven.run pom: 'maven-examples/maven-example/pom.xml', goals: 'clean install', buildInfo: buildInfo
            }
        }
        stage('Xray scan') {
            steps {
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
}