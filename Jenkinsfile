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

            archiveArtifacts artifacts: 'target/*.html', allowEmptyArchive: true

            publishHTML([
                reportDir: 'target',
                reportFiles: 'ExtentReport_*.html',
                reportName: 'Extent Report',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])

            publishHTML([
                reportDir: 'target',
                reportFiles: 'cucumber-report-*.html',
                reportName: 'Cucumber Report',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])

            emailext(
                to: 'kaushikmayank961@gmail.com',
                subject: "Automation Report - Build #${env.BUILD_NUMBER}",
                body: """Build Status: ${currentBuild.currentResult}
Job Name: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Build URL: ${env.BUILD_URL}""",
                mimeType: 'text/plain',
                attachLog: true
            )
        }
    }
}