pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Build the project
                sh './gradlew assemble'
            }
        }

        stage('Test') {
            steps {
                // Run unit tests
                sh './gradlew test'
            }
        }
    }

    post {
        always {
            // Publish test results if available
            junit 'build/test-results/**/*.xml'
        }
    }
}
