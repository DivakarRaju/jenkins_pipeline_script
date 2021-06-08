pipelineJob(BUILD_INFRASTRUCTURE){
    displayName(PROJECT_NAME)
    description('Deploys the Terraform configurations')
    logRotator{
        daysToKeep(30)
        numToKeep(10)
    }
    
}