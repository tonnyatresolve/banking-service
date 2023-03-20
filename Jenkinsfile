pipeline {
    agent any

    stages {
        stage('Git clone') {
            steps {
                git 'https://github.com/tonnyatresolve/test-frog-notification.git'
            }
        }
        stage('Build code') {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}