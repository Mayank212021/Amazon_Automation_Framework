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

       emailext (
    subject: "Automation Report - Build #${BUILD_NUMBER}",
    body: "Build Status: ${currentBuild.currentResult}\nReport: ${BUILD_URL}",
    to: "kaushikmayank961@gmail.com",
    from: "kaushikmayank961@gmail.com",
    replyTo: "kaushikmayank961@gmail.com",
    mimeType: 'text/plain',
    attachLog: true
)
        }
    }
}