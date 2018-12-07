pipeline {
    agent {
      label "jenkins-maven"
    }
    environment {
      ORG               = 'activiti'
      APP_NAME          = 'activiti-cloud-acceptance-tests'
      CHARTMUSEUM_CREDS = credentials('jenkins-x-chartmuseum')
      GITHUB_CHARTS_REPO    = "https://github.com/almerico/helmrepo.git"
      GITHUB_HELM_REPO_URL = "https://almerico.github.io/helmrepo"
      GATEWAY_HOST = "activiti-cloud-gateway.jx-staging.35.228.195.195.nip.io"
      SSO_HOST = "activiti-keycloak.jx-staging.35.228.195.195.nip.io"
      REALM = "springboot"
    }
    stages {
      stage('CI Build and push snapshot') {
        when {
          branch 'PR-*'
        }
        environment {
          PREVIEW_VERSION = "0.0.0-SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER"
          PREVIEW_NAMESPACE = "$APP_NAME-$BRANCH_NAME".toLowerCase()
          HELM_RELEASE = "$PREVIEW_NAMESPACE".toLowerCase()
        }
        steps {
          container('maven') {
            // sh "mvn versions:set -DnewVersion=$PREVIEW_VERSION"
            // sh "mvn install -DskipTests"
            // sh 'export VERSION=$PREVIEW_VERSION && skaffold build -f skaffold.yaml'
            sh "mvn clean install -DskipTests && mvn clean verify"


//           skip building docker image for now
   //        sh "jx step post build --image $DOCKER_REGISTRY/$ORG/$APP_NAME:$PREVIEW_VERSION"


             dir(".charts/$APP_NAME") {
               sh "make build"
             }
          }
        }
      }
      stage('Build Release') {
        when {
          branch 'develop'
        }
        steps {
          container('maven') {
            // ensure we're not on a detached head
            sh "git checkout develop"
            sh "git config --global credential.helper store"

            sh "jx step git credentials"
            // so we can retrieve the version in later steps
            sh "echo \$(jx-release-version) > VERSION"
            sh "mvn versions:set -DnewVersion=\$(cat VERSION)"

            dir ("./charts/$APP_NAME") {
              sh "make tag"
            }
            //sh 'mvn clean deploy'
            sh "mvn clean install -DskipTests && mvn clean verify"
            //sh 'export VERSION=`cat VERSION` && skaffold build -f skaffold.yaml'

            //sh "jx step post build --image $DOCKER_REGISTRY/$ORG/$APP_NAME:\$(cat VERSION)"
          }
        }
      }
      stage('Promote to Environments') {
        when {
          branch 'develop'
        }
        steps {
          container('maven') {
            dir ("./charts/$APP_NAME") {
              sh 'jx step changelog --version v\$(cat ../../VERSION)'

              // release the helm chart
              sh 'make release'
              sh 'make github'
              // promote through all 'Auto' promotion Environments
//            sh 'jx promote -b --all-auto --timeout 1h --version \$(cat ../../VERSION) --no-wait'
               sh 'jx step git credentials'
               sh 'jx promote -b --all-auto --helm-repo-url=$GITHUB_HELM_REPO_URL --timeout 1h --version \$(cat ../../VERSION) --no-wait'

            }
          }
        }
      }
    }  
    
    post {
        always {
            cleanWs()
        }
    }
}
