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
			
			
			
			script {
    def metricsFile = 'target/retry-metrics.properties'

    if (fileExists(metricsFile)) {

        def metrics = [:]

        readFile(metricsFile).readLines().each { line ->

            def parts = line.split('=', 2)

            if (parts.size() == 2) {
                metrics[parts[0].trim()] = parts[1].trim()
            }
        }

        echo "📊 Retry Metrics File Found: ${metricsFile}"
        echo "📊 Retry Metrics: ${metrics}"

        env.RETRY_EXECUTED = metrics['retryExecuted'] ?: '0'
        env.RECOVERED_BY_RETRY = metrics['recoveredByRetry'] ?: '0'
        env.FINAL_FAILURES = metrics['finalFailures'] ?: '0'

    } else {

        echo "⚠️ Retry Metrics File Not Found"

        env.RETRY_EXECUTED = '0'
        env.RECOVERED_BY_RETRY = '0'
        env.FINAL_FAILURES = '0'
    }
}


script {
    echo "🔎 Generating Failure Analysis..."

    powershell '''
        $jsonFile = Get-ChildItem "target\\cucumber-*.json" |
                    Sort-Object LastWriteTime -Descending |
                    Select-Object -First 1

        if ($null -eq $jsonFile) {
            "<p>⚠️ Failure Analysis: Cucumber JSON report not found.</p>" |
            Set-Content "target\\failure-analysis.html"
            exit 0
        }

        $data = Get-Content $jsonFile.FullName -Raw | ConvertFrom-Json

        $html = "<h3>❌ FAILURE ANALYSIS</h3>"

        $failureNumber = 0

        foreach ($feature in $data) {

            foreach ($scenario in $feature.elements) {

                $failedSteps = @(
                    $scenario.steps | Where-Object {
                        $_.result.status -eq "failed"
                    }
                )

                if ($failedSteps.Count -gt 0) {

                    $failureNumber++

                    $failedStep = $failedSteps[0]

                    $errorMessage = $failedStep.result.error_message

                    if ([string]::IsNullOrWhiteSpace($errorMessage)) {
                        $errorMessage = "Failure details not available."
                    }

                    $errorLines = $errorMessage -split "`r?`n"

                   $exception = "Test Failure"
$source = "See Cucumber Report"
$lineNumber = "See Cucumber Report"

$firstLine = $errorLines[0]

$colonIndex = $firstLine.IndexOf(":")

if ($colonIndex -gt 0) {
    $exception = $firstLine.Substring(0, $colonIndex).Trim()
}

foreach ($stackLine in $errorLines) {

    if ($stackLine.Contains(".java:") -and
    $stackLine.Contains("(") -and
    -not $stackLine.Contains("org.testng")) {

        $openBracket = $stackLine.LastIndexOf("(")
        $closeBracket = $stackLine.LastIndexOf(")")

        if ($openBracket -ge 0 -and $closeBracket -gt $openBracket) {

            $location = $stackLine.Substring(
                $openBracket + 1,
                $closeBracket - $openBracket - 1
            )

            $colonIndex = $location.LastIndexOf(":")

            if ($colonIndex -gt 0) {

                $source = $location.Substring(
                    0,
                    $colonIndex
                )

                $lineNumber = $location.Substring(
                    $colonIndex + 1
                )

                break
            }
        }
    }
}
                    $safeScenario =
                        [System.Net.WebUtility]::HtmlEncode($scenario.name)

                    $safeStep =
                        [System.Net.WebUtility]::HtmlEncode($failedStep.name)

                    $safeError =
                        [System.Net.WebUtility]::HtmlEncode($errorLines[0])

                    $safeException =
                        [System.Net.WebUtility]::HtmlEncode($exception)

                    $safeSource =
                        [System.Net.WebUtility]::HtmlEncode($source)

                    $safeLine =
                        [System.Net.WebUtility]::HtmlEncode($lineNumber)
                        
                        
                        
                        $safeScenarioFile = [regex]::Replace($scenario.name, '[^a-zA-Z0-9-_]', '_')

$screenshots = @(
    Get-ChildItem "target/screenshots/*.png" |
    Where-Object {
        $_.Name.StartsWith($safeScenarioFile + "_")
    } |
    Sort-Object LastWriteTime
)

$screenshotHtml = "<p><b>📸 Screenshot:</b> Not available</p>"

if ($screenshots.Count -gt 0) {

    $screenshotHtml = "<p><b>📸 Screenshot:</b><br>"

    $shotNumber = 0

    foreach ($shot in $screenshots) {

        $shotNumber++

        $artifactUrl = $env:BUILD_URL +
                        "artifact/target/screenshots/" +
                        $shot.Name

        $screenshotHtml +=
            "<a href='$artifactUrl'>View Screenshot #$shotNumber</a><br>"
    }

    $screenshotHtml += "</p>"
}
                        
                        
                        

                    $html += @"
<hr>

<p><b>Failure #$failureNumber</b></p>

<p><b>Scenario:</b> $safeScenario</p>

<p><b>Failed Step:</b> $safeStep</p>

<p><b>Exception:</b> $safeException</p>

<p><b>Error Message:</b><br>
$safeError
</p>

<p><b>Source:</b> $safeSource</p>

<p><b>Line:</b> $safeLine</p>
$screenshotHtml
"@
                }
            }
        }

        if ($failureNumber -eq 0) {
            $html = "<h3>✅ FAILURE ANALYSIS</h3><p>No failed scenarios found.</p>"
        }

        $html | Set-Content "target\\failure-analysis.html"
    '''

    if (fileExists('target/failure-analysis.html')) {
        env.FAILURE_ANALYSIS = readFile('target/failure-analysis.html')
        echo "✅ Failure Analysis generated successfully"
    } else {
        env.FAILURE_ANALYSIS =
            '<p>⚠️ Failure Analysis not available.</p>'
    }
}

			
			
			
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

    <p>
        <b>Browser:</b> ${params.browser}
        &nbsp; | &nbsp;
        <b>Suite:</b> ${params.TEST_TYPE}
    </p>

    <hr>

    <h3>📊 Test Summary</h3>

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Metric</th>
            <th>Count</th>
        </tr>
        <tr>
            <td>Total Tests</td>
            <td>\${TEST_COUNTS,var="total"}</td>
        </tr>
        <tr>
            <td>Passed</td>
            <td>\${TEST_COUNTS,var="pass"}</td>
        </tr>
        <tr>
            <td>Failed</td>
            <td>\${TEST_COUNTS,var="fail"}</td>
        </tr>
        <tr>
            <td>Skipped</td>
            <td>\${TEST_COUNTS,var="skip"}</td>
        </tr>
    </table>

    <h3>🔄 Retry Summary</h3>

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Metric</th>
            <th>Result</th>
        </tr>
        <tr>
            <td>Retry Executed</td>
            <td>${env.RETRY_EXECUTED == '1' ? 'Yes' : 'No'}</td>
        </tr>
        <tr>
            <td>Recovered by Retry</td>
            <td>${env.RECOVERED_BY_RETRY}</td>
        </tr>
        <tr>
            <td>Final Failures</td>
           <td>${env.FINAL_FAILURES}</td>
        </tr>
    </table>

    <h3>❌ Failure Details</h3>

${env.FAILURE_ANALYSIS}

    <h3>📄 Reports & Artifacts</h3>

    <p>
        🔹 <a href="${env.BUILD_URL}Extent_20Report/">
            Extent Report
        </a>
    </p>

    <p>
        🔹 <a href="${env.BUILD_URL}Cucumber_20Report/">
            Cucumber Report
        </a>
    </p>

    <p>
        🔹 <a href="${env.BUILD_URL}console">
            Console Log
        </a>
    </p>

<p>
    🔹 Screenshots are available in Jenkins Build Artifacts.
</p>
    <hr>

    <p>
        <b>Automation CI/CD Report</b><br>
        Generated by Jenkins
    </p>
    """,
    attachmentsPattern: 'target/ExtentReport_*.html',
    attachLog: true
)
        }
    }
}