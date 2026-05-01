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

        // 🔥 EMAIL भेजना
        emailext (
            subject: "Automation Report - Build #${BUILD_NUMBER}",
            body: """
            Build Status: ${currentBuild.currentResult}

            View Reports:
            ${BUILD_URL}

            """,
            to: "kaushikmayank961@gmail.com"
        )
    }
}