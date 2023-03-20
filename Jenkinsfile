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
                sh 'whoami'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}