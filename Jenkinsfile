pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/Mayank212021/Amazon_Automation_Framework.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }
}
