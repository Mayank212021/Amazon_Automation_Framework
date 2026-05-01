pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test || exit 0'
            }
        }
    }

    post {
        always {

            // HTML archive
            archiveArtifacts artifacts: 'target/*.html', allowEmptyArchive: true

            // Extent Report UI
            publishHTML([
                reportDir: 'target',
                reportFiles: 'ExtentReport_*.html',
                reportName: 'Extent Report',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])

            // Cucumber Report UI
            publishHTML([
                reportDir: 'target',
                reportFiles: 'cucumber-report-*.html',
                reportName: 'Cucumber Report',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])
        }
    } 
}
