BUILD_INFRASTRUCTURE = 'scm_terraform'
PROJECT_NAME = 'scm'
SCM_GIT_URL = 'https://github.com/nawinkumar94/Terraform-Aws-Two-Tier-Application.git'
BRANCH_NAME = 'main'
TERRAFORM_VERSION = '0.13.3'
REGION = 'ap-south-1'
CREDENTIALS_ID =
OPERATION = 'OPERATION

pipelineJob(BUILD_INFRASTRUCTURE){
    displayName(PROJECT_NAME)
    description('Deploys the Terraform configurations')
    logRotator{
        daysToKeep(30)
        numToKeep(10)
    }
    parameters{
        stringParam('BRANCH_NAME', BRANCH_NAME, 'SCM Branch Details')
        stringParam('TERRAFORM_VERSION', TERRAFORM_VERSION, 'Version of Terraform to Deploy')
        stringParam('SCM_GIT_URL', SCM_GIT_URL, 'Terraform Project Infrastructure')
        choiceParam('OPERATION', ['apply', 'destroy'])
        stringParam('REGION', defaultValue = REGION)
        stringParam('BUCKET_NAME', BUCKET_NAME)
        credentialsParam('CREDENTIALS_ID'){
            defaultValue('CREDENTIALS_ID')
        }
    }


}