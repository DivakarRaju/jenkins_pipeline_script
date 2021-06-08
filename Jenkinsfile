node{
    def workspace_dir = 'webapp'
    def operation = "${OPERATION}"
    stage("Git checkout"){
        checkout(
            [
                $class: 'Git',
                branches: [[name: "${BRANCH_NAME}"]],
                extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: workspace_dir]],
                userRemoteConfigs: [[url: "${GIT_URL}", credentialsId: "${CREDENTIALS_ID}"]]
            ]
        )
        println("Git Checkout Success")
    }

    stage('Download Terraform'){
        sh ('''
            #!/bin/bash
            if [! -d ${JENKINS_HOME}/project/terraform]; then
                echo $PWD
                echo ${JENKINS_HOME}
                mkdir -p ${JENKINS_HOME}/project/terraform
                cd ${JENKINS_HOME}/project/terraform
            else
                echo "Terraform directory created"
            fi
        ''')
    }

    stage('Install Terraform'){
        dir(workspace_dir) {
            sh ('''
                #!/bin/bash
                curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo apt-key add -
                sudo apt-add-repository "deb [arch=amd64] https://apt.releases.hashicorp.com $(lsb_release -cs) main"
                sudo apt-get update && sudo apt-get install terraform
                terrafom -v
            ''')
        }
    }

    stage('Initialize and Validate Terraform'){
        dir(workspace_dir){
        println("intializing terraform execution")
        ansiColor('xterm') {
        sh ('''
            echo $PWD
            terraform init"
        ''')
        }
        println("Validating terraform code")
            try{
            ansiColor('xterm') {
                    sh ('''
                        echo $PWD
                        terraform validate
                        echo "Validation Successful"
                    ''')
            }

            }
            catch(err){
                println("Template Validation Failed")
                println(err)
            }
        }
    }

    stage('Terraform Plan'){
        dir(workspace_dir) {
            if (operation == 'apply') {
                println("Generating Terraform Plan for Apply")
                ansiColor('xterm') {
                    sh ('''
                        terraform plan -out=plan.out
                        echo "Plan success"
                    ''')
                }
            }
            if (operation == 'destroy') {
                println("Generating Terraform Plan for Destroy")
                ansiColor('xterm') {
                    sh ('''
                        terraform plan -destroy -out=plan.out
                        ''')
                }
            }
        }
    }

    stage('Deploy Terraform'){
        try{
            dir(workspace_dir){
                if (operation == 'apply') {
                    ansiColor('xterm') {
                        sh ('''
                            terraform apply -auto-approve=true plan.out
                            echo "Deployment Applied Successfully"
                        ''')
                        slack_notify()
                    }
                }
                else if (operation == 'destroy'){
                    ansiColor('xterm') {
                        sh ('''
                            terraform destroy -auto-approve=true
                            echo "Deployment Destroy Success"
                        ''')
                        slack_notify()
                    }
                }
            }
        }
        catch(err) {
            println("Terraform Deployment Failed")
              println(err)
        }
        finally {
            deleteDir()
        }
    }

    def slack_notify(){
        println("Slack Notification")
        def attachments = [
            [
                title: 'Notification for Terraform Project',
                text: 'Result of Deployment',
                color: '#2eb886'
            ]
        ]
        slackSend(channel: "#devops", attachments: attachments)
    }

}
