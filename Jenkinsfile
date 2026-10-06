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
		environment {
    AMAZON_CREDENTIALS = credentials('amazon_valid_user_01')
}
		
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
            archiveArtifacts artifacts: 'target/screenshots/**', allowEmptyArchive: true

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
    subject: "${currentBuild.currentResult}: Automation Report - Build #${env.BUILD_NUMBER}",
    mimeType: 'text/html',
    body: """
    <h2>🚀 Automation Execution Report</h2>

    <p><b>Job:</b> ${env.JOB_NAME}</p>
    <p><b>Build:</b> #${env.BUILD_NUMBER}</p>

    <p><b>Status:</b>
        <span style="color:${currentBuild.currentResult == 'SUCCESS' ? 'green' : 'red'};">
            <b>${currentBuild.currentResult}</b>
        </span>
    </p>

    <p><b>Browser:</b> ${params.browser} |
       <b>Suite:</b> ${params.TEST_TYPE}</p>

    <h3>📊 Test Summary</h3>
    <p><b>Total Tests:</b> \${TEST_COUNTS,var="total"}</p>
    <p><b>Passed:</b> \${TEST_COUNTS,var="pass"}</p>
    <p><b>Failed:</b> \${TEST_COUNTS,var="fail"}</p>
    <p><b>Skipped:</b> \${TEST_COUNTS,var="skip"}</p>

    <h3>📄 Reports</h3>

    <p>
        <a href="${env.BUILD_URL}Extent_20Report/">
            Extent Report
        </a>
    </p>

    <p>
        <a href="${env.BUILD_URL}console">
            Console Log
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