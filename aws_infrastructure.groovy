BUILD_INFRASTRUCTURE = 'terraform'
PROJECT_NAME = 'InfraWithTerraform'
GIT_URL = 'https://github.com/DivakarRaju/Deploy_Two_Tier_With_Terraform_Ansible.git'
BRANCH_NAME = 'main'
TERRAFORM_VERSION = '0.13.3'
CREDENTIALS_ID =
OPERATION = 'OPERATION'

pipelineJob(BUILD_INFRASTRUCTURE) {
    displayName(PROJECT_NAME)
    description('Deploys the Terraform configurations')
    logRotator {
        daysToKeep(30)
        numToKeep(10)
    }
    parameters {
        stringParam('BRANCH_NAME', BRANCH_NAME, 'SCM Branch Details')
        stringParam('TERRAFORM_VERSION', TERRAFORM_VERSION, 'Version of Terraform to Deploy')
        stringParam('GIT_URL', GIT_URL, 'Terraform Project Infrastructure')
        choiceParam('OPERATION', ['apply', 'destroy'])
        credentialsParam('CREDENTIALS_ID') {
            defaultValue('CREDENTIALS_ID')
        }
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        credentials(CREDENTIALS_ID)
                        url(GIT_PATH)
                    }
                    branch('*/BRANCH_NAME')
                }
                scriptPath(SCRIPT_PATH)
                lightweight()
            }
        }
    }
    properties {
        rebuild {
            autoRebuild(false)
            rebuildDisabled(false)
        }
    }
}
