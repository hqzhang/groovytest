@Library('my-shared-library') _
//library("my-shared-library") _
//YOU SHOULD OPEN CONFIG and DO SAVE 
properties([ 
    parameters([
           choice(name: 'ENVIRONMENT', choices: ['qa1','qa2','qa3'], description: 'input cluster'),         
         
           [$class: 'CascadeChoiceParameter', choiceType: 'PT_RADIO',
            description: 'Suggested branch', 
            name: 'GIT_BRANCH2', randomName: 'choice-parameter-7601237141171',
            referencedParameters: 'ENVIRONMENT', 
            script: [$class: 'GroovyScript', 
            fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], 
            script: [classpath: [], sandbox: false, 
            script: '''if(ENVIRONMENT.equals("qa1")){ return ["gpi_master"] }
                   else if(ENVIRONMENT.equals("qa2")){ return ["ssr_master"] }
                   else if(ENVIRONMENT.equals("qa3")){ return ["lynx_master"] }
                   else { return ["Unknown state"] } 
                   ''']]]
    ])
])

pipeline{
  agent any

 stages{
     stage('init'){
         steps{
           script{
              echo "param: ${Utils.param}"
              Utils.hello()
         }}}
       } 
}


