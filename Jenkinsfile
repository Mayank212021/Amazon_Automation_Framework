pipeline {
    agent any

    parameters {
        choice(name: 'browser', choices: ['chrome', 'edge'], description: 'Select browser')
        choice(name: 'TEST_TYPE', choices: ['@Smoke', '@Regression'], description: 'Suite')
    }

    triggers {
        parameterizedCron('30 23 * * * %browser=edge;TEST_TYPE=@Regression')
    }

    stages {

        stage('Build') { 
            steps {
                bat 'mvn clean compile'
            }
        }

     stage('Test') {
    steps {
       bat 'mvn test -Dbrowser=%browser% -Dcucumber.filter.tags=%TEST_TYPE%'
    }
}
    } 

    post {
        always { 
			
            testNG(
    reportFilenamePattern: 'target/surefire-reports/testng-results.xml',
    escapeExceptionMsg: true,
    escapeTestDescp: true,
    showFailedBuilds: true
)
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
    
    mimeType: 'text/html',

    body: """
    <h2>🚀 Automation Execution Report</h2>

    <p><b>Job:</b> ${env.JOB_NAME}</p>
    <p><b>Build:</b> ${env.BUILD_NUMBER}</p>
    <p><b>Status:</b> ${currentBuild.currentResult}</p>

    <h3>📊 Extent Report</h3>
    <p>
        👉 <a href="${env.BUILD_URL}Extent_20Report/">
        Click here to view full Extent Report
        </a>
    </p>

    <br>
    <p>Thanks,<br>Jenkins</p>
    """,

    attachmentsPattern: 'target/ExtentReport_*.html',

    attachLog: true
)
        }
    }
}