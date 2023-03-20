pipeline {
    agent any

    stages {
        stage('Git clone') {
            steps {
                git url 'https://github.com/tonnyatresolve/test-frog-notification.git'
            }
        }
        stage('Build code') {
            steps {
                withMaven {
                    sh "mvn clean verify"
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