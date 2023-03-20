pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('Git clone') {
            steps {
                git url: 'https://github.com/tonnyatresolve/test-frog-notification.git'
            }
        }
        stage('Build code') {
            steps {
                withMaven (
                    maven: 'Maven'
                ) {
                    sh "export JAVA_HOME=/root/jdk-15.0.2"
                    sh "export PATH=$JAVA_HOME/bin:/root/apache-maven-3.9.1/bin:$PATH"
                    sh "mvn deploy"
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}