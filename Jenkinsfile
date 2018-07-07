#!groovy​

properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

stage('build & unit tests & nexus & sonar') {
    node {
        checkout scm
        def v = version()
        currentBuild.displayName = "${env.BRANCH_NAME}-${v}-${env.BUILD_NUMBER}"
        mvn "clean deploy sonar:sonar"
    }
}

def branch_type = get_branch_type "${env.BRANCH_NAME}"
def branch_deployment_environment = get_branch_deployment_environment branch_type

if (branch_deployment_environment) {

    stage('build docker image') {

        node {
            mvn "clean package docker:build -DpushImage -DskipTests"
        }
    }

    stage('deploy artifact to DC/OS') {

        if (branch_deployment_environment == "prod") {
            timeout(time: 1, unit: 'DAYS') {
                input "Do you want to deploy to production?"
            }
        }

        node {
            deployArtifact branch_deployment_environment
        }
    }

    if (branch_deployment_environment != "prod") {


        stage('perform tests') {

            node {
                executeTests branch_deployment_environment
            }
        }
    }


    if (branch_type == "dev") {

        stage('start release') {

            timeout(time: 1, unit: 'HOURS') {
                input "Do you want to prepare a release process?"
            }
            node {
                mvn("jgitflow:release-start")
            }
        }
    } else if (branch_type == "release") {

        branch_deployment_environment = "uat"

        stage('deploy release candidate to UAT') {

            timeout(time: 1, unit: 'HOURS') {
                input 'Do you want to execute a release process? If so, after deployment and integration tests successfully passed against ${branch_deployment_environment}, release will be created...'
            }
            node {
                deployArtifact branch_deployment_environment
            }
        }

        stage('perform acceptation tests') {

            node {
                executeTests branch_deployment_environment
            }
        }

        stage('end release') {

            node {
                mvn("jgitflow:release-finish -Dmaven.javadoc.skip=true -DnoDeploy=true")
            }
        }
    } else if (branch_type == "hotfix") {

        stage('finish hotfix') {

            timeout(time: 1, unit: 'HOURS') {
                input "Is the hotfix finished?"
            }
            node {
                mvn("jgitflow:hotfix-finish -Dmaven.javadoc.skip=true -DnoDeploy=true")
            }
        }
    }
}

// Utility functions
def get_branch_type(String branch_name) {

    def dev_pattern = ".*develop"
    def release_pattern = ".*release/.*"
    def feature_pattern = ".*feature/.*"
    def hotfix_pattern = ".*hotfix/.*"
    def master_pattern = ".*master"

    if (branch_name =~ dev_pattern) {
        return "dev"
    } else if (branch_name =~ release_pattern) {
        return "release"
    } else if (branch_name =~ master_pattern) {
        return "master"
    } else if (branch_name =~ feature_pattern) {
        return "feature"
    } else if (branch_name =~ hotfix_pattern) {
        return "hotfix"
    } else {
        return null;
    }
}

def get_branch_deployment_environment(String branch_type) {

    if (branch_type == "dev") {
        return "dev"
    } else if (branch_type == "release") {
        return "syt"
    } else if (branch_type == "master") {
        return "prod"
    } else {
        return null;
    }
}

def mvn(String goals) {
    def mvnHome = tool "Maven-3.3.9"
    def javaHome = tool "JDK1.8.0_102"

    withEnv(["JAVA_HOME=${javaHome}", "PATH+MAVEN=${mvnHome}/bin"]) {
        sh "mvn -B ${goals}"
    }
}

def version() {
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    return matcher ? matcher[0][1] : null
}

def executeTests(String branch_deployment_environment) {
    //TODO: Pass tests!
}

def deployArtifact(String branch_deployment_environment) {

    withCredentials([usernamePassword(credentialsId: 'jenkinsDcos_' + branch_deployment_environment, usernameVariable: 'USER_ID', passwordVariable: 'USER_PASSWORD'),
                     string(credentialsId: 'dcosLoginUrl_' + branch_deployment_environment, variable: 'DCOS_LOGIN_URL'),
                     string(credentialsId: 'marathonApiUrl_' + branch_deployment_environment, variable: 'MARATHON_API_URL')]) {

        sh "echo Deploying to ${branch_deployment_environment}"
        sh "/opt/dcos_deploy.sh"
    }
}
